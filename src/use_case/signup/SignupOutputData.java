package use_case.signup;

/**
 * Output Data for the Signup Use Case.
 */

public class SignupOutputData {
    private final String username;
    private final String password;  // Added this field
    private final boolean useCaseFailed;

    public SignupOutputData(String username, String password, boolean useCaseFailed) {  // Added password parameter
        this.username = username;
        this.password = password;  // Store password
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {  // Add getter for password
        return password;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}