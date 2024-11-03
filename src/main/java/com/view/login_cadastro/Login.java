package com.view.login_cadastro;

import java.net.URL;
import java.util.ResourceBundle;

import com.UserSession;
import com.controller.login_cadastro.LoginController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends BaseLoginCadastro implements Initializable {
    @FXML
    private VBox leftSection;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();
    }

    @FXML
    private void register() {
        Stage stage = (Stage) leftSection.getScene().getWindow();
        super.redirectTo("/com/login_cadastro/paginaCadastro.fxml", stage);
    }

    @FXML
    private void logar() {
        LoginController plc = new LoginController();
        UserSession.getInstance().setUserEmail(user.getText());
        String isCheck = plc.isCheck(user.getText(), password.getText());
        Stage stage = (Stage) leftSection.getScene().getWindow();

        if (isCheck =="true") {
            super.redirectTo("/com/paginaInical.fxml", stage);
        }else if(isCheck == "incomplete student") {
            super.redirectTo("/com/login_cadastro/paginaCadastroStudent.fxml", stage);
        }
        else if(isCheck == "incomplete teacher") {
            super.redirectTo("/com/login_cadastro/paginaCadastroTeacher.fxml", stage);
        }else{
            UserSession.getInstance().clearSession();
        }
    }
}
