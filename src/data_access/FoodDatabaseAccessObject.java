package data_access;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;

public class FoodDatabaseAccessObject {
    private final String APP_ID = "f4d052b7";
    private final String APP_KEY = "46cfeab6f40ed5e084106e0870f61131%09";
    private final String BASE_URL = "https://api.edamam.com/api/food-database/v2";
    private final HttpClient httpClient;

    public FoodDatabaseAccessObject() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public JSONObject searchFood() throws Exception {
        String endpoint = String.format("%s/parser?app_id=%s&app_key=%s&nutrition-type=logging",
                BASE_URL, APP_ID, APP_KEY);

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
    }

    public JSONObject searchFoodWithIngredient(String ingredient) throws Exception {
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
    }

//    parse food items from response
//    public void printFoodItems(JSONObject response) {
//        JSONArray hints = response.getJSONArray("hints");
//        for (int i = 0; i < hints.length(); i++) {
//            JSONObject food = hints.getJSONObject(i).getJSONObject("food");
//            System.out.println("Label: " + food.getString("label"));
//            System.out.println("Category: " + food.optString("category", "N/A"));
//
//            JSONObject nutrients = food.getJSONObject("nutrients");
//            System.out.println("Nutrients:");
//            System.out.println("  - ENERC_KCAL: " + nutrients.optDouble("ENERC_KCAL", 0.0));
//            System.out.println("  - PROCNT: " + nutrients.optDouble("PROCNT", 0.0));
//            System.out.println("  - FAT: " + nutrients.optDouble("FAT", 0.0));
//            System.out.println("  - CHOCDF: " + nutrients.optDouble("CHOCDF", 0.0));
//            System.out.println("--------------------");
//        }
//    }
}