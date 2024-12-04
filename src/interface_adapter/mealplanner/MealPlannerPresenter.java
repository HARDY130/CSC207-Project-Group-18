package interface_adapter.meal_planner;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.meal_planner.MealPlannerOutputBoundary;
import use_case.meal_planner.MealPlannerOutputData;

public class MealPlannerPresenter implements MealPlannerOutputBoundary {
    private final MealPlannerViewModel mealPlannerViewModel;
    private final ViewManagerModel viewManagerModel;
    private final DashboardViewModel dashboardViewModel;

    public MealPlannerPresenter(
            MealPlannerViewModel viewModel,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel) {
        this.mealPlannerViewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(MealPlannerOutputData outputData) {
        MealPlannerState state = mealPlannerViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setBreakfastOptions(outputData.getBreakfastOptions());
        state.setLunchOptions(outputData.getLunchOptions());
        state.setDinnerOptions(outputData.getDinnerOptions());
        state.setError("");

        mealPlannerViewModel.setState(state);
        mealPlannerViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        MealPlannerState state = mealPlannerViewModel.getState();
        state.setError(error);
        mealPlannerViewModel.setState(state);
        mealPlannerViewModel.firePropertyChanged();
    }

    @Override
    public void backToDashboard() {
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
