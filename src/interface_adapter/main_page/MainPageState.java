package interface_adapter.main_page;

public class MainPageState {
    private final String userWeight;
    private final String userHeight;
    private final String[] createdBreakfast;
    private final String[] createdLunch;
    private final String[] createdSupper;

    public MainPageState(String userWeight, String userHeight, String[] createdBreakfast,
                         String[] createdLunch, String[] createdDinner) {
        this.userWeight = userWeight;
        this.userHeight = userHeight;
        this.createdBreakfast = createdBreakfast;
        this.createdLunch = createdLunch;
        this.createdSupper = createdDinner;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public String[] getCreatedBreakfast() {
        return createdBreakfast;
    }

    public String[] getCreatedLunch() {
        return createdLunch;
    }

    public String[] getCreatedSupper() {
        return createdSupper;
    }
}
