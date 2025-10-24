package com.view.login_cadastro.login.valid;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class LoginValid {
  private static final ValidationSupport validationSupport = new ValidationSupport();
  private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

  private static final String MSG_EMAIL_OBRIGATORIO = "Digite seu email para continuar";
  private static final String MSG_EMAIL_INVALIDO = "Formato inválido (exemplo: nome@provedor.com)";
  private static final String MSG_SENHA_OBRIGATORIA = "Digite sua senha para acessar";
  private static final String MSG_ERRO_GERAL = "Verifique os campos destacados";
  private static final int MIN_PASSWORD_LENGTH = 6;

  @FXML private TextField user;
  @FXML private PasswordField password;
  @FXML private Label userErrorLabel;
  @FXML private Label passwordErrorLabel;

  @SuppressWarnings("unused")
  public void setupInitialState(
      TextField user, PasswordField password, Label userErrorLabel, Label passwordErrorLabel) {
    userErrorLabel.setVisible(false);
    passwordErrorLabel.setVisible(false);

    loadValues(user, password, userErrorLabel, passwordErrorLabel);

    user.textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (user.getText().matches(EMAIL_REGEX)) {
                updateErrorDisplay(user, userErrorLabel, true, null);
              }
            });

    user.focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              validationSupport.registerValidator(
                  user,
                  false,
                  Validator.createPredicateValidator(
                      value -> {
                        if (value instanceof String) {
                          String strValue = (String) value;
                          return !strValue.trim().isEmpty() && strValue.matches(EMAIL_REGEX);
                        }
                        return false;
                      },
                      MSG_EMAIL_INVALIDO));
            });

    password
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (password.getText().length() >= MIN_PASSWORD_LENGTH) {
                updateErrorDisplay(password, passwordErrorLabel, true, null);
              }
            });

    password
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              validationSupport.registerValidator(
                  password,
                  false,
                  Validator.createPredicateValidator(
                      value -> {
                        if (value instanceof String) {
                          String strValue = (String) value;
                          return !strValue.trim().isEmpty()
                              && strValue.length() >= MIN_PASSWORD_LENGTH;
                        }
                        return false;
                      },
                      MSG_SENHA_OBRIGATORIA));
            });
  }

  public void loadValues(
      TextField user, PasswordField password, Label userErrorLabel, Label passwordErrorLabel) {
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

  private void updateErrorDisplay(
      Control field, Label errorLabel, boolean isValid, String message) {
    field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
    errorLabel.setText(isValid ? "" : message);
    errorLabel.setVisible(!isValid);

    if (isValid) {
      field.getStyleClass().remove("error-field");
    } else {
      field.getStyleClass().add("error-field");
    }
  }

  public boolean validateFields() {
    boolean isValid = true;
    String email = user.getText();
    String senha = password.getText();

    if (email == null || email.trim().isEmpty()) {
      updateErrorDisplay(user, userErrorLabel, false, MSG_EMAIL_OBRIGATORIO);
      isValid = false;
    } else if (!isValidEmail(email)) {
      updateErrorDisplay(user, userErrorLabel, false, MSG_EMAIL_INVALIDO);
      isValid = false;
    }

    if (senha == null || senha.trim().isEmpty()) {
      updateErrorDisplay(password, passwordErrorLabel, false, MSG_SENHA_OBRIGATORIA);
      isValid = false;
    } else if (!isValidPassword(senha)) {
      updateErrorDisplay(password, passwordErrorLabel, false, MSG_ERRO_GERAL);
      isValid = false;
    }

    return isValid;
  }
}
