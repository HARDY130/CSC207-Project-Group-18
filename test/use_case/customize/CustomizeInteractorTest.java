package use_case.customize;
import data_access.FoodDatabaseAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.*;
import interface_adapter.customize.CustomizeController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.dashboard.DashboardDataAccessInterface;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomizeInteractorTest {

    private FoodDatabaseAccessObject foodDatabaseAccessObject;
    private CustomizeOutputBoundary customizePresenter;
    private DashboardDataAccessInterface userDataAccessObject;
    private CustomizeInputBoundary customizeInteractor;
    private CustomizeController customizeController;
    private CustomizeOutputData data;

    @BeforeEach
    void setUp() {
        // Initialize the necessary dependencies
        foodDatabaseAccessObject = new FoodDatabaseAccessObject(); // Replace with your implementation
        userDataAccessObject = new InMemoryUserDataAccessObject(); // Replace with your implementation

        CustomizeOutputBoundary customizePresenter = new CustomizeOutputBoundary() {
            @Override
            public void presentSearchResults(CustomizeOutputData outputData) {
                outputData = outputData;
                assertTrue(outputData.getSearchResults() != null
                        && outputData.getError() == null
                        && outputData.getSuccessMessage() == null
                );
            }

            @Override
            public void presentError(String error) {
                assertNotNull(error);
            }

            @Override
            public void presentSuccess(String message) {
                assertNotNull(message);
            }

            @Override
            public void presentDashboard() {

            }
        };

        this.customizeInteractor = new CustomizeInteractor(
                foodDatabaseAccessObject,
                customizePresenter,
                userDataAccessObject
        );

        this.customizeController = new CustomizeController(customizeInteractor);
    }

    @Test
    void testSearchFoodSuccess() {
        // Prepare test data
        String searchQuery = "apple";

        customizeController.searchFood("Hardy", searchQuery);

        // Verify the results through your output boundary
//        CustomizeOutputData outputData = ((CustomizeOutputBoundaryImpl) customizePresenter).getOutputData();
//        assertNotNull(outputData);
//        assertNull(outputData.getError());
//        assertEquals(1, outputData.getFoods().size());
//        assertEquals("Apple", outputData.getFoods().get(0).getLabel());
    }

    @Test
    void testSearchFoodError() {
        String username = "DNE user";

        customizeController.searchFood(username, "apple");

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

        List<Food> foods = new ArrayList<>();
        JSONArray hints = mockResponse.getJSONArray("hints");

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

        Food food = foods.get(0);
        MealType mealType = MealType.DINNER;


        // Perform the action
        customizeController.addFoodToMeal(username, food, mealType);
    }

//
    @Test
    void testAddFoodToMealSuccess() {
        // Prepare test data
        String username = "test_user";
        String password = "test_password";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String gender = "male";
        int weight = 100;
        int height = 170;
        double activityMultiplier = 1.5;
        Set<Allergy> allergies = new HashSet<>();
        User user = new CommonUser(username, password, birthDate, gender, weight, height, activityMultiplier, allergies);
        userDataAccessObject.save(user);

        customizeController.searchFood(username, "apple");

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

        List<Food> foods = new ArrayList<>();
        JSONArray hints = mockResponse.getJSONArray("hints");

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

        Food food = foods.get(0);
        MealType mealType = MealType.DINNER;


        // Perform the action
        customizeController.addFoodToMeal(username, food, mealType);
    }
//
    @Test
    void testReturnToDashboard() {
        String username = "test_user";

        // Perform the action
        customizeInteractor.returnToDashboard(username);
    }
}
