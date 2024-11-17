package view.customizeView.search_foods_view;

import view.LayoutAdapter;

import java.awt.*;

public class SearchFoodLayout extends LayoutAdapter {

    @Override
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        parent.getComponent(0).setBounds(10, 0, 100, 30);
        parent.getComponent(1).setBounds(10, 30, 200, 30);
        parent.getComponent(2).setBounds(200, 30, 80, 30);
    }
}