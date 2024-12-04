package use_case.mealplanner;

import entity.Food;

public interface MealPlannerInputBoundary {
    void execute(MealPlannerInputData inputData);
    void addMealToUser(String username, String mealType, Food food);
    void goHome();
}
