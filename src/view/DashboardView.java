package view;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.logout.LogoutController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "dashboard";
    private final DashboardViewModel dashboardViewModel;
    private DashboardController dashboardController;
    private LogoutController logoutController;

    private final JLabel titleLabel;
    private final JLabel welcomeLabel;
    private final JLabel activityLabel;
    private final NutritionProgressPanel nutritionPanel;
    private final JButton updateProfileButton;
    private final JButton generateMealButton;
    private final JButton recordMealButton;
    private final JButton logoutButton;
    private final JLabel errorLabel;

    public DashboardView(DashboardViewModel viewModel) {
        this.dashboardViewModel = viewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize components
        titleLabel = new JLabel(DashboardViewModel.TITLE_LABEL);
        welcomeLabel = new JLabel();
        activityLabel = new JLabel();
        nutritionPanel = new NutritionProgressPanel();
        updateProfileButton = new JButton(DashboardViewModel.UPDATE_PROFILE_BUTTON_LABEL);
        generateMealButton = new JButton(DashboardViewModel.GENERATE_MEAL_BUTTON_LABEL);
        recordMealButton = new JButton(DashboardViewModel.RECORD_MEAL_BUTTON_LABEL);
        logoutButton = new JButton("Logout");
        errorLabel = new JLabel();

        styleComponents();
        layoutComponents();
        addListeners();
    }

    private void styleComponents() {
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.BOLD, 18));
        activityLabel.setFont(new Font(activityLabel.getFont().getName(), Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension buttonSize = new Dimension(150, 40);
        updateProfileButton.setPreferredSize(buttonSize);
        generateMealButton.setPreferredSize(buttonSize);
        recordMealButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);
    }

    private void layoutComponents() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        activityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(activityLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(updateProfileButton);
        buttonPanel.add(generateMealButton);
        buttonPanel.add(recordMealButton);
        buttonPanel.add(logoutButton);

        add(headerPanel, BorderLayout.NORTH);
        add(nutritionPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        updateProfileButton.addActionListener(this);
        generateMealButton.addActionListener(this);
        recordMealButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        DashboardState state = (DashboardState) dashboardViewModel.getState();

        if (evt.getSource() == updateProfileButton) {
            dashboardController.onUpdateProfile();
        } else if (evt.getSource() == generateMealButton) {
            dashboardController.onGenerateMeal(state.getUsername());
        } else if (evt.getSource() == recordMealButton) {
            dashboardController.onCustomize();
        } else if (evt.getSource() == logoutButton && logoutController != null) {
            logoutController.execute(state.getUsername());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            DashboardState state = (DashboardState) evt.getNewValue();
            if (state != null) {
                welcomeLabel.setText(DashboardViewModel.WELCOME_LABEL + state.getUsername());
                activityLabel.setText("Activity Level: " + state.getActivityLevel());
                errorLabel.setText(state.getError());

                nutritionPanel.updateProgress(
                        state.getCaloriePercentage(),
                        state.getCarbsPercentage(),
                        state.getProteinPercentage(),
                        state.getFatPercentage(),
                        state.getFormattedCalorieProgress(),
                        state.getFormattedCarbsProgress(),
                        state.getFormattedProteinProgress(),
                        state.getFormattedFatProgress()
                );
            }
        }
    }

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    public void setLogoutController(LogoutController controller) {
        this.logoutController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}