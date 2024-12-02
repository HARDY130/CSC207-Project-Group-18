package use_case.meal_planner;

public interface MealPlannerOutputBoundary {
    void prepareSuccessView(MealPlannerOutputData outputData);
    void prepareFailView(String error);
    void backToDashboard();
}
