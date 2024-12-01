package view;

import entity.Food;
import entity.MealType;
import interface_adapter.customize.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CustomizeView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "customize";
    private final CustomizeViewModel customizeViewModel;
    private final CustomizeController customizeController;

    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton returnButton;
    private final JComboBox<MealType> mealTypeComboBox;
    private final JList<Food> searchResultsList;
    private final DefaultListModel<Food> listModel;
    private final JButton addToMealButton;
    private final JLabel errorLabel;

    public CustomizeView(CustomizeViewModel viewModel, CustomizeController controller) {
        this.customizeViewModel = viewModel;
        this.customizeController = controller;
        this.customizeViewModel.addPropertyChangeListener(this);

        // Initialize components
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        returnButton = new JButton("Return to Dashboard");
        mealTypeComboBox = new JComboBox<>(MealType.values());
        listModel = new DefaultListModel<>();
        searchResultsList = new JList<>(listModel);
        addToMealButton = new JButton("Add to Meal");
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        // Layout
        setLayout(new BorderLayout());
        setupLayout();
        setupListeners();
    }

    private void setupLayout() {
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Results panel
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(new JScrollPane(searchResultsList), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Meal Type:"));
        controlPanel.add(mealTypeComboBox);
        controlPanel.add(addToMealButton);

        resultsPanel.add(controlPanel, BorderLayout.SOUTH);
        add(resultsPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(errorLabel, BorderLayout.NORTH);
        bottomPanel.add(returnButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText().trim();
            CustomizeState state = customizeViewModel.getState();
            customizeController.searchFood(state.getUsername(), searchQuery);
        });

        addToMealButton.addActionListener(e -> {
            Food selectedFood = searchResultsList.getSelectedValue();
            if (selectedFood != null) {
                MealType selectedMealType = (MealType) mealTypeComboBox.getSelectedItem();
                CustomizeState state = customizeViewModel.getState();
                customizeController.addFoodToMeal(state.getUsername(), selectedFood, selectedMealType);
            }
        });

        returnButton.addActionListener(e -> {
            CustomizeState state = customizeViewModel.getState();
            customizeController.returnToDashboard(state.getUsername());
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            CustomizeState state = (CustomizeState) evt.getNewValue();
            updateView(state);
        }
    }

    private void updateView(CustomizeState state) {
        if (state.getError() != null) {
            errorLabel.setText(state.getError());
        } else {
            errorLabel.setText("");
            listModel.clear();
            List<Food> searchResults = state.getSearchResults();
            if (searchResults != null) {
                for (Food food : searchResults) {
                    listModel.addElement(food);
                }
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}
