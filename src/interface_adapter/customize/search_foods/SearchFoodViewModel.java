package interface_adapter.customize.search_foods;

import entity.Food;
import interface_adapter.ViewModel;

public class SearchFoodViewModel extends ViewModel<SearchFoodState> {
    public SearchFoodViewModel() {
        super("Search Food");
        final int LENGTHOFFOODS = 3;
        setState(new SearchFoodState(new Food[LENGTHOFFOODS]));
    }
}
