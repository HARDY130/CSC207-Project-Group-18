package view.customizeView.search_foods_view;

import interface_adapter.customize.search_foods.SearchFoodController;
import use_case.customize.search_food.SearchFoodInputBoundary;
import use_case.customize.search_food.SearchFoodInterator;

import javax.swing.*;
import java.awt.*;

public class SearchFoodView extends JPanel {

    private JTextField textField;

    public SearchFoodView() {
        JLabel label = new JLabel("Food Keywords");
        JTextField textField = new JTextField(15); //argument required
        JButton button = new JButton("Search");
        this.textField = textField;

        this.add(label);
        this.add(textField);
        this.add(button);

        button.addActionListener(e -> {
            Component source = (Component) e.getSource();
            Container parent = source.getParent();
            if (parent instanceof SearchFoodView) {
                SearchFoodView searchFoodView = (SearchFoodView) parent;
                String text = searchFoodView.getTextFromTextField();

                SearchFoodInputBoundary searchFoodInputBoundary= new SearchFoodInterator();
                SearchFoodController controller = new SearchFoodController(searchFoodInputBoundary);
                controller.execute(text);
            }
        });
    }

    public String getTextFromTextField() {
        return this.textField.getText();
    }
}
