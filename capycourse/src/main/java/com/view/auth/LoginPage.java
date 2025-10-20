package com.view.auth;

import java.io.IOException;

import com.view.auth.valid.LoginValid;
import com.view.utility.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginPage {
    @FXML
    private TextField user;
    @FXML
    private Label userErrorLabel;
    @FXML
    private PasswordField password;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private VBox formSection;

    private LoginValid valid;

    @FXML
    public void initialize(){
        valid = new LoginValid(user, userErrorLabel, password, passwordErrorLabel);
        valid.init();
    }

    @FXML
    private void login(){
        valid.isCheck();
    }

    @FXML
    private void register()throws IOException{
        ViewLoader.load("/com/auth/RegisterPage.fxml", formSection);
    }
}
