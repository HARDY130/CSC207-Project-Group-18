package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.info_collection.InfoCollectionState;
import interface_adapter.info_collection.InfoCollectionViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import entity.CommonUser;

public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final LoginViewModel loginViewModel;
    private final InfoCollectionViewModel infoCollectionViewModel;
    private final DashboardViewModel dashboardViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel,
                          InfoCollectionViewModel infoCollectionViewModel,
                          DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        if (response.isProfileComplete()) {
            DashboardState dashboardState = new DashboardState();
            dashboardState.setUsername(response.getUsername());

            // Set other necessary state properties from response
            CommonUser user = response.getUser();
            if (user != null) {
                dashboardState.setBirthDate(user.getBirthDate());
                dashboardState.setGender(user.getGender());
                dashboardState.setWeight(user.getWeight());
                dashboardState.setHeight(user.getHeight());
                dashboardState.setActivityMultiplier(user.getActivityMultiplier());
                dashboardState.setAllergies(user.getAllergies());
                dashboardState.setBmr(user.calculateBMR());
                dashboardState.setTdee(user.calculateTDEE());
            }

            dashboardViewModel.setState(dashboardState);
            dashboardViewModel.firePropertyChanged();
            viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        } else {
            prepareRedirectToInfoCollection(response);
        }
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareRedirectToInfoCollection(LoginOutputData response) {
        InfoCollectionState infoCollectionState = infoCollectionViewModel.getState();
        infoCollectionState.setUsername(response.getUsername());

        // If we have any partial user data, we can set it here
        CommonUser user = response.getUser();
        if (user != null) {
            if (user.getBirthDate() != null) {
                infoCollectionState.setYearOfBirth(user.getBirthDate().getYear());
            }
            if (!user.getGender().isEmpty()) {
                infoCollectionState.setGender(user.getGender());
            }
            if (user.getWeight() > 0) {
                infoCollectionState.setWeight(user.getWeight());
            }
            if (user.getHeight() > 0) {
                infoCollectionState.setHeight(user.getHeight());
            }
            if (user.getActivityMultiplier() > 0) {
                infoCollectionState.setActivityMultiplier(user.getActivityMultiplier());
            }
            infoCollectionState.setAllergies(user.getAllergies());
        }

        infoCollectionViewModel.setState(infoCollectionState);
        infoCollectionViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(infoCollectionViewModel.getViewName());
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }
}