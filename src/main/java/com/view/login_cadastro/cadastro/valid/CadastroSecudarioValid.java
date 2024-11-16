package com.view.login_cadastro.cadastro.valid;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.*;

public class CadastroSecudarioValid {
    @FXML
    private ComboBox<String> comboBoxEducation;
    @FXML
    private FlowPane interestContainer;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private Label cpfErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label educationErrorLabel;

    private String cpf;
    private String phone;
    private static final ValidationSupport validationSupport = new ValidationSupport();
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    public void setupInitialState(ComboBox<String> comboBoxEducation, TextField textFieldCPF, TextField textFieldPhone, Label cpfErrorLabel, Label educationErrorLabel,
            Label phoneErrorLabel) {
        loadField(comboBoxEducation, textFieldCPF, textFieldPhone, cpfErrorLabel,
                educationErrorLabel, phoneErrorLabel);

        textFieldCPF.textProperty().addListener((obs, old, newText) -> {
            String formatted = formatCPF(newText);
            textFieldCPF.setText(formatted);
            if (isValidCPF(newText)) {
                updateErrorDisplay(textFieldCPF, cpfErrorLabel, false, null);
            }
        });

        textFieldCPF.setOnKeyReleased(event -> {
            checkSizeCPF(event);
            cpf = textFieldCPF.getText();
        });

        textFieldCPF.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(textFieldCPF, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return strValue.length() > 13;
                        }
                        return false;
                    }, null));
        });

        textFieldPhone.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(textFieldPhone, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return strValue.length() > 13;
                        }
                        return false;
                    }, null));
        });

        textFieldPhone.textProperty().addListener((obs, old, newText) -> {
            String formatted = formatPhone(newText);
            textFieldPhone.setText(formatted);
            if (textFieldPhone.getText().length() < 14) {
                updateErrorDisplay(textFieldPhone, phoneErrorLabel, true, null);
            }else{
                updateErrorDisplay(textFieldPhone, phoneErrorLabel, false, null);
            }
        });

        textFieldPhone.setOnKeyReleased(event -> {
            checkSizePhone(event);
            phone = textFieldPhone.getText();
        });

        comboBoxEducation.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(comboBoxEducation, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return strValue != "null";
                        }
                        return false;
                    }, null));
        });

        comboBoxEducation.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                updateErrorDisplay(comboBoxEducation, educationErrorLabel, false, null);
            }
        });

    }

    private int calcularDigitoVerificador(String cpf, int peso) {
        int soma = 0;
        for (int i = 0; i < cpf.length(); i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    private boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int primeiroDigitoVerificador = calcularDigitoVerificador(cpf.substring(0, 9), 10);
        if (primeiroDigitoVerificador != (cpf.charAt(9) - '0')) {
            return false;
        }

        int segundoDigitoVerificador = calcularDigitoVerificador(cpf.substring(0, 10), 11);
        return segundoDigitoVerificador == (cpf.charAt(10) - '0');
    }

    private int sizeCPF() {
        return textFieldCPF.getText().length();
    }

    private int sizePhone() {
        return textFieldPhone.getText().length();
    }

    private void checkSizePhone(KeyEvent event) {
        if (sizePhone() > 14) {
            if (event.getCode() != KeyCode.BACK_SPACE && event.getCode() != KeyCode.DELETE) {
                textFieldPhone.setText(phone);
                textFieldPhone.positionCaret(textFieldPhone.getText().length());
            }
        }
    }

    private void checkSizeCPF(KeyEvent event) {
        if (sizeCPF() > 14) {
            if (event.getCode() != KeyCode.BACK_SPACE && event.getCode() != KeyCode.DELETE) {
                textFieldCPF.setText(cpf);
                textFieldCPF.positionCaret(textFieldCPF.getText().length());
            }
        }
    }

    private String formatCPF(String value) {
        int sizeCPF = sizeCPF();
        value = value.replaceAll("\\D", "");
        if (sizeCPF > 11) {
            value = value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{1})", "$1.$2.$3-$4");
        } else if (sizeCPF > 7) {
            value = value.replaceAll("(\\d{3})(\\d{3})(\\d{1})", "$1.$2.$3");
        } else if (sizeCPF > 3) {
            value = value.replaceAll("(\\d{3})(\\d{1})", "$1.$2");
        }
        return value;
    }

    private String formatPhone(String value) {
        value = value.replaceAll("\\D", "");
        int sizePhone = value.length();

        if (sizePhone > 7) {
            value = value.replaceAll("(\\d{2})(\\d{5})(\\d+)", "($1)$2-$3");
        } else if (sizePhone > 2) {
            value = value.replaceAll("(\\d{2})(\\d+)", "($1)$2");
        } else if (sizePhone > 1) {
            value = value.replaceAll("(\\d{2})", "($1)");
        } else if (sizePhone > 0) {
            value = value.replaceAll("(\\d{1})", "($1");
        }
        return value;
    }

    private void loadField(ComboBox<String> comboBoxEducation, TextField textFieldCPF, TextField textFieldPhone, Label cpfErrorLabel, Label educationErrorLabel,
            Label phoneErrorLabel) {
        this.comboBoxEducation = comboBoxEducation;
        this.textFieldCPF = textFieldCPF;
        this.textFieldPhone = textFieldPhone;
        this.cpfErrorLabel = cpfErrorLabel;
        this.educationErrorLabel = educationErrorLabel;
        this.phoneErrorLabel = phoneErrorLabel;
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, isValid);
        errorLabel.setText(!isValid ? "" : message);
        errorLabel.setVisible(isValid);
        errorLabel.setManaged(isValid);
        if (isValid) {
            field.getStyleClass().add("error-field");
        } else {
            field.getStyleClass().remove("error-field");
        }
    }

    public boolean validateFields(){
        boolean isValid = true;

        if(textFieldCPF.getText().isEmpty() || isValidCPF(textFieldCPF.getText())){
            updateErrorDisplay(textFieldCPF, cpfErrorLabel, true, "Por favor, insira um cpf válido");
            isValid = false;
        }
        if (textFieldPhone.getText().length() < 14) {
            updateErrorDisplay(textFieldPhone, phoneErrorLabel, true, "Por favor, insira um telefone válido");
            isValid = false;
        }
        if (comboBoxEducation.getValue() == null || comboBoxEducation.getValue().trim().isEmpty()) {
            updateErrorDisplay(comboBoxEducation, educationErrorLabel, true, "Por favor, selecione uma opção válida");
            isValid = false;
        }
        
        return isValid;
    }
}