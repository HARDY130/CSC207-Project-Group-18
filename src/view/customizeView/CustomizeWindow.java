package view.customizeView;

import view.customizeView.add_foods_view.AddFoodsLayout;
import view.customizeView.add_foods_view.AddFoodsView;
import view.customizeView.search_foods_view.SearchFoodLayout;
import view.customizeView.search_foods_view.SearchFoodView;

import javax.swing.*;

public class CustomizeWindow extends JPanel{
    public CustomizeWindow(){
        SearchFoodView sfp = new SearchFoodView();
        SearchFoodLayout sflay = new SearchFoodLayout();
        sfp.setLayout(sflay);
        AddFoodsView afp = new AddFoodsView();
        AddFoodsLayout afl = new AddFoodsLayout();
        afp.setLayout(afl);
//        BackPanel bp = new BackPanel();
        this.add(sfp);
        this.add(afp);

//        this.add(bp);
        this.updateUI();
        this.setVisible(true);
    }
}
