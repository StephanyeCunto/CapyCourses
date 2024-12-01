package com.view.professor.valid;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.css.PseudoClass;

public class CursoQuestionarioValid {
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MIN_QUESTION_LENGTH = 10;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    private List<TextField> titleFields = new ArrayList<>();
    private List<Label> titleErrorLabels = new ArrayList<>();
    private List<TextArea> questionTextFields = new ArrayList<>();
    private List<Label> questionErrorLabels = new ArrayList<>();
    private List<TextField> scoreFields = new ArrayList<>();
    private List<Label> scoreErrorLabels = new ArrayList<>();

    public void setupInitialStateQuestions(VBox questionsContainer) {
        clearFieldLists();
        loadQuestionFields(questionsContainer);
        setupValidationListeners();
    }

    private void clearFieldLists() {
        titleFields.clear();
        titleErrorLabels.clear();
        questionTextFields.clear();
        questionErrorLabels.clear();
        scoreFields.clear();
        scoreErrorLabels.clear();
    }

    private void loadQuestionFields(VBox questionsContainer) {
        for (Node questionNode : questionsContainer.getChildren()) {
            if (!(questionNode instanceof VBox)) continue;
            VBox questionCard = (VBox) questionNode;
            VBox questionContent = (VBox) questionCard.getChildren().get(1);

            TextField titleField = (TextField) (((VBox) questionContent.getChildren().get(0)).getChildren().get(1));
            Label titleErrorLabel = (Label) questionContent.getChildren().get(1);
            TextArea questionTextArea = (TextArea) ((VBox) questionContent.getChildren().get(2)).getChildren().get(1);
            Label questionErrorLabel = (Label) questionContent.getChildren().get(3);
            TextField scoreField = (TextField) ((VBox) questionContent.getChildren().get(4)).getChildren().get(1);
            Label scoreErrorLabel = (Label) questionContent.getChildren().get(5);

            titleFields.add(titleField);
            titleErrorLabels.add(titleErrorLabel);
            questionTextFields.add(questionTextArea);
            questionErrorLabels.add(questionErrorLabel);
            scoreFields.add(scoreField);
            scoreErrorLabels.add(scoreErrorLabel);
        }
    }

    private void setupValidationListeners() {
        ValidationSupport validationSupport = new ValidationSupport();

        for (int i = 0; i < questionTextFields.size(); i++) {
            setupQuestionTextValidation(questionTextFields.get(i), questionErrorLabels.get(i), validationSupport);
            setupScoreValidation(scoreFields.get(i), scoreErrorLabels.get(i), validationSupport);
            setupTitleValidation(titleFields.get(i), titleErrorLabels.get(i), validationSupport);
        }
    }

    private void setupQuestionTextValidation(TextArea questionField, Label errorLabel, ValidationSupport validationSupport) {
        questionField.textProperty().addListener((obs, old, newText) -> {
            if (newText.length() >= MIN_QUESTION_LENGTH) {
                updateErrorDisplay(questionField, errorLabel, false, null);
            }
        });

        questionField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(questionField, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty() && strValue.length() >= MIN_QUESTION_LENGTH;
                        }
                        return false;
                    }, "Por favor, insira uma descrição válida com pelo menos 10 caracteres"));
        });
    }

    private void setupScoreValidation(TextField scoreField, Label errorLabel, ValidationSupport validationSupport) {
        scoreField.textProperty().addListener((obs, old, newText) -> {
            if (!newText.isEmpty() && newText.matches("\\d*\\.?\\d*")) {
                updateErrorDisplay(scoreField, errorLabel, false, null);
            }
        });

        scoreField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(scoreField, false,
                    Validator.createPredicateValidator(value -> {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.isEmpty() && strValue.matches("\\d*\\.?\\d*");
                        }
                        return false;
                    }, "Por favor, insira uma pontuação válida (número)"));
        });
    }

    private void setupTitleValidation(TextField titleField, Label errorLabel, ValidationSupport validationSupport) {
        titleField.textProperty().addListener((obs, old, newText) -> {
            if (newText.length() >= MIN_TITLE_LENGTH) {
                updateErrorDisplay(titleField, errorLabel, false, null);
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
        
        for (int i = 0; i < questionTextFields.size(); i++) {
            boolean isQuestionValid = true;

            if (titleFields.get(i).getText().length() < MIN_TITLE_LENGTH) {
                updateErrorDisplay(titleFields.get(i), titleErrorLabels.get(i), true,
                    "Por favor, insira um título válido, com pelo menos 10 caracteres");
                isQuestionValid = false;
            }

            if (questionTextFields.get(i).getText().length() < MIN_QUESTION_LENGTH) {
                updateErrorDisplay(questionTextFields.get(i), questionErrorLabels.get(i), true,
                    "Por favor, insira uma descrição válida, com pelo menos 10 caracteres");
                isQuestionValid = false;
            }

            if (scoreFields.get(i).getText().isEmpty() || !scoreFields.get(i).getText().matches("\\d*\\.?\\d*")) {
                updateErrorDisplay(scoreFields.get(i), scoreErrorLabels.get(i), true, 
                    "Por favor, insira uma pontuação válida");
                isQuestionValid = false;
            }

            if (!isQuestionValid) {
                isAllValid = false;
            }
        }

        return isAllValid;
    }
}
