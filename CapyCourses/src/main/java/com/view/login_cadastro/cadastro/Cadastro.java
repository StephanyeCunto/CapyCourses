package com.view.login_cadastro.cadastro;

import java.time.LocalDateTime;

import com.controller.login_cadastro.CadastroController;
import com.view.login_cadastro.cadastro.valid.CadastroValid;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Cadastro {
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
    private Label userNameErrorLabel;
    @FXML
    private Label userEmailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label passwordConfirmErrorLabel;

    private final CadastroValid VALIDADOR = new CadastroValid();

    private StringProperty page = new SimpleStringProperty("Cadastro");

    public void initialize() {
        VALIDADOR.setupInitialState(textFieldName, textFieldEmail, passwordFieldPassword, passwordFieldPasswordConfirm,
                userNameErrorLabel, userEmailErrorLabel, passwordErrorLabel, passwordConfirmErrorLabel);
    }

    @FXML
    private void createAccount() {
        try {
            if (VALIDADOR.validateFields()) {
                String userType = radioButtonStudent.isSelected() ? "STUDENT" : "TEACHER";

                CadastroController controller = new CadastroController();
                String result = controller.cadastrar(
                        textFieldName.getText(),
                        textFieldEmail.getText(),
                        passwordFieldPassword.getText(),
                        LocalDateTime.now(),
                        userType);

                        switch (result) {
                            case "incomplete student":
                                setPage("StudentRegister");
                                break;
                            case "incomplete teacher":
                                setPage("TeacherRegister");
                                break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro durante o cadastro: " + e.getMessage());
        }
    }

    @FXML
    private void login() {
        page.set("Login");
    }

    public StringProperty getPage(){
        return page;
    }

    private void setPage(String page){
        this.page.set(page);
    }
}
