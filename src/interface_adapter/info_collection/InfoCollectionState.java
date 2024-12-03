package interface_adapter.info_collection;

import entity.Allergy;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class InfoCollectionState {
    // User data fields
    private String username = "";
    private String password = "";
    private int yearOfBirth = LocalDate.now().getYear() - 25; // default age 25
    private String gender = "";
    private int weight = 0;
    private int height = 0;
    private double activityMultiplier = 1.2; // default to sedentary
    private Set<Allergy> allergies = new HashSet<>();

    // Error message fields
    private String yearOfBirthError = "";
    private String weightError = "";
    private String heightError = "";
    private String genderError = "";
    private String allegySelectionError = "";

    // Copy constructor
    public InfoCollectionState(InfoCollectionState copy) {
        this.username = copy.username;
        this.password = copy.password;
        this.yearOfBirth = copy.yearOfBirth;
        this.gender = copy.gender;
        this.weight = copy.weight;
        this.height = copy.height;
        this.activityMultiplier = copy.activityMultiplier;
        this.allergies = new HashSet<>(copy.allergies);
        this.yearOfBirthError = copy.yearOfBirthError;
        this.weightError = copy.weightError;
        this.heightError = copy.heightError;
        this.genderError = copy.genderError;
        this.allegySelectionError = copy.allegySelectionError;
    }

    // Default constructor
    public InfoCollectionState() {}

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getYearOfBirth() { return yearOfBirth; }
    public String getGender() { return gender; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }
    public double getActivityMultiplier() { return activityMultiplier; }
    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }

    // Error getters
    public String getYearOfBirthError() { return yearOfBirthError; }
    public String getWeightError() { return weightError; }
    public String getHeightError() { return heightError; }
    public String getGenderError() { return genderError; }
    public String getAllergySelectionError() { return allegySelectionError; }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setActivityMultiplier(double activityMultiplier) {
        this.activityMultiplier = activityMultiplier;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = new HashSet<>(allergies);
    }

    // Error setters
    public void setYearOfBirthError(String error) {
        this.yearOfBirthError = error;
    }

    public void setWeightError(String error) {
        this.weightError = error;
    }

    public void setHeightError(String error) {
        this.heightError = error;
    }

    public void setGenderError(String error) {
        this.genderError = error;
    }

    public void setAllergySelectionError(String error) {
        this.allegySelectionError = error;
    }

    // Helper method to clear all error messages
    public void clearErrors() {
        this.yearOfBirthError = "";
        this.weightError = "";
        this.heightError = "";
        this.genderError = "";
        this.allegySelectionError = "";
    }

    // Helper method to check if there are any error messages
    public boolean hasErrors() {
        return !yearOfBirthError.isEmpty()
                || !weightError.isEmpty()
                || !heightError.isEmpty()
                || !genderError.isEmpty()
                || !allegySelectionError.isEmpty();
    }

    // Helper method to convert to LocalDate
    public LocalDate getBirthDate() {
        return LocalDate.of(yearOfBirth, 1, 1);
    }

    // Helper method to check if all required fields are filled
    public boolean isComplete() {
        return !username.isEmpty()
                && !password.isEmpty()
                && yearOfBirth > 0
                && !gender.isEmpty()
                && weight > 0
                && height > 0
                && activityMultiplier > 0;
        // Note: allergies can be empty as they're optional
    }
}
