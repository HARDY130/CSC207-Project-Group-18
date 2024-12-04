package use_case.dashboard;

import entity.CommonUser;
import entity.Food;
import entity.MealType;

import java.util.Map;

public class DashboardInteractor implements DashboardInputBoundary {
    final DashboardDataAccessInterface userDataAccessObject;
    final DashboardOutputBoundary dashboardPresenter;

    public DashboardInteractor(DashboardDataAccessInterface userDataAccessObject,
                               DashboardOutputBoundary dashboardPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.dashboardPresenter = dashboardPresenter;
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {
        try {
            if (!userDataAccessObject.existsByName(dashboardInputData.getUsername())) {
                dashboardPresenter.prepareFailView("User not found.");
                return;
            }

            CommonUser user = (CommonUser) userDataAccessObject.get(dashboardInputData.getUsername());
            Map<MealType, Map<String, Food>> userMeals = user.getAllMeals();

            // Convert user meals to the format needed for output
            Map<MealType, List<Food>> meals = new EnumMap<>(MealType.class);
            for (MealType type : MealType.values()) {
                meals.put(type, new ArrayList<>(userMeals.get(type).values()));
            }

            // Calculate nutrition totals from all meals
            double totalCalories = 0;
            double totalCarbs = 0;
            double totalProtein = 0;
            double totalFat = 0;

            for (List<Food> mealFoods : meals.values()) {
                for (Food food : mealFoods) {
                    Map<String, Double> nutrients = food.getNutrients();
                    totalCalories += nutrients.getOrDefault("ENERC_KCAL", 0.0);
                    totalCarbs += nutrients.getOrDefault("CHOCDF", 0.0);
                    totalProtein += nutrients.getOrDefault("PROCNT", 0.0);
                    totalFat += nutrients.getOrDefault("FAT", 0.0);
                }
            }

            // Calculate nutrition goals
            double bmr = user.calculateBMR();
            double tdee = user.calculateTDEE();
            double carbsGoal = user.calculateCarbsGrams();
            double proteinGoal = user.calculateProteinGrams();
            double fatGoal = user.calculateFatGrams();

            // Update nutrition progress in the database
            userDataAccessObject.updateNutritionProgress(
                    user.getName(),
                    totalCalories,
                    totalCarbs,
                    totalProtein,
                    totalFat
            );

            DashboardOutputData outputData = new DashboardOutputData(
                    user.getName(),
                    bmr,
                    tdee,
                    carbsGoal,
                    proteinGoal,
                    fatGoal,
                    meals,
                    totalCalories,
                    totalCarbs,
                    totalProtein,
                    totalFat,
                    user.getActivityLevel(),
                    user.getAllergies(),
                    true,
                    null
            );

            dashboardPresenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            dashboardPresenter.prepareFailView(e.getMessage());
        }
    }

    @Override
    public void switchToUpdateProfile() {
        dashboardPresenter.prepareSwitchToInfoCollection();
    }

    @Override
    public void switchToMealPlanner(String username) {
        if (!userDataAccessObject.existsByName(username)) {
            dashboardPresenter.prepareFailView("User not found.");
            return;
        }

        CommonUser user = (CommonUser) userDataAccessObject.get(username);
        DashboardOutputData outputData = new DashboardOutputData(
                user.getName(),
                user.calculateBMR(),
                user.calculateTDEE(),
                user.calculateCarbsGrams(),
                user.calculateProteinGrams(),
                user.calculateFatGrams(),
                user.getAllMeals(),
                0.0,
                0.0,
                0.0,
                0.0,
                user.getActivityLevel(),
                user.getAllergies(),
                true,
                null
        );

        dashboardPresenter.prepareSwitchToMealPlanner(outputData);
    }

    @Override
    public void switchToCustomize() {
        dashboardPresenter.prepareSwitchToCustomize();
    }
}