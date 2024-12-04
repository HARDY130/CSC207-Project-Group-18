package use_case.logout;

import entity.User;

/**
 * Interface for the data access operations needed by the Logout use case.
 */
public interface LogoutUserDataAccessInterface {
    /**
     * Returns the username of the current user of the application.
     * @return the username of the current user
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     * @param username the new current username
     */
    void setCurrentUsername(String username);

    /**
     * Gets a user by their username.
     * @param username the username to look up
     * @return the User with the given username
     */
    User get(String username);

    /**
     * Checks if a user exists by their username.
     * @param username the username to check
     * @return true if the user exists, false otherwise
     */
    boolean existsByName(String username);
}