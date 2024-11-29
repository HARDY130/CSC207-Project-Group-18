package use_case.mealPlanner;

import org.json.JSONObject;
import use_case.mealPlanner.mealPlannerInputData;

public interface mealPlannerDataAccessInterface {
    String createMealPlan(String jsonInputString);

}
