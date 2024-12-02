package interface_adapter.dashboard;

import interface_adapter.ViewModel;

public class DashboardViewModel extends ViewModel<DashboardState> {
    // View labels/constants
    public static final String TITLE_LABEL = "Dashboard";
    public static final String MACROS_TITLE = "Daily Macro Goals";
    public static final String CALORIES_LABEL = "Daily Calories:";
    public static final String ACTIVITY_LABEL = "Activity Level:";
    public static final String UPDATE_PROFILE_BUTTON_LABEL = "Update My Profile";
    public static final String GENERATE_MEAL_BUTTON_LABEL = "Generate Meal Plan";
    public static final String RECORD_MEAL_BUTTON_LABEL = "Record Meal";

    public DashboardViewModel() {
        super("dashboard"); // This is the view name that will be used to identify this view
        setState(new DashboardState());
    }
}