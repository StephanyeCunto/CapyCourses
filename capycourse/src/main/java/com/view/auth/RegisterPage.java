package com.view.auth;

import java.io.IOException;

import com.view.auth.valid.RegisterValid;
import com.view.utility.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterPage {
    @FXML
    private TextField textFieldName;
    @FXML
    private Label userNameErrorLabel;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private Label userEmailErrorLabel;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private PasswordField passwordFieldPasswordConfirm;
    @FXML
    private Label passwordConfirmErrorLabel;
    @FXML
    private VBox formSection;

    private RegisterValid valid;

    @FXML
    public void initialize(){
        valid = new RegisterValid(textFieldName, userNameErrorLabel, textFieldEmail, userEmailErrorLabel, passwordFieldPassword, passwordErrorLabel, passwordFieldPasswordConfirm, passwordConfirmErrorLabel);
        valid.init();
    }

    @FXML
    public void register()throws IOException{
       if(valid.isCheck()) ViewLoader.load("/com/auth/RegisterPageSecundary.fxml", formSection);
    }

     @FXML
    private void login()throws IOException{
        ViewLoader.load("/com/auth/LoginPage.fxml", formSection);
    }
}
