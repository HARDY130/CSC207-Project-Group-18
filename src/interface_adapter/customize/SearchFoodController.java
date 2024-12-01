package interface_adapter.customize;

import use_case.customize.SearchFoodInputBoundary;
import use_case.customize.SearchFoodInputData;

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
