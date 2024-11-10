package com.view.login_cadastro;

import java.net.URL;
import java.util.ResourceBundle;
import java.awt.Toolkit;

import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import com.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.controller.login_cadastro.LoginController;
import com.view.login_cadastro.elements.ErrorNotification;
import com.view.login_cadastro.valid.LoginValid;


public class Login extends BaseLoginCadastro implements Initializable {
    @FXML
    private VBox leftSection;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label passwordErrorLabel;

    private final LoginValid validator = new LoginValid();
    private ErrorNotification errorNotification;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();
        validator.setupInitialState(user, password, userErrorLabel, passwordErrorLabel);
        setupErrorNotification();
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
                showError();
                password.clear();
                user.requestFocus();
            }
        }
    }

    private void setupErrorNotification() {
        StackPane root = new StackPane();
        leftSection.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                errorNotification = new ErrorNotification(root);
                if (newScene.getRoot() instanceof StackPane) {
                    ((StackPane) newScene.getRoot()).getChildren().add(errorNotification.getContainer());
                } else {
                    root.getChildren().addAll(newScene.getRoot(), errorNotification.getContainer());
                    newScene.setRoot(root);
                }
            }
        });
    }

    private void showError() {
        Toolkit.getDefaultToolkit().beep();
        errorNotification.show();
    }

    @FXML
    private void register() {
        Stage stage = (Stage) leftSection.getScene().getWindow();
        super.redirectTo("/com/login_cadastro/paginaCadastro.fxml", stage);
    }
}