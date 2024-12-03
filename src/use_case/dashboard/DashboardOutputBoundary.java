package use_case.dashboard;

public interface DashboardOutputBoundary {
    void prepareSuccessView(DashboardOutputData outputData);
    void prepareSwitchToInfoCollection();
    void prepareSwitchToCustomize();
    void prepareFailView(String error);
}