package use_case.logout;

/**
 * The Logout Interactor.
 */
//public class LogoutInteractor implements LogoutInputBoundary {
//    private LogoutUserDataAccessInterface userDataAccessObject;
//    private LogoutOutputBoundary logoutPresenter;
//
//    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
//                            LogoutOutputBoundary logoutOutputBoundary) {
//        this.logoutPresenter = logoutOutputBoundary;
//        this.userDataAccessObject = userDataAccessInterface;
//    }
//
//    @Override
//    public void execute(LogoutInputData logoutInputData) {
//        // * get the username out of the input data,
//        final String username = logoutInputData.username;
//        // * set the username to null in the DAO
//        userDataAccessObject.setCurrentUsername(null);
//        // * instantiate the `LogoutOutputData`, which needs to contain the username.
//        final LogoutOutputData logoutOutputData = new LogoutOutputData(username, true);
//        // * tell the presenter to prepare a success view.
//        logoutPresenter.prepareSuccessView(logoutOutputData);
//    }

import entity.CommonUser;
import entity.User;

public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutUserDataAccessInterface userDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        final String username = logoutInputData.getUsername();

        // Get user data before logging out
        User user = userDataAccessObject.get(username);
        CommonUser commonUser = (user instanceof CommonUser) ? (CommonUser) user : null;
        boolean isProfileComplete = false;

        if (commonUser != null) {
            isProfileComplete = checkProfileComplete(commonUser);
        }

        // Set current user to null (logout)
        userDataAccessObject.setCurrentUsername(null);

        // Create output data with all required information
        final LogoutOutputData logoutOutputData = new LogoutOutputData(
            username,    // username being logged out
            commonUser, // user data if needed for view transition
            isProfileComplete,  // profile status
            false       // useCase didn't fail
        );

        logoutPresenter.prepareSuccessView(logoutOutputData);
    }

    private boolean checkProfileComplete(CommonUser user) {
        return user.getBirthDate() != null &&
            !user.getGender().isEmpty() &&
            user.getWeight() > 0 &&
            user.getHeight() > 0 &&
            user.getActivityMultiplier() > 0;
    }
}

