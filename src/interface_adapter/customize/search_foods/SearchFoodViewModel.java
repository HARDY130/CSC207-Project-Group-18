package interface_adapter.customize.search_foods;

import entity.Food;
import interface_adapter.ViewModel;
import use_case.customize.search_food.Constant;

public class SearchFoodViewModel extends ViewModel<SearchFoodState> {
    public SearchFoodViewModel() {
        super("Search Food");
        setState(new SearchFoodState(new Food[Constant.LENGTHOFFOODS]));
    }
}
