package use_case.dashboard;

public interface DashboardOutputBoundary {
    void prepareSuccessView(DashboardOutputData outputData);
    void prepareSwitchToInfoCollection();
    void prepareSwitchToMealPlanner(DashboardOutputData outputData);
    void prepareSwitchToCustomize();
    void prepareFailView(String error);
}