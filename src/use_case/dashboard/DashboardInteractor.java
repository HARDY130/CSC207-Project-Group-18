package use_case.dashboard;

import entity.CommonUser;


import java.util.HashSet;

public class DashboardInteractor implements DashboardInputBoundary {
    final DashboardDataAccessInterface userDataAccessObject;
    final DashboardOutputBoundary dashboardPresenter;
//    final DashboardViewModel dashboardViewModel = new DashboardViewModel();

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
                    true);

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
    public void switchToMealPlanner(String username) {
//        // Switch to meal planner view with current user info
//        DashboardState state = (DashboardState) dashboardViewModel.getState();

//        MealPlannerInputData inputData = new MealPlannerInputData(
//                new HashSet<>(),
//                state.getUsername()// Start with no dietary preferences selected
//        );

//        try {
//            // Initialize the meal planner with default options
//            DashboardOutputData outputData = new DashboardOutputData(
//                    state.getUsername(),
//                    state.getBmr(),
//                    state.getTdee(),
//                    state.getCarbsGoalGrams(),
//                    state.getProteinGoalGrams(),
//                    state.getFatGoalGrams(),
//                    state.getConsumedCalories(),
//                    state.getConsumedCarbs(),
//                    state.getConsumedProtein(),
//                    state.getConsumedFat(),
//                    state.getActivityLevel(),
//                    state.getAllergies()
//            );
//
//            dashboardPresenter.prepareSwitchToMealPlanner(outputData);
//        } catch (Exception e) {
//            dashboardPresenter.prepareFailView(e.getMessage());
//        }
//    }

//        String currentUser = userDataAccessObject.getCurrentUsername();
//        if (currentUser == null || !userDataAccessObject.existsByName(currentUser)) {
//            dashboardPresenter.prepareFailView("No active user session found");
//            return;
//        }
//
//        CommonUser user = (CommonUser) userDataAccessObject.get(currentUser);
//
//        // Create output data with user's dietary information
//        DashboardOutputData outputData = new DashboardOutputData(
//                user.getName(),
//                user.calculateBMR(),
//                user.calculateTDEE(),
//                user.calculateCarbsGrams(),
//                user.calculateProteinGrams(),
//                user.calculateFatGrams(),
//                0.0, // Reset consumed values for new meal plan
//                0.0,
//                0.0,
//                0.0,
//                user.getActivityLevel(),
//                user.getAllergies(),
//                true
//        );

//        // Trigger the transition to meal planner view
//        dashboardPresenter.prepareSwitchToMealPlanner(outputData);

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
                0.0,
                0.0,
                0.0,
                0.0,
                user.getActivityLevel(),
                user.getAllergies(),
                true
        );

        dashboardPresenter.prepareSwitchToMealPlanner(outputData);

    }

    @Override
    public void switchToMealRecorder() {
        // Similar to switchToUpdateProfile, prepare for view transition
        dashboardPresenter.prepareSuccessView(
                new DashboardOutputData("", 0, 0, 0, 0, 0, 0, 0, 0, 0, "", new HashSet<>(), true)
        );
    }
}