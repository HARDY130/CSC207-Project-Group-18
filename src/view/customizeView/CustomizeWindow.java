package view.customizeView;

import view.customizeView.search_foods_view.SearchFoodView;

import javax.swing.*;

public class CustomizeWindow extends JPanel{
    public CustomizeWindow(){
        SearchFoodView sfp = new SearchFoodView();
//        AddFoodsPanel afp = new AddFoodsPanel();
//        BackPanel bp = new BackPanel();
        this.add(sfp);
//        this.add(afp);
//        this.add(bp);
        this.setLayout(null);

        this.updateUI();

    }
}
