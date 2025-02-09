package com.view.login_cadastro.login;

import com.controller.login_cadastro.LoginController;

import javafx.beans.property.*;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RecuperarSenha {
    @FXML
    private TextField emailField;
    @FXML
    private Label mensagemLabel;

    private StringProperty page = new SimpleStringProperty("passwordRecovery");

    private final LoginController loginController = new LoginController();

    @FXML
    private void enviarRecuperacao() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            mensagemLabel.setText("Por favor, insira um email.");
            mensagemLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (loginController.recuperarSenha(email)) {
            mensagemLabel.setText("Email de recuperação enviado com sucesso!");
            mensagemLabel.setStyle("-fx-text-fill: green;");
        } else {
            mensagemLabel.setText("Email não encontrado no sistema.");
            mensagemLabel.setStyle("-fx-text-fill: red;");
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
