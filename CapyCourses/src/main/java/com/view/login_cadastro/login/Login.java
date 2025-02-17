package com.view.login_cadastro.login;

import com.controller.login_cadastro.LoginController;
import com.singleton.UserSession;
import com.view.login_cadastro.elements.ErrorNotification;
import com.view.login_cadastro.login.valid.LoginValid;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login implements Initializable {
  private StringProperty page = new SimpleStringProperty("Login");

  private final LoginValid VALIDATOR = new LoginValid();
  private ErrorNotification errorNotification;

  @FXML private TextField user;
  @FXML private PasswordField password;
  @FXML private Label userErrorLabel;
  @FXML private Label passwordErrorLabel;
  @FXML private VBox leftSection;
  @FXML private CheckBox lembrar;

  public void initialize(URL location, ResourceBundle resources) {
    VALIDATOR.setupInitialState(user, password, userErrorLabel, passwordErrorLabel);
  }

  public void setupErrorNotification() {
    StackPane root = new StackPane();
    leftSection
        .sceneProperty()
        .addListener(
            (obs, oldScene, newScene) -> {
              if (newScene != null) {
                errorNotification = new ErrorNotification(root, "Usuário ou senhas incorretos");
                if (newScene.getRoot() instanceof StackPane) {
                  ((StackPane) newScene.getRoot())
                      .getChildren()
                      .add(errorNotification.getContainer());
                } else {
                  root.getChildren().addAll(newScene.getRoot(), errorNotification.getContainer());
                  newScene.setRoot(root);
                }
              }
            });
  }

  @FXML
  private void logar() {
    if (!VALIDATOR.validateFields()) {
      return;
    }

    LoginController plc = new LoginController();
    UserSession.getInstance().setUserEmail(user.getText());
    String authenticationStatus = plc.isCheck(user.getText(), password.getText());

    switch (authenticationStatus) {
      case "true":
        handleSuccessfulLogin(plc);
        break;
      case "incomplete student":
        handleIncompleteRegistration("Student");
        break;
      case "incomplete teacher":
        handleIncompleteRegistration("Teacher");
        break;
      default:
        handleFailedLogin();
        break;
    }
  }

  private void handleSuccessfulLogin(LoginController plc) {
    String userType = plc.getUserType(user.getText());
    UserSession.getInstance().setUserType(userType);

    try {
      String pagePath;
      if (userType.equalsIgnoreCase("TEACHER")) {
        pagePath = "/com/professor/paginaCadastroCurso.fxml";
      } else {
        pagePath = "/com/estudante/paginaInicial/paginaInicial.fxml";
      }

      Parent root = FXMLLoader.load(getClass().getResource(pagePath));
      Scene scene = user.getScene();
      Stage stage = (Stage) scene.getWindow();

      Scene newScene = new Scene(root, scene.getWidth(), scene.getHeight());
      stage.setScene(newScene);
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
      // Adicione tratamento de erro adequado aqui
    }
  }

  private void handleIncompleteRegistration(String userType) {
    UserSession.getInstance().setRegisterIncomplet(userType);
    UserSession.getInstance().setUserType(userType);
    page.set(userType + "Register");
  }

  private void handleFailedLogin() {
    UserSession.getInstance().clearSession();
    showError();
    password.clear();
    user.requestFocus();
  }

  private void showError() {
    errorNotification.show();
  }

  @FXML
  private void register() {
    page.set("Cadastro");
  }

  @FXML
  private void loadPasswordRecovery() {
    page.set("passwordRecovery");
  }
}
