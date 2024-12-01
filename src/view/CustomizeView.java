package view;

import entity.Food;
import entity.MealType;
import interface_adapter.customize.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        searchButton.setActionCommand("search");
        addToMealButton.setActionCommand("add_meal");
        returnButton.setActionCommand("return");

        searchButton.addActionListener(this);
        addToMealButton.addActionListener(this);
        returnButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CustomizeState state = customizeViewModel.getState();

        switch (command) {
            case "search":
                String searchQuery = searchField.getText().trim();
                if (!searchQuery.isEmpty()) {
                    customizeController.searchFood(state.getUsername(), searchQuery);
                }
                break;

            case "add_meal":
                Food selectedFood = searchResultsList.getSelectedValue();
                if (selectedFood != null) {
                    MealType selectedMealType = (MealType) mealTypeComboBox.getSelectedItem();
                    customizeController.addFoodToMeal(state.getUsername(), selectedFood, selectedMealType);
                }
                break;

            case "return":
                customizeController.returnToDashboard(state.getUsername());
                break;
        }
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
            errorLabel.setForeground(Color.RED);
        } else if (state.getSuccessMessage() != null) {
            errorLabel.setText(state.getSuccessMessage());
            errorLabel.setForeground(new Color(0, 128, 0)); // Dark green
        } else {
            errorLabel.setText("");
        }

        listModel.clear();
        List<Food> searchResults = state.getSearchResults();
        if (searchResults != null && !searchResults.isEmpty()) {
            for (Food food : searchResults) {
                listModel.addElement(food);
            }
        }

        // Handle loading state
        if (state.isLoading()) {
            searchButton.setEnabled(false);
            searchButton.setText("Searching...");
        } else {
            searchButton.setEnabled(true);
            searchButton.setText("Search");
        }
    }

    public String getViewName() {
        return viewName;
    }
}