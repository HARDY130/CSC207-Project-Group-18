package use_case.meal_planner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.CommonUser;
import entity.Food;
import entity.MealType;

/**
 * Interface for the MealPlanner data access object.
 */
public interface MealPlannerDataAccessInterface {
    /**
     * Generates a meal plan for the given user based on their selected diets.
     *
     * @param user The user for whom the meal plan is generated.
     * @param selectedDiets The list of selected diets.
     * @return A map of meal types to lists of foods.
     * @throws Exception If an error occurs during meal plan generation.
     */
    Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets) throws Exception;
    /**
     * Generates meal options based on dietary preferences and meal type.
     *
     * @param dietaryPreferences The set of dietary preferences.
     * @param mealType The type of meal.
     * @return A list of foods that match the dietary preferences and meal type.
     */

    List<Food> generateMealOptions(Set<String> dietaryPreferences, String mealType);
    /**
     * Checks if a user exists by their username.
     *
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     */

    boolean existsByName(String username);
}
