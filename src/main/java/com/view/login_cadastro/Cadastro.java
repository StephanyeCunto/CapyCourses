package com.view.login_cadastro;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.controller.login_cadastro.CadastroController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cadastro extends BaseLoginCadastro implements Initializable {
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passworFieldPassword;
    @FXML
    private PasswordField passworFieldPasswordConfirm;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private ToggleGroup userType;
    @FXML
    private RadioButton radioButtonStudent;
    @FXML
    private RadioButton radioButtonTeacher;
    @FXML
    private Hyperlink logar;
    @FXML
    private VBox leftSection;

    private String typeUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();

        logar.setOnAction(event -> {
            redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
        });
    }

    public void register() {
        Stage stage = (Stage) leftSection.getScene().getWindow();

        if (radioButtonStudent.isSelected()) {
            typeUser = "student";
            super.redirectTo("/com/login_cadastro/paginaCadastroStudent.fxml", stage);
        } else if (radioButtonTeacher.isSelected()) {
            typeUser = "teacher";
            super.redirectTo("/com/login_cadastro/paginaLogin.fxml", stage);
        }

        LocalDateTime date = LocalDateTime.now();
        new CadastroController(textFieldName.getText(), textFieldEmail.getText(),
                passworFieldPasswordConfirm.getText(), date, typeUser);
    }
}