package interface_adapter.customize;

import interface_adapter.ViewManagerModel;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.customize.CustomizeOutputBoundary;
import use_case.customize.CustomizeOutputData;
import use_case.dashboard.DashboardInputData;

import java.util.HashSet;

/**
 * The presenter for the customize page.
 */
public class CustomizePresenter implements CustomizeOutputBoundary {
    private final CustomizeViewModel customizeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final DashboardViewModel dashboardViewModel;
    private final DashboardController dashboardController;


    public CustomizePresenter(
            CustomizeViewModel customizeViewModel,
            ViewManagerModel viewManagerModel,
            DashboardViewModel dashboardViewModel, DashboardController dashboardController) {
        this.customizeViewModel = customizeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardController = dashboardController;

        if (dashboardController == null) {
            System.out.println("Warning: dashboardController is null");
        }
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
  
    /**
     * Presents the search results to the user.
     */

    public void presentDashboard() {
        // Create a new dashboard input data with the current user
        System.out.println("CustomizePresenter presentDashboard called");
        CustomizeState currentState = customizeViewModel.getState();
        DashboardInputData dashboardInputData = new DashboardInputData(
                currentState.getUsername()
        );

        // This will trigger the dashboard to refresh with the latest data
        dashboardController.execute(currentState.getUsername());

        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
