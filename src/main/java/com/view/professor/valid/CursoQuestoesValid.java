package com.view.professor.valid;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.css.PseudoClass;

public class CursoQuestoesValid {
    private static final int MIN_QUESTION_LENGTH = 10;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    private List<TextArea> questionFields = new ArrayList<>();
    private List<Label> questionErrorLabels = new ArrayList<>();
    private List<TextField> scoreFields = new ArrayList<>();
    private List<Label> scoreErrorLabels = new ArrayList<>();
    
    private List<Label> evaluationCriteriaErrorLabels = new ArrayList<>();
    private List<TextArea> evaluationCriteriaFields = new ArrayList<>();

    public void setupInitialStateQuestions(VBox questionsContainer) {
        loadValues(questionsContainer);
        setupValidationListeners();
    }

    private void loadValues(VBox questionsContainer) {
        for (Node questionNode : questionsContainer.getChildren()) {
            if (!(questionNode instanceof VBox)) continue;
            VBox questionCard = (VBox) questionNode;
            VBox questionContent = (VBox) questionCard.getChildren().get(1);
  
            HBox scoreHbox = (HBox) questionContent.getChildren().get(0);
            TextField scoreField = (TextField) scoreHbox.getChildren().get(1);
            Label scoreErrorLabel = (Label) questionContent.getChildren().get(1);
    
            TextArea questionText = (TextArea) questionContent.getChildren().get(2);
            Label questionErrorLabel = (Label) questionContent.getChildren().get(3);

            questionFields.add(questionText);
            questionErrorLabels.add(questionErrorLabel);
            scoreFields.add(scoreField);
            scoreErrorLabels.add(scoreErrorLabel);

            if(questionContent.getChildren().get(7) instanceof TextArea){
                TextArea evaluationCriteria = (TextArea) questionContent.getChildren().get(7);
                Label evaluationCriteriaError = (Label) questionContent.getChildren().get(8);
                evaluationCriteriaFields.add(evaluationCriteria);
                evaluationCriteriaErrorLabels.add(evaluationCriteriaError);
            }

        }

    }

    private void setupValidationListeners() {
        ValidationSupport validationSupport = new ValidationSupport();

        for (int i = 0; i < questionFields.size(); i++) {
            TextArea questionText = questionFields.get(i);
            Label questionErrorLabel = questionErrorLabels.get(i);
            TextField scoreField = scoreFields.get(i);
            Label scoreErrorLabel = scoreErrorLabels.get(i);

            questionText.textProperty().addListener((obs, old, newText) -> {
                if (newText.length() >= MIN_QUESTION_LENGTH) {
                    updateErrorDisplay(questionText, questionErrorLabel, false, null);
                }
            });

            questionText.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(questionText, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty() && strValue.length() >= MIN_QUESTION_LENGTH;
                            }
                            return false;
                        }, "Por favor, insira uma pergunta válida, com pelo menos 10 caracteres"));
            });

            scoreField.textProperty().addListener((obs, old, newText) -> {
                if (newText.length() > 0) {
                    updateErrorDisplay(scoreField, scoreErrorLabel, false, null);
                }
            });

            scoreField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(scoreField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty() && strValue.matches("\\d*\\.?\\d*");
                            }
                            return false;
                        }, "Por favor, insira uma pontuação válida"));
            });
        }

        for (int i = 0; i < evaluationCriteriaFields.size(); i++) {
            TextArea evaluationCriteria = evaluationCriteriaFields.get(i);
            Label evaluationCriteriaErrorLabel = evaluationCriteriaErrorLabels.get(i);

            evaluationCriteria.textProperty().addListener((obs, old, newText) -> {
                if (newText.length() >= MIN_QUESTION_LENGTH) {
                    updateErrorDisplay(evaluationCriteria, evaluationCriteriaErrorLabel, false, null);
                }
            });

            evaluationCriteria.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(evaluationCriteria, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty() && strValue.length() >= MIN_QUESTION_LENGTH;
                            }
                            return false;
                        }, "Por favor, insira critérios de avaliação válidos, com pelo menos 10 caracteres"));
            });
        }
    }


    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, isValid);
        errorLabel.setText(isValid ? message : "");
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
        
        for (int i = 0; i < questionFields.size(); i++) {
            boolean isQuestionValid = true;

            if (questionFields.get(i).getText().length() < MIN_QUESTION_LENGTH) {
                updateErrorDisplay(
                    questionFields.get(i), 
                    questionErrorLabels.get(i), 
                    true,
                    "Por favor, insira uma pergunta válida, com pelo menos 10 caracteres"
                );
                isQuestionValid = false;
            }

            if (scoreFields.get(i).getText() == null || 
                scoreFields.get(i).getText().isEmpty()) {
                updateErrorDisplay(
                    scoreFields.get(i), 
                    scoreErrorLabels.get(i), 
                    true, 
                    "Por favor, insira uma pontuação válida"
                );
                isQuestionValid = false;
            }

            if (i < evaluationCriteriaFields.size()) {
                TextArea criteriaField = evaluationCriteriaFields.get(i);
                if (criteriaField.getText().length() < MIN_QUESTION_LENGTH) {
                    updateErrorDisplay(
                        criteriaField, 
                        evaluationCriteriaErrorLabels.get(i), 
                        true,
                        "Por favor, insira critérios de avaliação válidos, com pelo menos 10 caracteres"
                    );
                    isQuestionValid = false;
                }
            }

            if (i < evaluationCriteriaFields.size()) {
                TextArea criteriaField = evaluationCriteriaFields.get(i);
                if (criteriaField.getText().length() < MIN_QUESTION_LENGTH) {
                    updateErrorDisplay(
                        criteriaField, 
                        evaluationCriteriaErrorLabels.get(i), 
                        true,
                        "Por favor, insira critérios de avaliação válidos, com pelo menos 10 caracteres"
                    );
                    isQuestionValid = false;
                }
            }
    
    

            if (!isQuestionValid) {
                isAllValid = false;
            }
        }

        return isAllValid;
    }
}