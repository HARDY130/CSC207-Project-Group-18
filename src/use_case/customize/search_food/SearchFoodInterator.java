package use_case.customize.search_food;

import data_access.FoodDatabaseAccessObject;
import interface_adapter.customize.search_foods.SearchFoodPresenter;
import interface_adapter.customize.search_foods.SearchFoodViewModel;
import org.json.JSONObject;

public class SearchFoodInterator implements SearchFoodInputBoundary{
    @Override
    public void execute(SearchFoodInputData searchFoodInputData) {
        String text = searchFoodInputData.getfoodname();

        FoodDatabaseAccessObject foodDatabaseAccessObject = new FoodDatabaseAccessObject();

        JSONObject obj = null;
        try {
            obj = foodDatabaseAccessObject.searchFoodWithIngredient(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SearchFoodViewModel searchFoodViewModel = new SearchFoodViewModel();
        SearchFoodOutputBoundary presenter = new SearchFoodPresenter(searchFoodViewModel);

        SearchFoodOutputData searchFoodOutputData = new SearchFoodOutputData(obj);

        presenter.prepareSuccessView(searchFoodOutputData);
    }
}
