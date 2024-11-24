package view.customizeView;

import interface_adapter.customize.search_foods.SearchFoodViewModel;
import view.customizeView.add_foods_view.AddFoodsLayout;
import view.customizeView.add_foods_view.AddFoodsView;
import view.customizeView.search_foods_view.SearchFoodLayout;
import view.customizeView.search_foods_view.SearchFoodView;

import javax.swing.*;

public class CustomizeWindow extends JPanel{
    public CustomizeWindow(?controller){
        SearchFoodViewModel searchFoodViewModel = new SearchFoodViewModel();
        SearchFoodView sfp = new SearchFoodView(searchFoodViewModel);
        SearchFoodLayout sflay = new SearchFoodLayout();
        sfp.setLayout(sflay);
        AddFoodsView afp = new AddFoodsView(?);
        searchFoodViewModel.addPropertyChangeListener(afp);
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
