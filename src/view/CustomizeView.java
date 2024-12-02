package view;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import entity.Food;
import entity.MealType;
import interface_adapter.ViewManagerModel;
import interface_adapter.customize.CustomizeController;
import interface_adapter.customize.CustomizeState;
import interface_adapter.customize.CustomizeViewModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

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

//    public CustomizeView(CustomizeViewModel viewModel, CustomizeController controller, ViewManagerModel viewManagerModel) {
//        this.customizeViewModel = viewModel;
//        this.customizeController = controller;
//        this.customizeViewModel.addPropertyChangeListener(this);
//
//        addToMealButton = new JButton("Add to Meal");
//        addToMealButton.setEnabled(false); // Disabled until user selects a food item
//        errorLabel = new JLabel();
//        errorLabel.setForeground(Color.RED);
//
//        // Initialize components
//        searchField = new JTextField(20);
//        searchButton = new JButton("Search");
//        returnButton = new JButton("Return to Dashboard");
//        mealTypeComboBox = new JComboBox<>(MealType.values());

    /// /        listModel = new DefaultListModel<>();
    /// /        searchResultsList = new JList<>(listModel);
//        // Initialize list components with a more user-friendly display
//        listModel = new DefaultListModel<>();
//        searchResultsList = new JList<>(listModel);
//        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        searchResultsList.setCellRenderer(new FoodListCellRenderer());
//
//        // Style the list
//        searchResultsList.setFixedCellHeight(50);
//        searchResultsList.setBackground(Color.WHITE);
//
//        // Add listener for list selection to enable/disable add button
//        searchResultsList.addListSelectionListener(e -> {
//            addToMealButton.setEnabled(!searchResultsList.isSelectionEmpty());
//        });
//
//        // Add action listener for add button
//        addToMealButton.addActionListener(e -> {
//            Food selectedFood = searchResultsList.getSelectedValue();
//            if (selectedFood != null) {
//                MealType selectedMealType = (MealType) mealTypeComboBox.getSelectedItem();
//                CustomizeState state = customizeViewModel.getState();
//                customizeController.addFoodToMeal(state.getUsername(), selectedFood, selectedMealType);
//            }
//        });
//
//        // Add to scroll pane
//        JScrollPane scrollPane = new JScrollPane(searchResultsList);
//        scrollPane.setPreferredSize(new Dimension(400, 300));
//
//        // Update the layout
//        JPanel resultsPanel = new JPanel(new BorderLayout());
//        resultsPanel.add(scrollPane, BorderLayout.CENTER);
//        resultsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
//
//        add(resultsPanel, BorderLayout.CENTER);
//        addToMealButton = new JButton("Add to Meal");
//        errorLabel = new JLabel();
//        errorLabel.setForeground(Color.RED);
//
//        // Layout
//        setLayout(new BorderLayout());
//        setupLayout();
//        setupListeners();
//    }
//

//    private void updateView(CustomizeState state) {
//        listModel.clear();
//        List<Food> searchResults = state.getSearchResults();
//        if (searchResults != null) {
//            for (Food food : searchResults) {
//                listModel.addElement(formatFoodDisplay(food));
//            }
//        }
//    }
    public CustomizeView(CustomizeViewModel viewModel, CustomizeController controller,
                         ViewManagerModel viewManagerModel) {
        this.customizeViewModel = viewModel;
        this.customizeController = controller;
        this.customizeViewModel.addPropertyChangeListener(this);

        // Set the main layout first
        setLayout(new BorderLayout());

        // Initialize components
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        returnButton = new JButton("Return to Dashboard");
        mealTypeComboBox = new JComboBox<>(MealType.values());

        // Initialize list components
        listModel = new DefaultListModel<>();
        searchResultsList = new JList<>(listModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsList.setCellRenderer(new FoodListCellRenderer());
        searchResultsList.setFixedCellHeight(50);
        searchResultsList.setBackground(Color.WHITE);

        addToMealButton = new JButton("Add to Meal");
        addToMealButton.setEnabled(false);
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Create scrollable results panel
        JScrollPane scrollPane = new JScrollPane(searchResultsList);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
        add(resultsPanel, BorderLayout.CENTER);

        // Create bottom control panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Meal Type:"));
        controlPanel.add(mealTypeComboBox);
        controlPanel.add(addToMealButton);
        controlPanel.add(returnButton);
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(errorLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Setup listeners
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

//        returnButton.addActionListener(e -> {
//            CustomizeState state = customizeViewModel.getState();
//            customizeController.returnToDashboard(state.getUsername());
//        });
        returnButton.addActionListener(e -> {
            System.out.println("Return button clicked"); // Add this
            CustomizeState state = customizeViewModel.getState();
            customizeController.returnToDashboard(state.getUsername());
        });
    }

    private String formatFoodDisplay(Food food) {
        Map<String, Double> nutrients = food.getNutrients();
        return String.format("%s\nCalories: %.0f kcal | Protein: %.1fg | Carbs: %.1fg | Fat: %.1fg",
            food.getLabel(),
            nutrients.getOrDefault("ENERC_KCAL", 0.0),
            nutrients.getOrDefault("PROCNT", 0.0),
            nutrients.getOrDefault("CHOCDF", 0.0),
            nutrients.getOrDefault("FAT", 0.0));
    }

    private void setupLayout() {
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Update panel layout to include new components
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Meal Type:"));
        controlPanel.add(mealTypeComboBox);
        controlPanel.add(addToMealButton);
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(errorLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

//    private void setupListeners() {
//        searchButton.setActionCommand("search");
//        addToMealButton.setActionCommand("add_meal");
//        returnButton.setActionCommand("return");
//
//        searchButton.addActionListener(this);
//        addToMealButton.addActionListener(this);
//        returnButton.addActionListener(this);
//    }

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