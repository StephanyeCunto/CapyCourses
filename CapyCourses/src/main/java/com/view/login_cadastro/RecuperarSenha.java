package com.view.login_cadastro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.controller.login_cadastro.LoginController;

public class RecuperarSenha extends BaseLoginCadastro {
    @FXML
    private TextField emailField;
    @FXML
    private Label mensagemLabel;

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
        Stage stage = (Stage) emailField.getScene().getWindow();
        super.redirectTo("/com/login_cadastro/paginaLogin.fxml", stage);
    }
} 