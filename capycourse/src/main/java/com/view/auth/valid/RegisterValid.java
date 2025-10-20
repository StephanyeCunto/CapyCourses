package com.view.auth.valid;

import com.view.utility.ValidUtility;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterValid implements AuthValid{
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

    @Override
    public void init(){
        ValidUtility.checkWithValidationSupport(textFieldName, NAME_REGEX);
        ValidUtility.checkWithValidationSupport(textFieldEmail,USER_REGEX);
        ValidUtility.checkWithValidationSupport(passwordFieldPassword,PASSWORD_REGEX);
        ValidUtility.checkWithValidationSupport(passwordFieldPasswordConfirm, PASSWORD_REGEX);
    }

    @Override
    public boolean isCheck(){
        boolean isCheck = true;

        if(!ValidUtility.isCheck(textFieldName, userNameErrorLabel, NAME_REGEX, "Nome inválido! Digite nome e sobrenome (exemplo: Maria Silva)")) isCheck = false;
        if(!ValidUtility.isCheck(textFieldEmail, userEmailErrorLabel, USER_REGEX, "Formato inválido (exemplo: nome@provedor.com)")) isCheck = false;
        if(!ValidUtility.isCheck(passwordFieldPassword, passwordErrorLabel, PASSWORD_REGEX,"Senha inválida! Use 8-20 caracteres com maiúscula, número e símbolo.")) isCheck = false;
        if (!ValidUtility.areEqual(passwordFieldPassword, passwordFieldPasswordConfirm, passwordConfirmErrorLabel, "As senhas não coincidem.")) isCheck = false;

        return isCheck;
    }
}
