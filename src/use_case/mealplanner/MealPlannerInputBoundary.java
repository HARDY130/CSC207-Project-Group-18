package use_case.meal_planner;

import entity.Food;

/**
 * Interface for the MealPlanner input boundary.
 */
public interface MealPlannerInputBoundary {

    /**
     * Executes the meal planner input data.
     *
     * @param inputData the meal planner input data
     */
    void execute(MealPlannerInputData inputData);

    /**
     * Adds a meal to the user.
     *
     * @param username the username
     * @param mealType the meal type
     * @param food the food
     */
    void addMealToUser(String username, String mealType, Food food);
    /**
     * Goes back to the home screen.
     */

    void goHome();
}
