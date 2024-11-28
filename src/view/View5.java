package view;

import javax.swing.*;
import java.awt.*;

public class View5 extends JPanel {
    public View5() {
        final JLabel title = new JLabel("View 5");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JTextArea filterTextArea = new JTextArea(5, 20);
        final JTextField breakfastInputField = new JTextField(15);
        final JTextField lunchInputField = new JTextField(15);
        final JTextField supperInputField = new JTextField(15);
        final JButton addBreakfastButton = new JButton("Add");
        final JButton addLunchButton = new JButton("Add");
        final JButton addSupperButton = new JButton("Add");
        final JButton generateButton = new JButton("Generate");
        final JButton backButton = new JButton("Back");

        final JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.add(new JLabel("Filters:"));
        filterPanel.add(filterTextArea);

        final JPanel mealPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        mealPanel.add(new JLabel("Breakfast:"));
        mealPanel.add(breakfastInputField);
        mealPanel.add(addBreakfastButton);
        mealPanel.add(new JLabel("Lunch:"));
        mealPanel.add(lunchInputField);
        mealPanel.add(addLunchButton);
        mealPanel.add(new JLabel("Supper:"));
        mealPanel.add(supperInputField);
        mealPanel.add(addSupperButton);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(backButton);
        buttonsPanel.add(generateButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(filterPanel);
        this.add(mealPanel);
        this.add(buttonsPanel);

        // Add action listeners for buttons (implement as needed)
        backButton.addActionListener(evt -> {
            // Back button logic
        });

        generateButton.addActionListener(evt -> {
            // Generate button logic
        });
    }
}