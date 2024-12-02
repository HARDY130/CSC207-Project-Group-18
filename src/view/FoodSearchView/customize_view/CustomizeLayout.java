package view.FoodSearchView.customize_view;

import java.awt.Container;
import view.FoodSearchView.LayoutAdapter;

public class CustomizeLayout extends LayoutAdapter {


    @Override
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        parent.getComponent(0).setBounds(0, 0, parent.getWidth(), 60);
        parent.getComponent(1).setBounds(0, 40, parent.getWidth(), 400);

    }
}