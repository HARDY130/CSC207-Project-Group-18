package use_case.home;

import java.util.Map;

public class HomeInputData {

    private final String username;
    private final String yourWeight;
    private final String yourHeight;
    private final String[] createdBreakfast;
    private final String[] createdLunch;
    private final String[] createdDinner;


    public HomeInputData(String username, String yourWeight, String yourHeight, String[] createdBreakfast, String[] createdLunch, String[] createdDinner) {
        this.username = username;
        this.yourWeight = yourWeight;
        this.yourHeight = yourHeight;
        this.createdBreakfast = createdBreakfast;
        this.createdLunch = createdLunch;
        this.createdDinner = createdDinner;
    }

    public String getUsername() {
        return username;
    }

    public String getYourWeight() {
        return yourWeight;
    }

    public String getYourHeight() {
        return yourHeight;
    }

    public String[] getCreatedBreakfast() {
        return createdBreakfast;
    }

    public String[] getCreatedLunch() {
        return createdLunch;
    }

    public String[] getCreatedDinner() {
        return createdDinner;
    }
}
