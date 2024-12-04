package interface_adapter.dashboard;

import interface_adapter.ViewModel;
import entity.MealType;

import java.util.Map;

public class DashboardViewModel extends ViewModel<DashboardState> {
    public static final String TITLE_LABEL = "Dashboard";
    public static final String WELCOME_LABEL = "Welcome, ";
    public static final String UPDATE_PROFILE_BUTTON_LABEL = "Update Profile";
    public static final String GENERATE_MEAL_BUTTON_LABEL = "Generate Meal Plan";
    public static final String RECORD_MEAL_BUTTON_LABEL = "Record Meal";
    public static final String LOGOUT_BUTTON_LABEL = "Logout";

    public static final Map<MealType, String> MEAL_LABELS = Map.of(
            MealType.BREAKFAST, "Breakfast",
            MealType.LUNCH, "Lunch",
            MealType.DINNER, "Dinner"
    );

    public DashboardViewModel() {
        super("dashboard");
        setState(new DashboardState());
    }
}