package com.view.login_cadastro.base;

import com.controller.login_cadastro.LoginController;
import com.singleton.UserSession;
import com.view.login_cadastro.elements.ErrorNotification;
import com.view.login_cadastro.valid.LoginValid;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import javafx.beans.property.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login implements Initializable {
    private StringProperty page = new SimpleStringProperty("Login");

    private final LoginValid VALIDATOR = new LoginValid();
    private ErrorNotification errorNotification;

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private VBox leftSection;
    @FXML
    private CheckBox lembrar;

    public void initialize(URL location, ResourceBundle resources) {
        VALIDATOR.setupInitialState(user, password, userErrorLabel, passwordErrorLabel);
    }

    public void setupErrorNotification() {
        StackPane root = new StackPane();
        leftSection.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                errorNotification = new ErrorNotification(root, "Usuário ou senhas incorretos");
                if (newScene.getRoot() instanceof StackPane) {
                    ((StackPane) newScene.getRoot()).getChildren().add(errorNotification.getContainer());
                } else {
                    root.getChildren().addAll(newScene.getRoot(), errorNotification.getContainer());
                    newScene.setRoot(root);
                }
            }
        });
    }

    @FXML
    private void logar() {
        VALIDATOR.getValidationSupport().setValidationDecorator(new GraphicValidationDecoration());
        if (!VALIDATOR.validateFields()) {
            return;
        }

        LoginController plc = new LoginController();
        UserSession.getInstance().setUserEmail(user.getText());
        String isCheck = plc.isCheck(user.getText(), password.getText());

        if (isCheck.equals("true")) {
            String userType = plc.getUserType(user.getText());
            UserSession.getInstance().setUserType(userType);

            switch (userType.toUpperCase()) {
                case "STUDENT":
                    page.set("StudentEntry");
                    break;
                case "TEACHER":
                    page.set("TeacherEntry");
                    break;
                default:
                    showError();
                    break;
            }
        } else if (isCheck.equals("incomplete student")) {
            UserSession.getInstance().setRegisterIncomplet("Student");
            UserSession.getInstance().setUserType("STUDENT");
            page.set("StudentRegister");
        } else if (isCheck.equals("incomplete teacher")) {
            UserSession.getInstance().setRegisterIncomplet("Teacher");
            UserSession.getInstance().setUserType("TEACHER");
            page.set("TeacherRegister");
        } else {
            UserSession.getInstance().clearSession();
            showError();
            password.clear();
            user.requestFocus();
        }
    }

    private void showError() {
        errorNotification.show();
    }

    @FXML
    private void register() {
        page.set("Cadastro");
    }
}
