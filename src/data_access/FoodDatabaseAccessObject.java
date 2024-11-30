package data_access;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
}