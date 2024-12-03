package interface_adapter.dashboard;

import entity.Allergy;
import entity.Food;
import entity.MealType;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;

import java.time.LocalDate;
import java.util.Set;

public class DashboardController {
    private final DashboardInputBoundary dashboardUseCaseInteractor;

    public DashboardController(DashboardInputBoundary dashboardUseCaseInteractor) {
        this.dashboardUseCaseInteractor = dashboardUseCaseInteractor;
    }

    public void execute(String username, LocalDate birthDate, String gender,
                        int weight, int height, double activityMultiplier,
                        Set<Allergy> allergies, double consumedCalories,
                        double consumedCarbs, double consumedProtein,
                        double consumedFat) {

        DashboardInputData dashboardInputData = new DashboardInputData(
                username, birthDate, gender, weight, height, activityMultiplier,
                allergies, consumedCalories, consumedCarbs, consumedProtein, consumedFat
        );

        dashboardUseCaseInteractor.execute(dashboardInputData);
    }

    public void onUpdateProfile() {
        dashboardUseCaseInteractor.switchToUpdateProfile();
    }

    public void onGenerateMeal(String username) {
        dashboardUseCaseInteractor.switchToMealPlanner(username);
    }

    public void onRecordMeal() {
        dashboardUseCaseInteractor.switchToMealRecorder();
    }
}