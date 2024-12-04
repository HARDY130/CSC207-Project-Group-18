package interface_adapter.meal_planner;

import entity.Food;
import use_case.meal_planner.MealPlannerInputBoundary;
import use_case.meal_planner.MealPlannerInputData;

import java.util.Set;
/**
 * Controller class for the MealPlanner use case.
 */

public class MealPlannerController {
    private final MealPlannerInputBoundary mealPlannerUseCaseInteractor;

    public MealPlannerController(MealPlannerInputBoundary mealPlannerUseCaseInteractor) {
        this.mealPlannerUseCaseInteractor = mealPlannerUseCaseInteractor;
    }
    /**
     * Executes the MealPlanner use case.
     * @param username The username of the user.
     * @param dietaryPreferences The dietary preferences of the user.
     */

    public void execute(String username, Set<String> dietaryPreferences) {
        MealPlannerInputData inputData = new MealPlannerInputData(dietaryPreferences, username);
        mealPlannerUseCaseInteractor.execute(inputData);
    }
    /**
     * Adds a meal to the user's meal plan.
     * @param username The username of the user.
     * @param mealType The type of meal.
     * @param food The food to add.
     */
    public void addMealToUser(String username, String mealType, Food food) {
        mealPlannerUseCaseInteractor.addMealToUser(username, mealType, food);
    }
    /**
     * Removes a meal from the user's meal plan.
     */

    public void goHome() {
        mealPlannerUseCaseInteractor.goHome();
    }
}
