package com.view.login_cadastro;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastroStudent extends BaseLoginCadastro implements Initializable {
    @FXML
    private GridPane mainPane;
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
    @FXML
    private VBox rightSection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();
        
        logar.setOnAction(event -> {
            Stage stage = (Stage) leftSection.getScene().getWindow();
            redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
        });
    }

}
