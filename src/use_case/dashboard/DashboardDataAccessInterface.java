package use_case.dashboard;

import entity.CommonUser;
import entity.Food;
import entity.MealType;
import entity.User;

import java.util.Map;

public interface DashboardDataAccessInterface {
    void save(User user);
    User get(String username);
    boolean existsByName(String username);
    String getCurrentUsername();
    Map<MealType, Map<String, Food>> getUserMeals(String username);

    void updateNutritionProgress(String username,
                                 double consumedCalories,
                                 double consumedCarbs,
                                 double consumedProtein,
                                 double consumedFat);
}