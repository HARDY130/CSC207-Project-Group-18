package use_case.dashboard;

//import entity.CommonUser;
//import entity.Food;
//import entity.MealType;
//import use_case.dashboard.*;
//
//
//import java.util.HashSet;
//import java.util.Map;
//
//public class DashboardInteractor implements DashboardInputBoundary {
//    final DashboardDataAccessInterface userDataAccessObject;
//    final DashboardOutputBoundary dashboardPresenter;
//
//    public DashboardInteractor(DashboardDataAccessInterface userDataAccessInterface,
//                               DashboardOutputBoundary outputBoundary) {
//        this.userDataAccessObject = userDataAccessInterface;
//        this.dashboardPresenter = outputBoundary;
//    }
//
//    @Override
//    public void execute(DashboardInputData dashboardInputData) {
//        try {
//            if (!userDataAccessObject.existsByName(dashboardInputData.getUsername())) {
//                dashboardPresenter.prepareFailView("User not found.");
//                return;
//            }
//
//            CommonUser user = (CommonUser) userDataAccessObject.get(dashboardInputData.getUsername());
//
//            // Update nutrition progress
//            userDataAccessObject.updateNutritionProgress(
//                    dashboardInputData.getUsername(),
//                    dashboardInputData.getConsumedCalories(),
//                    dashboardInputData.getConsumedCarbs(),
//                    dashboardInputData.getConsumedProtein(),
//                    dashboardInputData.getConsumedFat()
//            );
//
//            // Calculate nutrition goals
//            double bmr = user.calculateBMR();
//            double tdee = user.calculateTDEE();
//            double carbsGoal = user.calculateCarbsGrams();
//            double proteinGoal = user.calculateProteinGrams();
//            double fatGoal = user.calculateFatGrams();
//
//            DashboardOutputData dashboardOutputData = new DashboardOutputData(
//                    user.getName(),
//                    bmr,
//                    tdee,
//                    carbsGoal,
//                    proteinGoal,
//                    fatGoal,
//                    dashboardInputData.getConsumedCalories(),
//                    dashboardInputData.getConsumedCarbs(),
//                    dashboardInputData.getConsumedProtein(),
//                    dashboardInputData.getConsumedFat(),
//                    user.getActivityLevel(),
//                    user.getAllergies(),
//                    true
//            );
//
//            dashboardPresenter.prepareSuccessView(dashboardOutputData);
//
//        } catch (Exception e) {
//            dashboardPresenter.prepareFailView(e.getMessage());
//        }
//    }
//
//    @Override
//    public void switchToUpdateProfile() {
//        dashboardPresenter.prepareSwitchToInfoCollection();
//    }
//
//
//    @Override
//    public void switchToMealPlanner() {
//        dashboardPresenter.prepareSuccessView(
//                new DashboardOutputData("", 0, 0, 0, 0, 0, 0, 0, 0, 0, "", new HashSet<>(), true)
//        );
//    }
//
//    @Override
//    public void switchToCustomize() {
//        dashboardPresenter.prepareSwitchToCustomize();
//    }
//}

import entity.CommonUser;
import entity.Food;
import entity.MealType;

import java.util.*;

public class DashboardInteractor implements DashboardInputBoundary {
    private final DashboardDataAccessInterface userDataAccessObject;
    private final DashboardOutputBoundary dashboardPresenter;

    public DashboardInteractor(DashboardDataAccessInterface userDataAccessObject,
                               DashboardOutputBoundary dashboardPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.dashboardPresenter = dashboardPresenter;
    }

//    @Override
//    public void execute(DashboardInputData dashboardInputData) {
//        try {
//            if (!userDataAccessObject.existsByName(dashboardInputData.getUsername())) {
//                dashboardPresenter.prepareFailView("User not found.");
//                return;
//            }
//
//            CommonUser user = (CommonUser) userDataAccessObject.get(dashboardInputData.getUsername());
//            Map<MealType, Map<String, Food>> userMeals = user.getAllMeals();
//
//            // Convert user meals to the format needed for output
//            Map<MealType, List<Food>> meals = new EnumMap<>(MealType.class);
//            for (MealType type : MealType.values()) {
//                meals.put(type, new ArrayList<>(userMeals.get(type).values()));
//            }
//
//            // Calculate nutrition totals from all meals
//            double totalCalories = 0;
//            double totalCarbs = 0;
//            double totalProtein = 0;
//            double totalFat = 0;
//
//            for (List<Food> mealFoods : meals.values()) {
//                for (Food food : mealFoods) {
//                    Map<String, Double> nutrients = food.getNutrients();
//                    totalCalories += nutrients.getOrDefault("ENERC_KCAL", 0.0);
//                    totalCarbs += nutrients.getOrDefault("CHOCDF", 0.0);
//                    totalProtein += nutrients.getOrDefault("PROCNT", 0.0);
//                    totalFat += nutrients.getOrDefault("FAT", 0.0);
//                }
//            }
//
//            // Calculate nutrition goals
//            double bmr = user.calculateBMR();
//            double tdee = user.calculateTDEE();
//            double carbsGoal = user.calculateCarbsGrams();
//            double proteinGoal = user.calculateProteinGrams();
//            double fatGoal = user.calculateFatGrams();
//
//            // Update nutrition progress in the database
//            userDataAccessObject.updateNutritionProgress(
//                    user.getName(),
//                    totalCalories,
//                    totalCarbs,
//                    totalProtein,
//                    totalFat
//            );
//
//            DashboardOutputData outputData = new DashboardOutputData(
//                    user.getName(),
//                    bmr,
//                    tdee,
//                    carbsGoal,
//                    proteinGoal,
//                    fatGoal,
//                    meals,
//                    totalCalories,
//                    totalCarbs,
//                    totalProtein,
//                    totalFat,
//                    user.getActivityLevel(),
//                    user.getAllergies(),
//                    true,
//                    null
//            );
//
//            dashboardPresenter.prepareSuccessView(outputData);
//
//        } catch (Exception e) {
//            dashboardPresenter.prepareFailView(e.getMessage());
//        }
//    }

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
    public void switchToMealPlanner() {
        // Implementation for meal planner navigation
        // This will be implemented when meal planning feature is added
    }

    @Override
    public void switchToCustomize() {
        dashboardPresenter.prepareSwitchToCustomize();
    }
}