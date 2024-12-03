package com.view.professor.valid;

import java.util.*;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.css.PseudoClass;

import com.view.login_cadastro.elements.ErrorNotification;

public class CursoQuestoesValid {
    private static final int MIN_QUESTION_LENGTH = 10;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    private Map<Integer, List<TextArea>> questionTextFieldsMap = new HashMap<>();
    private Map<Integer, List<Label>> questionErrorLabelsMap = new HashMap<>();
    private Map<Integer, List<TextField>> scoreFieldsMap = new HashMap<>();
    private Map<Integer, List<Label>> scoreErrorLabelsMap = new HashMap<>();
    private Map<Integer, List<TextArea>> evaluationCriteriaFieldsMaps = new HashMap<>();
    private Map<Integer, List<Label>> evaluationCriteriaErrorLabelsMaps = new HashMap<>();

    private GridPane parentContainer;

    public void setParentContainer(GridPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setupInitialStateQuestions(VBox questionsContainer, int moduleNumber) {
        clearLists(moduleNumber);
        loadValues(questionsContainer, moduleNumber);
        setupValidationListeners(moduleNumber);
    }

    private void clearLists(int moduleNumber) {
        questionTextFieldsMap.remove(moduleNumber);
        questionErrorLabelsMap.remove(moduleNumber);
        scoreFieldsMap.remove(moduleNumber);
        scoreErrorLabelsMap.remove(moduleNumber);
        evaluationCriteriaFieldsMaps.remove(moduleNumber);
        evaluationCriteriaErrorLabelsMaps.remove(moduleNumber);
    }

    private void loadValues(VBox questionsContainer, int moduleNumber) {
        for (Node questionNode : questionsContainer.getChildren()) {
            if (!(questionNode instanceof VBox))
                continue;
            VBox questionCard = (VBox) questionNode;
            VBox questionContent = (VBox) questionCard.getChildren().get(1);

            HBox scoreHbox = (HBox) questionContent.getChildren().get(0);
            TextField scoreField = (TextField) scoreHbox.getChildren().get(1);
            Label scoreErrorLabel = (Label) questionContent.getChildren().get(1);

            TextArea questionTextArea = (TextArea) questionContent.getChildren().get(2);
            Label questionErrorLabel = (Label) questionContent.getChildren().get(3);

            questionTextFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(questionTextArea);
            questionErrorLabelsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(questionErrorLabel);
            scoreFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(scoreField);
            scoreErrorLabelsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(scoreErrorLabel);

            if (questionContent.getChildren().get(7) instanceof TextArea) {
                TextArea evaluationCriteria = (TextArea) questionContent.getChildren().get(7);
                Label evaluationCriteriaError = (Label) questionContent.getChildren().get(8);

                evaluationCriteriaFieldsMaps.computeIfAbsent(moduleNumber, k -> new ArrayList<>())
                        .add(evaluationCriteria);
                evaluationCriteriaErrorLabelsMaps.computeIfAbsent(moduleNumber, k -> new ArrayList<>())
                        .add(evaluationCriteriaError);
            }

        }

    }

    private void setupValidationListeners(int moduleNumber) {
        ValidationSupport validationSupport = new ValidationSupport();
        List<TextArea> questionTextFields = questionTextFieldsMap.get(moduleNumber);
        List<Label> questionErrorLabels = questionErrorLabelsMap.get(moduleNumber);
        List<TextField> scoreFields = scoreFieldsMap.get(moduleNumber);
        List<Label> scoreErrorLabels = scoreErrorLabelsMap.get(moduleNumber);
        List<TextArea> evaluationCriteriaFields = evaluationCriteriaFieldsMaps.get(moduleNumber);
        List<Label> evaluationCriteriaErrorLabels = evaluationCriteriaErrorLabelsMaps.get(moduleNumber);

        for (int i = 0; i < questionTextFields.size(); i++) {
            TextArea questionText = questionTextFields.get(i);
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
                if ("0".equals(newText)) {
                    scoreField.setText(old);
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
        for (Integer moduleNumber : questionTextFieldsMap.keySet()) {

            List<TextArea> questionTextFields = questionTextFieldsMap.get(moduleNumber);
            List<Label> questionErrorLabels = questionErrorLabelsMap.get(moduleNumber);
            List<TextField> scoreFields = scoreFieldsMap.get(moduleNumber);
            List<Label> scoreErrorLabels = scoreErrorLabelsMap.get(moduleNumber);
            List<TextArea> evaluationCriteriaFields = evaluationCriteriaFieldsMaps.get(moduleNumber);
            List<Label> evaluationCriteriaErrorLabels = evaluationCriteriaErrorLabelsMaps.get(moduleNumber);

            boolean isQuestionValid = true;
            for (int i = 0; i < questionTextFields.size(); i++) {

                if (questionTextFields.get(i).getText().length() < MIN_QUESTION_LENGTH) {
                    updateErrorDisplay(
                            questionTextFields.get(i),
                            questionErrorLabels.get(i),
                            true,
                            "Por favor, insira uma pergunta válida, com pelo menos 10 caracteres");
                    isQuestionValid = false;
                }

                if (scoreFields.get(i).getText() == null ||
                        scoreFields.get(i).getText().isEmpty()) {
                    updateErrorDisplay(
                            scoreFields.get(i),
                            scoreErrorLabels.get(i),
                            true,
                            "Por favor, insira uma pontuação válida");
                    isQuestionValid = false;
                }

                if (i < evaluationCriteriaFields.size()) {
                    TextArea criteriaField = evaluationCriteriaFields.get(i);
                    if (criteriaField.getText().length() < MIN_QUESTION_LENGTH) {
                        updateErrorDisplay(
                                criteriaField,
                                evaluationCriteriaErrorLabels.get(i),
                                true,
                                "Por favor, insira critérios de avaliação válidos, com pelo menos 10 caracteres");
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
                                "Por favor, insira critérios de avaliação válidos, com pelo menos 10 caracteres");
                        isQuestionValid = false;
                    }
                }

                if (!isQuestionValid) {
                    ErrorNotification errorNotification = new ErrorNotification(
                            parentContainer,
                            "Preencha todos os campos corretamente");

                    errorNotification.show();

                    isAllValid = false;
                }
            }
        }

        return isAllValid;
    }

    public int getQuestionFieldsCount() {
        return questionTextFieldsMap.values().stream().mapToInt(List::size).sum();
    }

    public int getScoreFieldsCount() {
        return scoreFieldsMap.values().stream().mapToInt(List::size).sum();
    }

    public int getEvaluationCriteriaFieldsCount() {
        return evaluationCriteriaFieldsMaps.values().stream().mapToInt(List::size).sum();

    }

    public int getValidatedQuestionFieldsCount() {
        return (int) questionTextFieldsMap.values().stream()
                .flatMap(List::stream)
                .filter(field -> field.getText().length() >= MIN_QUESTION_LENGTH)
                .count();
    }

    public int getValidatedScoreFieldsCount() {
        return (int) scoreFieldsMap.values().stream()
                .flatMap(List::stream)
                .filter(field -> !field.getText().isEmpty() && field.getText().matches("\\d*\\.?\\d*"))
                .count();
    }

    public int getValidatedEvaluationCriteriaFieldsCount() {
        return (int) evaluationCriteriaFieldsMaps.values().stream()
        .flatMap(List::stream)
        .filter(field -> field.getText().length() >= MIN_QUESTION_LENGTH)
        .count();
    }
}