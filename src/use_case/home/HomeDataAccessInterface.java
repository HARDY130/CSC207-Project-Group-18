package use_case.home;

import entity.Food;

import java.util.Map;

public interface HomeDataAccessInterface {

    public void foodSearch(Map<String,String> foods);

    public void mealPlanner(Map<String, Food> foods);

    public void personalInfo(String username);
}
