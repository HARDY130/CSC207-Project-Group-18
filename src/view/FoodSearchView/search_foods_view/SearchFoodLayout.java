package view.FoodSearchView.search_foods_view;

import java.awt.Container;
import view.FoodSearchView.LayoutAdapter;

public class SearchFoodLayout extends LayoutAdapter {

    @Override
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        parent.getComponent(0).setBounds(10, 0, 100, 30);
        parent.getComponent(1).setBounds(10, 30, 200, 30);
        parent.getComponent(2).setBounds(200, 30, 80, 30);
    }
}