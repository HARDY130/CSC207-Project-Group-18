package use_case.mealPlanner;

public class mealPlannerOutputData {
    private String mealPlan;
    private boolean success;
    private String errorMessage;

    public mealPlannerOutputData(String mealPlan, boolean success, String errorMessage) {
        this.mealPlan = mealPlan;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public String getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(String mealPlan) {
        this.mealPlan = mealPlan;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
