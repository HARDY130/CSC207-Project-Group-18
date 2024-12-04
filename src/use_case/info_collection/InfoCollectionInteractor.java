package use_case.info_collection;

import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import entity.CommonUser;

public class InfoCollectionInteractor implements InfoCollectionInputBoundary {
    final InfoCollectionUserDataAccessInterface userDataAccessObject;
    final InfoCollectionOutputBoundary infoCollectionPresenter;
    final CommonUserFactory userFactory;

    public InfoCollectionInteractor(
            InfoCollectionUserDataAccessInterface userDataAccessInterface,
            InfoCollectionOutputBoundary infoOutputBoundary,
            UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessInterface;
        this.infoCollectionPresenter = infoOutputBoundary;
        this.userFactory = (CommonUserFactory) userFactory;
    }

    @Override
    public void execute(InfoCollectionInputData infoCollectionInputData) {
        try {
            if (userDataAccessObject.existsByName(infoCollectionInputData.getUsername())) {
                User existingUser = userDataAccessObject.get(infoCollectionInputData.getUsername());
                if (existingUser != null && !existingUser.getPassword().equals(infoCollectionInputData.getPassword())) {
                    infoCollectionPresenter.prepareFailView("Incorrect password for existing user.");
                    return;
                }
            }

            int currentYear = java.time.LocalDate.now().getYear();
            int age = currentYear - infoCollectionInputData.getBirthDate().getYear();
            if (age < 13 || age > 120) {
                infoCollectionPresenter.prepareFailView("Invalid age. Must be between 13 and 120 years old.");
                return;
            }

            if (infoCollectionInputData.getWeight() < 30 || infoCollectionInputData.getWeight() > 300) {
                infoCollectionPresenter.prepareFailView("Invalid weight. Must be between 30 and 300 kg.");
                return;
            }

            if (infoCollectionInputData.getHeight() < 100 || infoCollectionInputData.getHeight() > 250) {
                infoCollectionPresenter.prepareFailView("Invalid height. Must be between 100 and 250 cm.");
                return;
            }

            if (!infoCollectionInputData.getGender().equals("Male") &&
                    !infoCollectionInputData.getGender().equals("Female")) {
                infoCollectionPresenter.prepareFailView("Gender must be either 'Male' or 'Female'.");
                return;
            }

            User user = userFactory.createWithInfo(
                    infoCollectionInputData.getUsername(),
                    infoCollectionInputData.getPassword(),
                    infoCollectionInputData.getBirthDate(),
                    infoCollectionInputData.getGender(),
                    infoCollectionInputData.getWeight(),
                    infoCollectionInputData.getHeight(),
                    infoCollectionInputData.getActivityMultiplier(),
                    infoCollectionInputData.getAllergies()
            );

            userDataAccessObject.save(user);

            CommonUser commonUser = (CommonUser) user;

            InfoCollectionOutputData infoCollectionOutputData = new InfoCollectionOutputData(
                    user.getName(),
                    infoCollectionInputData.getBirthDate(),
                    infoCollectionInputData.getGender(),
                    infoCollectionInputData.getWeight(),
                    infoCollectionInputData.getHeight(),
                    infoCollectionInputData.getActivityMultiplier(),
                    infoCollectionInputData.getAllergies(),
                    commonUser.calculateBMR(),
                    commonUser.calculateTDEE(),
                    true,
                    null
            );

            infoCollectionPresenter.prepareSuccessView(infoCollectionOutputData);

        } catch (Exception e) {
            infoCollectionPresenter.prepareFailView(e.getMessage());
        }
    }
}