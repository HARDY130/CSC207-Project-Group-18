package view;

import entity.Food;
import interface_adapter.meal_planner.MealPlannerController;
import interface_adapter.meal_planner.MealPlannerState;
import interface_adapter.meal_planner.MealPlannerViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

public class MealPlannerView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "meal planner";
    private final MealPlannerViewModel mealPlannerViewModel;
    private MealPlannerController mealPlannerController;

    private final JLabel titleLabel;
    private final JPanel dietFilterPanel;
    private final JPanel mealsPanel;
    private final JButton generateButton;
    private final JButton backButton;
    private final Map<String, JCheckBox> dietCheckboxes = new HashMap<>();
    private final JLabel errorLabel;

    public MealPlannerView(MealPlannerViewModel viewModel) {
        this.mealPlannerViewModel = viewModel;
        this.mealPlannerViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel(MealPlannerViewModel.TITLE_LABEL);
        dietFilterPanel = createDietFilterPanel();
        mealsPanel = new JPanel();
        generateButton = new JButton(MealPlannerViewModel.GENERATE_BUTTON_LABEL);
        errorLabel = new JLabel();
        backButton = new JButton(MealPlannerViewModel.BACK_BUTTON_LABEL);

        styleComponents();

        layoutComponents();

        addListeners();
    }

    private void styleComponents() {
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dietFilterPanel.setBorder(BorderFactory.createTitledBorder(
                MealPlannerViewModel.DIET_FILTER_LABEL));

        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mealsPanel.setLayout(new GridLayout(3, 1, 0, 10));
    }

    private JPanel createDietFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        for (String diet : MealPlannerViewModel.DIET_OPTIONS) {
            JCheckBox checkbox = new JCheckBox(diet);
            dietCheckboxes.put(diet, checkbox);
            panel.add(checkbox);
        }

        return panel;
    }

    private void layoutComponents() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(dietFilterPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(generateButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(mealsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(errorLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(backButton);

        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }

    private void addListeners() {
        generateButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void updateMealsPanel(List<Food> breakfast, List<Food> lunch, List<Food> dinner) {
        mealsPanel.removeAll();

        mealsPanel.add(createMealTypePanel("Breakfast", breakfast));
        mealsPanel.add(createMealTypePanel("Lunch", lunch));
        mealsPanel.add(createMealTypePanel("Dinner", dinner));

        mealsPanel.revalidate();
        mealsPanel.repaint();
    }

    private JPanel createMealTypePanel(String mealType, List<Food> foods) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(mealType));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        if (foods != null) {
            for (Food food : foods) {
                JPanel foodItemPanel = createFoodItemPanel(food, mealType.substring(0, 1));
                contentPanel.add(foodItemPanel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        panel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFoodItemPanel(Food food, String mealTypeCode) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(food.getLabel());
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        infoPanel.add(nameLabel);

        Map<String, Double> nutrients = food.getNutrients();
        String nutritionInfo = String.format("Calories: %.0f | Protein: %.1fg | Carbs: %.1fg | Fat: %.1fg",
                nutrients.getOrDefault("ENERC_KCAL", 0.0),
                nutrients.getOrDefault("PROCNT", 0.0),
                nutrients.getOrDefault("CHOCDF", 0.0),
                nutrients.getOrDefault("FAT", 0.0));
        infoPanel.add(new JLabel(nutritionInfo));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            if (mealPlannerController != null) {
                MealPlannerState currentState = mealPlannerViewModel.getState();
                mealPlannerController.addMealToUser(
                        currentState.getUsername(),
                        mealTypeCode,
                        food
                );
            }
        });

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.EAST);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == generateButton && mealPlannerController != null) {
            String username = mealPlannerViewModel.getState().getUsername();
            Set<String> selectedDiets = new HashSet<>();
            for (Map.Entry<String, JCheckBox> entry : dietCheckboxes.entrySet()) {
                if (entry.getValue().isSelected()) {
                    selectedDiets.add(entry.getKey());
                }
            }

            mealPlannerController.execute(username, selectedDiets);
        }
        else if (evt.getSource() == backButton && mealPlannerController != null) {
            mealPlannerController.goHome();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            updateFromViewModel();
        }
    }

    private void updateFromViewModel() {
        MealPlannerState state = mealPlannerViewModel.getState();
        if (state != null) {
            errorLabel.setText(state.getError());
            updateMealsPanel(
                    state.getBreakfastOptions(),
                    state.getLunchOptions(),
                    state.getDinnerOptions()
            );
        }
    }

    public void setMealPlannerController(MealPlannerController controller) {
        this.mealPlannerController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}
