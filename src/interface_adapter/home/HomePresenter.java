package interface_adapter.home;

import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;
import interface_adapter.info_collection.InfoCollectionState;
import interface_adapter.info_collection.InfoCollectionViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.search_foods.SearchFoodViewModel;
import interface_adapter.mealplanner.MealPlannerViewModel;
import use_case.home.HomeOutputBoundary;
import use_case.home.HomeOutputData;

public class HomePresenter implements HomeOutputBoundary {

    private HomeViewModel homeViewModel;
    private InfoCollectionViewModel infoCollectionViewModel;
    private SearchFoodViewModel searchFoodViewModel;
    private MealPlannerViewModel mealPlannerViewModel;
    private ViewManagerModel viewManagerModel;

    public HomePresenter(HomeViewModel homeViewModel, InfoCollectionViewModel infoCollectionViewModel,
                         SearchFoodViewModel searchFoodViewModel, MealPlannerViewModel mealPlannerViewModel,
                         ViewManagerModel viewManagerModel) {
        this.homeViewModel = homeViewModel;
        this.infoCollectionViewModel = infoCollectionViewModel;
        this.searchFoodViewModel = searchFoodViewModel;
        this.mealPlannerViewModel = mealPlannerViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareMealPlannerView() {

    }

    @Override
    public void prepareFoodSearchView() {

    }

    @Override
    public void updateView() {
        final InfoCollectionState infoCollectionState = infoCollectionViewModel.getState();
    }
}
