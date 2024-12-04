package interface_adapter.info_collection;

import use_case.info_collection.InfoCollectionInputBoundary;
import use_case.info_collection.InfoCollectionInputData;
import java.time.LocalDate;
import java.util.Set;
import entity.Allergy;
import use_case.info_collection.UserUpdateInfo;

public class InfoCollectionController {
    final InfoCollectionInputBoundary infoCollectionUseCaseInteractor;

    public InfoCollectionController(InfoCollectionInputBoundary infoCollectionUseCaseInteractor) {
        this.infoCollectionUseCaseInteractor = infoCollectionUseCaseInteractor;
    }

    public void execute(String username, int yearOfBirth,
                        String gender, int weight, int height,
                        double activityMultiplier, Set<Allergy> allergies) {

        LocalDate birthDate = LocalDate.of(yearOfBirth, 1, 1);
        UserUpdateInfo updateInfo = new UserUpdateInfo(
            birthDate, gender, weight, height, activityMultiplier, allergies);

        InfoCollectionInputData inputData = new InfoCollectionInputData(username, updateInfo);
        infoCollectionUseCaseInteractor.execute(inputData);
    }
}
