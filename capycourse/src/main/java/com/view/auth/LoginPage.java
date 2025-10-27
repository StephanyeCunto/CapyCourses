package com.view.auth;

import java.io.IOException;

import com.controller.auth.LoginController;
import com.view.auth.valid.LoginValid;
import com.view.utility.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginPage {
    @FXML
    private TextField user;
    @FXML
    private Label userErrorLabel,passwordErrorLabel;
    @FXML
    private PasswordField password;
    @FXML
    private VBox formSection;

    private LoginValid valid;

    @FXML
    public void initialize(){
        valid = new LoginValid(user, userErrorLabel, password, passwordErrorLabel);
        valid.init();
    }

    @FXML
    private void login()throws IOException{
        if(valid.isCheck()) performLogin();
    }

    private void performLogin()throws IOException{
        LoginController lgc = new LoginController();
        if(lgc.isCheck(user.getText(), password.getText())) logar();
        else valid.printError();
    }

    private void logar()throws IOException{
        ViewLoader.load("/com/auth/RegisterPage.fxml", formSection);
    }

    @FXML
    private void register()throws IOException{
        ViewLoader.load("/com/auth/RegisterPage.fxml", formSection);
    }
}
