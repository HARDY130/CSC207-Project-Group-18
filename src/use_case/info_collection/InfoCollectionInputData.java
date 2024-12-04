package use_case.info_collection;

import entity.Allergy;

import java.time.LocalDate;
import java.util.Set;

/**
 * The Input Data for the Logout Use Case.
 */

public class InfoCollectionInputData {
    private final String username;
    private final UserUpdateInfo updateInfo;

    public InfoCollectionInputData(String username, UserUpdateInfo updateInfo) {
        this.username = username;
        this.updateInfo = updateInfo;
    }

    public String getUsername() {
        return username;
    }

    public UserUpdateInfo getUpdateInfo() {
        return updateInfo;
    }
}