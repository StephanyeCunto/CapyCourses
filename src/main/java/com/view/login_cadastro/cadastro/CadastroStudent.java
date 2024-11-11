package com.view.login_cadastro.cadastro;

import java.net.URL;
import java.util.ResourceBundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.controller.login_cadastro.CadastroStudentController;
import com.view.login_cadastro.BaseLoginCadastro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastroStudent extends BaseLoginCadastro implements Initializable {
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
        super.loadCalendar();
        super.setupInterestButtons();
    }

    @FXML
    private void changeModeStyle(){
        super.changeMode();
    }

    public void createStudent() throws ParseException{
        String interests = String.join(". ", super.getSelectedInterests());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(super.getDateInputPopup().getDate());
        new CadastroStudentController(date,textFieldCPF.getText(), Long.parseLong(textFieldPhone.getText()), comboBoxEducation.getValue(),interests);

        super.redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
    }
}
