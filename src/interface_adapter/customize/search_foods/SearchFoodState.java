package interface_adapter.customize.search_foods;

import entity.Food;

public class SearchFoodState {
    private final Food[] foods;
    public SearchFoodState(Food[] foods) {
        this.foods = foods;
    }

    public entity.Food[] getFoods() {
        return foods;
    }
}
