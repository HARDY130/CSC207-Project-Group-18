package use_case.mealplanner;

import entity.CommonUser;
import entity.Food;
import entity.MealType;
import entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MealPlannerDataAccessInterface {
    // Methods for meal planning functionality
    Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets) throws Exception;

    List<Food> generateMealOptions(Set<String> dietaryPreferences, String mealType);
//    // Methods for user-related operations
//    void save(User user);
//    User get(String username);
    boolean existsByName(String username);

//    // Methods for managing meal selections
//    void addMealToUser(String username, String mealType, Food food);
}