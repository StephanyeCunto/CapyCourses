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
    private VBox formSection;

    @FXML
    private void login(){
       LoginValid valid = new LoginValid(user, userErrorLabel, password);
       valid.teste();
       valid.init();
    }

    @FXML
    private void register()throws IOException{
        ViewLoader.load("/com/auth/RegisterPage.fxml", formSection);
    }
}
