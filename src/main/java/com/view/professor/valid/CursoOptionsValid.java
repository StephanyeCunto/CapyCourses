package com.view.professor.valid;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.css.PseudoClass;

import com.view.login_cadastro.elements.ErrorNotification;

public class CursoOptionsValid {
    private static final int MIN_OPTION_LENGTH = 1;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    private List<TextField> optionFields = new ArrayList<>();
    private List<Label> optionErrorLabels = new ArrayList<>();
    private List<Node> selectionControls = new ArrayList<>();
    private ValidationSupport validationSupport = new ValidationSupport();

    private GridPane parentContainer;

    public void setParentContainer(GridPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setupInitialStateOptions(VBox optionsList, boolean singleChoice) {
        loadValues(optionsList);
        setupValidationListeners();
    }

    private void loadValues(VBox optionsList) {

        for (Node node : optionsList.getChildren()) {
            if (!(node instanceof HBox))
                continue;

            HBox optionBox = (HBox) node;
            Node selectionControl = null;
            TextField optionField = null;
            Label optionErrorLabel = null;

            // Find selection control and text field within the HBox
            for (Node child : optionBox.getChildren()) {
                if (child instanceof RadioButton || child instanceof CheckBox) {
                    selectionControl = child;
                }
                if (child instanceof TextField) {
                    optionField = (TextField) child;
                }
            }

            // Find the corresponding error label
            int index = optionsList.getChildren().indexOf(node);
            if (index + 1 < optionsList.getChildren().size()) {
                Node nextNode = optionsList.getChildren().get(index + 1);
                if (nextNode instanceof Label) {
                    optionErrorLabel = (Label) nextNode;
                }
            }

            // Add to lists if all components are found
            if (optionField != null && optionErrorLabel != null && selectionControl != null) {
                optionFields.add(optionField);
                optionErrorLabels.add(optionErrorLabel);
                selectionControls.add(selectionControl);
            }
        }
    }

    private void setupValidationListeners() {
        for (int i = 0; i < optionFields.size(); i++) {
            TextField optionField = optionFields.get(i);
            Label optionErrorLabel = optionErrorLabels.get(i);

            optionField.textProperty().addListener((obs, old, newText) -> {
                if (newText.length() >= MIN_OPTION_LENGTH) {
                    updateErrorDisplay(optionField, optionErrorLabel, false, null);
                }
            });

            optionField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(optionField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty() && strValue.length() >= MIN_OPTION_LENGTH;
                            }
                            return false;
                        }, "Por favor, insira uma opção válida, com pelo menos 1 caracter"));
            });
        }
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isError, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, isError);
        errorLabel.setText(isError ? message : "");
        errorLabel.setVisible(isError);
        errorLabel.setManaged(isError);
        if (isError) {
            field.getStyleClass().add("error-field");
        } else {
            field.getStyleClass().remove("error-field");
        }
    }

    public boolean validateFields() {
        boolean isAllValid = true;

        for (int i = 0; i < optionFields.size(); i++) {
            TextField optionField = optionFields.get(i);
            Label optionErrorLabel = optionErrorLabels.get(i);

            boolean isFieldValid = optionField.getText() != null &&
                    optionField.getText().trim().length() >= MIN_OPTION_LENGTH;

            if (!isFieldValid) {
                updateErrorDisplay(
                        optionField,
                        optionErrorLabel,
                        true,
                        "Por favor, insira uma opção válida, com pelo menos 1 caracter");
                isAllValid = false;
            } else {
                updateErrorDisplay(optionField, optionErrorLabel, false, null);
            }
        }

        if (!isAllValid) {
            ErrorNotification errorNotification = new ErrorNotification(
                    parentContainer,
                    "Preencha todos os campos corretamente");

            errorNotification.show();
        }

        return isAllValid;
    }

    public List<String> getSelectedOptions() {
        List<String> selectedOptions = new ArrayList<>();

        for (int i = 0; i < optionFields.size(); i++) {
            Node selectionControl = selectionControls.get(i);

            if (selectionControl instanceof RadioButton) {
                RadioButton radio = (RadioButton) selectionControl;
                if (radio.isSelected()) {
                    selectedOptions.add(optionFields.get(i).getText());
                }
            } else if (selectionControl instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) selectionControl;
                if (checkbox.isSelected()) {
                    selectedOptions.add(optionFields.get(i).getText());
                }
            }
        }

        return selectedOptions;
    }
}