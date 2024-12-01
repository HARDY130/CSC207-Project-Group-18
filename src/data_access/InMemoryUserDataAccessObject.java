package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.dashboard.DashboardDataAccessInterface;
import use_case.info_collection.InfoCollectionUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        InfoCollectionUserDataAccessInterface,
        DashboardDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

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
        return this.currentUsername;
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

    // Class to store nutrition progress
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
