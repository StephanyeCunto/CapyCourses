package com.view.login_cadastro.login;

import com.view.login_cadastro.login.valid.RecuperarSenhaValid;
import javafx.beans.property.*;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RecuperarSenha {
  @FXML private TextField emailField;
  @FXML private Label mensagemLabel;

  private StringProperty page = new SimpleStringProperty("passwordRecovery");

  private final RecuperarSenhaValid VALIDADOR = new RecuperarSenhaValid();

  @FXML
  private void enviarRecuperacao() {

    mensagemLabel.getStyleClass().add("error-label");

    VALIDADOR.loadValues(emailField, mensagemLabel);

    if (!VALIDADOR.validateFields()) {
      return;
    } else {
      mensagemLabel.setText("Email de recuperação enviado com sucesso!");
      mensagemLabel.setStyle("-fx-text-fill: green;");
    }
  }

  @FXML
  private void voltar() {
    page.set("Login");
  }

  public StringProperty getPage() {
    return page;
  }
}
