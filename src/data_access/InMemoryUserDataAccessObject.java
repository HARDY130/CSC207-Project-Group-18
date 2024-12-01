package data_access;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.CommonUser;
import entity.Food;
import entity.MealType;
import entity.User;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.dashboard.DashboardDataAccessInterface;
import use_case.info_collection.InfoCollectionUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.mealplanner.MealPlannerDataAccessInterface;
import use_case.mealplanner.MealStorageDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        InfoCollectionUserDataAccessInterface,
        DashboardDataAccessInterface,
//        MealPlannerDataAccessInterface,
        MealStorageDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Map<MealType, Map<String, Food>>> userMeals = new HashMap<>();
    private final Map<String, NutritionProgress> userProgress = new HashMap<>();
    private String currentUsername;

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }


    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void updateNutritionProgress(String username,
                                        double consumedCalories,
                                        double consumedCarbs,
                                        double consumedProtein,
                                        double consumedFat) {
        userProgress.put(username, new NutritionProgress(
                consumedCalories, consumedCarbs, consumedProtein, consumedFat
        ));
    }

    // Helper class to store nutrition progress
    private static class NutritionProgress {
        double calories, carbs, protein, fat;

        NutritionProgress(double calories, double carbs, double protein, double fat) {
            this.calories = calories;
            this.carbs = carbs;
            this.protein = protein;
            this.fat = fat;
        }
    }

    @Override
    public Map<MealType, Map<String, Food>> getUserMeals(String username) {
        return userMeals.getOrDefault(username, new EnumMap<>(MealType.class));
    }

//    @Override
//    public Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets) throws Exception {
//        // This should actually be in a separate MealPlannerDataAccessObject
//        // that handles API calls to the meal planning service
//        throw new UnsupportedOperationException("Meal planning should be handled by MealPlannerDataAccessObject");
//    }

    @Override
    public void addMealToUser(String username, String mealType, Food food) {
        User user = users.get(username);
        if (user instanceof CommonUser) {
            ((CommonUser) user).addMeal(MealType.valueOf(mealType.toUpperCase()), food.getLabel(), food);
        }
    }
}
