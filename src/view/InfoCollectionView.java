//package view;
//
//import entity.Allergy;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class InfoCollectionView {
//    private JPanel createAllergyPanel() {
//        JPanel allergyPanel = new JPanel();
//        allergyPanel.setLayout(new GridLayout(0, 3, 10, 5));  // 3 columns, flexible rows
//        allergyPanel.setBorder(BorderFactory.createTitledBorder("Allergies & Preferences"));
//
//        // Create a map to store checkboxes
//        Map<Allergy, JCheckBox> allergyCheckboxes = new HashMap<>();
//
//        // Create checkboxes for each allergy
//        for (Allergy allergy : Allergy.values()) {
//            JCheckBox checkBox = new JCheckBox(allergy.getDisplayName());
//            allergyCheckboxes.put(allergy, checkBox);
//
//            // Add checkbox with icon button for information
//            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//            itemPanel.add(checkBox);
//
//            JButton infoButton = new JButton("i");
//            infoButton.setPreferredSize(new Dimension(20, 20));
//            infoButton.addActionListener(e -> showAllergyInfo(allergy));
//            itemPanel.add(infoButton);
//
//            allergyPanel.add(itemPanel);
//        }
//
//        // Create a collapsible section
//        JButton toggleButton = new JButton("Show/Hide Allergies");
//        allergyPanel.setVisible(false);
//        toggleButton.addActionListener(e -> allergyPanel.setVisible(!allergyPanel.isVisible()));
//
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BorderLayout());
//        mainPanel.add(toggleButton, BorderLayout.NORTH);
//        mainPanel.add(allergyPanel, BorderLayout.CENTER);
//
//        return mainPanel;
//    }
//
//    private void showAllergyInfo(Allergy allergy) {
//        String info = getAllergyInfo(allergy);
//        JOptionPane.showMessageDialog(this, info,
//                allergy.getDisplayName() + " Information",
//                JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    private String getAllergyInfo(Allergy allergy) {
//        return switch (allergy) {
//            case DAIRY -> "Products free from milk and milk derivatives.";
//            case GLUTEN -> "Products free from wheat, barley, rye, and their derivatives.";
//            case PEANUT -> "Products free from peanuts and peanut derivatives.";
//            // Add descriptions for other allergies
//            default -> "Products free from " + allergy.getDisplayName().toLowerCase();
//        };
//    }
//}
