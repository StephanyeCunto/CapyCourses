package com.view.login_cadastro.valid;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;

public class LoginValid {
    private static final ValidationSupport validationSupport = new ValidationSupport();
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$";
    private static final int MIN_PASSWORD_LENGTH = 1;

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label passwordErrorLabel;

    public void setupInitialState(TextField user, PasswordField password, Label userErrorLabel,
            Label passwordErrorLabel) {
        userErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        loadValues(user, password, userErrorLabel, passwordErrorLabel);

        user.textProperty().addListener((observable, oldValue, newValue) -> {
            if (user.getText().matches(EMAIL_REGEX)) {
                updateErrorDisplay(user, userErrorLabel, true, null);
            } 
        });

        user.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(user, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty() && strValue.matches(EMAIL_REGEX);
                        }
                        return false;
                    }, "Por favor, insira um email válido"));
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (password.getText().length() >= MIN_PASSWORD_LENGTH) {
                updateErrorDisplay(password, passwordErrorLabel, true, null);
            }
        });

        password.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(password, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty() && strValue.length() >= MIN_PASSWORD_LENGTH;
                        }
                        return false;
                    }, "Por favor, insira uma senha válida"));
        });
    }

    public void loadValues(TextField user, PasswordField password, Label userErrorLabel, Label passwordErrorLabel) {
        this.user = user;
        this.password = password;
        this.userErrorLabel = userErrorLabel;
        this.passwordErrorLabel = passwordErrorLabel;
    }

    public ValidationSupport getValidationSupport() {
        return validationSupport;
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.trim().isEmpty() && email.matches(EMAIL_REGEX);
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
        String currentStyle = field.getStyle();
        field.setStyle(currentStyle + "-fx-border-color: #FF6F61;  -fx-border-width: 1.5px;");
        errorLabel.setText(isValid ? "" : message);
        errorLabel.setVisible(!isValid);
    }

    public boolean validateFields() {
        boolean isValid = true;
        if (!isValidEmail(user.getText())) {
            updateErrorDisplay(user, userErrorLabel, false, "Por favor, insira um email válido");
            isValid = false;
        }
        if (!isValidPassword(password.getText())) {
            updateErrorDisplay(password, passwordErrorLabel, false,
                    "Por favor, insira uma senha");
            isValid = false;
        }

        return isValid;
    }
}
