package use_case.logout;

import entity.CommonUser;
import entity.User;

/**
 * The Logout Interactor.
 */
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
        
        userDataAccessObject.setCurrentUsername(null);
        
        final LogoutOutputData logoutOutputData = new LogoutOutputData(
                username,    
                commonUser, 
                isProfileComplete, 
                false      
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

