package com.view.auth;

import java.io.IOException;
import java.time.LocalDate;

import com.view.auth.valid.RegisterValidSecundary;
import com.view.utility.ViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterPageSecundary {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private Label cpfErrorLabel;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private ComboBox<String> comboBoxEducation;
    @FXML
    private Label educationErrorLabel;
    @FXML
    private VBox formSection;

    private RegisterValidSecundary valid;

    @FXML
    public void initialize(){
        loadDate();
        loadCPF();
        loadPhone();
        loadDataCombobox();
        valid = new RegisterValidSecundary(datePicker, textFieldCPF, cpfErrorLabel, textFieldPhone, phoneErrorLabel, comboBoxEducation, educationErrorLabel);
        valid.init();
    }

    @FXML
    public void register()throws IOException{
        valid.isCheck();
       if(valid.isCheck()) ViewLoader.load("/com/auth/LoginPage.fxml", formSection);
    }

    private void loadDate(){
        LocalDate dateMax = LocalDate.now().minusYears(14);
        datePicker.setValue(dateMax);
         datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(dateMax))  setDisable(true);
            }
        });
    }

    private void loadCPF(){
        textFieldCPF.textProperty().addListener((obs, oldValue, newValue) -> {
            String numbers = newValue.replaceAll("[^\\d]", "");
            if(numbers.length() <= 3) textFieldCPF.setText(numbers);
            else if(numbers.length() <= 6) textFieldCPF.setText(numbers.substring(0,3) + "." + numbers.substring(3));
            else if(numbers.length() <= 9) textFieldCPF.setText(numbers.substring(0,3) + "." + numbers.substring(3,6) + "." + numbers.substring(6));
            else if(numbers.length() <= 11) textFieldCPF.setText(numbers.substring(0,3) + "." + numbers.substring(3,6) + "." + numbers.substring(6,9) + "-" + numbers.substring(9));
            else textFieldCPF.setText(oldValue);
        });
    }

    private void loadPhone(){
        textFieldPhone.textProperty().addListener((obs, oldValue, newValue) -> {
            String numbers = newValue.replaceAll("[^\\d]", "");
            if (numbers.length() <= 2)  textFieldPhone.setText("(" + numbers);
            else if (numbers.length() <= 7) textFieldPhone.setText("(" + numbers.substring(0,2) + ") " + numbers.substring(2));
            else if (numbers.length() <= 11) textFieldPhone.setText("(" + numbers.substring(0,2) + ") " + numbers.substring(2,7) + "-" + numbers.substring(7));
            else textFieldPhone.setText(oldValue);
        });
    }

    private void loadDataCombobox(){
        comboBoxEducation.getItems().addAll(
            "Ensino Fundamental Incompleto","Ensino Fundamental Completo","Ensino Médio Incompleto","Ensino Médio Completo",
            "Ensino Superior Incompleto","Ensino Superior Completo","Pós-graduação","Mestrado","Doutorado"
        );
    }
}
