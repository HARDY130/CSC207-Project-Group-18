package interface_adapter.customize;

import entity.Food;
import entity.MealType;
import use_case.customize.CustomizeInputBoundary;
import use_case.customize.CustomizeInputData;

public class CustomizeController {
    private final CustomizeInputBoundary customizeInteractor;

    public CustomizeController(CustomizeInputBoundary customizeInteractor) {
        this.customizeInteractor = customizeInteractor;
    }

    public void searchFood(String username, String searchQuery) {
        CustomizeInputData inputData = new CustomizeInputData(searchQuery);
        customizeInteractor.searchFood(inputData);
    }

    public void addFoodToMeal(String username, Food food, MealType mealType) {
        customizeInteractor.addFoodToMeal(username, food, mealType);
    }

    public void returnToDashboard(String username) {
        customizeInteractor.returnToDashboard(username);
    }
}
