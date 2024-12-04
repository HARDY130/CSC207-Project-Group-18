package interface_adapter.meal_planner;

import entity.Food;
import use_case.meal_planner.MealPlannerInputBoundary;
import use_case.meal_planner.MealPlannerInputData;

import java.util.Set;

public class MealPlannerController {
    private final MealPlannerInputBoundary mealPlannerUseCaseInteractor;

    public MealPlannerController(MealPlannerInputBoundary mealPlannerUseCaseInteractor) {
        this.mealPlannerUseCaseInteractor = mealPlannerUseCaseInteractor;
    }

    public void execute(String username, Set<String> dietaryPreferences) {
        MealPlannerInputData inputData = new MealPlannerInputData(dietaryPreferences, username);
        mealPlannerUseCaseInteractor.execute(inputData);
    }

    public void addMealToUser(String username, String mealType, Food food) {
        mealPlannerUseCaseInteractor.addMealToUser(username, mealType, food);
    }

    public void goHome() {
        mealPlannerUseCaseInteractor.goHome();
    }
}
