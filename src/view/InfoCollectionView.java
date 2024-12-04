package view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import entity.Allergy;
import interface_adapter.ViewManagerModel;
import interface_adapter.info_collection.InfoCollectionController;
import interface_adapter.info_collection.InfoCollectionState;
import interface_adapter.info_collection.InfoCollectionViewModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InfoCollectionView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "info collection";
    private final InfoCollectionViewModel infoCollectionViewModel;
    private final ViewManagerModel viewManagerModel;
    private final JTextField yearOfBirthField = new JTextField(15);
    private final JComboBox<String> genderField = new JComboBox<>(new String[] {"Male", "Female"});
    private final JTextField weightField = new JTextField(15);
    private final JTextField heightField = new JTextField(15);
    private final JComboBox<String> activityField;
    private final JPanel allergyPanel;
    private final Map<Allergy, JCheckBox> allergyCheckboxes = new HashMap<>();
    private final JButton toggleAllergiesButton;
    private final JButton saveButton = new JButton(InfoCollectionViewModel.SAVE_BUTTON_LABEL);
    private final JButton cancelButton = new JButton(InfoCollectionViewModel.CANCEL_BUTTON_LABEL);
    private final JLabel yearOfBirthErrorField = new JLabel();
    private final JLabel weightErrorField = new JLabel();
    private final JLabel heightErrorField = new JLabel();
    private final Map<String, Double> activityMultipliers = new HashMap<>();
    private InfoCollectionController infoCollectionController;

    {
        activityMultipliers.put("Sedentary (little or no exercise)", 1.2);
        activityMultipliers.put("Lightly active (light exercise 1-3 days/week)", 1.375);
        activityMultipliers.put("Moderately active (moderate exercise 3-5 days/week)", 1.55);
        activityMultipliers.put("Very active (hard exercise 6-7 days/week)", 1.725);
        activityMultipliers.put("Super active (very hard exercise/physical job)", 1.9);
    }

    public InfoCollectionView(InfoCollectionViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.infoCollectionViewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.infoCollectionViewModel.addPropertyChangeListener(this);

        // Initialize components
        activityField = new JComboBox<>(activityMultipliers.keySet().toArray(new String[0]));
        allergyPanel = new JPanel();
        toggleAllergiesButton = new JButton(InfoCollectionViewModel.ALLERGIES_LABEL);

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setPreferredSize(new Dimension(500, mainContentPanel.getPreferredSize().height));
        mainContentPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

        yearOfBirthErrorField.setForeground(Color.RED);
        weightErrorField.setForeground(Color.RED);
        heightErrorField.setForeground(Color.RED);

        JLabel title = new JLabel(InfoCollectionViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 16));
        mainContentPanel.add(title);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel formPanel = createBasicInfoPanel();
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContentPanel.add(formPanel);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel allergySection = createAllergyPanel();
        allergySection.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContentPanel.add(allergySection);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContentPanel.add(buttonPanel);

        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(600, 500));

        setupListeners();
    }

    private JPanel createBasicInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(panel, gbc, 0, InfoCollectionViewModel.BIRTH_YEAR_LABEL,
            yearOfBirthField, yearOfBirthErrorField);

        addFormRow(panel, gbc, 1, InfoCollectionViewModel.GENDER_LABEL,
            genderField, null);

        addFormRow(panel, gbc, 2, InfoCollectionViewModel.WEIGHT_LABEL,
            weightField, weightErrorField);

        addFormRow(panel, gbc, 3, InfoCollectionViewModel.HEIGHT_LABEL,
            heightField, heightErrorField);

        addFormRow(panel, gbc, 4, InfoCollectionViewModel.ACTIVITY_LABEL,
            activityField, null);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        return panel;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row,
                            String label, JComponent field, JComponent error) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);

        if (error != null) {
            gbc.gridx = 2;
            gbc.weightx = 0;
            panel.add(error, gbc);
        }
    }

    private JPanel createAllergyPanel() {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        allergyPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        allergyPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (Allergy allergy : Allergy.values()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));

            JCheckBox checkBox = new JCheckBox(allergy.getDisplayName());
            allergyCheckboxes.put(allergy, checkBox);
            itemPanel.add(checkBox);

            JButton infoButton = new JButton("i");
            infoButton.setPreferredSize(new Dimension(20, 20));
            infoButton.addActionListener(e -> showAllergyInfo(allergy));
            itemPanel.add(infoButton);

            allergyPanel.add(itemPanel);
        }

        allergyPanel.setVisible(false);

        toggleAllergiesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        containerPanel.add(toggleAllergiesButton);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        containerPanel.add(allergyPanel);

        return containerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(saveButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(cancelButton);
        return panel;
    }

    private void setupListeners() {
        toggleAllergiesButton.addActionListener(e -> {
            boolean isVisible = !allergyPanel.isVisible();
            allergyPanel.setVisible(isVisible);
            toggleAllergiesButton.setText(
                isVisible ? "Hide Allergies & Preferences" : InfoCollectionViewModel.ALLERGIES_LABEL);
            revalidate();
            repaint();
        });

        saveButton.addActionListener(e -> {
            if (validateInput()) {
                Set<Allergy> selectedAllergies = new HashSet<>();
                allergyCheckboxes.forEach((allergy, checkbox) -> {
                    if (checkbox.isSelected()) {
                        selectedAllergies.add(allergy);
                    }
                });

                infoCollectionController.execute(
                    infoCollectionViewModel.getState().getUsername(),
                    infoCollectionViewModel.getState().getPassword(),
                    Integer.parseInt(yearOfBirthField.getText()),
                    (String) genderField.getSelectedItem(),
                    Integer.parseInt(weightField.getText()),
                    Integer.parseInt(heightField.getText()),
                    activityMultipliers.get(activityField.getSelectedItem()),
                    selectedAllergies
                );
            }
        });

        cancelButton.addActionListener(this);

        addTextFieldListener(yearOfBirthField, "yearOfBirth");
        addTextFieldListener(weightField, "weight");
        addTextFieldListener(heightField, "height");
    }

    private void addTextFieldListener(JTextField textField, String fieldName) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                InfoCollectionState currentState = infoCollectionViewModel.getState();
                try {
                    int value = Integer.parseInt(textField.getText());
                    switch (fieldName) {
                        case "yearOfBirth" -> currentState.setYearOfBirth(value);
                        case "weight" -> currentState.setWeight(value);
                        case "height" -> currentState.setHeight(value);
                    }
                } catch (NumberFormatException ignored) {
                }
                infoCollectionViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateState();
            }
        });
    }

    private boolean validateInput() {
        boolean isValid = true;

        try {
            int yearOfBirth = Integer.parseInt(yearOfBirthField.getText());
            int age = LocalDate.now().getYear() - yearOfBirth;
            if (age < 13 || age > 120) {
                yearOfBirthErrorField.setText("Invalid age range (13-120)");
                isValid = false;
            } else {
                yearOfBirthErrorField.setText("");
            }
        } catch (NumberFormatException e) {
            yearOfBirthErrorField.setText("Invalid year");
            isValid = false;
        }

        try {
            int weight = Integer.parseInt(weightField.getText());
            if (weight < 30 || weight > 200) {
                weightErrorField.setText("Invalid weight (30-200 kg)");
                isValid = false;
            } else {
                weightErrorField.setText("");
            }
        } catch (NumberFormatException e) {
            weightErrorField.setText("Invalid weight");
            isValid = false;
        }

        try {
            int height = Integer.parseInt(heightField.getText());
            if (height < 100 || height > 250) {
                heightErrorField.setText("Invalid height (100-250 cm)");
                isValid = false;
            } else {
                heightErrorField.setText("");
            }
        } catch (NumberFormatException e) {
            heightErrorField.setText("Invalid height");
            isValid = false;
        }

        return isValid;
    }

    private void showAllergyInfo(Allergy allergy) {
        String info = getAllergyInfo(allergy);
        JOptionPane.showMessageDialog(this, info,
            allergy.getDisplayName() + " Information",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private String getAllergyInfo(Allergy allergy) {
        return switch (allergy) {
            case DAIRY -> "Products free from milk and milk derivatives.";
            case GLUTEN -> "Products free from wheat, barley, rye, and their derivatives.";
            case PEANUT -> "Products free from peanuts and peanut derivatives.";
            default -> "Products free from " + allergy.getDisplayName().toLowerCase();
        };
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(cancelButton)) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to cancel? You'll need to complete this information later.",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                viewManagerModel.setActiveView("sign up");
                viewManagerModel.firePropertyChanged();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        InfoCollectionState state = (InfoCollectionState) evt.getNewValue();
        if (state != null) {
            if (state.getYearOfBirth() != 0) {
                yearOfBirthField.setText(String.valueOf(state.getYearOfBirth()));
            }
            if (state.getWeight() != 0) {
                weightField.setText(String.valueOf(state.getWeight()));
            }
            if (state.getHeight() != 0) {
                heightField.setText(String.valueOf(state.getHeight()));
            }
            if (!state.getGender().isEmpty()) {
                genderField.setSelectedItem(state.getGender());
            }

            yearOfBirthErrorField.setText(state.getYearOfBirthError());
            weightErrorField.setText(state.getWeightError());
            heightErrorField.setText(state.getHeightError());

            Set<Allergy> stateAllergies = state.getAllergies();
            allergyCheckboxes.forEach((allergy, checkbox) ->
                checkbox.setSelected(stateAllergies.contains(allergy))
            );
        }
    }

    public void setInfoCollectionController(InfoCollectionController controller) {
        this.infoCollectionController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}