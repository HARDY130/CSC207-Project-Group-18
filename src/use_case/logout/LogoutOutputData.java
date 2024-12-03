package use_case.logout;

import entity.CommonUser;

public class LogoutOutputData {
    private final String username;
    private final CommonUser user;
    private final boolean profileComplete;
    private final boolean useCaseFailed;

    public LogoutOutputData(String username, CommonUser user,
                            boolean profileComplete, boolean useCaseFailed) {
        this.username = username;
        this.user = user;
        this.profileComplete = profileComplete;
        this.useCaseFailed = useCaseFailed;
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

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}