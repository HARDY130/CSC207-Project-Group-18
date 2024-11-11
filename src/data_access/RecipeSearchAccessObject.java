package data_access;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;

public class RecipeSearchAccessObject {
    private final String APP_ID = "e4b60eb9";
    private final String APP_KEY = "fec8a143bf81dd902b7dfe918d81066c%09";
    private final String BASE_URL = "https://api.edamam.com/api/recipes/v2";
    private final HttpClient httpClient;

    public RecipeSearchAccessObject() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public JSONObject searchRecipes(String query) throws Exception {
        String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
        String endpoint = String.format("%s?type=user&app_id=%s&app_key=%s&q=%s",
                BASE_URL, APP_ID, APP_KEY, encodedQuery);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("accept", "application/json")
                .header("Accept-Language", "en")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Recipe search failed with status: " + response.statusCode());
        }

        return new JSONObject(response.body());
    }

    // Used to print info about recipe
    public void printRecipeResults(JSONObject response) {
        try {
            JSONArray hits = response.getJSONArray("hits");
            System.out.println("Found " + hits.length() + " recipes:\n");

            for (int i = 0; i < hits.length(); i++) {
                JSONObject recipe = hits.getJSONObject(i).getJSONObject("recipe");
                System.out.println("Recipe #" + (i + 1));
                System.out.println("Title: " + recipe.getString("label"));

                System.out.println("Source: " + recipe.optString("source", "N/A"));
                System.out.println("URL: " + recipe.optString("url", "N/A"));

                JSONArray ingredients = recipe.getJSONArray("ingredients");
                System.out.println("Ingredients (" + ingredients.length() + "):");
                for (int j = 0; j < ingredients.length(); j++) {
                    JSONObject ingredient = ingredients.getJSONObject(j);
                    System.out.println("- " + ingredient.getString("text"));
                }

                if (recipe.has("totalNutrients")) {
                    JSONObject nutrients = recipe.getJSONObject("totalNutrients");
                    System.out.println("\nNutrition Information:");
                    if (nutrients.has("ENERC_KCAL")) {
                        JSONObject calories = nutrients.getJSONObject("ENERC_KCAL");
                        System.out.printf("Calories: %.1f %s%n",
                                calories.getDouble("quantity"),
                                calories.getString("unit"));
                    }
                }

                System.out.println("-------------------\n");
            }

            if (response.has("_links")) {
                JSONObject links = response.getJSONObject("_links");
                if (links.has("next")) {
                    System.out.println("More results available in next page");
                }
            }

        } catch (Exception e) {
            System.out.println("Error printing results: " + e.getMessage());
        }
    }
}