package use_case.meal_planner;

import entity.CommonUser;
import entity.Food;
import entity.MealType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealPlannerInteractor implements MealPlannerInputBoundary {
    final MealStorageDataAccessInterface mealStorageDataAccessInterface;
    final MealPlannerDataAccessInterface mealPlannerDataAccessObject;
    final MealPlannerOutputBoundary mealPlannerPresenter;

    public MealPlannerInteractor(
            MealStorageDataAccessInterface mealStorageDataAccessInterface,
            MealPlannerDataAccessInterface dataAccessInterface,
            MealPlannerOutputBoundary outputBoundary) {
        this.mealStorageDataAccessInterface = mealStorageDataAccessInterface;
        this.mealPlannerDataAccessObject = dataAccessInterface;
        this.mealPlannerPresenter = outputBoundary;
    }

    @Override
    public void execute(MealPlannerInputData inputData) {
        try {
            String currentUsername = mealStorageDataAccessInterface.existsByName(inputData.getUsername())
                    ? inputData.getUsername() : null;

            if (currentUsername == null || !mealStorageDataAccessInterface.existsByName(currentUsername)) {
                mealPlannerPresenter.prepareFailView("User not found.");
                return;
            }

            CommonUser user = (CommonUser) mealStorageDataAccessInterface.get(currentUsername);
            Map<MealType, List<Food>> mealPlan = mealPlannerDataAccessObject.generateMealPlan(
                    user,
                    new ArrayList<>(inputData.getDietaryPreferences())
            );

            MealPlannerOutputData outputData = new MealPlannerOutputData(
                    currentUsername,
                    mealPlan.get(MealType.BREAKFAST),
                    mealPlan.get(MealType.LUNCH),
                    mealPlan.get(MealType.DINNER),
                    true,
                    null
            );

            mealPlannerPresenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            mealPlannerPresenter.prepareFailView(e.getMessage());
        }
    }

    @Override
    public void addMealToUser(String username, String mealType, Food food) {
        try {
            mealStorageDataAccessInterface.addMealToUser(username, mealType, food);
        } catch (Exception e) {
            mealPlannerPresenter.prepareFailView(e.getMessage());
        }
    }

    @Override
    public void goHome() {
        mealPlannerPresenter.backToDashboard();
    }
}
