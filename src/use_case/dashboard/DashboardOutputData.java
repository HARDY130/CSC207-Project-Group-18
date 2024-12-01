package use_case.dashboard;

//import entity.Allergy;
//import entity.Food;
//import entity.MealType;
//
//import java.util.*;
//
//public class DashboardOutputData {
//    private final String username;
//    private final double bmr;
//    private final double tdee;
//    private final double carbsGoal;
//    private final double proteinGoal;
//    private final double fatGoal;
//    private final double consumedCalories;
//    private final double consumedCarbs;
//    private final double consumedProtein;
//    private final double consumedFat;
//    private final String activityLevel;
//    private final Set<Allergy> allergies;
//    private final boolean useCaseSuccess;
//
//    public DashboardOutputData(String username, double bmr, double tdee,
//                               double carbsGoal, double proteinGoal, double fatGoal,
//                               double consumedCalories, double consumedCarbs,
//                               double consumedProtein, double consumedFat,
//                               String activityLevel, Set<Allergy> allergies,
//                               boolean useCaseSuccess) {
//        this.username = username;
//        this.bmr = bmr;
//        this.tdee = tdee;
//        this.carbsGoal = carbsGoal;
//        this.proteinGoal = proteinGoal;
//        this.fatGoal = fatGoal;
//        this.consumedCalories = consumedCalories;
//        this.consumedCarbs = consumedCarbs;
//        this.consumedProtein = consumedProtein;
//        this.consumedFat = consumedFat;
//        this.activityLevel = activityLevel;
//        this.allergies = new HashSet<>(allergies);
//        this.useCaseSuccess = useCaseSuccess;
//    }
//
//    public String getUsername() { return username; }
//    public double getBmr() { return bmr; }
//    public double getTdee() { return tdee; }
//    public double getCarbsGoal() { return carbsGoal; }
//    public double getProteinGoal() { return proteinGoal; }
//    public double getFatGoal() { return fatGoal; }
//    public double getConsumedCalories() { return consumedCalories; }
//    public double getConsumedCarbs() { return consumedCarbs; }
//    public double getConsumedProtein() { return consumedProtein; }
//    public double getConsumedFat() { return consumedFat; }
//    public String getActivityLevel() { return activityLevel; }
//    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }
//    public boolean isUseCaseSuccess() { return useCaseSuccess; }
//
//}

import entity.Allergy;
import entity.Food;
import entity.MealType;
import java.util.*;

public class DashboardOutputData {
    private final String username;
    private final double bmr;
    private final double tdee;
    private final double carbsGoal;
    private final double proteinGoal;
    private final double fatGoal;
    private final Map<MealType, Map<String, Food>> meals;
    private final double consumedCalories;
    private final double consumedCarbs;
    private final double consumedProtein;
    private final double consumedFat;
    private final String activityLevel;
    private final Set<Allergy> allergies;
    private final boolean useCaseSuccess;
    private final String errorMessage;

    public DashboardOutputData(String username, double bmr, double tdee,
                               double carbsGoal, double proteinGoal, double fatGoal,
                               Map<MealType, Map<String, Food>> meals,
                               double consumedCalories, double consumedCarbs,
                               double consumedProtein, double consumedFat,
                               String activityLevel, Set<Allergy> allergies,
                               boolean useCaseSuccess, String errorMessage) {
        this.username = username;
        this.bmr = bmr;
        this.tdee = tdee;
        this.carbsGoal = carbsGoal;
        this.proteinGoal = proteinGoal;
        this.fatGoal = fatGoal;
        this.meals = meals;
        this.consumedCalories = consumedCalories;
        this.consumedCarbs = consumedCarbs;
        this.consumedProtein = consumedProtein;
        this.consumedFat = consumedFat;
        this.activityLevel = activityLevel;
        this.allergies = allergies;
        this.useCaseSuccess = useCaseSuccess;
        this.errorMessage = errorMessage;
    }

    // Getters
    public String getUsername() { return username; }
    public double getBmr() { return bmr; }
    public double getTdee() { return tdee; }
    public double getCarbsGoal() { return carbsGoal; }
    public double getProteinGoal() { return proteinGoal; }
    public double getFatGoal() { return fatGoal; }
    public Map<MealType, Map<String, Food>> getMeals() { return meals; }
    public double getConsumedCalories() { return consumedCalories; }
    public double getConsumedCarbs() { return consumedCarbs; }
    public double getConsumedProtein() { return consumedProtein; }
    public double getConsumedFat() { return consumedFat; }
    public String getActivityLevel() { return activityLevel; }
    public Set<Allergy> getAllergies() { return allergies; }
    public boolean isUseCaseSuccess() { return useCaseSuccess; }
    public String getErrorMessage() { return errorMessage; }
}