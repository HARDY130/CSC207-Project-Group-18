package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;  // Add this field

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();
    private final JButton logIn;
    private final JButton cancel;
    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {  // Add viewManagerModel parameter
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;  // Store viewManagerModel
        this.loginViewModel.addPropertyChangeListener(this);

        // Rest of the constructor remains the same
        JLabel title = new JLabel("Login Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo = new LabelTextPanel(new JLabel("Username"), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(new JLabel("Password"), passwordInputField);

        JPanel buttons = new JPanel();
        logIn = new JButton("Log in");
        buttons.add(logIn);
        cancel = new JButton("Cancel");
        buttons.add(cancel);

        // Add the login button action listener
        logIn.addActionListener(evt -> {
            if (evt.getSource().equals(logIn)) {
                LoginState currentState = loginViewModel.getState();
                loginController.execute(currentState.getUsername(), currentState.getPassword());
            }
        });

        // Add the cancel button action listener
        cancel.addActionListener(this);

        // Set up the text field listeners
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
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

            private void updateState() {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
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

            private void updateState() {
                LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(passwordInfo);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == cancel) {
            viewManagerModel.setActiveView("sign up");
            viewManagerModel.firePropertyChanged();
            // Clear the input fields
            usernameInputField.setText("");
            passwordInputField.setText("");
        }
    }

    // Rest of the class remains the same
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        if (state != null) {
            usernameInputField.setText(state.getUsername());
            passwordInputField.setText(state.getPassword());
            usernameErrorField.setText(state.getLoginError());
        }
    }

    public void setLoginController(LoginController controller) {
        this.loginController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}