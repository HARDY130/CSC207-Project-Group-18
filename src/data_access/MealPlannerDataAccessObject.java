package data_access;

import entity.Allergy;
import entity.CommonUser;
import entity.Food;
import entity.MealType;

import org.json.JSONArray;
import org.json.JSONObject;
import use_case.meal_planner.MealPlannerDataAccessInterface;
import use_case.meal_planner.MealStorageDataAccessInterface;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class MealPlannerDataAccessObject implements MealPlannerDataAccessInterface {
    private static final String APP_ID = "db5fb0da";
    private static final String APP_KEY = "4bdcc547ac16ae072b5c9207f264f7e1";
    private static final String ACCOUNT_USER = "CHUXUANaaa";
    private static final String BASE_URL = "https://api.edamam.com/api/meal-planner/v1";

    private static final String RS_ID = "18c92ef1";
    private static final String RS_KEY = "f2e78bebf0400504c0710cbb8ac5719a";

    private static final double BREAKFAST_MIN_RATIO = 0.1;
    private static final double BREAKFAST_MAX_RATIO = 0.3;
    private static final double LUNCH_MIN_RATIO = 0.3;
    private static final double LUNCH_MAX_RATIO = 0.45;
    private static final double DINNER_MIN_RATIO = 0.2;
    private static final double DINNER_MAX_RATIO = 0.45;

    private final HttpClient httpClient;
    private final MealStorageDataAccessInterface userDataAccess;

    public MealPlannerDataAccessObject(MealStorageDataAccessInterface userDataAccess) {
        this.userDataAccess = userDataAccess;
        this.httpClient = HttpClient.newHttpClient();
    }

    private String getAllergyHealthLabel(Allergy allergy) {
        return switch (allergy) {
            case DAIRY -> "DAIRY_FREE";
            case PEANUT -> "PEANUT_FREE";
            case FISH -> "FISH_FREE";
            case GLUTEN -> "GLUTEN_FREE";
            case CELERY -> "CELERY_FREE";
            case CRUSTACEAN -> "CRUSTACEAN_FREE";
            case EGG -> "EGG_FREE";
            case LUPINE -> "LUPINE_FREE";
            case MUSTARD -> "MUSTARD_FREE";
            case SESAME -> "SESAME_FREE";
            case SHELLFISH -> "SHELLFISH_FREE";
            case SOY -> "SOY_FREE";
            case TREE_NUT -> "TREE_NUT_FREE";
            case WHEAT -> "WHEAT_FREE";
            case FODMAP -> "FODMAP_FREE";
            case IMMUNO_SUPPORTIVE -> "IMMUNO_SUPPORTIVE";
        };
    }

    @Override
    public Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("size", 7);

        JSONObject plan = new JSONObject();

        JSONObject mainAccept = new JSONObject();
        JSONArray mainAll = new JSONArray();

        // Health labels
        JSONObject healthObj = new JSONObject();
        JSONArray healthLabels = new JSONArray();
        healthLabels.put("MEDITERRANEAN");
        for (Allergy allergy : user.getAllergies()) {
            healthLabels.put(getAllergyHealthLabel(allergy));
        }
        healthObj.put("health", healthLabels);
        mainAll.put(healthObj);

        JSONObject dietObj = new JSONObject();
        JSONArray dietLabels = new JSONArray();
        for (String diet : selectedDiets) {
            if (!diet.equals("MEDITERRANEAN")) {
                dietLabels.put("HIGH_PROTEIN");
            }
        }
        dietObj.put("diet", dietLabels);
        mainAll.put(dietObj);

        mainAccept.put("all", mainAll);
        plan.put("accept", mainAccept);

        double tdee = user.calculateTDEE();
        JSONObject mainFit = new JSONObject();
        JSONObject mainCalories = new JSONObject();
        mainCalories.put("min", tdee - 100);
        mainCalories.put("max", tdee + 100);
        mainFit.put("ENERC_KCAL", mainCalories);
        plan.put("fit", mainFit);

        JSONObject sections = new JSONObject();

        // Breakfast section
        sections.put("Breakfast", createMealSection("breakfast", tdee * BREAKFAST_MIN_RATIO, tdee * BREAKFAST_MAX_RATIO));

        // Lunch section
        sections.put("Lunch", createMealSection("lunch/dinner", tdee * LUNCH_MIN_RATIO, tdee * LUNCH_MAX_RATIO));

        // Dinner section
        sections.put("Dinner", createMealSection("lunch/dinner", tdee * DINNER_MIN_RATIO, tdee * DINNER_MAX_RATIO));

        plan.put("sections", sections);
        requestBody.put("plan", plan);

        String endpoint = String.format("%s/%s/select?beta=true", BASE_URL, APP_ID);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endpoint)).header("Accept", "application/json").header("Content-Type", "application/json").header("Edamam-Account-User", ACCOUNT_USER).header("Authorization", "Basic " + Base64.getEncoder().encodeToString((APP_ID + ":" + APP_KEY).getBytes())).POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Meal plan generation failed with status: " + response.statusCode() + "\nResponse: " + response.body());
        }

        return parseMealPlanResponse(new JSONObject(response.body()));
    }

    private JSONObject createMealSection(String mealType, double minCalories, double maxCalories) {
        JSONObject section = new JSONObject();

        JSONObject accept = new JSONObject();
        JSONArray all = new JSONArray();
        JSONObject mealObj = new JSONObject();
        JSONArray mealTypes = new JSONArray();
        mealTypes.put(mealType);
        mealObj.put("meal", mealTypes);
        all.put(mealObj);
        accept.put("all", all);
        section.put("accept", accept);

        JSONObject fit = new JSONObject();
        JSONObject calories = new JSONObject();
        calories.put("min", minCalories);
        calories.put("max", maxCalories);
        fit.put("ENERC_KCAL", calories);
        section.put("fit", fit);

        return section;
    }

    private Map<MealType, List<Food>> parseMealPlanResponse(JSONObject response) {
        Map<MealType, List<Food>> mealPlan = new EnumMap<>(MealType.class);

        if (!response.has("selection")) {
            return mealPlan;
        }

        JSONArray selections = response.getJSONArray("selection");

        if (selections.length() > 0) {
            JSONObject selection = selections.getJSONObject(0);

            if (!selection.has("sections")) {
                return mealPlan;
            }

            JSONObject sections = selection.getJSONObject("sections");

            for (MealType mealType : MealType.values()) {
                String sectionName = mealType.name().charAt(0) + mealType.name().substring(1).toLowerCase();

                if (sections.has(sectionName)) {
                    JSONObject mealSection = sections.getJSONObject(sectionName);

                    if (mealSection.has("_links") && mealSection.getJSONObject("_links").has("self")) {

                        String recipeUrl = mealSection.getJSONObject("_links").getJSONObject("self").getString("href");

                        try {
                            List<Food> foods = fetchRecipeDetails(recipeUrl);
                            if (!foods.isEmpty()) {
                                mealPlan.put(mealType, foods);
                            }
                        } catch (Exception e) {
                            System.err.println("Error fetching recipe details: " + e.getMessage());
                        }
                    }
                }
            }
        }

        return mealPlan;
    }

    private List<Food> fetchRecipeDetails(String url) throws Exception {
        String recipeId = url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];

        String fullUrl = String.format("https://api.edamam.com/api/recipes/v2/%s" + "?type=public" + "&beta=true" + "&app_id=%s" + "&app_key=%s" + "&field=uri" + "&field=label" + "&field=image" + "&field=calories" + "&field=totalWeight" + "&field=cuisineType" + "&field=mealType" + "&field=totalNutrients", recipeId, RS_ID, RS_KEY);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fullUrl)).header("Accept", "application/json").GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject recipe = new JSONObject(response.body());
            if (recipe.has("recipe")) {
                recipe = recipe.getJSONObject("recipe");
            }
            Food food = parseRecipeToFood(recipe);
            return Collections.singletonList(food);
        }

        return Collections.emptyList();
    }

    private Food parseRecipeToFood(JSONObject recipe) {
        String id = recipe.getString("uri");
        String label = recipe.getString("label");
        String category = recipe.optString("cuisineType", "General");

        Map<String, Double> nutrientMap = new HashMap<>();

        if (recipe.has("totalNutrients")) {
            JSONObject nutrients = recipe.getJSONObject("totalNutrients");

            if (nutrients.has("ENERC_KCAL")) {
                JSONObject calories = nutrients.getJSONObject("ENERC_KCAL");
                nutrientMap.put("ENERC_KCAL", calories.getDouble("quantity"));
            }
            if (nutrients.has("PROCNT")) {
                JSONObject protein = nutrients.getJSONObject("PROCNT");
                nutrientMap.put("PROCNT", protein.getDouble("quantity"));
            }
            if (nutrients.has("FAT")) {
                JSONObject fat = nutrients.getJSONObject("FAT");
                nutrientMap.put("FAT", fat.getDouble("quantity"));
            }
            if (nutrients.has("CHOCDF")) {
                JSONObject carbs = nutrients.getJSONObject("CHOCDF");
                nutrientMap.put("CHOCDF", carbs.getDouble("quantity"));
            }
        }

        return new Food(id, label, category, nutrientMap);
    }

    @Override
    public List<Food> generateMealOptions(Set<String> dietaryPreferences, String mealType) {
        try {
            String username = userDataAccess.getCurrentUsername();
            if (username == null) {
                return new ArrayList<>();
            }

            CommonUser user = (CommonUser) userDataAccess.get(username);
            Map<MealType, List<Food>> fullPlan = generateMealPlan(user, new ArrayList<>(dietaryPreferences));

            return switch (mealType.toUpperCase()) {
                case "BREAKFAST" -> fullPlan.get(MealType.BREAKFAST);
                case "LUNCH" -> fullPlan.get(MealType.LUNCH);
                case "DINNER" -> fullPlan.get(MealType.DINNER);
                default -> new ArrayList<>();
            };
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean existsByName(String username) {
        return userDataAccess.existsByName(username);
    }
}
