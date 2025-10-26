package com.view.auth;

import java.io.IOException;

import com.view.auth.valid.RegisterValid;
import com.view.utility.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterPage {
    @FXML
    private TextField textFieldName,textFieldEmail;
    @FXML
    private Label userNameErrorLabel,userEmailErrorLabel,passwordErrorLabel,passwordConfirmErrorLabel;
    @FXML
    private PasswordField passwordFieldPassword, passwordFieldPasswordConfirm;
    @FXML
    private VBox formSection;

    private RegisterValid valid;

    @FXML
    public void initialize(){
        valid = new RegisterValid(textFieldName, textFieldEmail,userNameErrorLabel, userEmailErrorLabel, passwordErrorLabel, passwordConfirmErrorLabel, passwordFieldPassword,passwordFieldPasswordConfirm);
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
