package com.view.auth.valid;

import com.view.utility.ValidUtility;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterValidSecundary implements AuthValid{
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

    @Override
    public void init(){
        ValidUtility.checkWithValidationSupport(textFieldCPF,CPF_REGEX);
        ValidUtility.checkWithValidationSupport(textFieldPhone, PHONE_REGEX);
    }

    @Override
    public boolean isCheck(){
        boolean isCheck = true;

        if(!ValidUtility.isCheck(textFieldCPF, cpfErrorLabel, CPF_REGEX, "CPF inválido. Formato esperado: 000.000.000-00")) isCheck = false;
        if(!isValidCPF(textFieldCPF.getText())) {
            ValidUtility.updateErrorDisplay(textFieldCPF, cpfErrorLabel, false, "CPF inválido. Digite um CPF existente");
            isCheck = false;
        }
        if(!ValidUtility.isCheck(textFieldPhone, phoneErrorLabel, PHONE_REGEX, "Telefone inválido. Formato esperado: (00)00000-0000")) isCheck = false;
        if(!ValidUtility.isCheck(comboBoxEducation,educationErrorLabel, "Selecione um nível de educação")) isCheck = false;
        return isCheck;
    } 

    private static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int sum = 0;
        int weight = 10;

        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weight;
            weight--;
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;
        if (firstDigit != Character.getNumericValue(cpf.charAt(9))) return false;

        sum = 0;
        weight = 11;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weight;
            weight--;
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;
        if (secondDigit != Character.getNumericValue(cpf.charAt(10))) return false;

        return true;
    }

}
