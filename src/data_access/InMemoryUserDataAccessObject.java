package data_access;


import entity.CommonUser;
import entity.Food;
import entity.MealType;
import entity.User;
import use_case.dashboard.DashboardDataAccessInterface;
import use_case.info_collection.InfoCollectionUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.meal_planner.MealStorageDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        InfoCollectionUserDataAccessInterface,
        DashboardDataAccessInterface,
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

//    @Override
//    public Map<MealType, Map<String, Food>> getUserMeals(String username) {
//        return userMeals.getOrDefault(username, new EnumMap<>(MealType.class));
//    }

    @Override
    public void addMealToUser(String username, String mealType, Food food) {
        User user = users.get(username);
        if (user instanceof CommonUser) {
            ((CommonUser) user).addMeal(MealType.valueOf(mealType.toUpperCase()), food.getLabel(), food);
            updateUserNutritionProgress(username);
        }
    }

    private void updateUserNutritionProgress(String username) {
        CommonUser user = (CommonUser) users.get(username);
        if (user != null) {
            double totalCalories = 0;
            double totalCarbs = 0;
            double totalProtein = 0;
            double totalFat = 0;

            Map<MealType, Map<String, Food>> meals = user.getAllMeals();
            for (Map<String, Food> mealFoods : meals.values()) {
                for (Food food : mealFoods.values()) {
                    Map<String, Double> nutrients = food.getNutrients();
                    totalCalories += nutrients.getOrDefault("ENERC_KCAL", 0.0);
                    totalCarbs += nutrients.getOrDefault("CHOCDF", 0.0);
                    totalProtein += nutrients.getOrDefault("PROCNT", 0.0);
                    totalFat += nutrients.getOrDefault("FAT", 0.0);
                }
            }

            updateNutritionProgress(username, totalCalories, totalCarbs, totalProtein, totalFat);
        }
    }

    private static class NutritionProgress {
        double calories, carbs, protein, fat;

        NutritionProgress(double calories, double carbs, double protein, double fat) {
            this.calories = calories;
            this.carbs = carbs;
            this.protein = protein;
            this.fat = fat;
        }
    }
}