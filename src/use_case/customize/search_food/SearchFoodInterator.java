package use_case.customize.search_food;

import data_access.FoodDatabaseAccessObject;
import org.json.JSONObject;

public class SearchFoodInterator implements SearchFoodInputBoundary{
    private final FoodDatabaseAccessObject foodDatabaseAccessObject1;
    private final SearchFoodOutputBoundary searchFoodPresenter;

    public SearchFoodInterator(FoodDatabaseAccessObject foodDatabaseAccessObject, SearchFoodOutputBoundary searchFoodPresenter) {
        this.foodDatabaseAccessObject1 = foodDatabaseAccessObject;
        this.searchFoodPresenter = searchFoodPresenter;
    }
    @Override
    public void execute(SearchFoodInputData searchFoodInputData) {
        String text = searchFoodInputData.getfoodname();

        JSONObject obj = null;
        try {
            obj = foodDatabaseAccessObject1.searchFoodWithIngredient(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SearchFoodOutputData searchFoodOutputData = new SearchFoodOutputData(obj);

        searchFoodPresenter.prepareSuccessView(searchFoodOutputData);
    }
}
