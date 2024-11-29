package use_case.login;

import entity.CommonUser;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {
    private final String username;
    private final boolean profileComplete;
    private final CommonUser user;

    public LoginOutputData(String username, CommonUser user, boolean profileComplete) {
        this.username = username;
        this.user = user;
        this.profileComplete = profileComplete;
    }

    public String getUsername() {
        return username;
    }

    public CommonUser getUser() {
        return user;
    }

    public boolean isProfileComplete() {
        return profileComplete;
    }
}
