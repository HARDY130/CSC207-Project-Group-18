package interface_adapter.info_collection;

import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.info_collection.InfoCollectionOutputBoundary;
import use_case.info_collection.InfoCollectionOutputData;

public class InfoCollectionPresenter implements InfoCollectionOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final InfoCollectionViewModel infoCollectionViewModel;
    private final DashboardViewModel dashboardViewModel;

    public InfoCollectionPresenter(ViewManagerModel viewManagerModel,
                                   InfoCollectionViewModel infoCollectionViewModel,
                                   DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(InfoCollectionOutputData response) {
        // First update info collection state
        InfoCollectionState infoCollectionState = infoCollectionViewModel.getState();
        infoCollectionState.setUsername(response.getUsername());
        infoCollectionViewModel.setState(infoCollectionState);
        infoCollectionViewModel.firePropertyChanged();

        // Prepare dashboard state
        DashboardState dashboardState = (DashboardState) dashboardViewModel.getState();

        // Set user information
        dashboardState.setUsername(response.getUsername());
        dashboardState.setBirthDate(response.getBirthDate());
        dashboardState.setGender(response.getGender());
        dashboardState.setWeight(response.getWeight());
        dashboardState.setHeight(response.getHeight());
        dashboardState.setActivityMultiplier(response.getActivityMultiplier());
        dashboardState.setAllergies(response.getAllergies());

        // Set calculated values
        dashboardState.setBmr(response.getCalculatedBMR());
        dashboardState.setTdee(response.getCalculatedTDEE());

        // Set activity level description based on multiplier
        dashboardState.setActivityLevel(getActivityLevelDescription(response.getActivityMultiplier()));

        // Clear any existing progress
        dashboardState.setConsumedCalories(0.0);
        dashboardState.setConsumedCarbs(0.0);
        dashboardState.setConsumedProtein(0.0);
        dashboardState.setConsumedFat(0.0);

        // Clear any error messages and set success message
        dashboardState.setError("");
        dashboardState.setSuccessMessage("Profile created successfully!");
        dashboardState.setLoading(false);

        // Update dashboard view model
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

    private String getActivityLevelDescription(double multiplier) {
        return switch ((int) (multiplier * 10)) {
            case 12 -> "Sedentary (little or no exercise)";
            case 13, 14 -> "Lightly active (light exercise 1-3 days/week)";
            case 15, 16 -> "Moderately active (moderate exercise 3-5 days/week)";
            case 17, 18 -> "Very active (hard exercise 6-7 days/week)";
            case 19, 20 -> "Super active (very hard exercise/physical job)";
            default -> "Unknown activity level";
        };
    }
}