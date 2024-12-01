package use_case.customize.search_food;

import entity.Food;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchFoodOutputData {
    private final Food[] foods;
    private static final int LENGTHOFFOODS = 3;

    public SearchFoodOutputData(JSONObject jsonObject) {
        this.foods = new Food[LENGTHOFFOODS];
        JSONArray hints = jsonObject.getJSONArray("hints");
        int length = hints.length();
        for (int i = 0; i < Math.min(length, LENGTHOFFOODS); i++) {
            JSONObject obj = hints.getJSONObject(i);
            JSONObject fff = obj.getJSONObject("food");
            // chuli
            JSONObject nutrition = fff.getJSONObject("nutrients");
            Map<String, Double> nutrientsMap = new HashMap<>();
            for (String key : nutrition.keySet()) {
                nutrientsMap.put(key, nutrition.getDouble(key));
            }

            foods[i] = new Food(fff.getString("foodId"), fff.getString("label"),
                    fff.getString("category"), nutrientsMap);
        }
    }

    public Food[] getFoods() {
        return foods;
    }
}
