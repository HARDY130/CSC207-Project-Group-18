package use_case.meal_planner;

import java.util.Set;

public class MealPlannerInputData {
    private final Set<String> dietaryPreferences;
    private final String username;

    public MealPlannerInputData(Set<String> dietaryPreferences, String username) {
        this.dietaryPreferences = dietaryPreferences;
        this.username = username;
    }

    public String getUsername() { return username; }
    public Set<String> getDietaryPreferences() { return dietaryPreferences; }
}
