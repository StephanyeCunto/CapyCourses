package com.view.auth;

import java.io.IOException;

import com.controller.auth.RegisterController;
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
    public void register()throws IOException{
       if(valid.isCheck()){
            RegisterController rgc = new RegisterController();
            String isRegister = rgc.isRegister(textFieldName.getText(),textFieldEmail.getText(),passwordFieldPassword.getText(),getUserType());
            if(isRegister.equals("true")) ViewLoader.load("/com/auth/RegisterPageSecundary.fxml", formSection);

       }
    }

    @FXML
    private void login()throws IOException{
        ViewLoader.load("/com/auth/LoginPage.fxml", formSection);
    }

    private String getUserType(){
        return (radioButtonStudent.isSelected()) ? "STUDENT" : "TEACHER";
    }
}
