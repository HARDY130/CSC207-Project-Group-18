package interface_adapter.mealplanner;

import use_case.mealplanner.MealPlannerInputBoundary;
import use_case.mealplanner.MealPlannerInputData;
import entity.Food;
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

