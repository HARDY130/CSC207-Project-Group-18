package interface_adapter.mealplanner;

import interface_adapter.ViewModel;

public class MealPlannerViewModel extends ViewModel<MealPlannerState> {
    // View constants
    public static final String TITLE_LABEL = "Meal Planner";
    public static final String GENERATE_BUTTON_LABEL = "Generate New Meals";
    public static final String DIET_FILTER_LABEL = "Dietary Preferences";
    public static final String BACK_BUTTON_LABEL = "Back";

    public static final String[] DIET_OPTIONS = {
            "Balanced", "High-Fiber", "High-Protein", "Low-Carb",
            "Low-Fat", "Low-Sodium", "Mediterranean", "Vegan", "Vegetarian"
    };

    public MealPlannerViewModel() {
        super("meal planner");
        setState(new MealPlannerState());
    }
}