package use_case.info_collection;

import entity.Allergy;
import java.time.LocalDate;
import java.util.Set;

public class InfoCollectionOutputData {
    private final String username;
    private final LocalDate birthDate;
    private final String gender;
    private final int weight;
    private final int height;
    private final double activityMultiplier;
    private final Set<Allergy> allergies;
    private final double calculatedBMR;
    private final double calculatedTDEE;

    public InfoCollectionOutputData(String username, LocalDate birthDate,
                                    String gender, int weight, int height,
                                    double activityMultiplier, Set<Allergy> allergies,
                                    double calculatedBMR, double calculatedTDEE) {
        this.username = username;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.activityMultiplier = activityMultiplier;
        this.allergies = allergies;
        this.calculatedBMR = calculatedBMR;
        this.calculatedTDEE = calculatedTDEE;
    }

    public String getUsername() { return username; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getGender() { return gender; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }
    public double getActivityMultiplier() { return activityMultiplier; }
    public Set<Allergy> getAllergies() { return allergies; }
    public double getCalculatedBMR() { return calculatedBMR; }
    public double getCalculatedTDEE() { return calculatedTDEE; }
}