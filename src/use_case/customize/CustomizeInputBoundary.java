package use_case.customize;

import entity.Food;
import entity.MealType;

public interface CustomizeInputBoundary {
    void searchFood(CustomizeInputData inputData);

    void addFoodToMeal(String username, Food food, MealType mealType);

    void returnToDashboard(String username);
}