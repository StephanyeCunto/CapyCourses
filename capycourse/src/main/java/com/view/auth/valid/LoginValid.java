package com.view.auth.valid;

import org.controlsfx.validation.ValidationSupport;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginValid {
    private static final ValidationSupport validationSupport = new ValidationSupport();
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    private static final String USER_REGEX = "^[A-Za-z]+[A-Za-z0-9+_.-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    
    @FXML 
    private TextField user;
    @FXML
    private Label userErrorLabel;
    @FXML 
    private PasswordField password;


  public void teste(){
    System.out.println(user.getText());
    System.out.println(password.getText());
  }

    public void init(){
        user.textProperty().addListener((observable) -> {
            boolean updateError = user.textProperty().get().matches(USER_REGEX);
            updateErrorDisplay(user, userErrorLabel, updateError, null);
        });
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
        errorLabel.setText(isValid ? "" : message);
        errorLabel.setVisible(!isValid);
    } 
}
