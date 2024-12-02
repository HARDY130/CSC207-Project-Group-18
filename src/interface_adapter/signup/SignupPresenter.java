package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.info_collection.InfoCollectionState;
import interface_adapter.info_collection.InfoCollectionViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final InfoCollectionViewModel infoCollectionViewModel;  // Added this field
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel,
                           InfoCollectionViewModel infoCollectionViewModel) {  // Added parameter
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.infoCollectionViewModel = infoCollectionViewModel;  // Store the parameter
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // Update info collection state with the username and password
        InfoCollectionState infoCollectionState = infoCollectionViewModel.getState();
        infoCollectionState.setUsername(response.getUsername());
        infoCollectionState.setPassword(response.getPassword());
        infoCollectionViewModel.setState(infoCollectionState);
        infoCollectionViewModel.firePropertyChanged();

        // Switch to info collection view
        viewManagerModel.setActiveView(infoCollectionViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}