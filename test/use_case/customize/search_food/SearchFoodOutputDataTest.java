package use_case.customize.search_food;

import data_access.FoodDatabaseAccessObject;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Arrays;

public class SearchFoodOutputDataTest {

    @Test
    public void test_search_food_output_data() {
        FoodDatabaseAccessObject foodDatabaseAccessObject = new FoodDatabaseAccessObject();

        JSONObject obj = null;
        try {
            obj = foodDatabaseAccessObject.searchFoodWithIngredient("cheese");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SearchFoodOutputData searchFoodOutputData = new SearchFoodOutputData(obj);
        System.out.println(Arrays.toString(searchFoodOutputData.getFoods()));
    }
}
