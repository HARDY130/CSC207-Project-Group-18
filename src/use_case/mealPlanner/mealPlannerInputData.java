package use_case.mealPlanner;

public class mealPlannerInputData {
    private String jsonInputString;

    public mealPlannerInputData(String jsonInputString) {
        this.jsonInputString = jsonInputString;
    }

    public String getJsonInputString() {
        return jsonInputString;
    }

    public void setJsonInputString(String jsonInputString) {
        this.jsonInputString = jsonInputString;
    }
}
