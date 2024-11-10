package com.view.login_cadastro.cadastro;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import com.controller.login_cadastro.CadastroController;
import com.view.login_cadastro.BaseLoginCadastro;
import com.view.login_cadastro.cadastro.valid.CadastroValid;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.awt.Toolkit;

public class Cadastro extends BaseLoginCadastro implements Initializable {
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldPasswordConfirm;
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
    @FXML
    private Label userNameErrorLabel;
    @FXML
    private Label userEmailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label passwordConfirmErrorLabel;

    private String typeUser;

    private final CadastroValid validator = new CadastroValid();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();

        validator.setupInitialState(textFieldName, textFieldEmail, passwordFieldPassword, passwordFieldPasswordConfirm, userNameErrorLabel, userEmailErrorLabel, passwordErrorLabel, passwordConfirmErrorLabel);

        logar.setOnAction(event -> {
            redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
        });
    }

    @FXML
    public void register() {
        validator.getValidationSupport().setValidationDecorator(new GraphicValidationDecoration());
        if (!validator.validateFields()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        System.out.print("radioButtonStudent.isSelected() "+ radioButtonStudent.isSelected()+ " , adioButtonTeacher.isSelected() "+ radioButtonTeacher.isSelected());

        Stage stage = (Stage) leftSection.getScene().getWindow();

        if (radioButtonStudent.isSelected()) {
            typeUser = "student";
            super.redirectTo("/com/login_cadastro/paginaCadastroStudent.fxml", stage);
        } else if (radioButtonTeacher.isSelected()) {
            typeUser = "teacher";
            super.redirectTo("/com/login_cadastro/paginaCadastroTeacher.fxml", stage);
        }

        LocalDateTime date = LocalDateTime.now();
        new CadastroController(textFieldName.getText(), textFieldEmail.getText(),
                passwordFieldPasswordConfirm.getText(), date, typeUser);
    }
}