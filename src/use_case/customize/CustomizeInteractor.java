package use_case.customize;

import data_access.FoodDatabaseAccessObject;
import entity.Food;
import entity.CommonUser;
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
//        try {
        List<Food> foods = foodDatabaseAccessObject.searchFoods(inputData.getSearchQuery());
        customizePresenter.presentSearchResults(new CustomizeOutputData(foods, null));
//        }
//        catch (Exception e) {
//            customizePresenter.presentError("Failed to search foods: " + e.getMessage());
//        }
    }

    @Override
    public void addFoodToMeal(String username, Food food, MealType mealType) {
//        try {
            // Get current user
        CommonUser user = (CommonUser) userDataAccessObject.get(username);
        if (user == null) {
            customizePresenter.presentError("User not found");
            return;
        }

        // Add food to meal
        user.addMeal(mealType, food.getLabel(), food);

        // Calculate totals from all meals
        double totalCalories = 0;
        double totalCarbs = 0;
        double totalProtein = 0;
        double totalFat = 0;

        // Sum up nutrients from all meals
        for (Map<String, Food> mealFoods : user.getAllMeals().values()) {
            for (Food mealFood : mealFoods.values()) {
                Map<String, Double> nutrients = mealFood.getNutrients();
                totalCalories += nutrients.getOrDefault("ENERC_KCAL", 0.0);
                totalCarbs += nutrients.getOrDefault("CHOCDF", 0.0);
                totalProtein += nutrients.getOrDefault("PROCNT", 0.0);
                totalFat += nutrients.getOrDefault("FAT", 0.0);
            }
        }

        // Update the user's nutrition progress in the DAO
        userDataAccessObject.updateNutritionProgress(
                username,
                totalCalories,
                totalCarbs,
                totalProtein,
                totalFat
        );

        // Save updated user
        userDataAccessObject.save(user);

        customizePresenter.presentSuccess("Food added to " + mealType.toString().toLowerCase());
//        } catch (Exception e) {
//            customizePresenter.presentError("Failed to add food: " + e.getMessage());
//        }
    }

    @Override
    public void returnToDashboard(String username) {
        System.out.println("CustomizeInteractor returnToDashboard called"); // Add this
        customizePresenter.presentDashboard();
    }

}