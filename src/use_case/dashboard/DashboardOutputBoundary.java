package use_case.dashboard;

import use_case.mealplanner.MealPlannerOutputData;

public interface DashboardOutputBoundary {
    void prepareSuccessView(DashboardOutputData outputData);
    void prepareSwitchToInfoCollection();
    void prepareSwitchToMealPlanner(DashboardOutputData outputData);
    void prepareFailView(String error);
}