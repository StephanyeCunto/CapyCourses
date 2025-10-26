package com.view.auth;

import java.io.IOException;

import com.singleton.User;
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
    @FXML 
    private RadioButton radioButtonStudent;

    private RegisterValid valid;

    @FXML
    public void initialize(){
        valid = new RegisterValid(textFieldName, textFieldEmail,userNameErrorLabel, userEmailErrorLabel, passwordErrorLabel, passwordConfirmErrorLabel, passwordFieldPassword,passwordFieldPasswordConfirm);
        valid.init();
    }

    @FXML
    private void login()throws IOException{
        ViewLoader.load("/com/auth/LoginPage.fxml", formSection);
    }


    @FXML
    public void register()throws IOException{
        System.out.println("valid.isCheck(): "+valid.isCheck());
       if(valid.isCheck()){
            loadUser();
            ViewLoader.load("/com/auth/RegisterPageSecundary.fxml", formSection);
       }
    }

    private void loadUser(){
        User user = User.getInstance();
        user.setUserName(textFieldName.getText());
        user.setUserEmail(textFieldEmail.getText());
        user.setPassword(passwordFieldPassword.getText());
        user.setType(getUserType());
    }


    private String getUserType(){
        return (radioButtonStudent.isSelected()) ? "STUDENT" : "TEACHER";
    }
}
