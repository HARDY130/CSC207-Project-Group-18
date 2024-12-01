package view.FoodSearchView.customize_view;

import interface_adapter.customize.SearchFoodViewModel;
import view.FoodSearchView.add_foods_view.AddFoodsLayout;
import view.FoodSearchView.add_foods_view.AddFoodsView;
import view.FoodSearchView.search_foods_view.SearchFoodLayout;
import view.FoodSearchView.search_foods_view.SearchFoodView;

import javax.swing.*;

public class CustomizeWindowView extends JPanel{
    private String viewName = "customize";

    public CustomizeWindowView(){
        SearchFoodViewModel searchFoodViewModel = new SearchFoodViewModel();
        SearchFoodView sfp = new SearchFoodView(searchFoodViewModel);
        SearchFoodLayout sflay = new SearchFoodLayout();
        sfp.setLayout(sflay);
        AddFoodsView afp = new AddFoodsView();
        searchFoodViewModel.addPropertyChangeListener(afp);
        AddFoodsLayout afl = new AddFoodsLayout();
        afp.setLayout(afl);
        this.add(sfp);
        this.add(afp);

        this.updateUI();
        this.setVisible(true);
    }

    public String getViewName(){
        return viewName;
    }
}