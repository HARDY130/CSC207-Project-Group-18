package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import interface_adapter.customize.CustomizeState;
import interface_adapter.customize.CustomizeViewModel;
import interface_adapter.info_collection.InfoCollectionViewModel;
import interface_adapter.meal_planner.MealPlannerState;
import interface_adapter.meal_planner.MealPlannerViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final InfoCollectionViewModel infoCollectionViewModel;
    private final CustomizeViewModel customizeViewModel;
    private final MealPlannerViewModel mealPlannerViewModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel,
                              DashboardViewModel dashboardViewModel,
                              InfoCollectionViewModel infoCollectionViewModel,
                              CustomizeViewModel customizeViewModel,
                              MealPlannerViewModel mealPlannerViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
        this.customizeViewModel = customizeViewModel;
        this.mealPlannerViewModel = mealPlannerViewModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData outputData) {
        DashboardState state = new DashboardState();

        // Set basic user info
        state.setUsername(outputData.getUsername());
        state.setActivityLevel(outputData.getActivityLevel());
        state.setAllergies(outputData.getAllergies());

        // Set calculated values
        state.setBmr(outputData.getBmr());
        state.setTdee(outputData.getTdee());

        // Set nutrition goals
        state.setDailyCalorieGoal(outputData.getTdee());
        state.setCarbsGoalGrams(outputData.getCarbsGoal());
        state.setProteinGoalGrams(outputData.getProteinGoal());
        state.setFatGoalGrams(outputData.getFatGoal());

        // Set consumed values
        state.setConsumedCalories(outputData.getConsumedCalories());
        state.setConsumedCarbs(outputData.getConsumedCarbs());
        state.setConsumedProtein(outputData.getConsumedProtein());
        state.setConsumedFat(outputData.getConsumedFat());

        // Set meals
        state.setMeals(outputData.getMeals());

        // Clear any errors and set success
        state.setError("");
        state.setSuccessMessage("Dashboard updated successfully!");
        state.setLoading(false);

        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSwitchToInfoCollection() {
        viewManagerModel.setActiveView(infoCollectionViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSwitchToCustomize() {
        // Transfer current user data to customize view
        CustomizeState customizeState = customizeViewModel.getState();
        DashboardState dashboardState = dashboardViewModel.getState();
        customizeState.setUsername(dashboardState.getUsername());
        customizeViewModel.setState(customizeState);

        // Switch to customize view
        viewManagerModel.setActiveView(customizeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSwitchToMealPlanner(DashboardOutputData outputData) {
        // Initialize meal planner state with user data
        MealPlannerState mealPlannerState = new MealPlannerState();
        mealPlannerState.setUsername(outputData.getUsername());
        mealPlannerState.setDailyCalorieGoal(outputData.getTdee());
        mealPlannerState.setAllergies(outputData.getAllergies());

        mealPlannerViewModel.setState(mealPlannerState);
        mealPlannerViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(mealPlannerViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        DashboardState state = dashboardViewModel.getState();
        state.setError(error);
        state.setSuccessMessage("");
        dashboardViewModel.setState(state);
        dashboardViewModel.firePropertyChanged();
    }
}