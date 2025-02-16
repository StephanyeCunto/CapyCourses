package com.view.login_cadastro.cadastro;

import com.controller.login_cadastro.CadastroController;
import com.view.login_cadastro.cadastro.valid.CadastroValid;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Cadastro {
  private static final Logger LOGGER = Logger.getLogger(Cadastro.class.getName());

  @FXML private TextField textFieldName;
  @FXML private TextField textFieldEmail;
  @FXML private PasswordField passwordFieldPassword;
  @FXML private PasswordField passwordFieldPasswordConfirm;
  @FXML private ToggleGroup userType;
  @FXML private RadioButton radioButtonStudent;
  @FXML private RadioButton radioButtonTeacher;
  @FXML private Label userNameErrorLabel;
  @FXML private Label userEmailErrorLabel;
  @FXML private Label passwordErrorLabel;
  @FXML private Label passwordConfirmErrorLabel;

  private final CadastroValid VALIDADOR = new CadastroValid();

  @SuppressWarnings("PMD.ImmutableField")
  private final StringProperty page = new SimpleStringProperty("Cadastro");

  public void initialize() {
    VALIDADOR.setupInitialState(
        textFieldName,
        textFieldEmail,
        passwordFieldPassword,
        passwordFieldPasswordConfirm,
        userNameErrorLabel,
        userEmailErrorLabel,
        passwordErrorLabel,
        passwordConfirmErrorLabel);
  }

  @FXML
  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private void createAccount() {
    if (!VALIDADOR.validateFields()) {
      return;
    }

    String userTypeSelected =
        (radioButtonStudent != null && radioButtonStudent.isSelected()) ? "STUDENT" : "TEACHER";

    try {
      CadastroController controller = new CadastroController();
      String result =
          controller.cadastrar(
              getTextOrEmpty(textFieldName),
              getTextOrEmpty(textFieldEmail),
              getTextOrEmpty(passwordFieldPassword),
              LocalDateTime.now(),
              userTypeSelected);

      System.out.println(result);

      switch (result) {
        case "incomplete student":
          setPage("StudentRegister");
          break;
        case "incomplete teacher":
          setPage("TeacherRegister");
          break;
        case "email_exists":
          VALIDADOR.updateErrorDisplay(
              textFieldEmail,
              userEmailErrorLabel,
              false,
              "Este email já está em uso. Tente outro ou recupere sua senha.");
        default:
          LOGGER.log(Level.WARNING, "Resultado inválido: {0}", result);
          break;
      }
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
    }
  }

  private String getTextOrEmpty(TextField field) {
    return (field != null && field.getText() != null) ? field.getText() : "";
  }

  @FXML
  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private void login() {
    page.set("Login");
  }

  public StringProperty getPage() {
    return page;
  }

  private void setPage(String page) {
    this.page.set(page);
  }
}
