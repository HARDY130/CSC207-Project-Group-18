package data_access;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class FoodDatabaseAccessObject {
    private final String APP_ID = "f4d052b7";
    private final String APP_KEY = "46cfeab6f40ed5e084106e0870f61131%09";
    private final String BASE_URL = "https://api.edamam.com/api/recipes/v2";
    private final HttpClient httpClient;

    public FoodDatabaseAccessObject() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // General recipe search (the first one)
    public JSONObject searchRecipes(String query) throws Exception {
        String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
        String endpoint = String.format("%s?app_id=%s&app_key=%s&type=public&q=%s",
                BASE_URL, APP_ID, APP_KEY, encodedQuery);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Recipe search failed with status: " + response.statusCode());
        }

        return new JSONObject(response.body());
    }

    // Get specific recipe by ID
    public JSONObject getRecipeById(String recipeId) throws Exception {
        String endpoint = String.format("%s/%s?app_id=%s&app_key=%s&type=public",
                BASE_URL, recipeId, APP_ID, APP_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Recipe lookup failed with status: " + response.statusCode());
        }

        return new JSONObject(response.body());
    }

    // Lookup recipes by URIs
    public JSONObject getRecipesByUris(List<String> uris) throws Exception {
        String encodedUris = java.net.URLEncoder.encode(String.join(",", uris), "UTF-8");
        String endpoint = String.format("%s/by-uri?app_id=%s&app_key=%s&type=public&uri=%s",
                BASE_URL, APP_ID, APP_KEY, encodedUris);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Recipe URI lookup failed with status: " + response.statusCode());
        }

        return new JSONObject(response.body());
    }

    // Recipe details
    public void printRecipeDetails(JSONObject recipe) {
        System.out.println("Label: " + recipe.getString("label"));
        System.out.println("URI: " + recipe.getString("uri"));
        System.out.println("Image: " + recipe.optString("image", "No image available"));

        // Print ingredients
        JSONArray ingredients = recipe.getJSONArray("ingredients");
        System.out.println("\nIngredients:");
        for (int i = 0; i < ingredients.length(); i++) {
            JSONObject ingredient = ingredients.getJSONObject(i);
            System.out.println("- " + ingredient.getString("text"));
        }

        // Print nutrition info
        JSONObject nutrients = recipe.getJSONObject("totalNutrients");
        System.out.println("\nNutrition Information:");
        if (nutrients.has("ENERC_KCAL")) {
            JSONObject calories = nutrients.getJSONObject("ENERC_KCAL");
            System.out.println("Calories: " + calories.getDouble("quantity") + " " + calories.getString("unit"));
        }
        if (nutrients.has("PROCNT")) {
            JSONObject protein = nutrients.getJSONObject("PROCNT");
            System.out.println("Protein: " + protein.getDouble("quantity") + " " + protein.getString("unit"));
        }
        System.out.println("--------------------");
    }
}