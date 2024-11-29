package use_case.dashboard;

public interface DashboardOutputBoundary {
    void prepareSuccessView(DashboardOutputData outputData);
    void prepareSwitchToInfoCollection();
    void prepareFailView(String error);
}