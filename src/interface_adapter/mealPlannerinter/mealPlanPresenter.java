package interface_adapter.mealPlannerinter;

import use_case.mealPlanner.*;

public class mealPlanPresenter implements mealPlannerOutputBoundary {
    private mealPlanViewModel viewModel;

    public mealPlanPresenter(mealPlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentMealPlan(mealPlannerOutputData outputData) {
        viewModel.setMealPlan(outputData.getMealPlan());
        viewModel.setSuccess(outputData.getSuccess());
        viewModel.setErrorMessage(outputData.getErrorMessage());
    }

}
