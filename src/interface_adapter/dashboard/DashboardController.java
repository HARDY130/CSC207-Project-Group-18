package interface_adapter.dashboard;

import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;

public class DashboardController {
    private final DashboardInputBoundary dashboardUseCaseInteractor;

    public DashboardController(DashboardInputBoundary dashboardUseCaseInteractor) {
        this.dashboardUseCaseInteractor = dashboardUseCaseInteractor;
    }

    public void execute(String username) {
        DashboardInputData dashboardInputData = new DashboardInputData(username);
        dashboardUseCaseInteractor.execute(dashboardInputData);
    }

    public void onUpdateProfile() {
        dashboardUseCaseInteractor.switchToUpdateProfile();
    }

    public void onGenerateMeal(String username) {
        dashboardUseCaseInteractor.switchToMealPlanner(username);
    }

    public void onCustomize() {
        dashboardUseCaseInteractor.switchToCustomize();
    }
}