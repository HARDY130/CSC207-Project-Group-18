package interface_adapter.mealplanner;

import entity.Allergy;
import entity.Food;
import java.util.*;

public class MealPlannerState {
    private String username = "";
    private List<Food> breakfastOptions = new ArrayList<>();
    private List<Food> lunchOptions = new ArrayList<>();
    private List<Food> dinnerOptions = new ArrayList<>();
    private Set<String> selectedDiets = new HashSet<>();
    private double dailyCalorieGoal = 0.0;
    private Set<Allergy> allergies = new HashSet<>();
    private String error = "";

    // Copy constructor
    public MealPlannerState(MealPlannerState copy) {
        this.username = copy.username;
        this.breakfastOptions = new ArrayList<>(copy.breakfastOptions);
        this.lunchOptions = new ArrayList<>(copy.lunchOptions);
        this.dinnerOptions = new ArrayList<>(copy.dinnerOptions);
        this.selectedDiets = new HashSet<>(copy.selectedDiets);
        this.dailyCalorieGoal = copy.dailyCalorieGoal;
        this.allergies = new HashSet<>(copy.allergies);
        this.error = copy.error;
    }

    public MealPlannerState() {}

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<Food> getBreakfastOptions() { return new ArrayList<>(breakfastOptions); }
    public void setBreakfastOptions(List<Food> options) {
        this.breakfastOptions = new ArrayList<>(options);
    }

    public List<Food> getLunchOptions() { return new ArrayList<>(lunchOptions); }
    public void setLunchOptions(List<Food> options) {
        this.lunchOptions = new ArrayList<>(options);
    }

    public List<Food> getDinnerOptions() { return new ArrayList<>(dinnerOptions); }
    public void setDinnerOptions(List<Food> options) {
        this.dinnerOptions = new ArrayList<>(options);
    }

    public Set<String> getSelectedDiets() { return new HashSet<>(selectedDiets); }
    public void setSelectedDiets(Set<String> diets) {
        this.selectedDiets = new HashSet<>(diets);
    }

    public double getDailyCalorieGoal() { return dailyCalorieGoal; }
    public void setDailyCalorieGoal(double dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }
    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = new HashSet<>(allergies);
    }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
