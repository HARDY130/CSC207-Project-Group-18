package view.FoodSearchView.add_foods_view;

import java.awt.Container;
import view.FoodSearchView.LayoutAdapter;

public class AddFoodsLayout extends LayoutAdapter {

    @Override
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        parent.getComponent(0).setBounds(10, 30, 80, 30);
        parent.getComponent(1).setBounds(90, 30, 200, 30);
        parent.getComponent(2).setBounds(10, 70, parent.getWidth(), 20);
        parent.getComponent(3).setBounds(10, 100, 300, 40);
        parent.getComponent(4).setBounds(310, 104, 60, 30);
        parent.getComponent(5).setBounds(10, 150, 300, 40);
        parent.getComponent(6).setBounds(310, 154, 60, 30);
        parent.getComponent(7).setBounds(10, 200, 300, 40);
        parent.getComponent(8).setBounds(310, 204, 60, 30);
        parent.getComponent(9).setBounds(40, 250, 300, 40);
    }
}
