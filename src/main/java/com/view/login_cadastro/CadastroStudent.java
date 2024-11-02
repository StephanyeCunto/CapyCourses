package com.view.login_cadastro;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML
    private ComboBox comboBoxEducation;
    @FXML
    private DatePicker datePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();
        
        loadComboBox();
        logar.setOnAction(event -> {
            Stage stage = (Stage) leftSection.getScene().getWindow();
            redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
        });
    }

    private void loadComboBox(){
        comboBoxEducation.getItems().addAll(
            "Ensino Fundamental Incompleto",
            "Ensino Fundamental Completo",
            "Ensino Médio Incompleto",
            "Ensino Médio Completo",
            "Ensino Superior Incompleto",
            "Ensino Superior Completo",
            "Pós-graduação",
            "Mestrado",
            "Doutorado"
        );
    }
}
