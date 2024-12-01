package com.view.professor.valid;

import javafx.scene.control.*;
import javafx.css.PseudoClass;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class CursoSettingsValidation {
    private ValidationSupport validationSupport;
    private TextField durationTotal;
    private Label durationTotalErrorLabel;
    private ComboBox<String> comboBoxVisibility;
    private Label comboBoxVisibilityErrorLabel;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    public CursoSettingsValidation() {
        validationSupport = new ValidationSupport();
    }

    public void setupInitialState(TextField durationTotal, ComboBox<String> comboBoxVisibility,
                                  Label durationTotalErrorLabel, Label comboBoxVisibilityErrorLabel) {
        this.durationTotal = durationTotal;
        this.comboBoxVisibility = comboBoxVisibility;
        this.durationTotalErrorLabel = durationTotalErrorLabel;
        this.comboBoxVisibilityErrorLabel = comboBoxVisibilityErrorLabel;

        setupValidation();
        addRealTimeValidationListeners();
    }

    private void setupValidation() {
        configureDurationValidation();
        configureVisibilityValidation();
    }

    private void configureDurationValidation() {
        validationSupport.registerValidator(durationTotal, false,
            Validator.createPredicateValidator(value -> {
                if (value instanceof String) {
                    String strValue = (String) value;
                    return !strValue.trim().isEmpty() && strValue.matches("^\\d+(\\.\\d+)?$");
                }
                return false;
            }, "Por favor, insira uma duração válida"));
    }

    private void configureVisibilityValidation() {
        validationSupport.registerValidator(comboBoxVisibility, false,
            Validator.createPredicateValidator(value -> {
                if (value instanceof String) {
                    String strValue = (String) value;
                    return strValue != null && !strValue.trim().isEmpty() && !strValue.equals("null");
                }
                return false;
            }, "Por favor, selecione a visibilidade"));
    }

    private void addRealTimeValidationListeners() {
        // Listener para o campo de duração
        durationTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = newValue != null && !newValue.trim().isEmpty() && newValue.matches("^\\d+(\\.\\d+)?$");
            updateErrorDisplay(durationTotal, durationTotalErrorLabel, !isValid, 
                isValid ? null : "Por favor, insira uma duração válida");
        });

        // Listener para o comboBox de visibilidade
        comboBoxVisibility.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = newValue != null && !newValue.trim().isEmpty() && !newValue.equals("null");
            updateErrorDisplay(comboBoxVisibility, comboBoxVisibilityErrorLabel, !isValid, 
                isValid ? null : "Por favor, selecione a visibilidade");
        });
    }

    public boolean validateFields() {
        boolean isValid = true;

        if (durationTotal.getText() == null || durationTotal.getText().trim().isEmpty()
            || !durationTotal.getText().matches("^\\d+(\\.\\d+)?$")) {
            updateErrorDisplay(durationTotal, durationTotalErrorLabel, true, "Por favor, insira uma duração válida");
            isValid = false;
        }

        if (comboBoxVisibility.getValue() == null || comboBoxVisibility.getValue().trim().isEmpty()) {
            updateErrorDisplay(comboBoxVisibility, comboBoxVisibilityErrorLabel, true, "Por favor, selecione a visibilidade");
            isValid = false;
        }

        return isValid;
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean hasError, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, hasError);
        errorLabel.setText(hasError ? message : "");
        errorLabel.setVisible(hasError);
        errorLabel.setManaged(hasError);
    }
}
