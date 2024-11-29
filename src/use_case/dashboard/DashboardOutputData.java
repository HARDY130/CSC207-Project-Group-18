package use_case.dashboard;

import entity.Allergy;
import entity.Food;
import entity.MealType;

import java.util.*;

//public class DashboardOutputData {
//    // User Info
//    private final String username;
//
//    // Nutrition Goals
//    private final double dailyCalorieGoal;
//    private final double carbsGoalGrams;
//    private final double proteinGoalGrams;
//    private final double fatGoalGrams;
//
//    // Current Progress
//    private final double totalCaloriesConsumed;
//    private final double totalCarbsConsumed;
//    private final double totalProteinConsumed;
//    private final double totalFatConsumed;
//
//    // Progress Percentages
//    private final int caloriePercentage;
//    private final int carbsPercentage;
//    private final int proteinPercentage;
//    private final int fatPercentage;
//
//    // Meals Organization
//    private final Map<MealType, Map<String, Food>> meals;  // type -> {name -> food}
//
//    // Success/Error State
//    private final boolean success;
//    private final String message;
//
//    public DashboardOutputData(String username,
//                               double dailyCalorieGoal,
//                               double carbsGoalGrams,
//                               double proteinGoalGrams,
//                               double fatGoalGrams,
//                               double totalCaloriesConsumed,
//                               double totalCarbsConsumed,
//                               double totalProteinConsumed,
//                               double totalFatConsumed,
//                               Map<MealType, Map<String, Food>> meals,
//                               boolean success,
//                               String message) {
//        this.username = username;
//        this.dailyCalorieGoal = dailyCalorieGoal;
//        this.carbsGoalGrams = carbsGoalGrams;
//        this.proteinGoalGrams = proteinGoalGrams;
//        this.fatGoalGrams = fatGoalGrams;
//        this.totalCaloriesConsumed = totalCaloriesConsumed;
//        this.totalCarbsConsumed = totalCarbsConsumed;
//        this.totalProteinConsumed = totalProteinConsumed;
//        this.totalFatConsumed = totalFatConsumed;
//        this.meals = new EnumMap<>(meals);
//        this.success = success;
//        this.message = message;
//
//        // Calculate percentages
//        this.caloriePercentage = calculatePercentage(totalCaloriesConsumed, dailyCalorieGoal);
//        this.carbsPercentage = calculatePercentage(totalCarbsConsumed, carbsGoalGrams);
//        this.proteinPercentage = calculatePercentage(totalProteinConsumed, proteinGoalGrams);
//        this.fatPercentage = calculatePercentage(totalFatConsumed, fatGoalGrams);
//    }
//
//    private int calculatePercentage(double current, double goal) {
//        return goal > 0 ? (int)((current / goal) * 100) : 0;
//    }
//
//    // Getters for all fields
//    public String getUsername() { return username; }
//    public double getDailyCalorieGoal() { return dailyCalorieGoal; }
//    public double getCarbsGoalGrams() { return carbsGoalGrams; }
//    public double getProteinGoalGrams() { return proteinGoalGrams; }
//    public double getFatGoalGrams() { return fatGoalGrams; }
//    public double getTotalCaloriesConsumed() { return totalCaloriesConsumed; }
//    public double getTotalCarbsConsumed() { return totalCarbsConsumed; }
//    public double getTotalProteinConsumed() { return totalProteinConsumed; }
//    public double getTotalFatConsumed() { return totalFatConsumed; }
//    public int getCaloriePercentage() { return caloriePercentage; }
//    public int getCarbsPercentage() { return carbsPercentage; }
//    public int getProteinPercentage() { return proteinPercentage; }
//    public int getFatPercentage() { return fatPercentage; }
//    public Map<MealType, Map<String, Food>> getMeals() {
//        return new EnumMap<>(meals);
//    }
//    public boolean isSuccess() { return success; }
//    public String getMessage() { return message; }
//
//    // Helper method to get meals of specific type
//    public Map<String, Food> getMealsByType(MealType type) {
//        return new HashMap<>(meals.getOrDefault(type, new HashMap<>()));
//    }
public class DashboardOutputData {
    private final String username;
    private final double bmr;
    private final double tdee;
    private final double carbsGoal;
    private final double proteinGoal;
    private final double fatGoal;
    private final double consumedCalories;
    private final double consumedCarbs;
    private final double consumedProtein;
    private final double consumedFat;
    private final String activityLevel;
    private final Set<Allergy> allergies;
    private final boolean useCaseSuccess;

    public DashboardOutputData(String username, double bmr, double tdee,
                               double carbsGoal, double proteinGoal, double fatGoal,
                               double consumedCalories, double consumedCarbs,
                               double consumedProtein, double consumedFat,
                               String activityLevel, Set<Allergy> allergies,
                               boolean useCaseSuccess) {
        this.username = username;
        this.bmr = bmr;
        this.tdee = tdee;
        this.carbsGoal = carbsGoal;
        this.proteinGoal = proteinGoal;
        this.fatGoal = fatGoal;
        this.consumedCalories = consumedCalories;
        this.consumedCarbs = consumedCarbs;
        this.consumedProtein = consumedProtein;
        this.consumedFat = consumedFat;
        this.activityLevel = activityLevel;
        this.allergies = new HashSet<>(allergies);
        this.useCaseSuccess = useCaseSuccess;
    }

    // Getters
    public String getUsername() { return username; }
    public double getBmr() { return bmr; }
    public double getTdee() { return tdee; }
    public double getCarbsGoal() { return carbsGoal; }
    public double getProteinGoal() { return proteinGoal; }
    public double getFatGoal() { return fatGoal; }
    public double getConsumedCalories() { return consumedCalories; }
    public double getConsumedCarbs() { return consumedCarbs; }
    public double getConsumedProtein() { return consumedProtein; }
    public double getConsumedFat() { return consumedFat; }
    public String getActivityLevel() { return activityLevel; }
    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }
    public boolean isUseCaseSuccess() { return useCaseSuccess; }

}