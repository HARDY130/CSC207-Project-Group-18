package use_case.dashboard;

//import entity.Allergy;
//import entity.Food;
//import entity.MealType;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//public class DashboardInputData {
//    private final String username;
//    private final LocalDate birthDate;
//    private final String gender;
//    private final int weight;
//    private final int height;
//    private final double activityMultiplier;
//    private final Set<Allergy> allergies;
//    private final double consumedCalories;
//    private final double consumedCarbs;
//    private final double consumedProtein;
//    private final double consumedFat;
//
//    public DashboardInputData(String username, LocalDate birthDate, String gender,
//                              int weight, int height, double activityMultiplier,
//                              Set<Allergy> allergies, double consumedCalories,
//                              double consumedCarbs, double consumedProtein,
//                              double consumedFat) {
//        this.username = username;
//        this.birthDate = birthDate;
//        this.gender = gender;
//        this.weight = weight;
//        this.height = height;
//        this.activityMultiplier = activityMultiplier;
//        this.allergies = new HashSet<>(allergies);
//        this.consumedCalories = consumedCalories;
//        this.consumedCarbs = consumedCarbs;
//        this.consumedProtein = consumedProtein;
//        this.consumedFat = consumedFat;
//    }
//
//    public String getUsername() { return username; }
//    public LocalDate getBirthDate() { return birthDate; }
//    public String getGender() { return gender; }
//    public int getWeight() { return weight; }
//    public int getHeight() { return height; }
//    public double getActivityMultiplier() { return activityMultiplier; }
//    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }
//    public double getConsumedCalories() { return consumedCalories; }
//    public double getConsumedCarbs() { return consumedCarbs; }
//    public double getConsumedProtein() { return consumedProtein; }
//    public double getConsumedFat() { return consumedFat; }
//}

import entity.Allergy;
import java.time.LocalDate;
import java.util.Set;

public class DashboardInputData {
    private final String username;
    private final LocalDate birthDate;
    private final String gender;
    private final int weight;
    private final int height;
    private final double activityMultiplier;
    private final Set<Allergy> allergies;

    public DashboardInputData(String username, LocalDate birthDate, String gender,
                              int weight, int height, double activityMultiplier,
                              Set<Allergy> allergies) {
        this.username = username;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.activityMultiplier = activityMultiplier;
        this.allergies = allergies;
    }

    // Getters
    public String getUsername() { return username; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getGender() { return gender; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }
    public double getActivityMultiplier() { return activityMultiplier; }
    public Set<Allergy> getAllergies() { return allergies; }
}