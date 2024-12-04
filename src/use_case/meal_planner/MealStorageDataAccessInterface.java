package use_case.meal_planner;

import entity.Food;
import entity.User;

public interface MealStorageDataAccessInterface {
    void save(User user);
    
    User get(String username);
    
    boolean existsByName(String username);
    
    void addMealToUser(String username, String mealType, Food food);
    
    String getCurrentUsername();
}