package use_case.meal_planner;

import entity.CommonUser;
import entity.Food;
import entity.MealType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MealPlannerDataAccessInterface {
    Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets) throws Exception;
    
    List<Food> generateMealOptions(Set<String> dietaryPreferences, String mealType);
    
    boolean existsByName(String username);
}
