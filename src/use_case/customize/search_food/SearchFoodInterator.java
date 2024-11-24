package use_case.customize.search_food;

import data_access.FoodDatabaseAccessObject;
import interface_adapter.customize.search_foods.SearchFoodPresenter;
import interface_adapter.customize.search_foods.SearchFoodViewModel;
import org.json.JSONObject;

public class SearchFoodInterator implements SearchFoodInputBoundary{

    private final SearchFoodViewModel viewModel;

    public SearchFoodInterator(SearchFoodViewModel viewModel) {
        this.viewModel = viewModel;
    }
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

        SearchFoodOutputBoundary presenter = new SearchFoodPresenter(viewModel);

        SearchFoodOutputData searchFoodOutputData = new SearchFoodOutputData(obj);

        presenter.prepareSuccessView(searchFoodOutputData);
    }
}
