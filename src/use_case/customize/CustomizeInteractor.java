package use_case.customize;

import data_access.FoodDatabaseAccessObject;
import entity.Food;
import entity.CommonUser;
import entity.User;
import entity.MealType;
import use_case.dashboard.DashboardDataAccessInterface;
import java.util.List;
import java.util.Map;

public class CustomizeInteractor implements CustomizeInputBoundary {
    private final FoodDatabaseAccessObject foodDatabaseAccessObject;
    private final CustomizeOutputBoundary customizePresenter;
    private final DashboardDataAccessInterface userDataAccessObject;

    public CustomizeInteractor(
            FoodDatabaseAccessObject foodDatabaseAccessObject,
            CustomizeOutputBoundary customizePresenter,
            DashboardDataAccessInterface userDataAccessObject) {
        this.foodDatabaseAccessObject = foodDatabaseAccessObject;
        this.customizePresenter = customizePresenter;
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void searchFood(CustomizeInputData inputData) {
        try {
            List<Food> foods = foodDatabaseAccessObject.searchFoods(inputData.getSearchQuery());
            customizePresenter.presentSearchResults(new CustomizeOutputData(foods, null));
        } catch (Exception e) {
            customizePresenter.presentError("Failed to search foods: " + e.getMessage());
        }
    }

    @Override
    public void addFoodToMeal(String username, Food food, MealType mealType) {
        try {
            if (!userDataAccessObject.existsByName(username)) {
                customizePresenter.presentError("User not found");
                return;
            }

            User user = userDataAccessObject.get(username);
            if (!(user instanceof CommonUser)) {
                customizePresenter.presentError("Invalid user type");
                return;
            }

            CommonUser commonUser = (CommonUser) user;
            commonUser.addMeal(mealType, food.getLabel(), food);
            userDataAccessObject.save(commonUser);

            // Update nutrition progress in the user data access object
            updateNutritionProgress(commonUser);

            customizePresenter.presentSuccess("Food added to " + mealType.toString().toLowerCase());
        } catch (Exception e) {
            customizePresenter.presentError("Failed to add food to meal: " + e.getMessage());
        }
    }

    private void updateNutritionProgress(CommonUser user) {
        double totalCalories = 0;
        double totalCarbs = 0;
        double totalProtein = 0;
        double totalFat = 0;

        // Calculate totals from all meals
        for (MealType mealType : MealType.values()) {
            for (Food food : user.getMealsByType(mealType).values()) {
                Map<String, Double> nutrients = food.getNutrients();
                totalCalories += nutrients.getOrDefault("ENERC_KCAL", 0.0);
                totalCarbs += nutrients.getOrDefault("CHOCDF", 0.0);
                totalProtein += nutrients.getOrDefault("PROCNT", 0.0);
                totalFat += nutrients.getOrDefault("FAT", 0.0);
            }
        }

        userDataAccessObject.updateNutritionProgress(
                user.getName(),
                totalCalories,
                totalCarbs,
                totalProtein,
                totalFat
        );
    }

    @Override
    public void returnToDashboard(String username) {
        customizePresenter.presentDashboard();
    }
}