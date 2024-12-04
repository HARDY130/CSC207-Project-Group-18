package interface_adapter.customize;

import entity.Food;
import entity.MealType;
import use_case.customize.CustomizeInputBoundary;
import use_case.customize.CustomizeInputData;

/**
 * The controller for the customize page.
 */
public class CustomizeController {
    private final CustomizeInputBoundary customizeInteractor;

    public CustomizeController(CustomizeInputBoundary customizeInteractor) {
        this.customizeInteractor = customizeInteractor;
    }

    public void searchFood(String username, String searchQuery) {
        CustomizeInputData inputData = new CustomizeInputData(username, searchQuery);
        customizeInteractor.searchFood(inputData);
    }
    /**
     * Adds food to a meal.
     * @param username the username of the user
     * @param food the food to add
     * @param mealType the meal type to add the food to
     */

    public void addFoodToMeal(String username, Food food, MealType mealType) {
        customizeInteractor.addFoodToMeal(username, food, mealType);
    }

    public void returnToDashboard(String username) {
        customizeInteractor.returnToDashboard(username);
    }
}
