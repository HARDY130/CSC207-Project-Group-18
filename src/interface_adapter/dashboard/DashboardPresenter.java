package interface_adapter.dashboard;

//import interface_adapter.ViewManagerModel;
//import interface_adapter.customize.CustomizeWindowViewModel;
//import interface_adapter.info_collection.InfoCollectionViewModel;
//import use_case.dashboard.DashboardOutputBoundary;
//import use_case.dashboard.DashboardOutputData;
//
//public class DashboardPresenter implements DashboardOutputBoundary {
//    private final DashboardViewModel dashboardViewModel;
//    private final ViewManagerModel viewManagerModel;
//    private final InfoCollectionViewModel infoCollectionViewModel;
//    private final CustomizeWindowViewModel customizeWindowViewModel;
//
//    public DashboardPresenter(ViewManagerModel viewManagerModel,
//                              DashboardViewModel dashboardViewModel,
//                              InfoCollectionViewModel infoCollectionViewModel,
//                              CustomizeWindowViewModel customizeWindowViewModel) {
//        this.viewManagerModel = viewManagerModel;
//        this.dashboardViewModel = dashboardViewModel;
//        this.infoCollectionViewModel = infoCollectionViewModel;
//        this.customizeWindowViewModel = customizeWindowViewModel;
//
//    }
//
//    @Override
//    public void prepareSuccessView(DashboardOutputData outputData) {
//        DashboardState dashboardState = new DashboardState();
//
//        dashboardState.setUsername(outputData.getUsername());
//        dashboardState.setBmr(outputData.getBmr());
//        dashboardState.setTdee(outputData.getTdee());
//
//        dashboardState.setDailyCalorieGoal(outputData.getTdee());
//        dashboardState.setCarbsGoalGrams(outputData.getCarbsGoal());
//        dashboardState.setProteinGoalGrams(outputData.getProteinGoal());
//        dashboardState.setFatGoalGrams(outputData.getFatGoal());
//
//        dashboardState.setConsumedCalories(outputData.getConsumedCalories());
//        dashboardState.setConsumedCarbs(outputData.getConsumedCarbs());
//        dashboardState.setConsumedProtein(outputData.getConsumedProtein());
//        dashboardState.setConsumedFat(outputData.getConsumedFat());
//
//        dashboardState.setAllergies(outputData.getAllergies());
//        dashboardState.setActivityLevel(outputData.getActivityLevel());
//
//        dashboardViewModel.setState(dashboardState);
//        dashboardViewModel.firePropertyChanged();
//
//        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareSwitchToInfoCollection() {
//        viewManagerModel.setActiveView(infoCollectionViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareSwitchToCustomize() {
//        viewManagerModel.setActiveView(customizeWindowViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareFailView(String error) {
//        DashboardState dashboardState = (DashboardState) dashboardViewModel.getState();
//        dashboardState.setError(error);
//        dashboardViewModel.setState(dashboardState);
//        dashboardViewModel.firePropertyChanged();
//    }
//}

import interface_adapter.ViewManagerModel;
import interface_adapter.customize.CustomizeState;
import interface_adapter.customize.CustomizeViewModel;
import interface_adapter.info_collection.InfoCollectionViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;
import entity.Food;
import entity.MealType;

import java.util.Map;
import java.util.List;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final InfoCollectionViewModel infoCollectionViewModel;
    private final CustomizeViewModel customizeViewModel;

    public DashboardPresenter(ViewManagerModel viewManagerModel,
                              DashboardViewModel dashboardViewModel,
                              InfoCollectionViewModel infoCollectionViewModel,
                              CustomizeViewModel customizeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
        this.customizeViewModel = customizeViewModel;
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
        // Transfer relevant state to customize view
        CustomizeState customizeState = customizeViewModel.getState();
        DashboardState dashboardState = dashboardViewModel.getState();
        customizeState.setUsername(dashboardState.getUsername());
        customizeViewModel.setState(customizeState);

        viewManagerModel.setActiveView(customizeViewModel.getViewName());
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