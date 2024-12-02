package use_case.dashboard;

import entity.CommonUser;
import entity.Food;
import entity.MealType;

import java.util.Map;

public class DashboardInteractor implements DashboardInputBoundary {
    final DashboardDataAccessInterface userDataAccessObject;
    final DashboardOutputBoundary dashboardPresenter;

    public DashboardInteractor(DashboardDataAccessInterface userDataAccessObject,
                               DashboardOutputBoundary dashboardPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.dashboardPresenter = dashboardPresenter;
    }

    @Override
    public void execute(DashboardInputData inputData) {
        if (!userDataAccessObject.existsByName(inputData.getUsername())) {
            dashboardPresenter.prepareFailView("User not found");
            return;
        }

        CommonUser user = (CommonUser) userDataAccessObject.get(inputData.getUsername());

        // Calculate base nutritional values
        double bmr = user.calculateBMR();
        double tdee = user.calculateTDEE();
        double carbsGoal = user.calculateCarbsGrams();
        double proteinGoal = user.calculateProteinGrams();
        double fatGoal = user.calculateFatGrams();

        // Get all meals and calculate current consumption
        Map<MealType, Map<String, Food>> meals = user.getAllMeals();
        double consumedCalories = 0;
        double consumedCarbs = 0;
        double consumedProtein = 0;
        double consumedFat = 0;

        for (Map<String, Food> mealFoods : meals.values()) {
            for (Food food : mealFoods.values()) {
                Map<String, Double> nutrients = food.getNutrients();
                consumedCalories += nutrients.getOrDefault("ENERC_KCAL", 0.0);
                consumedCarbs += nutrients.getOrDefault("CHOCDF", 0.0);
                consumedProtein += nutrients.getOrDefault("PROCNT", 0.0);
                consumedFat += nutrients.getOrDefault("FAT", 0.0);
            }
        }

        DashboardOutputData outputData = new DashboardOutputData(
                user.getName(),
                bmr,
                tdee,
                carbsGoal,
                proteinGoal,
                fatGoal,
                meals,
                consumedCalories,
                consumedCarbs,
                consumedProtein,
                consumedFat,
                user.getActivityLevel(),
                user.getAllergies(),
                true,
                null
        );

        dashboardPresenter.prepareSuccessView(outputData);
    }

    @Override
    public void switchToUpdateProfile() {
        dashboardPresenter.prepareSwitchToInfoCollection();
    }

    @Override
    public void switchToMealPlanner(String username) {
        if (!userDataAccessObject.existsByName(username)) {
            dashboardPresenter.prepareFailView("User not found.");
            return;
        }

        CommonUser user = (CommonUser) userDataAccessObject.get(username);
        DashboardOutputData outputData = new DashboardOutputData(
                user.getName(),
                user.calculateBMR(),
                user.calculateTDEE(),
                user.calculateCarbsGrams(),
                user.calculateProteinGrams(),
                user.calculateFatGrams(),
                user.getAllMeals(),
                0.0,
                0.0,
                0.0,
                0.0,
                user.getActivityLevel(),
                user.getAllergies(),
                true,
                null
        );

        dashboardPresenter.prepareSwitchToMealPlanner(outputData);
    }

    @Override
    public void switchToCustomize() {
        dashboardPresenter.prepareSwitchToCustomize();
    }
}