package use_case.mealplanner;

public interface MealPlannerOutputBoundary {
    void prepareSuccessView(MealPlannerOutputData outputData);
    void prepareFailView(String error);
    void backToDashboard();
}