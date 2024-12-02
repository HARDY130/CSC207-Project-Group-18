package use_case.login;

import entity.CommonUser;
import entity.User;

import java.time.LocalDate;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        } else {
            String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            } else {
                User user = userDataAccessObject.get(loginInputData.getUsername());

                // Check if user info is complete
                if (user instanceof CommonUser) {
                    CommonUser commonUser = (CommonUser) user;
                    if (isUserInfoIncomplete(commonUser)) {
                        // Redirect to info collection if incomplete
                        loginPresenter.prepareRedirectToInfoCollection(
                                new LoginOutputData(username, false)
                        );
                    } else {
                        // Normal login success flow
                        userDataAccessObject.setCurrentUsername(user.getName());
                        LoginOutputData loginOutputData = new LoginOutputData(user.getName(), false);
                        loginPresenter.prepareSuccessView(loginOutputData);
                    }
                }
            }
        }
    }

    private boolean isUserInfoIncomplete(CommonUser user) {
        return user.getWeight() == 0
                || user.getHeight() == 0
                || user.getGender().isEmpty()
                || user.getBirthDate().equals(LocalDate.now()); // default date indicates not set
    }
}
