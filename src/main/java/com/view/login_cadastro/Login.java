package com.view.login_cadastro;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import com.UserSession;
import com.controller.login_cadastro.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import com.view.valid.login_cadastro.LoginValid;

public class Login extends BaseLoginCadastro implements Initializable {
    @FXML private VBox leftSection;
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private Label userErrorLabel;
    @FXML private Label passwordErrorLabel;

    private final LoginValid validator = new LoginValid();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();
        validator.loadValues(user, password, userErrorLabel, passwordErrorLabel);
        validator.setupInitialState(user,password,userErrorLabel,passwordErrorLabel);
    }


    @FXML
    private void logar() {
        validator.getValidationSupport().setValidationDecorator(new GraphicValidationDecoration());
        if (!validator.validateFields()) {
            return;
        }

        LoginController plc = new LoginController();
        UserSession.getInstance().setUserEmail(user.getText());
        String isCheck = plc.isCheck(user.getText(), password.getText());
        Stage stage = (Stage) leftSection.getScene().getWindow();

        switch (isCheck) {
            case "true" -> super.redirectTo("/com/professor/paginaCadastroCurso.fxml", stage);
            case "incomplete student" -> super.redirectTo("/com/login_cadastro/paginaCadastroStudent.fxml", stage);
            case "incomplete teacher" -> super.redirectTo("/com/login_cadastro/paginaCadastroTeacher.fxml", stage);
            default -> {
                UserSession.getInstance().clearSession();
                showErrorAlert("Erro de Login", "Credenciais inválidas", 
                               "Por favor, verifique seu email e senha.");
            }
        }
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void register() {
        Stage stage = (Stage) leftSection.getScene().getWindow();
        super.redirectTo("/com/login_cadastro/paginaCadastro.fxml", stage);
    }
}
