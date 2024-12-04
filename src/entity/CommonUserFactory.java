package entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Factory for creating CommonUser objects.
 */

public class CommonUserFactory implements UserFactory {
    @Override
    public User create(String name, String password) {
        return new CommonUser(name, password, LocalDate.now(), "", 0, 0, CommonUser.SEDENTARY, new HashSet<>());
    }

    public User createWithInfo(String name, String password, LocalDate birthDate,
                               String gender, int weight, int height, double activityMultiplier, Set<Allergy> allergies) {
        return new CommonUser(name, password, birthDate, gender, weight, height, activityMultiplier, allergies);
    }
}
