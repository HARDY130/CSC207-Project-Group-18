package use_case.dashboard;

import entity.User;

public interface DashboardDataAccessInterface {
    void save(User user);

    User get(String username);

    boolean existsByName(String username);

    void updateNutritionProgress(String username,
                                 double consumedCalories,
                                 double consumedCarbs,
                                 double consumedProtein,
                                 double consumedFat);
}