package interface_adapter.info_collection;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.info_collection.InfoCollectionOutputBoundary;
import use_case.info_collection.InfoCollectionOutputData;

public class InfoCollectionPresenter implements InfoCollectionOutputBoundary {
    private final InfoCollectionViewModel infoCollectionViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public InfoCollectionPresenter(ViewManagerModel viewManagerModel,
                                   InfoCollectionViewModel infoCollectionViewModel,
                                   DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(InfoCollectionOutputData response) {
        // Update info collection state
        InfoCollectionState infoCollectionState = infoCollectionViewModel.getState();
        infoCollectionState.setUsername(response.getUsername());
        infoCollectionViewModel.setState(infoCollectionState);
        infoCollectionViewModel.firePropertyChanged();

        // Prepare dashboard state
        DashboardState dashboardState = dashboardViewModel.getState();
        dashboardState.setUsername(response.getUsername());
        dashboardState.setBirthDate(response.getBirthDate());
        dashboardState.setGender(response.getGender());
        dashboardState.setWeight(response.getWeight());
        dashboardState.setHeight(response.getHeight());
        dashboardState.setActivityMultiplier(response.getActivityMultiplier());
        dashboardState.setAllergies(response.getAllergies());
        dashboardState.setBmr(response.getCalculatedBMR());
        dashboardState.setTdee(response.getCalculatedTDEE());

        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChanged();

        // Switch to dashboard view
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        InfoCollectionState infoCollectionState = infoCollectionViewModel.getState();
        if (error.contains("age") || error.contains("birth")) {
            infoCollectionState.setYearOfBirthError(error);
        } else if (error.contains("weight")) {
            infoCollectionState.setWeightError(error);
        } else if (error.contains("height")) {
            infoCollectionState.setHeightError(error);
        } else if (error.contains("gender")) {
            infoCollectionState.setGenderError(error);
        }
        infoCollectionViewModel.firePropertyChanged();
    }
}