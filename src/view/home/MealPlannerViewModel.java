//package view.MainPanel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//
//public class MealPlannerViewModel extends JPanel implements ActionListener, PropertyChangeListener {
//
//    // UI Components
//    private JTextField weightField, heightField, breakfastField, lunchField, supperField;
//    private JButton updateButton, offerButton, customizeButton;
//
//    private JPanel parentPanel; // Reference to the parent panel for switching views
//    private CardLayout cardLayout; // CardLayout for switching between views
//
//    public MealPlannerViewModel(JPanel parentPanel, CardLayout cardLayout) {
//        this.parentPanel = parentPanel;
//        this.cardLayout = cardLayout;
//
//        // Set up layout
//        setLayout(new BorderLayout(10, 10));
//
//        // Header Panel
//        JPanel headerPanel = new JPanel();
//        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
//
//        JLabel greetingLabel = new JLabel("Hi, username!");
//        greetingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        JLabel weightLabel = new JLabel("Your weight:");
//        weightField = new JTextField(10);
//        weightPanel.add(weightLabel);
//        weightPanel.add(weightField);
//
//        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        JLabel heightLabel = new JLabel("Your height:");
//        heightField = new JTextField(10);
//        heightPanel.add(heightLabel);
//        heightPanel.add(heightField);
//
//        updateButton = new JButton("Update");
//        updateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
//        updateButton.addActionListener(this); // Attach ActionListener
//
//        headerPanel.add(greetingLabel);
//        headerPanel.add(weightPanel);
//        headerPanel.add(heightPanel);
//        headerPanel.add(updateButton);
//
//        // Meal Panel
//        JPanel mealPanel = new JPanel(new GridLayout(3, 2, 5, 5));
//        JLabel breakfastLabel = new JLabel("Breakfast:");
//        JLabel lunchLabel = new JLabel("Lunch:");
//        JLabel supperLabel = new JLabel("Supper:");
//
//        breakfastField = new JTextField();
//        lunchField = new JTextField();
//        supperField = new JTextField();
//
//        mealPanel.add(breakfastLabel);
//        mealPanel.add(breakfastField);
//        mealPanel.add(lunchLabel);
//        mealPanel.add(lunchField);
//        mealPanel.add(supperLabel);
//        mealPanel.add(supperField);
//
//        // Button Panel
//        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
//        offerButton = new JButton("Offer me");
//        customizeButton = new JButton("Customize");
//
//        offerButton.addActionListener(this);
//        customizeButton.addActionListener(this);
//
//        buttonPanel.add(offerButton);
//        buttonPanel.add(customizeButton);
//
//        // Add panels to main layout
//        add(headerPanel, BorderLayout.NORTH);
//        add(mealPanel, BorderLayout.CENTER);
//        add(buttonPanel, BorderLayout.SOUTH);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == updateButton) {
//            // Switch to InfoCollectionView
//            cardLayout.show(parentPanel, "InfoCollectionView");
//        } else if (e.getSource() == offerButton) {
//            JOptionPane.showMessageDialog(this, "Offering meals based on your input!");
//        } else if (e.getSource() == customizeButton) {
//            JOptionPane.showMessageDialog(this, "Customize your meal preferences!");
//        }
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        // Handle property changes if needed
//        System.out.println("Property changed: " + evt.getPropertyName() + " to " + evt.getNewValue());
//    }
//}
//
//// Main Class (with existing InfoCollectionView)
//class MealPlannerApp {
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Meal Planner");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//
//        // Create CardLayout and Parent Panel
//        CardLayout cardLayout = new CardLayout();
//        JPanel parentPanel = new JPanel(cardLayout);
//
//        // Create Views
//        MealPlannerViewModel mealPlannerView = new MealPlannerViewModel(parentPanel, cardLayout);
//        InfoCollectionView infoCollectionView = new InfoCollectionView(); // Use your existing class here
//
//        // Add Views to Parent Panel
//        parentPanel.add(mealPlannerView, "MealPlannerView");
//        parentPanel.add(infoCollectionView, "InfoCollectionView");
//
//        // Add Parent Panel to Frame
//        frame.add(parentPanel);
//
//        // Show the first view
//        cardLayout.show(parentPanel, "MealPlannerView");
//
//        // Display the Frame
//        frame.setVisible(true);
//    }
//}
