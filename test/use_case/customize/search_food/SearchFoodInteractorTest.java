package use_case.customize.search_food;

import data_access.FoodDatabaseAccessObject;
import entity.Food;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchFoodInteractorTest {

    @Test
    void successTest() {
        SearchFoodInputData inputData = new SearchFoodInputData("Apple");

        FoodDatabaseAccessObject databaseAccessObject = new FoodDatabaseAccessObject() {
            @Override
            public JSONObject searchFoodWithIngredient(String ingredient) {
                JSONObject mockResponse = new JSONObject();
                JSONArray hintsArray = new JSONArray();

                JSONObject food1 = new JSONObject();
                food1.put("foodId", "food_a1gb9ubb72c7snbuxr3weagw0dd");
                food1.put("label", "Apple");
                food1.put("category", "Generic foods");
                food1.put("nutrients", new JSONObject()
                        .put("PROCNT", 0.26)
                        .put("ENERC_KCAL", 52.0)
                        .put("FAT", 0.17)
                        .put("CHOCD", 13.8)
                        .put("FIBTG", 2.4));
                hintsArray.put(new JSONObject().put("food", food1));

                JSONObject food2 = new JSONObject();
                food2.put("foodId", "food_a1gb9ubb72c7snbuxr3weagw0dd");
                food2.put("label", "Empire Apple");
                food2.put("category", "Generic foods");
                food2.put("nutrients", new JSONObject()
                        .put("PROCNT", 0.26)
                        .put("ENERC_KCAL", 52.0)
                        .put("FAT", 0.17)
                        .put("CHOCD", 13.8)
                        .put("FIBTG", 2.4));
                hintsArray.put(new JSONObject().put("food", food2));

                JSONObject food3 = new JSONObject();
                food3.put("foodId", "food_a1gb9ubb72c7snbuxr3weagw0dd");
                food3.put("label", "Sorb Apple");
                food3.put("category", "Generic foods");
                food3.put("nutrients", new JSONObject()
                        .put("PROCNT", 0.26)
                        .put("ENERC_KCAL", 52.0)
                        .put("FAT", 0.17)
                        .put("CHOCD", 13.8)
                        .put("FIBTG", 2.4));
                hintsArray.put(new JSONObject().put("food", food3));

                mockResponse.put("hints", hintsArray);

                return mockResponse;
            }
        };

        SearchFoodOutputBoundary successPresenter = new SearchFoodOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchFoodOutputData outputData) {
                // check the food list
                Food[] foods = outputData.getFoods();
                assertNotNull(foods);
                assertEquals(3, foods.length);

                // verify the first food
                assertEquals("Apple", foods[0].getLabel());
                assertEquals("Generic foods", foods[0].getCategory());
                assertEquals(52.0, foods[0].getNutrients().get("ENERC_KCAL"));
                assertEquals(0.26, foods[0].getNutrients().get("PROCNT"));
                assertEquals(0.17, foods[0].getNutrients().get("FAT"));
                assertEquals(13.8, foods[0].getNutrients().get("CHOCD"));
                assertEquals(2.4, foods[0].getNutrients().get("FIBTG"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case should not fail in this scenario.");
            }
        };

        SearchFoodInteractor interactor = new SearchFoodInteractor(databaseAccessObject, successPresenter);

        interactor.execute(inputData);
    }

    @Test
    void errorTest() {
        SearchFoodInputData inputData = new SearchFoodInputData("Apple");

        FoodDatabaseAccessObject databaseAccessObject = new FoodDatabaseAccessObject() {
            @Override
            public JSONObject searchFoodWithIngredient(String ingredient) {
                throw new RuntimeException("Mock database error");
            }
        };

        SearchFoodOutputBoundary errorPresenter = new SearchFoodOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchFoodOutputData outputData) {
                fail("Use case should not succeed in this scenario.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Mock database error", error, "Error message should match the exception thrown.");
            }
        };

        SearchFoodInteractor interactor = new SearchFoodInteractor(databaseAccessObject, errorPresenter);

        assertThrows(RuntimeException.class, () -> interactor.execute(inputData));
    }
}
