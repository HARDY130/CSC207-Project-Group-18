package use_case.mealPlanner;

public class mealPlannerInteractor {
    private final mealPlannerDataAccessInterface dataAccess;

    public mealPlannerInteractor(mealPlannerDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    public mealPlannerOutputData createMealPlan(mealPlannerInputData inputData) {
        try {
            String response = dataAccess.createMealPlan(inputData.getJsonInputString());
            return new mealPlannerOutputData(response, true, null);
        } catch (Exception e) {
            return new mealPlannerOutputData(null, false, e.getMessage());
        }

    }
}
