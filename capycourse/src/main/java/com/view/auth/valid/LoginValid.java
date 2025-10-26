package com.view.auth.valid;

import com.view.utility.ValidUtility;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginValid implements AuthValid{
    @FXML
    private TextField user;
    @FXML
    private Label userErrorLabel;
    @FXML 
    private PasswordField password;
    @FXML
    private Label passwordErrorLabel;

    @Override
    public void init(){
        ValidUtility.checkWithValidationSupport(user, USER_REGEX);
        ValidUtility.checkWithValidationSupport(password, PASSWORD_REGEX);
    }

    @Override
    public boolean isCheck(){
        boolean isCheck = true;
        if(!ValidUtility.isCheck(user, userErrorLabel, USER_REGEX, "Formato inválido (exemplo: nome@provedor.com)")) isCheck = false;
        if(!ValidUtility.isCheck(password, passwordErrorLabel, PASSWORD_REGEX, "Senha inválida")) isCheck = false;

        return isCheck;
    }

    public void printError(){
        ValidUtility.updateErrorDisplay(user, userErrorLabel, false, "Email ou senha incorreto");
        ValidUtility.updateErrorDisplay(password, passwordErrorLabel, false, "Email ou senha incorreto");
    }
}
