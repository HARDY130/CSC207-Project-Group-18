package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import interface_adapter.customize.CustomizeWindowViewModel;
import interface_adapter.customize.SearchFoodViewModel;
import interface_adapter.info_collection.InfoCollectionViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

import java.util.EnumMap;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final InfoCollectionViewModel infoCollectionViewModel;
    private final CustomizeWindowViewModel customizeWindowViewModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel,
                              DashboardViewModel dashboardViewModel,
                              InfoCollectionViewModel infoCollectionViewModel,
                              CustomizeWindowViewModel customizeWindowViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
        this.customizeWindowViewModel = customizeWindowViewModel;

    }

    @Override
    public void prepareSuccessView(DashboardOutputData outputData) {
        DashboardState dashboardState = new DashboardState();

        dashboardState.setUsername(outputData.getUsername());
        dashboardState.setBmr(outputData.getBmr());
        dashboardState.setTdee(outputData.getTdee());

        dashboardState.setDailyCalorieGoal(outputData.getTdee());
        dashboardState.setCarbsGoalGrams(outputData.getCarbsGoal());
        dashboardState.setProteinGoalGrams(outputData.getProteinGoal());
        dashboardState.setFatGoalGrams(outputData.getFatGoal());

        dashboardState.setConsumedCalories(outputData.getConsumedCalories());
        dashboardState.setConsumedCarbs(outputData.getConsumedCarbs());
        dashboardState.setConsumedProtein(outputData.getConsumedProtein());
        dashboardState.setConsumedFat(outputData.getConsumedFat());

        dashboardState.setAllergies(outputData.getAllergies());
        dashboardState.setActivityLevel(outputData.getActivityLevel());

        dashboardViewModel.setState(dashboardState);
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
        viewManagerModel.setActiveView(customizeWindowViewModel.getViewName());
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