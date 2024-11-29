package interface_adapter.customize.search_foods;

import entity.Food;
import use_case.customize.search_food.SearchFoodOutputBoundary;
import use_case.customize.search_food.SearchFoodOutputData;

public class SearchFoodPresenter implements SearchFoodOutputBoundary {

    private final SearchFoodViewModel viewModel;

    public SearchFoodPresenter(SearchFoodViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SearchFoodOutputData outputData) {
        Food[] foods = outputData.getFoods();
        final SearchFoodState searchFoodState = new SearchFoodState(foods);
        viewModel.setState(searchFoodState);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
