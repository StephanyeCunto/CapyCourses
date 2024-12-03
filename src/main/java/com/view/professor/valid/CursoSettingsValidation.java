package com.view.professor.valid;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.css.PseudoClass;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.view.login_cadastro.elements.ErrorNotification;

public class CursoSettingsValidation {
    private ValidationSupport validationSupport;
    private TextField durationTotal;
    private Label durationTotalErrorLabel;
    private ComboBox<String> comboBoxVisibility;
    private Label comboBoxVisibilityErrorLabel;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    private GridPane parentContainer;

    public void setParentContainer(GridPane parentContainer) {
        this.parentContainer = parentContainer;
    }

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
        durationTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("0".equals(newValue)) {
                durationTotal.setText(oldValue); 
            }
            if (newValue != null && !newValue.matches("\\d*")) {
                durationTotal.setText(oldValue);
                durationTotal.positionCaret(durationTotal.getText().length());
            }

        });

        validationSupport.registerValidator(durationTotal, false,
                Validator.createPredicateValidator(value -> {
                    if (value instanceof String) {
                        String strValue = (String) value;
                        return !strValue.trim().isEmpty() && strValue.matches("^\\d+$");
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
        durationTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = newValue != null && !newValue.trim().isEmpty() && newValue.matches("^\\d+(\\.\\d+)?$");
            updateErrorDisplay(durationTotal, durationTotalErrorLabel, !isValid,
                    isValid ? null : "Por favor, insira uma duração válida");
        });

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
            updateErrorDisplay(comboBoxVisibility, comboBoxVisibilityErrorLabel, true,
                    "Por favor, selecione a visibilidade");
            isValid = false;
        }

        if (!isValid) {
            ErrorNotification errorNotification = new ErrorNotification(
                    parentContainer,
                    "Preencha todos os campos corretamente");

            errorNotification.show();
        }

        return isValid;
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean hasError, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, hasError);
        errorLabel.setText(hasError ? message : "");
        errorLabel.setVisible(hasError);
        errorLabel.setManaged(hasError);
    }

    public boolean isDurationValid() {
        if (durationTotal == null || durationTotal.getText() == null) {
            return false;
        }
        try {
            double duration = Double.parseDouble(durationTotal.getText().trim());
            return duration > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isVisibilitySelected() {
        return comboBoxVisibility != null &&
                comboBoxVisibility.getValue() != null &&
                !comboBoxVisibility.getValue().trim().isEmpty() &&
                !comboBoxVisibility.getValue().equals("null");
    }
}
