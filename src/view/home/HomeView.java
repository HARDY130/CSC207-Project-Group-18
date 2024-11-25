package view.home;

import interface_adapter.home.HomeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField lunchField;
    private final JTextField supperField;
    private final JButton updateButton;
    private final JButton offerButton;
    private final JButton customizeButton;
    private NutritionGraphPanel graphPanel;

    public HomeView() {
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel greetingLabel = new JLabel("Hi, username!");
        greetingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

//        CommonUser user = null;
//        int weightData = user.getWeight();
//        String yourWeight = String.valueOf(weightData);
        JLabel weightLabel = new JLabel("Your weight:");
        weightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

//        int heightData = user.getHeight();
//        String yourHeight = String.valueOf(heightData);
        JLabel heightLabel = new JLabel("Your height:");
        heightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        updateButton = new JButton(HomeViewModel.Update_Button);
        updateButton.addActionListener(this);

        graphPanel = new NutritionGraphPanel();

        headerPanel.add(greetingLabel);
        headerPanel.add(new JLabel());
        headerPanel.add(weightLabel);
        headerPanel.add(heightLabel);
        headerPanel.add(updateButton);
        headerPanel.add(graphPanel);

        JPanel mealPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel breakfastLabel = new JLabel("Breakfast:", JLabel.LEFT);
        JLabel lunchLabel = new JLabel("Lunch:", JLabel.LEFT);
        JLabel supperLabel = new JLabel("Supper:", JLabel.LEFT);

        JTextField breakfastField = new JTextField();
        lunchField = new JTextField();
        supperField = new JTextField();

        mealPanel.add(breakfastLabel);
        mealPanel.add(breakfastField);
        mealPanel.add(lunchLabel);
        mealPanel.add(lunchField);
        mealPanel.add(supperLabel);
        mealPanel.add(supperField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        offerButton = new JButton(HomeViewModel.MealPlanner_Button);
        customizeButton = new JButton(HomeViewModel.FoodSearch_Button);

        offerButton.addActionListener(this);
        customizeButton.addActionListener(this);

        buttonPanel.add(offerButton);
        buttonPanel.add(customizeButton);

        add(headerPanel, BorderLayout.NORTH);
        add(mealPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            JOptionPane.showMessageDialog(this, "Updated! Weight: " +  ", Height: ");
        } else if (e.getSource() == offerButton) {
            JOptionPane.showMessageDialog(this, "Jump to OfferMeal.");
        } else if (e.getSource() == customizeButton) {
            JOptionPane.showMessageDialog(this, "Jump to CustomizeMealPlanner.");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property changed: " + evt.getPropertyName() + " to " + evt.getNewValue());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Meal Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new HomeView());
        frame.setVisible(true);
    }
}

