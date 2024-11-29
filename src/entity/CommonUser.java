package entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class CommonUser implements User {
    private final String name;
    private final String password;
    private final LocalDate birthDate;
    private final String gender;
    private final int weight;
    private final int height;
    private final double activityMultiplier;
    private final Set<Allergy> allergies;

    // Constants for activity multipliers
    public static final double SEDENTARY = 1.2;
    public static final double LIGHTLY_ACTIVE = 1.375;
    public static final double MODERATELY_ACTIVE = 1.55;
    public static final double VERY_ACTIVE = 1.725;
    public static final double SUPER_ACTIVE = 1.9;

    // Constants for macronutrient calculations
    private static final double CARBS_PERCENT = 0.55;
    private static final double PROTEIN_PERCENT = 0.225;
    private static final double FAT_PERCENT = 0.275;

    private static final double CARBS_CALORIES_PER_GRAM = 4.0;
    private static final double PROTEIN_CALORIES_PER_GRAM = 4.0;
    private static final double FAT_CALORIES_PER_GRAM = 9.0;

    private final Map<MealType, Map<String, Food>> dailyMeals;

    public CommonUser(String name, String password, LocalDate birthDate, String gender,
                      int weight, int height, double activityMultiplier, Set<Allergy> allergies) {
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.activityMultiplier = activityMultiplier;
        this.allergies = new HashSet<>(allergies);
        this.dailyMeals = new EnumMap<>(MealType.class);
        for (MealType type : MealType.values()) {
            dailyMeals.put(type, new HashMap<>());
        }
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public Set<Allergy> getAllergies() {
        return new HashSet<>(allergies);
    }

    public double getActivityMultiplier() {
        return activityMultiplier;
    }

    public String getActivityLevel() {
        if (activityMultiplier == SEDENTARY) return "Sedentary (little or no exercise)";
        if (activityMultiplier == LIGHTLY_ACTIVE) return "Lightly active (light exercise/sports 1-3 days a week)";
        if (activityMultiplier == MODERATELY_ACTIVE) return "Moderately active (moderate exercise/sports 3-5 days a week)";
        if (activityMultiplier == VERY_ACTIVE) return "Very active (hard exercise/sports 6-7 days a week)";
        if (activityMultiplier == SUPER_ACTIVE) return "Super active (very hard exercise/physical job)";
        return "Activity level not specified";
    }

    public double calculateBMR() {
        if (gender.equalsIgnoreCase("Male")) {
            return (10 * weight) + (6.25 * height) - (5 * getAge()) + 5;
        } else {
            return (10 * weight) + (6.25 * height) - (5 * getAge()) - 161;
        }
    }

    public double calculateTDEE() {
        return calculateBMR() * activityMultiplier;
    }

    public double calculateCarbsGrams() {
        double carbsCalories = calculateTDEE() * CARBS_PERCENT;
        return carbsCalories / CARBS_CALORIES_PER_GRAM;
    }

    public double calculateProteinGrams() {
        double proteinCalories = calculateTDEE() * PROTEIN_PERCENT;
        return proteinCalories / PROTEIN_CALORIES_PER_GRAM;
    }

    public double calculateFatGrams() {
        double fatCalories = calculateTDEE() * FAT_PERCENT;
        return fatCalories / FAT_CALORIES_PER_GRAM;
    }

    // Method to log a meal
    public void addMeal(MealType type, String mealName, Food food) {
        dailyMeals.get(type).put(mealName, food);
    }

    // Method to get meals by type
    public Map<String, Food> getMealsByType(MealType type) {
        return new HashMap<>(dailyMeals.get(type));
    }

    // Method to get all meals
    public Map<MealType, Map<String, Food>> getAllMeals() {
        Map<MealType, Map<String, Food>> result = new EnumMap<>(MealType.class);
        for (MealType type : MealType.values()) {
            result.put(type, new HashMap<>(dailyMeals.get(type)));
        }
        return result;
    }

    // Method to clear meals for a new day
    public void clearMeals() {
        for (MealType type : MealType.values()) {
            dailyMeals.get(type).clear();
        }
    }
}