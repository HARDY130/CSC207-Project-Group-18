package use_case.logout;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private LogoutUserDataAccessInterface userDataAccessObject;
    private LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.logoutPresenter = logoutOutputBoundary;
        this.userDataAccessObject = userDataAccessInterface;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        // * get the username out of the input data,
        final String username = logoutInputData.username;
        // * set the username to null in the DAO
        userDataAccessObject.setCurrentUsername(null);
        // * instantiate the `LogoutOutputData`, which needs to contain the username.
        final LogoutOutputData logoutOutputData = new LogoutOutputData(username, true);
        // * tell the presenter to prepare a success view.
        logoutPresenter.prepareSuccessView(logoutOutputData);
    }
}

