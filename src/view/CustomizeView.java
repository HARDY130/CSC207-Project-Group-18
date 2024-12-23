package view;

import entity.Food;
import entity.MealType;
import interface_adapter.ViewManagerModel;
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

    public CustomizeView(CustomizeViewModel viewModel, CustomizeController controller, ViewManagerModel viewManagerModel) {
        this.customizeViewModel = viewModel;
        this.customizeController = controller;
        this.customizeViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        searchField = new JTextField(20);
        searchButton = new JButton(CustomizeViewModel.SEARCH_BUTTON_LABEL);
        returnButton = new JButton(CustomizeViewModel.RETURN_BUTTON_LABEL);
        mealTypeComboBox = new JComboBox<>(MealType.values());

        listModel = new DefaultListModel<>();
        searchResultsList = new JList<>(listModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsList.setCellRenderer(new FoodListCellRenderer());
        searchResultsList.setFixedCellHeight(50);
        searchResultsList.setBackground(Color.WHITE);

        addToMealButton = new JButton(CustomizeViewModel.ADD_BUTTON_LABEL);
        addToMealButton.setEnabled(false);
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(searchResultsList);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
        add(resultsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Meal Type:"));
        controlPanel.add(mealTypeComboBox);
        controlPanel.add(addToMealButton);
        controlPanel.add(returnButton);
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(errorLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setupListeners();
    }

    private void setupListeners() {
        searchResultsList.addListSelectionListener(e -> {
            addToMealButton.setEnabled(!searchResultsList.isSelectionEmpty());
        });

        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText().trim();
            if (!searchQuery.isEmpty()) {
                CustomizeState state = customizeViewModel.getState();
                customizeController.searchFood(state.getUsername(), searchQuery);
            }
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
            System.out.println("Return button clicked"); // Add this
            CustomizeState state = customizeViewModel.getState();
            customizeController.returnToDashboard(state.getUsername());
        });
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
        else {
            searchButton.setEnabled(true);
            searchButton.setText("Search");
        }
    }

    public String getViewName() {
        return viewName;
    }
}