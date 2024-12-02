package use_case.mealplanner;

import entity.Food;
import java.util.*;

public class MealPlannerOutputData {
    private final String username;
    private final List<Food> breakfastOptions;
    private final List<Food> lunchOptions;
    private final List<Food> dinnerOptions;
    private final boolean useCaseSuccess;
    private final String errorMessage;

    public MealPlannerOutputData(String username, List<Food> breakfastOptions,
                                 List<Food> lunchOptions, List<Food> dinnerOptions,
                                 boolean success, String errorMessage) {
        this.username = username;
        this.breakfastOptions = breakfastOptions;
        this.lunchOptions = lunchOptions;
        this.dinnerOptions = dinnerOptions;
        this.useCaseSuccess = success;
        this.errorMessage = errorMessage;
    }

    // Getters
    public String getUsername() { return username; }
    public List<Food> getBreakfastOptions() { return breakfastOptions; }
    public List<Food> getLunchOptions() { return lunchOptions; }
    public List<Food> getDinnerOptions() { return dinnerOptions; }
    public boolean isUseCaseSuccess() { return useCaseSuccess; }
    public String getErrorMessage() { return errorMessage; }
}
