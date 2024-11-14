package interface_adapter.customize.search_foods;

import use_case.customize.search_food.SearchFoodInputBoundary;
import use_case.customize.search_food.SearchFoodInputData;

public class SearchFoodController {

    private SearchFoodInputBoundary boundary;

    public SearchFoodController(SearchFoodInputBoundary searchFoodInputBoundary) {
        this.boundary = searchFoodInputBoundary;
    }

    public void execute(String text){
        SearchFoodInputData searchFoodInputData = new SearchFoodInputData(text);
        this.boundary.execute(searchFoodInputData);
    }
}
