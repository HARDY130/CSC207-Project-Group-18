package use_case.dashboard;

public interface DashboardInputBoundary {
    void execute(DashboardInputData inputData);

    void switchToUpdateProfile();

    void switchToMealPlanner(String username);

    void switchToCustomize();
}