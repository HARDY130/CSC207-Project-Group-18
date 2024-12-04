package interface_adapter.customize;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.customize.CustomizeOutputBoundary;
import use_case.customize.CustomizeOutputData;

public class CustomizePresenter implements CustomizeOutputBoundary {
    private final CustomizeViewModel customizeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final DashboardViewModel dashboardViewModel;

    public CustomizePresenter(
            CustomizeViewModel customizeViewModel,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel) {
        this.customizeViewModel = customizeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void presentSearchResults(CustomizeOutputData outputData) {
        CustomizeState currentState = customizeViewModel.getState();
        currentState.setSearchResults(outputData.getSearchResults());
        currentState.setError(null);
        currentState.setSuccessMessage(null);
        currentState.setLoading(false);
        customizeViewModel.setState(currentState);
        customizeViewModel.firePropertyChanged();
    }

    @Override
    public void presentError(String error) {
        CustomizeState currentState = customizeViewModel.getState();
        currentState.setError(error);
        currentState.setSuccessMessage(null);
        currentState.setLoading(false);
        customizeViewModel.setState(currentState);
        customizeViewModel.firePropertyChanged();
    }

    @Override
    public void presentSuccess(String message) {
        CustomizeState currentState = customizeViewModel.getState();
        currentState.setSuccessMessage(message);
        currentState.setError(null);
        currentState.setLoading(false);
        customizeViewModel.setState(currentState);
        customizeViewModel.firePropertyChanged();
    }

    @Override
    public void presentDashboard() {
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}