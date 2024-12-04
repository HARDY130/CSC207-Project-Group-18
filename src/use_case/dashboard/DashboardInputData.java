package use_case.dashboard;

import entity.Allergy;
import java.time.LocalDate;
import java.util.Set;

public class DashboardInputData {
    private final String username;

    public DashboardInputData(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
}