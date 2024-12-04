package interface_adapter.info_collection;

import entity.Allergy;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class InfoCollectionState {
    private String username = "";
    private String password = "";
    private int yearOfBirth = LocalDate.now().getYear() - 25; // default age 25
    private String gender = "";
    private int weight = 0;
    private int height = 0;
    private double activityMultiplier = 1.2; // default to sedentary
    private Set<Allergy> allergies = new HashSet<>();

    private String yearOfBirthError = "";
    private String weightError = "";
    private String heightError = "";
    private String genderError = "";
    private String allegySelectionError = "";

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

    public InfoCollectionState() {}

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getYearOfBirth() { return yearOfBirth; }
    public String getGender() { return gender; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }
    public double getActivityMultiplier() { return activityMultiplier; }
    public Set<Allergy> getAllergies() { return new HashSet<>(allergies); }

    public String getYearOfBirthError() { return yearOfBirthError; }
    public String getWeightError() { return weightError; }
    public String getHeightError() { return heightError; }
    public String getGenderError() { return genderError; }
    public String getAllergySelectionError() { return allegySelectionError; }

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

    public void clearErrors() {
        this.yearOfBirthError = "";
        this.weightError = "";
        this.heightError = "";
        this.genderError = "";
        this.allegySelectionError = "";
    }

    public boolean hasErrors() {
        return !yearOfBirthError.isEmpty()
                || !weightError.isEmpty()
                || !heightError.isEmpty()
                || !genderError.isEmpty()
                || !allegySelectionError.isEmpty();
    }

    public LocalDate getBirthDate() {
        return LocalDate.of(yearOfBirth, 1, 1);
    }

    public boolean isComplete() {
        return !username.isEmpty()
                && !password.isEmpty()
                && yearOfBirth > 0
                && !gender.isEmpty()
                && weight > 0
                && height > 0
                && activityMultiplier > 0;
    }
}
