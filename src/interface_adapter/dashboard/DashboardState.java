package interface_adapter.dashboard;

import entity.Allergy;
import java.time.LocalDate;
import java.util.*;

public class DashboardState {
    // User information
    private String username = "";
    private LocalDate birthDate;
    private String gender = "";
    private int weight = 0;
    private int height = 0;
    private double activityMultiplier = 1.2;
    private String activityLevel = "";
    private Set<Allergy> allergies = new HashSet<>();

    // These are nutrition goals set based on personal info
    private double bmr = 0.0;
    private double tdee = 0.0;
    private double dailyCalorieGoal = 0.0;
    private double carbsGoalGrams = 0.0;
    private double proteinGoalGrams = 0.0;
    private double fatGoalGrams = 0.0;

    // These are current progress
    private double consumedCalories = 0.0;
    private double consumedCarbs = 0.0;
    private double consumedProtein = 0.0;
    private double consumedFat = 0.0;

    // UI state
    private String error = "";
    private String successMessage = "";
    private boolean isLoading = false;

    // Copy constructor
    public DashboardState(DashboardState copy) {
        this.username = copy.username;
        this.birthDate = copy.birthDate;
        this.gender = copy.gender;
        this.weight = copy.weight;
        this.height = copy.height;
        this.activityMultiplier = copy.activityMultiplier;
        this.activityLevel = copy.activityLevel;
        this.allergies = new HashSet<>(copy.allergies);
        this.bmr = copy.bmr;
        this.tdee = copy.tdee;
        this.dailyCalorieGoal = copy.dailyCalorieGoal;
        this.carbsGoalGrams = copy.carbsGoalGrams;
        this.proteinGoalGrams = copy.proteinGoalGrams;
        this.fatGoalGrams = copy.fatGoalGrams;
        this.consumedCalories = copy.consumedCalories;
        this.consumedCarbs = copy.consumedCarbs;
        this.consumedProtein = copy.consumedProtein;
        this.consumedFat = copy.consumedFat;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
        this.isLoading = copy.isLoading;
    }

    // Default constructor
    public DashboardState() {}

    // Base information getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public double getActivityMultiplier() { return activityMultiplier; }
    public void setActivityMultiplier(double activityMultiplier) { this.activityMultiplier = activityMultiplier; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }
    public void setAllergies(Set<Allergy> allergies) { this.allergies = new HashSet<>(allergies); }

    // Nutrition calculations getters and setters
    public double getBmr() { return bmr; }
    public void setBmr(double bmr) {
        this.bmr = bmr;
        updateNutritionGoals();
    }

    public double getTdee() { return tdee; }
    public void setTdee(double tdee) {
        this.tdee = tdee;
        this.dailyCalorieGoal = tdee;
        updateNutritionGoals();
    }

    public double getDailyCalorieGoal() { return dailyCalorieGoal; }
    public void setDailyCalorieGoal(double dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    // Macro goals
    public double getCarbsGoalGrams() { return carbsGoalGrams; }
    public void setCarbsGoalGrams(double carbsGoalGrams) { this.carbsGoalGrams = carbsGoalGrams; }

    public double getProteinGoalGrams() { return proteinGoalGrams; }
    public void setProteinGoalGrams(double proteinGoalGrams) { this.proteinGoalGrams = proteinGoalGrams; }

    public double getFatGoalGrams() { return fatGoalGrams; }
    public void setFatGoalGrams(double fatGoalGrams) { this.fatGoalGrams = fatGoalGrams; }

    // Consumption tracking
    public double getConsumedCalories() { return consumedCalories; }
    public void setConsumedCalories(double consumedCalories) { this.consumedCalories = consumedCalories; }

    public double getConsumedCarbs() { return consumedCarbs; }
    public void setConsumedCarbs(double consumedCarbs) { this.consumedCarbs = consumedCarbs; }

    public double getConsumedProtein() { return consumedProtein; }
    public void setConsumedProtein(double consumedProtein) { this.consumedProtein = consumedProtein; }

    public double getConsumedFat() { return consumedFat; }
    public void setConsumedFat(double consumedFat) { this.consumedFat = consumedFat; }

    // UI state getters and setters
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getSuccessMessage() { return successMessage; }
    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }

    public boolean isLoading() { return isLoading; }
    public void setLoading(boolean loading) { isLoading = loading; }

    // Progress calculation methods
    public int getCaloriePercentage() {
        return calculatePercentage(consumedCalories, dailyCalorieGoal);
    }

    public int getCarbsPercentage() {
        return calculatePercentage(consumedCarbs, carbsGoalGrams);
    }

    public int getProteinPercentage() {
        return calculatePercentage(consumedProtein, proteinGoalGrams);
    }

    public int getFatPercentage() {
        return calculatePercentage(consumedFat, fatGoalGrams);
    }

    // Helper methods
    private int calculatePercentage(double current, double goal) {
        return goal > 0 ? (int)((current / goal) * 100) : 0;
    }

    private void updateNutritionGoals() {
        // Calculate macro goals based on TDEE
        // Carbs: 45-65% (using 50%)
        carbsGoalGrams = (tdee * 0.50) / 4.0;  // 4 calories per gram

        // Protein: 10-35% (using 25%)
        proteinGoalGrams = (tdee * 0.25) / 4.0;  // 4 calories per gram

        // Fat: 20-35% (using 25%)
        fatGoalGrams = (tdee * 0.25) / 9.0;  // 9 calories per gram
    }

    // Formatting helpers for display
    public String getFormattedCalorieProgress() {
        return String.format("%.0f / %.0f kcal", consumedCalories, dailyCalorieGoal);
    }

    public String getFormattedCarbsProgress() {
        return String.format("%.1f / %.1f g", consumedCarbs, carbsGoalGrams);
    }

    public String getFormattedProteinProgress() {
        return String.format("%.1f / %.1f g", consumedProtein, proteinGoalGrams);
    }

    public String getFormattedFatProgress() {
        return String.format("%.1f / %.1f g", consumedFat, fatGoalGrams);
    }
}