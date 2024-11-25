package use_case.main_page;

public class MainPanelOutputData {
    private final String username;
    private final String weight;
    private final String height;
    private final String[] breakfast;
    private final String[] lunch;
    private final String[] supper;

    public MainPanelOutputData(String username, String weight, String height, String[] breakfast, String[] lunch, String[] supper) {
        this.username = username;
        this.weight = weight;
        this.height = height;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.supper = supper;
    }

    public String getUsername() {
        return username;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String[] getBreakfast() {
        return breakfast;
    }

    public String[] getLunch() {
        return lunch;
    }

    public String[] getSupper() {
        return supper;
    }
}
