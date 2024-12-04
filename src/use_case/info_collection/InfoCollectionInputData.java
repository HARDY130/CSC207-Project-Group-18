package use_case.info_collection;

import entity.Allergy;

import java.time.LocalDate;
import java.util.Set;

/**
 * The Input Data for the Logout Use Case.
 */

public class InfoCollectionInputData {

    private final String username;
    private final String password;
    private final LocalDate birthDate;
    private final String gender;
    private final int weight;
    private final int height;
    private final double activityMultiplier;
    private final Set<Allergy> allergies;

    public InfoCollectionInputData(String username, String password, LocalDate birthDate,
                                   String gender, int weight, int height, double activityMultiplier, Set<Allergy> allergies) {
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.activityMultiplier = activityMultiplier;
        this.allergies = allergies;
    }


    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public LocalDate getBirthDate() { return birthDate; }

    public String getGender() { return gender; }

    public int getWeight() { return weight; }

    public int getHeight() { return height; }

    public double getActivityMultiplier() { return activityMultiplier; }

    public Set<Allergy> getAllergies() { return allergies; }
}