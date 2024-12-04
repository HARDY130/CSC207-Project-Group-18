package use_case.mealplanner;

import java.util.Set;

public class MealPlannerInputData {
//    private final String username;
    private final Set<String> dietaryPreferences;
    private final String username;

    public MealPlannerInputData(Set<String> dietaryPreferences, String username) {
//        this.username = username;
        this.dietaryPreferences = dietaryPreferences;
        this.username = username;
    }

    public String getUsername() { return username; }
    public Set<String> getDietaryPreferences() { return dietaryPreferences; }
}