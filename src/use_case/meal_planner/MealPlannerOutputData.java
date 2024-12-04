package use_case.meal_planner;

import entity.Food;

import java.util.List;

public class MealPlannerOutputData {
    private final String username;
    private final List<Food> breakfastOptions;
    private final List<Food> lunchOptions;
    private final List<Food> dinnerOptions;

    public MealPlannerOutputData(String username, List<Food> breakfastOptions,
                                 List<Food> lunchOptions, List<Food> dinnerOptions) {
        this.username = username;
        this.breakfastOptions = breakfastOptions;
        this.lunchOptions = lunchOptions;
        this.dinnerOptions = dinnerOptions;
    }

    public String getUsername() { return username; }
    public List<Food> getBreakfastOptions() { return breakfastOptions; }
    public List<Food> getLunchOptions() { return lunchOptions; }
    public List<Food> getDinnerOptions() { return dinnerOptions; }
}
