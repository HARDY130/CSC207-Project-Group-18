package interface_adapter.dashboard;

import entity.Allergy;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DashboardState {
    // User info
    private String username = "";
    private LocalDate birthDate;
    private String gender = "";
    private int weight = 0;
    private int height = 0;
    private double activityMultiplier = 1.2;
    private Set<Allergy> allergies = new HashSet<>();

    // Calculated values
    private double bmr = 0.0;
    private double tdee = 0.0;
    private double carbsTarget = 0.0;
    private double proteinTarget = 0.0;
    private double fatTarget = 0.0;

    // Progress tracking
    private double carbsProgress = 0.0;
    private double proteinProgress = 0.0;
    private double fatProgress = 0.0;

    // Copy constructor
    public DashboardState(DashboardState copy) {
        this.username = copy.username;
        this.birthDate = copy.birthDate;
        this.gender = copy.gender;
        this.weight = copy.weight;
        this.height = copy.height;
        this.activityMultiplier = copy.activityMultiplier;
        this.allergies = new HashSet<>(copy.allergies);
        this.bmr = copy.bmr;
        this.tdee = copy.tdee;
        this.carbsTarget = copy.carbsTarget;
        this.proteinTarget = copy.proteinTarget;
        this.fatTarget = copy.fatTarget;
        this.carbsProgress = copy.carbsProgress;
        this.proteinProgress = copy.proteinProgress;
        this.fatProgress = copy.fatProgress;
    }

    // Default constructor
    public DashboardState() {}

    // Getters and setters for user info
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
    public void setActivityMultiplier(double activityMultiplier) {
        this.activityMultiplier = activityMultiplier;
    }

    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }
    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = new HashSet<>(allergies);
    }

    // Getters and setters for calculated values
    public double getBmr() { return bmr; }
    public void setBmr(double bmr) {
        this.bmr = bmr;
        // Update macro targets based on TDEE
        updateMacroTargets();
    }

    public double getTdee() { return tdee; }
    public void setTdee(double tdee) {
        this.tdee = tdee;
        // Update macro targets based on new TDEE
        updateMacroTargets();
    }

    // Getters and setters for macro targets
    public double getCarbsTarget() { return carbsTarget; }
    public double getProteinTarget() { return proteinTarget; }
    public double getFatTarget() { return fatTarget; }

    // Getters and setters for progress
    public double getCarbsProgress() { return carbsProgress; }
    public void setCarbsProgress(double carbsProgress) {
        this.carbsProgress = carbsProgress;
    }

    public double getProteinProgress() { return proteinProgress; }
    public void setProteinProgress(double proteinProgress) {
        this.proteinProgress = proteinProgress;
    }

    public double getFatProgress() { return fatProgress; }
    public void setFatProgress(double fatProgress) {
        this.fatProgress = fatProgress;
    }

    // Helper method to update macro targets based on TDEE
    private void updateMacroTargets() {
        // Carbs: 45-65% of calories (using 55%)
        carbsTarget = (tdee * 0.55) / 4.0; // 4 calories per gram of carbs

        // Protein: 10-35% of calories (using 25%)
        proteinTarget = (tdee * 0.25) / 4.0; // 4 calories per gram of protein

        // Fat: 20-35% of calories (using 20%)
        fatTarget = (tdee * 0.20) / 9.0; // 9 calories per gram of fat
    }

    // Helper method to calculate macro percentages
    public int getCarbsPercentage() {
        return carbsTarget > 0 ? (int)((carbsProgress / carbsTarget) * 100) : 0;
    }

    public int getProteinPercentage() {
        return proteinTarget > 0 ? (int)((proteinProgress / proteinTarget) * 100) : 0;
    }

    public int getFatPercentage() {
        return fatTarget > 0 ? (int)((fatProgress / fatTarget) * 100) : 0;
    }
}