package use_case.dashboard;

import entity.CommonUser;
import entity.Food;
import entity.MealType;
import use_case.dashboard.*;


import java.util.HashSet;
import java.util.Map;

public class DashboardInteractor implements DashboardInputBoundary {
    final DashboardDataAccessInterface userDataAccessObject;
    final DashboardOutputBoundary dashboardPresenter;

    public DashboardInteractor(DashboardDataAccessInterface userDataAccessInterface,
                               DashboardOutputBoundary outputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.dashboardPresenter = outputBoundary;
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {
        try {
            if (!userDataAccessObject.existsByName(dashboardInputData.getUsername())) {
                dashboardPresenter.prepareFailView("User not found.");
                return;
            }

            CommonUser user = (CommonUser) userDataAccessObject.get(dashboardInputData.getUsername());

            // Update user's nutrition progress
            userDataAccessObject.updateNutritionProgress(
                    dashboardInputData.getUsername(),
                    dashboardInputData.getConsumedCalories(),
                    dashboardInputData.getConsumedCarbs(),
                    dashboardInputData.getConsumedProtein(),
                    dashboardInputData.getConsumedFat()
            );

            // Calculate nutrition goals based on user data
            double bmr = user.calculateBMR();
            double tdee = user.calculateTDEE();
            double carbsGoal = user.calculateCarbsGrams();
            double proteinGoal = user.calculateProteinGrams();
            double fatGoal = user.calculateFatGrams();

            DashboardOutputData dashboardOutputData = new DashboardOutputData(
                    user.getName(),
                    bmr,
                    tdee,
                    carbsGoal,
                    proteinGoal,
                    fatGoal,
                    dashboardInputData.getConsumedCalories(),
                    dashboardInputData.getConsumedCarbs(),
                    dashboardInputData.getConsumedProtein(),
                    dashboardInputData.getConsumedFat(),
                    user.getActivityLevel(),
                    user.getAllergies(),
                    true
            );

            dashboardPresenter.prepareSuccessView(dashboardOutputData);

        } catch (Exception e) {
            dashboardPresenter.prepareFailView(e.getMessage());
        }
    }

    @Override
    public void switchToUpdateProfile() {
        dashboardPresenter.prepareSwitchToInfoCollection();
    }


    @Override
    public void switchToMealPlanner() {
        // Similar to switchToUpdateProfile, prepare for view transition
        dashboardPresenter.prepareSuccessView(
                new DashboardOutputData("", 0, 0, 0, 0, 0, 0, 0, 0, 0, "", new HashSet<>(), true)
        );
    }

    @Override
    public void switchToMealRecorder() {
        // Similar to switchToUpdateProfile, prepare for view transition
        dashboardPresenter.prepareSuccessView(
                new DashboardOutputData("", 0, 0, 0, 0, 0, 0, 0, 0, 0, "", new HashSet<>(), true)
        );
    }
}