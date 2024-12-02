package use_case.home;

import data_access.FileUserDataAccessObject;
import entity.Food;

import java.util.Map;

public class HomeInteractor implements HomeInputBoundary {

    private final HomeOutputBoundary homePresenter;
    private final FileUserDataAccessObject fileUserDataAccessObject;

    public HomeInteractor(HomeOutputBoundary homePresenter, FileUserDataAccessObject fileUserDataAccessObject) {
        this.homePresenter = homePresenter;
        this.fileUserDataAccessObject = fileUserDataAccessObject;
    }


    @Override
    public void foodSearch(Map<String, String> foods) {

    }

    @Override
    public void mealPlanner(Map<String, Food[]> foods) {
        Food[] createdBreakfast = foods.get("B");
        Food[] createdLunch = foods.get("L");
        Food[] createdSupper = foods.get("S");
    }

    @Override
    public void personalInfo(String username) {

    }
}
