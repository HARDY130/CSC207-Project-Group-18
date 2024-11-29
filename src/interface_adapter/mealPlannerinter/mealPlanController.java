package interface_adapter.mealPlannerinter;

import use_case.mealPlanner.*;

public class mealPlanController {
    private final mealPlannerInputBoundary interactor;

    public mealPlanController(mealPlannerInputBoundary interactor) {
        this.interactor = interactor;
    }

    public createMealPlan(String jsonInputString) {
        mealPlannerInputData inputData = new mealPlannerInputData(jsonInputString);
        mealPlannerOutputData outputData = interactor.createMealPlan(inputData);
    }
}
