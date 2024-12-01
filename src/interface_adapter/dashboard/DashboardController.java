package interface_adapter.dashboard;

import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;
import entity.Allergy;
import java.time.LocalDate;
import java.util.Set;

public class DashboardController {
    private final DashboardInputBoundary dashboardUseCaseInteractor;

    public DashboardController(DashboardInputBoundary dashboardUseCaseInteractor) {
        this.dashboardUseCaseInteractor = dashboardUseCaseInteractor;
    }

    public void execute(String username, LocalDate birthDate, String gender,
                        int weight, int height, double activityMultiplier,
                        Set<Allergy> allergies) {

        DashboardInputData dashboardInputData = new DashboardInputData(
                username, birthDate, gender, weight, height,
                activityMultiplier, allergies);

        dashboardUseCaseInteractor.execute(dashboardInputData);
    }

    public void onUpdateProfile() {
        dashboardUseCaseInteractor.switchToUpdateProfile();
    }

    public void onGenerateMeal() {
        dashboardUseCaseInteractor.switchToMealPlanner();
    }

    // This is the method called when "Record Meal" button is clicked
    public void onCustomize() {
        dashboardUseCaseInteractor.switchToCustomize();
    }
}