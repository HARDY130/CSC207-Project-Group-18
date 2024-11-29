package data_access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.HttpURLConnection;

import okhttp3.*;

import org.json.JSONObject;
import use_case.mealPlanner.mealPlannerDataAccessInterface;

public class MealPlannerDataAccessObject implements  mealPlannerDataAccessInterface {

    private static final String BASE_URL = "https://api.edamam.com";
    private static final String APP_ID = "31bfc726";
    private static final String APP_KEY = "111f71ed74f18748d245f50ef005e1a7";

    public String createMealPlan(String jsonInputSring) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = BASE_URL + "/search?q=&app_id=" + APP_ID + "&app_key=" + APP_KEY + "&diet=" + diet + "&calories=" + calories + "&health=" + healthLabels;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return e.getMessage();
        }
    }


}
