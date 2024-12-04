package use_case.login;

import entity.User;
import entity.CommonUser;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessObject,
                           LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
            return;
        }

        User user = userDataAccessObject.get(username);
        if (!password.equals(user.getPassword())) {
            loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            return;
        }

        userDataAccessObject.setCurrentUsername(username);

        CommonUser commonUser = null;
        boolean isProfileComplete = false;

        if (user instanceof CommonUser) {
            commonUser = (CommonUser) user;
            isProfileComplete = checkProfileComplete(commonUser);
        }

        LoginOutputData loginOutputData = new LoginOutputData(
                username,
                commonUser,
                isProfileComplete
        );

        loginPresenter.prepareSuccessView(loginOutputData);
    }

    private boolean checkProfileComplete(CommonUser user) {
        if (user == null) {
            return false;
        }
        return user.getBirthDate() != null &&
                !user.getGender().isEmpty() &&
                user.getWeight() > 0 &&
                user.getHeight() > 0 &&
                user.getActivityMultiplier() > 0;
    }
}