package com.view.login_cadastro.base;

import com.view.elements.buttonModos.ButtonModos;
import com.view.login_cadastro.cadastro.Cadastro;
import com.view.login_cadastro.cadastro.CadastroSecundario;
import com.view.login_cadastro.login.Login;
import com.view.login_cadastro.login.RecuperarSenha;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Base implements Initializable {
  @FXML private GridPane mainPane;
  @FXML private StackPane container;
  @FXML private VBox leftSection;
  @FXML private VBox rightSection;
  @FXML private HBox toggleButtonHBox;

  private StringProperty page = new SimpleStringProperty("Login");

  public void initialize(URL location, ResourceBundle resources) {
    Inicializar in = new Inicializar();
    in.loadAnimation(mainPane, container, leftSection, rightSection);

    loadButtonModos();
    loadPage();

    getPage()
        .addListener(
            (observable, oldValue, newValue) -> {
              loadPage();
            });
  }

  private void loadButtonModos() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/buttonModos.fxml"));
      Parent root = loader.load();
      ButtonModos buttonModos = loader.getController();
      buttonModos.setContainer(container);
      buttonModos.setDarkStylePath("/com/login_cadastro/style/dark/style.css");
      buttonModos.setLigthStringPath("/com/login_cadastro/style/ligth/style.css");
      buttonModos.changeMode();
      buttonModos.initialize();
      toggleButtonHBox.getChildren().add(root);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadPage() {
    try {
      if (getPage().get().equals("Login")) {
        loadLogin();
      } else if (getPage().get().equals("Cadastro")) {
        loadCadastro();
      } else if (getPage().get().equals("TeacherRegister")
          || getPage().get().equals("StudentRegister")) {
        loadCadastroSecundario();
      } else if (getPage().get().equals("passwordRecovery")) {
        loadPasswordRecovery();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadLogin() {
    try {
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/com/login_cadastro/login/paginaLogin.fxml"));
      Parent root = loader.load();
      Login controller = loader.getController();
      controller
          .getPage()
          .addListener(
              (observable, oldValue, newValue) -> {
                page.set(controller.getPage().get());
              });

      controller.setLeftSection(leftSection);
      controller.setupErrorNotification();

      rightSection.getChildren().clear();
      rightSection.getChildren().add(root);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadCadastro() {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/com/login_cadastro/cadastro/paginaCadastro.fxml"));
      Parent root = loader.load();
      Cadastro controller = loader.getController();
      controller
          .getPage()
          .addListener(
              (observable, oldValue, newValue) -> {
                page.set(controller.getPage().get());
              });

      rightSection.getChildren().clear();
      rightSection.getChildren().add(root);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadCadastroSecundario() {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/com/login_cadastro/cadastro/paginaCadastroSecundario.fxml"));
      Parent root = loader.load();
      CadastroSecundario controller = loader.getController();
      controller.setPage(page);
      controller.initial();

      controller
          .getPage()
          .addListener(
              (observable, oldValue, newValue) -> {
                page.set(controller.getPage().get());
              });

      rightSection.getChildren().clear();
      rightSection.getChildren().add(root);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadPasswordRecovery() {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/com/login_cadastro/login/paginaRecuperarSenha.fxml"));
      Parent root = loader.load();
      RecuperarSenha controller = loader.getController();
      controller
          .getPage()
          .addListener(
              (observable, oldValue, newValue) -> {
                page.set(((ObservableObjectValue<String>) controller.getPage()).get());
              });
      rightSection.getChildren().clear();
      rightSection.getChildren().add(root);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
