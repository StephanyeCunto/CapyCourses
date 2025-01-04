package com.view.login_cadastro.cadastro.valid;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CadastroValid {
    private static final ValidationSupport validationSupport = new ValidationSupport();
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    private static final String NAME_REGEX = "^[A-ZÀ-ÿa-zà-ÿ]+(?: [A-ZÀ-ÿa-zà-ÿ]+)+$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final int MIN_PASSWORD_LENGTH = 6;

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldPasswordConfirm;
    @FXML
    private Label userNameErrorLabel;
    @FXML
    private Label userEmailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label passwordConfirmErrorLabel;

    @SuppressWarnings("unused")
    public void setupInitialState(TextField textFieldName, TextField textFieldEmail,
            PasswordField passwordFieldPassword,
            PasswordField passwordFieldPasswordConfirm,
            Label userNameErrorLabel, Label userEmailErrorLabel, Label passwordErrorLabel,
            Label passwordConfirmErrorLabel) {

        loadValues(textFieldName, textFieldEmail, passwordFieldPassword, passwordFieldPasswordConfirm,userNameErrorLabel, userEmailErrorLabel, passwordErrorLabel, passwordConfirmErrorLabel);

        userNameErrorLabel.setVisible(false);
        userEmailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        passwordConfirmErrorLabel.setVisible(false);

        textFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(NAME_REGEX)) {
                updateErrorDisplay(textFieldName, userNameErrorLabel, true, null);
            }
        });

        textFieldName.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(textFieldName, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty()  && strValue.matches(NAME_REGEX);
                        }
                        return false;
                    }, "Por favor, insira um nome válido"));
        });

        textFieldEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(EMAIL_REGEX)) {
                updateErrorDisplay(textFieldEmail, userEmailErrorLabel, true, null);
            }
        });

        textFieldEmail.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(textFieldEmail, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty() && strValue.matches(EMAIL_REGEX);
                        }
                        return false;
                    }, "Por favor, insira um email válido"));
        });

        passwordFieldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= MIN_PASSWORD_LENGTH) {
                updateErrorDisplay(passwordFieldPassword, passwordErrorLabel, true, null);
            }
        });

        passwordFieldPassword.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(passwordFieldPassword, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty() && strValue.length() >= MIN_PASSWORD_LENGTH;
                        }
                        return false;
                    }, "Por favor, insira uma senha válida, ela deve ter "+MIN_PASSWORD_LENGTH+ " caracteres míninos." ));
        });

        passwordFieldPasswordConfirm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(passwordFieldPassword.getText())) {
                updateErrorDisplay(passwordFieldPasswordConfirm, passwordConfirmErrorLabel, true, null);
            } 
        });

        passwordFieldPasswordConfirm.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(passwordFieldPasswordConfirm, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return strValue.equals(passwordFieldPassword.getText());
                        }
                        return false;
                    }, "As senhas não coincidem"));
        });
    }

    private void loadValues(TextField textFieldName, TextField textFieldEmail, PasswordField passwordFieldPassword,
            PasswordField passwordFieldPasswordConfirm,
            Label userNameErrorLabel, Label userEmailErrorLabel, Label passwordErrorLabel,
            Label passwordConfirmErrorLabel) {
        this.textFieldName = textFieldName;
        this.textFieldEmail = textFieldEmail;
        this.passwordFieldPassword = passwordFieldPassword;
        this.passwordFieldPasswordConfirm = passwordFieldPasswordConfirm;
        this.userNameErrorLabel = userNameErrorLabel;
        this.userEmailErrorLabel = userEmailErrorLabel;
        this.passwordErrorLabel = passwordErrorLabel;
        this.passwordConfirmErrorLabel = passwordConfirmErrorLabel;
    }

    public ValidationSupport getValidationSupport() {
        return validationSupport;
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches(NAME_REGEX);
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.trim().isEmpty() && email.matches(EMAIL_REGEX);
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isValidPassWordConfirm(String passwordConfirm, String password) {
        return passwordConfirm != null && passwordConfirm.equals(password);
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
        errorLabel.setText(isValid ? "" : message);
        errorLabel.setVisible(!isValid);
        errorLabel.setManaged(!isValid);
        if(isValid){
            field.getStyleClass().add("error-field");
        }else{
            field.getStyleClass().remove("error-field");
        }
    }

    public boolean validateFields() {
        boolean isValid = true;
        if (!isValidName(textFieldName.getText())) {
            updateErrorDisplay(textFieldName, userNameErrorLabel, false, "Por favor, insira um nome válido");
            isValid = false;
        }
        if (!isValidEmail(textFieldEmail.getText())) {
            updateErrorDisplay(textFieldEmail, userEmailErrorLabel, false, "Por favor, insira um email válido");
            isValid = false;
        }
        if (!isValidPassword(passwordFieldPassword.getText())) {
            updateErrorDisplay(passwordFieldPassword, passwordErrorLabel, false, "Por favor, insira uma senha válida, ela deve ter "+MIN_PASSWORD_LENGTH+ " caracteres míninos.");
            isValid = false;
        }
        if (!isValidPassWordConfirm(passwordFieldPasswordConfirm.getText(), passwordFieldPassword.getText())) {
            updateErrorDisplay(passwordFieldPasswordConfirm, passwordConfirmErrorLabel, false,
                    "As senhas devem ser iguais");
            isValid = false;
        }

        return isValid;
    }
}