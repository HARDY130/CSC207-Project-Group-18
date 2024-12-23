package data_access;

import entity.Food;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;

public class FoodDatabaseDataAccessObject {
    private final String APP_ID = "f4d052b7";
    private final String APP_KEY = "46cfeab6f40ed5e084106e0870f61131%09";
    private final String BASE_URL = "https://api.edamam.com/api/food-database/v2";
    private final HttpClient httpClient;

    public FoodDatabaseDataAccessObject() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Searches for foods and returns them as Food entities
     * @param ingredient The ingredient to search for
     * @return List of Food entities matching the search
     * @throws Exception if the API request fails
     */
    public List<Food> searchFoods(String ingredient){
        try {
            JSONObject response = searchFoodWithIngredient(ingredient);
            return convertJsonToFoodList(response);
        }catch (Exception e){
            System.out.println("Failed to search foods: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject searchFoodWithIngredient(String ingredient){
        try {
            String encodedIngredient = java.net.URLEncoder.encode(ingredient, "UTF-8");
            String endpoint = String.format("%s/parser?app_id=%s&app_key=%s&nutrition-type=logging&ingr=%s",
                    BASE_URL, APP_ID, APP_KEY, encodedIngredient);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("API request failed with status: " + response.statusCode());
            }

            return new JSONObject(response.body());
        }catch (Exception e){
            e.printStackTrace();
        }return null;
    }

    private List<Food> convertJsonToFoodList(JSONObject response) {
        List<Food> foods = new ArrayList<>();
        JSONArray hints = response.getJSONArray("hints");

        for (int i = 0; i < hints.length(); i++) {
            JSONObject foodData = hints.getJSONObject(i).getJSONObject("food");

            String foodId = foodData.getString("foodId");
            String label = foodData.getString("label");
            String category = foodData.optString("category", "Unknown");

            JSONObject nutrientsJson = foodData.getJSONObject("nutrients");
            Map<String, Double> nutrients = new HashMap<>();

            // Extract common nutrients
            String[] nutrientKeys = {
                    "ENERC_KCAL",  // Calories
                    "PROCNT",      // Protein
                    "FAT",         // Fat
                    "CHOCDF",      // Carbohydrates
                    "FIBTG"        // Fiber
            };

            for (String key : nutrientKeys) {
                if (nutrientsJson.has(key)) {
                    nutrients.put(key, nutrientsJson.getDouble(key));
                }
            }

            Food food = new Food(foodId, label, category, nutrients);
            foods.add(food);
        }

        return foods;
    }

    // Helper method for formatting nutrient values (if needed internally)
    private String formatNutrientValue(String nutrientKey, double value) {
        return switch (nutrientKey) {
            case "ENERC_KCAL" -> String.format("%.0f kcal", value);
            case "PROCNT" -> String.format("%.1f g protein", value);
            case "FAT" -> String.format("%.1f g fat", value);
            case "CHOCDF" -> String.format("%.1f g carbs", value);
            case "FIBTG" -> String.format("%.1f g fiber", value);
            default -> String.format("%.1f", value);
        };
    }
}