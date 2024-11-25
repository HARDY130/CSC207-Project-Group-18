package use_case.main_page;

import entity.Food;

import java.util.Map;

public class MainPanelInputData {
    Map<String, Food[]> meal;
    Food[] breakfast;
    Food[] lunch;
    Food[] supper;

    public MainPanelInputData(Map<String, Food[]> meal) {
        this.meal = meal;
        this.breakfast = meal.get("B");
        this.lunch = meal.get("L");
        this.supper = meal.get("S");
    }

    public Food[] getBreakfast() {
        return breakfast;
    }

    public Food[] getLunch() {
        return lunch;
    }

    public Food[] getSupper() {
        return supper;
    }
}
