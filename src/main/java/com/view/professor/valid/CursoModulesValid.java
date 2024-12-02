package com.view.professor.valid;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.css.PseudoClass;

import com.view.login_cadastro.elements.ErrorNotification;

public class CursoModulesValid {
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MIN_DETAILS_LENGTH = 10;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    private List<TextField> titleFields = new ArrayList<>();
    private List<Label> titleErrorLabels = new ArrayList<>();
    private List<TextField> durationFields = new ArrayList<>();
    private List<Label> durationErrorLabels = new ArrayList<>();
    private List<TextArea> detailsFields = new ArrayList<>();
    private List<Label> detailsErrorLabels = new ArrayList<>();
    private VBox lessonList;
    private VBox moduleList;
    private GridPane parentContainer;

    public void setParentContainer(GridPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setupInitialStateModules(VBox modulesList) {
        loadValues(modulesList);

        setupValidationListeners();
    }

    private void loadValues(VBox modulesList) {
        moduleList = modulesList;

        for (Node moduleNode : modulesList.getChildren()) {
            if (!(moduleNode instanceof VBox))
                continue;

            VBox moduleCard = (VBox) moduleNode;

            lessonList = (VBox) moduleCard.getChildren().get(2);

            VBox moduleContent = (VBox) moduleCard.getChildren().get(1);

            TextField titleField = (TextField) ((HBox) ((VBox) moduleContent.getChildren().get(0)).getChildren().get(1))
                    .getChildren().get(0);
            Label titleErrorLabel = (Label) ((VBox) moduleContent.getChildren().get(0)).getChildren().get(2);

            TextField durationField = (TextField) ((VBox) ((HBox) moduleContent.getChildren().get(1)).getChildren()
                    .get(0)).getChildren().get(1);
            Label durationErrorLabel = (Label) moduleContent.getChildren().get(2);

            TextArea detailsField = (TextArea) ((VBox) moduleContent.getChildren().get(3)).getChildren().get(1);
            Label detailsErrorLabel = (Label) moduleContent.getChildren().get(4);

            titleFields.add(titleField);
            titleErrorLabels.add(titleErrorLabel);
            durationFields.add(durationField);
            durationErrorLabels.add(durationErrorLabel);
            detailsFields.add(detailsField);
            detailsErrorLabels.add(detailsErrorLabel);
        }
    }

    private void setupValidationListeners() {
        ValidationSupport validationSupport = new ValidationSupport();

        for (int i = 0; i < titleFields.size(); i++) {
            TextField titleField = titleFields.get(i);
            Label titleErrorLabel = titleErrorLabels.get(i);
            TextField durationField = durationFields.get(i);
            Label durationErrorLabel = durationErrorLabels.get(i);
            TextArea detailsField = detailsFields.get(i);
            Label detailsErrorLabel = detailsErrorLabels.get(i);

            titleField.textProperty().addListener((obs, old, newText) -> {
                if (newText.length() >= MIN_TITLE_LENGTH) {
                    updateErrorDisplay(titleField, titleErrorLabel, false, null);
                }
            });

            titleField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(titleField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty() && strValue.length() >= 5 && strValue.length() <= 100;
                            }
                            return false;
                        }, "Por favor, insira um título válido, entre 5 e 100 caracteres"));
            });

            durationField.textProperty().addListener((obs, old, newText) -> {
                if (newText.length() > 0) {
                    updateErrorDisplay(durationField, durationErrorLabel, false, null);
                }
            });

            durationField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(durationField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty();
                            }
                            return false;
                        }, "Por favor, insira uma duração válida"));
            });

            detailsField.textProperty().addListener((obs, old, newText) -> {
                if (newText.length() >= MIN_DETAILS_LENGTH) {
                    updateErrorDisplay(detailsField, detailsErrorLabel, false, null);
                }
            });

            detailsField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(detailsField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty() && strValue.length() >= 10;
                            }
                            return false;
                        }, "Por favor, insira uma descrição válida, no mínimo de 10 caracteres"));
            });
        }
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

    public boolean validateFields() {
        boolean isAllValid = true;

        for (int i = 0; i < titleFields.size(); i++) {
            boolean isModuleValid = true;

            if (titleFields.get(i).getText().length() < MIN_TITLE_LENGTH) {
                updateErrorDisplay(
                        titleFields.get(i),
                        titleErrorLabels.get(i),
                        true,
                        "Por favor, insira um título válido, entre 5 e 100 caracteres");
                isModuleValid = false;
            }

            if (durationFields.get(i).getText() == null ||
                    durationFields.get(i).getText().isEmpty()) {
                updateErrorDisplay(
                        durationFields.get(i),
                        durationErrorLabels.get(i),
                        true,
                        "Por favor, insira uma duração válida");
                isModuleValid = false;
            }

            if (detailsFields.get(i).getText().length() < MIN_DETAILS_LENGTH ||
                    detailsFields.get(i).getText().isEmpty()) {
                updateErrorDisplay(
                        detailsFields.get(i),
                        detailsErrorLabels.get(i),
                        true,
                        "Por favor, insira uma descrição válida, no mínimo 10 caracteres");
                isModuleValid = false;
            }

            for (Node moduleNode : moduleList.getChildren()) {
                VBox moduleCard = (VBox) moduleNode;
                VBox lessonList = (VBox) moduleCard.getChildren().get(2);
                if(lessonList.getChildren().size()<2){
                    ErrorNotification errorNotification = new ErrorNotification(
                        parentContainer, 
                        "Adicione uma aula no módulo" 
                    );
                    errorNotification.show();
                }
            }


            if (!isModuleValid) {
                ErrorNotification errorNotification = new ErrorNotification(
                    parentContainer, 
                    "Preencha todos os campos corretamente" 
                );
    
                errorNotification.show();
                isAllValid = false;

                for (Node moduleNode : moduleList.getChildren()) {
                    VBox moduleCard = (VBox) moduleNode;
                    VBox lessonList = (VBox) moduleCard.getChildren().get(2);
                    if(lessonList.getChildren().size()<2){
                        isAllValid = false;
                    }
                }
            }
        }

        if( getTotalTitleFields()==0){
            ErrorNotification errorNotification = new ErrorNotification(
                    parentContainer, 
                    "Deve haver um módulo no curso" 
                );
    
                errorNotification.show();
                isAllValid = false;
        }

        return isAllValid;
    }

    public int getTotalTitleFields() {
        return titleFields.size();
    }

    public int getTotalDurationFields() {
        return durationFields.size();
    }

    public int getTotalDetailsFields() {
        return detailsFields.size();
    }

    public int getValidatedTitleFields() {
        return (int) titleFields.stream()
                .filter(field -> field.getText().length() >= MIN_TITLE_LENGTH)
                .count();
    }

    public int getValidatedDurationFields() {
        return (int) durationFields.stream()
                .filter(field -> field.getText() != null && !field.getText().trim().isEmpty())
                .count();
    }

    public int getValidatedDetailsFields() {
        return (int) detailsFields.stream()
                .filter(field -> field.getText().length() >= MIN_DETAILS_LENGTH)
                .count();
    }

}