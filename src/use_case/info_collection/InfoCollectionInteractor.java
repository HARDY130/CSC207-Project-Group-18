package use_case.info_collection;

import entity.CommonUser;
import java.time.LocalDate;

public class InfoCollectionInteractor implements InfoCollectionInputBoundary {
    final InfoCollectionUserDataAccessInterface userDataAccessObject;
    final InfoCollectionOutputBoundary infoCollectionPresenter;

    public InfoCollectionInteractor(
        InfoCollectionUserDataAccessInterface userDataAccessInterface,
        InfoCollectionOutputBoundary infoOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.infoCollectionPresenter = infoOutputBoundary;
    }

    @Override
    public void execute(InfoCollectionInputData infoCollectionInputData) {
        try {
            // Get existing user
            CommonUser user = (CommonUser) userDataAccessObject.get(infoCollectionInputData.getUsername());
            if (user == null) {
                infoCollectionPresenter.prepareFailView("User not found.");
                return;
            }

            UserUpdateInfo updateInfo = infoCollectionInputData.getUpdateInfo();

            // Validate update info
            validateUpdateInfo(updateInfo);

            // Update user information
            updateUserInfo(user, updateInfo);
            userDataAccessObject.save(user);

            // Prepare success view with updated information
            InfoCollectionOutputData outputData = new InfoCollectionOutputData(
                user.getName(),
                updateInfo.getBirthDate(),
                updateInfo.getGender(),
                updateInfo.getWeight(),
                updateInfo.getHeight(),
                updateInfo.getActivityMultiplier(),
                updateInfo.getAllergies(),
                user.calculateBMR(),
                user.calculateTDEE(),
                true,
                null
            );

            infoCollectionPresenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            infoCollectionPresenter.prepareFailView(e.getMessage());
        }
    }

    private void validateUpdateInfo(UserUpdateInfo info) {
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - info.getBirthDate().getYear();

        if (age < 13 || age > 120) {
            throw new IllegalArgumentException("Invalid age. Must be between 13 and 120 years old.");
        }

        if (info.getWeight() < 30 || info.getWeight() > 300) {
            throw new IllegalArgumentException("Invalid weight. Must be between 30 and 300 kg.");
        }

        if (info.getHeight() < 100 || info.getHeight() > 250) {
            throw new IllegalArgumentException("Invalid height. Must be between 100 and 250 cm.");
        }

        if (!info.getGender().equals("Male") && !info.getGender().equals("Female")) {
            throw new IllegalArgumentException("Gender must be either 'Male' or 'Female'.");
        }
    }

    private void updateUserInfo(CommonUser user, UserUpdateInfo updateInfo) {
        // Note: This requires adding setter methods to CommonUser
        user.setBirthDate(updateInfo.getBirthDate());
        user.setGender(updateInfo.getGender());
        user.setWeight(updateInfo.getWeight());
        user.setHeight(updateInfo.getHeight());
        user.setActivityMultiplier(updateInfo.getActivityMultiplier());
        user.setAllergies(updateInfo.getAllergies());
    }
}