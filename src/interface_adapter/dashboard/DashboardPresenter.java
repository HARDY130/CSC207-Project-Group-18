package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import interface_adapter.info_collection.InfoCollectionViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

import java.util.EnumMap;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final InfoCollectionViewModel infoCollectionViewModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel,
                              DashboardViewModel dashboardViewModel,
                              InfoCollectionViewModel infoCollectionViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData outputData) {
        // On success, update the view model state
        DashboardState dashboardState = new DashboardState();

        // Update with user info
        dashboardState.setUsername(outputData.getUsername());
        dashboardState.setBmr(outputData.getBmr());
        dashboardState.setTdee(outputData.getTdee());

        // Set nutrition goals and progress
        dashboardState.setDailyCalorieGoal(outputData.getTdee());
        dashboardState.setCarbsGoalGrams(outputData.getCarbsGoal());
        dashboardState.setProteinGoalGrams(outputData.getProteinGoal());
        dashboardState.setFatGoalGrams(outputData.getFatGoal());

        dashboardState.setConsumedCalories(outputData.getConsumedCalories());
        dashboardState.setConsumedCarbs(outputData.getConsumedCarbs());
        dashboardState.setConsumedProtein(outputData.getConsumedProtein());
        dashboardState.setConsumedFat(outputData.getConsumedFat());

        // Additional info
        dashboardState.setAllergies(outputData.getAllergies());
        dashboardState.setActivityLevel(outputData.getActivityLevel());

        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSwitchToInfoCollection() {
        // Switch to info collection view
        viewManagerModel.setActiveView(infoCollectionViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }


    @Override
    public void prepareFailView(String error) {
        DashboardState dashboardState = (DashboardState) dashboardViewModel.getState();
        dashboardState.setError(error);
        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChanged();
    }
}