////package data_access;
////
////import entity.Allergy;
////import entity.Food;
////import entity.MealType;
////import entity.CommonUser;
////import org.json.JSONArray;
////import org.json.JSONObject;
////import java.net.URI;
////import java.net.http.HttpClient;
////import java.net.http.HttpRequest;
////import java.net.http.HttpResponse;
////import java.util.*;
////
////public class MealPlannerDataAccessObject {
////    private static final String APP_ID = "=db5fb0da";
////    private static final String APP_KEY = "4bdcc547ac16ae072b5c9207f264f7e1";
////    private static final String BASE_URL = "https://api.edamam.com/api/meal-planner/v1";
////    private final HttpClient httpClient;
////
////    // Valid diet labels from API
////    public static final Set<String> VALID_DIETS = Set.of(
////            "balanced", "high-protein", "high-fiber", "low-fat", "low-carb", "low-sodium",
////            "keto", "paleo", "mediterranean", "dash", "vegetarian", "vegan"
////    );
////
////    public MealPlannerDataAccessObject() {
////        this.httpClient = HttpClient.newHttpClient();
////    }
////
////    /**
////     * Generates a meal plan based on user preferences and requirements
////     * @param user Current user with stored preferences
////     * @param selectedDiets Diet preferences selected in UI
////     * @return Map of MealType to List of Food options
////     */
////    public Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets)
////            throws Exception {
////        JSONObject requestBody = new JSONObject();
////
////        // Add diet preferences
////        requestBody.put("diet", new JSONArray(selectedDiets));
////
////        // Add calories range based on user's TDEE
////        double tdee = user.calculateTDEE();
////        JSONObject calories = new JSONObject();
////        calories.put("min", tdee - 100);  // Allow small range around TDEE
////        calories.put("max", tdee + 100);
////        requestBody.put("calories", calories);
////
////        // Add user's allergies as health filters
////        JSONArray health = new JSONArray();
////        for (Allergy allergy : user.getAllergies()) {
////            health.put(allergy.getDisplayName());
////        }
////        requestBody.put("health", health);
////
////        // Set meals for full day
////        requestBody.put("meals_per_day", 3);
////        JSONArray slots = new JSONArray();
////        slots.put("breakfast");
////        slots.put("lunch");
////        slots.put("dinner");
////        requestBody.put("slots", slots);
////
////        String endpoint = String.format("%s/planner/plans?app_id=%s&app_key=%s",
////                BASE_URL, APP_ID, APP_KEY);
////
////        HttpRequest request = HttpRequest.newBuilder()
////                .uri(URI.create(endpoint))
////                .header("Content-Type", "application/json")
////                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
////                .build();
////
////        HttpResponse<String> response = httpClient.send(request,
////                HttpResponse.BodyHandlers.ofString());
////
////        if (response.statusCode() != 200) {
////            throw new Exception("Meal plan generation failed with status: " + response.statusCode());
////        }
////
////        return parseMealPlanResponse(new JSONObject(response.body()));
////    }
////
////    /**
////     * Parses API response into domain objects
////     */
////    private Map<MealType, List<Food>> parseMealPlanResponse(JSONObject response) {
////        Map<MealType, List<Food>> mealPlan = new EnumMap<>(MealType.class);
////        JSONArray meals = response.getJSONArray("meals");
////
////        for (int i = 0; i < meals.length(); i++) {
////            JSONObject meal = meals.getJSONObject(i);
////            MealType type = getMealTypeFromSlot(meal.getString("slot"));
////
////            List<Food> foods = new ArrayList<>();
////            JSONArray recipes = meal.getJSONArray("recipes");
////
////            for (int j = 0; j < recipes.length(); j++) {
////                JSONObject recipe = recipes.getJSONObject(j);
////
////                // Extract detailed nutrition information
////                JSONObject nutrients = recipe.getJSONObject("nutrients");
////                Map<String, Double> nutrientMap = new HashMap<>();
////
////                // Store essential macronutrients
////                nutrientMap.put("ENERC_KCAL", nutrients.optDouble("ENERC_KCAL", 0.0));
////                nutrientMap.put("PROCNT", nutrients.optDouble("PROCNT", 0.0)); // Protein
////                nutrientMap.put("FAT", nutrients.optDouble("FAT", 0.0));
////                nutrientMap.put("CHOCDF", nutrients.optDouble("CHOCDF", 0.0)); // Carbs
////                nutrientMap.put("FIBTG", nutrients.optDouble("FIBTG", 0.0)); // Fiber
////
////                Food food = new Food(
////                        recipe.getString("id"),
////                        recipe.getString("label"),
////                        recipe.optString("cuisineType", "General"), // Use cuisineType as category
////                        nutrientMap
////                );
////                foods.add(food);
////            }
////
////            mealPlan.put(type, foods);
////        }
////
////        return mealPlan;
////    }
////
////    private MealType getMealTypeFromSlot(String slot) {
////        return switch (slot.toLowerCase()) {
////            case "breakfast" -> MealType.BREAKFAST;
////            case "lunch" -> MealType.LUNCH;
////            case "dinner" -> MealType.DINNER;
////            default -> throw new IllegalArgumentException("Unknown meal slot: " + slot);
////        };
////    }
////
////    /**
////     * Validates if a diet preference is supported by the API
////     */
////    public boolean isValidDiet(String diet) {
////        return VALID_DIETS.contains(diet.toLowerCase());
////    }
////}
//
//
package data_access;

import entity.Allergy;
import entity.Food;
import entity.MealType;
import entity.CommonUser;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.mealplanner.MealPlannerDataAccessInterface;
import use_case.mealplanner.MealStorageDataAccessInterface;

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

    // Calorie distribution ratios
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

    public Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets)
            throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("size", 7);

        JSONObject plan = new JSONObject();

        // Main accept section for overall diet and health constraints
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

        // Diet labels
        JSONObject dietObj = new JSONObject();
        JSONArray dietLabels = new JSONArray();
        for (String diet : selectedDiets) {
            if (!diet.equals("MEDITERRANEAN")) {
                dietLabels.put("HIGH_PROTEIN"); // Using underscore format
            }
        }
        dietObj.put("diet", dietLabels);
        mainAll.put(dietObj);

        mainAccept.put("all", mainAll);
        plan.put("accept", mainAccept);

        // Fit object
        JSONObject mainFit = new JSONObject();
        JSONObject mainCalories = new JSONObject();
        mainCalories.put("min", 2424);
        mainCalories.put("max", 2624);
        mainFit.put("ENERC_KCAL", mainCalories);
        plan.put("fit", mainFit);

        // Sections
        JSONObject sections = new JSONObject();

        // Breakfast section
        sections.put("Breakfast", createMealSection(
                "breakfast",
                242.4,
                787.2
        ));

        // Lunch section
        sections.put("Lunch", createMealSection(
                "lunch/dinner",
                727.2,
                1180.8
        ));

        // Dinner section
        sections.put("Dinner", createMealSection(
                "lunch/dinner",
                484.8,
                1180.8
        ));

        plan.put("sections", sections);
        requestBody.put("plan", plan);

        String endpoint = String.format("%s/%s/select?beta=true", BASE_URL, APP_ID);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Edamam-Account-User", ACCOUNT_USER)
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((APP_ID + ":" + APP_KEY).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        System.out.println("Request URL: " + endpoint);
        System.out.println("Request Body: " + requestBody.toString(2));

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Meal plan generation failed with status: " + response.statusCode()
                    + "\nResponse: " + response.body());
        }

        return parseMealPlanResponse(new JSONObject(response.body()));
    }

    private JSONObject createMealSection(String mealType, double minCalories, double maxCalories) {
        JSONObject section = new JSONObject();

        // Accept
        JSONObject accept = new JSONObject();
        JSONArray all = new JSONArray();
        JSONObject mealObj = new JSONObject();
        JSONArray mealTypes = new JSONArray();
        mealTypes.put(mealType);
        mealObj.put("meal", mealTypes);
        all.put(mealObj);
        accept.put("all", all);
        section.put("accept", accept);

        // Fit
        JSONObject fit = new JSONObject();
        JSONObject calories = new JSONObject();
        calories.put("min", minCalories);
        calories.put("max", maxCalories);
        fit.put("ENERC_KCAL", calories);
        section.put("fit", fit);

        return section;
    }

//    private Map<MealType, List<Food>> parseMealPlanResponse(JSONObject response) {
//        Map<MealType, List<Food>> mealPlan = new EnumMap<>(MealType.class);
//        JSONArray selections = response.getJSONArray("selection");
//
//        if (selections.length() > 0) {
//            JSONObject selection = selections.getJSONObject(0);
//            JSONObject sections = selection.getJSONObject("sections");
//
//            for (MealType mealType : MealType.values()) {
//                String sectionName = mealType.name().charAt(0) + mealType.name().substring(1).toLowerCase();
//                if (sections.has(sectionName)) {
//                    JSONObject mealSection = sections.getJSONObject(sectionName);
//                    String recipeUrl = mealSection.getJSONObject("_links")
//                            .getJSONObject("self")
//                            .getString("href");
//                    try {
//                        List<Food> foods = fetchRecipeDetails(recipeUrl);
//                        mealPlan.put(mealType, foods);
//                    } catch (Exception e) {
//                        System.err.println("Error fetching recipe details: " + e.getMessage());
//                    }
//                }
//            }
//        }
//
//        return mealPlan;
//    }
//
//    private List<Food> fetchRecipeDetails(String url) throws Exception {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((APP_ID + ":" + APP_KEY).getBytes()))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//        if (response.statusCode() == 200) {
//            JSONObject recipe = new JSONObject(response.body());
//            Food food = parseRecipeToFood(recipe);
//            return Collections.singletonList(food);
//        }
//
//        return Collections.emptyList();
//    }
//
//    private Food parseRecipeToFood(JSONObject recipe) {
//        String id = recipe.getString("uri");
//        String label = recipe.getString("label");
//        String category = recipe.optString("cuisineType", "General");
//
//        JSONObject nutrients = recipe.getJSONObject("totalNutrients");
//        Map<String, Double> nutrientMap = new HashMap<>();
//
//        extractNutrient(nutrients, nutrientMap, "ENERC_KCAL");
//        extractNutrient(nutrients, nutrientMap, "PROCNT");
//        extractNutrient(nutrients, nutrientMap, "FAT");
//        extractNutrient(nutrients, nutrientMap, "CHOCDF");
//
//        return new Food(id, label, category, nutrientMap);
//    }
//
//    private void extractNutrient(JSONObject nutrients, Map<String, Double> nutrientMap, String key) {
//        if (nutrients.has(key)) {
//            JSONObject nutrient = nutrients.getJSONObject(key);
//            nutrientMap.put(key, nutrient.getDouble("quantity"));
//        }
//    }

    private Map<MealType, List<Food>> parseMealPlanResponse(JSONObject response) {
        Map<MealType, List<Food>> mealPlan = new EnumMap<>(MealType.class);

        System.out.println("\n=== Parsing API Response ===");

        if (!response.has("selection")) {
            System.out.println("No 'selection' field found in response");
            return mealPlan;
        }

        JSONArray selections = response.getJSONArray("selection");
        System.out.println("Number of selections: " + selections.length());

        if (selections.length() > 0) {
            JSONObject selection = selections.getJSONObject(0);

            if (!selection.has("sections")) {
                System.out.println("No 'sections' field found in selection");
                return mealPlan;
            }

            JSONObject sections = selection.getJSONObject("sections");
            System.out.println("\nFound sections: " + sections.keySet());

            for (MealType mealType : MealType.values()) {
                String sectionName = mealType.name().charAt(0) + mealType.name().substring(1).toLowerCase();

                if (sections.has(sectionName)) {
                    JSONObject mealSection = sections.getJSONObject(sectionName);
                    System.out.println("\nProcessing " + sectionName + ":");

                    if (mealSection.has("_links") &&
                            mealSection.getJSONObject("_links").has("self")) {

                        String recipeUrl = mealSection.getJSONObject("_links")
                                .getJSONObject("self")
                                .getString("href");
                        System.out.println("Found recipe URL: " + recipeUrl);

                        try {
                            List<Food> foods = fetchRecipeDetails(recipeUrl);
                            if (!foods.isEmpty()) {
                                mealPlan.put(mealType, foods);
                                System.out.println("Successfully fetched recipe details");
                            } else {
                                System.out.println("No recipe details returned");
                            }
                        } catch (Exception e) {
                            System.err.println("Error fetching recipe details: " + e.getMessage());
                        }
                    } else {
                        System.out.println("No recipe link found in section");
                    }
                } else {
                    System.out.println("\n" + sectionName + " section not found");
                }
            }
        }

        return mealPlan;
    }

    private List<Food> fetchRecipeDetails(String url) throws Exception {
        // Extract recipe ID from the URL
        String recipeId = url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];

        System.out.println("\nFetching details for recipe ID: " + recipeId);

        // Construct URL with proper parameters
        String fullUrl = String.format("https://api.edamam.com/api/recipes/v2/%s" +
                        "?type=public" +
                        "&beta=true" +
                        "&app_id=%s" +
                        "&app_key=%s" +
//                        "Edamam-Account-User=%s" +
                        "&field=uri" +
                        "&field=label" +
                        "&field=image" +
                        "&field=calories" +
                        "&field=totalWeight" +
                        "&field=cuisineType" +
                        "&field=mealType" +
                        "&field=totalNutrients",
                recipeId, RS_ID, RS_KEY);

        System.out.println("Making request to: " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Recipe API Response Status: " + response.statusCode());
        System.out.println("Recipe API Response Body: " + response.body());

        if (response.statusCode() == 200) {
            JSONObject recipe = new JSONObject(response.body());
            if (recipe.has("recipe")) {  // API returns recipe details in a nested "recipe" object
                recipe = recipe.getJSONObject("recipe");
            }
            Food food = parseRecipeToFood(recipe);
            System.out.println("Successfully parsed recipe: " + food.getLabel());
            return Collections.singletonList(food);
        } else {
            System.out.println("Failed to fetch recipe details");
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

    private String extractRecipeId(String url) {
        // Example URL: https://api.edamam.com/api/recipes/v2/7cb625ad75db461fa9b0ca5c2f906231?type=public
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart.split("\\?")[0];  // Get the ID part before any query parameters
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

            return switch(mealType.toUpperCase()) {
                case "BREAKFAST" -> fullPlan.get(MealType.BREAKFAST);
                case "LUNCH" -> fullPlan.get(MealType.LUNCH);
                case "DINNER" -> fullPlan.get(MealType.DINNER);
                default -> new ArrayList<>();
            };
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

//    private Food parseRecipeToFood(JSONObject recipe) {
//        String id = recipe.getString("uri");
//        String label = recipe.getString("label");
//        String category = recipe.optString("cuisineType", "General");
//
//        Map<String, Double> nutrientMap = new HashMap<>();
//
//        if (recipe.has("totalNutrients")) {
//            JSONObject nutrients = recipe.getJSONObject("totalNutrients");
//            System.out.println("\nExtracting nutrients for: " + label);
//
//            extractNutrient(nutrients, nutrientMap, "ENERC_KCAL", "Calories");
//            extractNutrient(nutrients, nutrientMap, "PROCNT", "Protein");
//            extractNutrient(nutrients, nutrientMap, "FAT", "Fat");
//            extractNutrient(nutrients, nutrientMap, "CHOCDF", "Carbs");
//        } else {
//            System.out.println("No nutrient information found in recipe");
//        }
//
//        return new Food(id, label, category, nutrientMap);
//    }

    private void extractNutrient(JSONObject nutrients, Map<String, Double> nutrientMap,
                                 String key, String label) {
        if (nutrients.has(key)) {
            JSONObject nutrient = nutrients.getJSONObject(key);
            double value = nutrient.getDouble("quantity");
            nutrientMap.put(key, value);
            System.out.printf("%s: %.2f %s%n",
                    label,
                    value,
                    nutrient.getString("unit"));
        } else {
            System.out.println(label + " information not found");
        }
    }

    @Override
    public boolean existsByName(String username) {
        return userDataAccess.existsByName(username);
    }
}

// MealPlannerDataAccessObject.java
//package data_access;
//
//import entity.Food;
//import use_case.mealplanner.MealPlannerDataAccessInterface;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import org.json.JSONObject;
//import org.json.JSONArray;
//import java.util.*;
//
//public class MealPlannerDataAccessObject implements MealPlannerDataAccessInterface {
//    private final String APP_ID = "f4d052b7";
//    private final String APP_KEY = "46cfeab6f40ed5e084106e0870f61131%09";
//    private final String BASE_URL = "https://api.edamam.com/api/food-database/v2";
//    private final HttpClient httpClient;
//    private final Map<String, Map<String, List<Food>>> cachedResults;
//    private final Random random;
//
//    public MealPlannerDataAccessObject() {
//        this.httpClient = HttpClient.newHttpClient();
//        this.cachedResults = new HashMap<>();
//        this.random = new Random();
//    }
//
//    @Override
//    public List<Food> generateMealOptions(Set<String> dietaryPreferences, String mealType) {
//        try {
//            // Create cache key from preferences
//            String cacheKey = String.join("-", dietaryPreferences);
//            Map<String, List<Food>> mealTypeCache = cachedResults.computeIfAbsent(cacheKey, k -> new HashMap<>());
//
//            // Check cache first
//            if (mealTypeCache.containsKey(mealType)) {
//                List<Food> cached = mealTypeCache.get(mealType);
//                if (!cached.isEmpty()) {
//                    return selectRandomFoods(cached, 3);
//                }
//            }
//
//            // Build API query with preferences
//            String endpoint = String.format("%s/parser?app_id=%s&app_key=%s&nutrition-type=logging",
//                    BASE_URL, APP_ID, APP_KEY);
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(endpoint))
//                    .header("accept", "application/json")
//                    .GET()
//                    .build();
//
//            HttpResponse<String> response = httpClient.send(request,
//                    HttpResponse.BodyHandlers.ofString());
//
//            if (response.statusCode() != 200) {
//                throw new Exception("API request failed with status: " + response.statusCode());
//            }
//
//            // Parse response
//            JSONObject responseBody = new JSONObject(response.body());
//            List<Food> foods = parseFoodItems(responseBody);
//
//            // Cache results
//            mealTypeCache.put(mealType, foods);
//
//            // Return random selection
//            return selectRandomFoods(foods, 3);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//
//    private List<Food> parseFoodItems(JSONObject response) {
//        List<Food> foods = new ArrayList<>();
//        JSONArray hints = response.getJSONArray("hints");
//
//        for (int i = 0; i < hints.length(); i++) {
//            JSONObject foodObj = hints.getJSONObject(i).getJSONObject("food");
//
//            // Extract nutrients
//            Map<String, Double> nutrients = new HashMap<>();
//            JSONObject nutrientsObj = foodObj.getJSONObject("nutrients");
//
//            // Extract standard nutrients
//            putNutrientIfExists(nutrientsObj, "ENERC_KCAL", nutrients);
//            putNutrientIfExists(nutrientsObj, "PROCNT", nutrients);
//            putNutrientIfExists(nutrientsObj, "FAT", nutrients);
//            putNutrientIfExists(nutrientsObj, "CHOCDF", nutrients);
//
//            Food food = new Food(
//                    foodObj.getString("foodId"),
//                    foodObj.getString("label"),
//                    foodObj.getString("category"),
//                    nutrients
//            );
//
//            foods.add(food);
//        }
//
//        return foods;
//    }
//
//    private void putNutrientIfExists(JSONObject nutrients, String key, Map<String, Double> nutrientMap) {
//        if (nutrients.has(key)) {
//            nutrientMap.put(key, nutrients.getDouble(key));
//        }
//    }
//
//    private List<Food> selectRandomFoods(List<Food> foods, int count) {
//        if (foods.size() <= count) {
//            return new ArrayList<>(foods);
//        }
//
//        List<Food> result = new ArrayList<>();
//        List<Food> copy = new ArrayList<>(foods);
//
//        for (int i = 0; i < count; i++) {
//            int index = random.nextInt(copy.size());
//            result.add(copy.remove(index));
//        }
//
//        return result;
//    }
//
//    @Override
//    public void addMealToUser(String username, String mealType, Food food) {
//        // This would typically be handled by a user service/database
//        // For now we'll just print what would be saved
//        System.out.println("Adding meal for user: " + username);
//        System.out.println("Meal type: " + mealType);
//        System.out.println("Food: " + food.getLabel());
//    }
//
//    @Override
//    public boolean existsByName(String username) {
//        // This would typically check a user database
//        // For now always return true
//        return true;
//    }
//}

//package data_access;
//
//import entity.Food;
//import use_case.mealplanner.MealPlannerDataAccessInterface;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import org.json.JSONObject;
//import org.json.JSONArray;
//import java.util.*;
//
//public class MealPlannerDataAccessObject implements MealPlannerDataAccessInterface {
//    private final String APP_ID = "f4d052b7";
//    private final String APP_KEY = "46cfeab6f40ed5e084106e0870f61131";
//    private final String BASE_URL = "https://api.edamam.com/api/food-database/v2";
//    private final HttpClient httpClient;
//    private final Random random;
//
//    // More diverse queries for each meal type
//    private final Map<String, List<String>> mealTypeQueries = new HashMap<>() {{
//        put("B", Arrays.asList(
//                "pancakes", "oatmeal", "eggs benedict", "waffles", "french toast",
//                "breakfast burrito", "yogurt parfait", "bagel sandwich"
//        ));
//        put("L", Arrays.asList(
//                "sandwich", "salad", "soup", "pasta", "wrap", "sushi",
//                "burger", "stir fry", "curry", "bowl"
//        ));
//        put("D", Arrays.asList(
//                "steak", "chicken", "fish", "pasta", "pizza", "curry",
//                "roasted vegetables", "rice bowl", "lasagna", "tacos"
//        ));
//    }};
//
//    public MealPlannerDataAccessObject() {
//        this.httpClient = HttpClient.newHttpClient();
//        this.random = new Random();
//    }
//
//    @Override
//    public List<Food> generateMealOptions(Set<String> dietaryPreferences, String mealType) {
//        try {
//            List<String> queries = mealTypeQueries.getOrDefault(mealType, Collections.singletonList("meal"));
//            List<Food> allFoods = new ArrayList<>();
//
//            // Randomly select multiple queries to get more variety
//            Set<String> selectedQueries = new HashSet<>();
//            for (int i = 0; i < 3 && i < queries.size(); i++) {
//                String query = queries.get(random.nextInt(queries.size()));
//                selectedQueries.add(query);
//            }
//
//            // Make API calls for each selected query
//            for (String query : selectedQueries) {
//                String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
//                String endpoint = String.format("%s/parser?app_id=%s&app_key=%s&ingr=%s",
//                        BASE_URL, APP_ID, APP_KEY, encodedQuery);
//
//                HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(endpoint))
//                        .header("accept", "application/json")
//                        .GET()
//                        .build();
//
//                HttpResponse<String> response = httpClient.send(request,
//                        HttpResponse.BodyHandlers.ofString());
//
//                if (response.statusCode() == 200) {
//                    JSONObject responseBody = new JSONObject(response.body());
//                    allFoods.addAll(parseFoodItems(responseBody));
//                }
//            }
//
//            // Randomize and return subset of results
//            return selectRandomFoods(allFoods, 3);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//
//    private List<Food> parseFoodItems(JSONObject response) {
//        List<Food> foods = new ArrayList<>();
//        JSONArray hints = response.getJSONArray("hints");
//
//        for (int i = 0; i < hints.length(); i++) {
//            try {
//                JSONObject hint = hints.getJSONObject(i);
//                JSONObject foodObj = hint.getJSONObject("food");
//
//                Map<String, Double> nutrients = new HashMap<>();
//                if (foodObj.has("nutrients")) {
//                    JSONObject nutrientsObj = foodObj.getJSONObject("nutrients");
//
//                    nutrients.put("ENERC_KCAL", nutrientsObj.optDouble("ENERC_KCAL", 0.0));
//                    nutrients.put("PROCNT", nutrientsObj.optDouble("PROCNT", 0.0));
//                    nutrients.put("FAT", nutrientsObj.optDouble("FAT", 0.0));
//                    nutrients.put("CHOCDF", nutrientsObj.optDouble("CHOCDF", 0.0));
//                }
//
//                Food food = new Food(
//                        foodObj.optString("foodId", "unknown"),
//                        foodObj.getString("label"),
//                        foodObj.optString("category", "Mixed"),
//                        nutrients
//                );
//
//                foods.add(food);
//
//            } catch (Exception e) {
//                continue;
//            }
//        }
//
//        return foods;
//    }
//
//    private List<Food> selectRandomFoods(List<Food> foods, int count) {
//        if (foods.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        if (foods.size() <= count) {
//            return new ArrayList<>(foods);
//        }
//
//        List<Food> result = new ArrayList<>();
//        List<Food> copy = new ArrayList<>(foods);
//
//        while (result.size() < count && !copy.isEmpty()) {
//            int index = random.nextInt(copy.size());
//            Food selectedFood = copy.remove(index);
//            if (!result.contains(selectedFood)) {
//                result.add(selectedFood);
//            }
//        }
//
//        return result;
//    }
//
//    @Override
//    public void addMealToUser(String username, String mealType, Food food) {
//        System.out.println("Adding meal for user: " + username);
//        System.out.println("Meal type: " + mealType);
//        System.out.println("Food: " + food.getLabel());
//    }
//
//    @Override
//    public boolean existsByName(String username) {
//        return true;
//    }
//}