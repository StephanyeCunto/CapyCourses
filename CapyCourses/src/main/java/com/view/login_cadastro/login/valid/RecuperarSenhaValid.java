package com.view.login_cadastro.login.valid;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.validation.ValidationSupport;

public class RecuperarSenhaValid {
  @FXML private Label emailErrorLabel;
  @FXML private TextField emailField;

  private static final ValidationSupport validationSupport = new ValidationSupport();
  private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
  private static final String MSG_EMAIL_OBRIGATORIO = "Digite seu email para continuar";
  private static final String MSG_EMAIL_INVALIDO = "Formato inválido (exemplo: nome@provedor.com)";

  public void initialize() {
    validationSupport.setErrorDecorationEnabled(false);
    emailErrorLabel.setVisible(false);

    emailField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.matches(EMAIL_REGEX)) {
                updateErrorDisplay(emailField, emailErrorLabel, true, null);
              }
            });
  }

  public void loadValues(TextField emailField, Label emailErrorLabel) {
    this.emailField = emailField;
    this.emailErrorLabel = emailErrorLabel;
  }

  private void updateErrorDisplay(
      Control field, Label errorLabel, boolean isValid, String message) {
    field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
    errorLabel.setText(isValid ? "" : message);
    errorLabel.setVisible(!isValid);

    if (isValid) {
      field.getStyleClass().remove("error-field");
    } else if (!field.getStyleClass().contains("error-field")) {
      field.getStyleClass().add("error-field");
    }
  }

  public boolean validateFields() {
    boolean isValid = true;
    String email = emailField.getText();

    if (email == null || email.trim().isEmpty()) {
      updateErrorDisplay(emailField, emailErrorLabel, false, MSG_EMAIL_OBRIGATORIO);
      isValid = false;
    } else if (!email.matches(EMAIL_REGEX)) {
      updateErrorDisplay(emailField, emailErrorLabel, false, MSG_EMAIL_INVALIDO);
      isValid = false;
    } else {
      updateErrorDisplay(emailField, emailErrorLabel, true, null);
    }

    return isValid;
  }
}
