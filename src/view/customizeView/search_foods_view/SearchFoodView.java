package view.customizeView.search_foods_view;

import data_access.FoodDatabaseAccessObject;
import interface_adapter.customize.search_foods.SearchFoodController;
import interface_adapter.customize.search_foods.SearchFoodPresenter;
import interface_adapter.customize.search_foods.SearchFoodViewModel;
import use_case.customize.search_food.SearchFoodInputBoundary;
import use_case.customize.search_food.SearchFoodInterator;
import use_case.customize.search_food.SearchFoodOutputBoundary;

import javax.swing.*;
import java.awt.*;

public class SearchFoodView extends JPanel {


    private JTextField textField;

    public SearchFoodView(SearchFoodViewModel viewModel) {
        JLabel label = new JLabel("Food Keywords");
        JTextField textField = new JTextField(20); //argument required
        JButton button = new JButton("Search");


        this.add(label);
        this.add(textField);
        this.add(button);

        this.textField = textField;

        button.addActionListener(e -> {
            Component source = (Component) e.getSource();
            Container parent = source.getParent();
            if (parent instanceof SearchFoodView) {
                SearchFoodView searchFoodView = (SearchFoodView) parent;
                String text = searchFoodView.getTextFromTextField();
                FoodDatabaseAccessObject foodDatabaseAccessObject = new FoodDatabaseAccessObject();
                SearchFoodOutputBoundary searchFoodPresenter = new SearchFoodPresenter(viewModel);
                SearchFoodInputBoundary searchFoodInputBoundary= new SearchFoodInterator(foodDatabaseAccessObject, searchFoodPresenter);
                SearchFoodController controller = new SearchFoodController(searchFoodInputBoundary);
                controller.execute(text);
            }
        });
    }

    public String getTextFromTextField() {
        return this.textField.getText();
    }
}
