package com.view.login_cadastro;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.controller.login_cadastro.CadastroTeacherController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastroTeacher extends BaseLoginCadastro implements Initializable {
    @FXML
    private Hyperlink logar;
    @FXML
    private VBox leftSection;
    @FXML
    private ComboBox<String> comboBoxEducation;
    @FXML
    private FlowPane interestContainer;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textFieldPhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();

        super.loadComboBox();
        super.addDateInputField();
        super.setupInterestButtons();

        logar.setOnAction(event -> super.redirectTo("/com/login_cadastro/paginaLogin.fxml",
                (Stage) leftSection.getScene().getWindow()));
    }

    public void createTeacher() throws ParseException {
        String interests = String.join(". ", super.getSelectedInterests());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(super.getDateInputPopup().getDate());

        new CadastroTeacherController(date, textFieldCPF.getText(), Long.parseLong(textFieldPhone.getText()),
                comboBoxEducation.getValue(), interests);

        super.redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
    }
}
