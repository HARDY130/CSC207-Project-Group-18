package interface_adapter.mealplanner;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.mealplanner.MealPlannerOutputBoundary;
import use_case.mealplanner.MealPlannerOutputData;
import use_case.mealplanner.MealPlannerOutputBoundary;
import use_case.mealplanner.MealPlannerOutputData;

public class MealPlannerPresenter implements use_case.mealplanner.MealPlannerOutputBoundary {
    private final MealPlannerViewModel mealPlannerViewModel;
    private final ViewManagerModel viewManagerModel;
    private final DashboardViewModel dashboardViewModel;

    public MealPlannerPresenter(MealPlannerViewModel viewModel, ViewManagerModel viewManagerModel, DashboardViewModel dashboardViewModel) {
        this.mealPlannerViewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(use_case.mealplanner.MealPlannerOutputData outputData) {
        // Update the view model's state with new meal options
        MealPlannerState state = mealPlannerViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setBreakfastOptions(outputData.getBreakfastOptions());
        state.setLunchOptions(outputData.getLunchOptions());
        state.setDinnerOptions(outputData.getDinnerOptions());
        state.setError("");  // Clear any existing errors

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

