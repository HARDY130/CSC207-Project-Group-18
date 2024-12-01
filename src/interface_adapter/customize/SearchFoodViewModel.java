package interface_adapter.customize;

import entity.Food;
import interface_adapter.ViewModel;
import use_case.customize.Constant;

public class SearchFoodViewModel extends ViewModel<SearchFoodState> {

    public SearchFoodViewModel() {
        super("search Food");
        setState(new SearchFoodState(new Food[Constant.LENGTHOFFOODS]));
    }
}
