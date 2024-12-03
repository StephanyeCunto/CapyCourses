package com.view.professor.valid;

import java.util.*;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.view.login_cadastro.elements.ErrorNotification;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.css.PseudoClass;

public class CursoQuestionarioValid {
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MIN_QUESTION_LENGTH = 10;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    private Map<Integer, List<TextField>> titleFieldsMap = new HashMap<>();
    private Map<Integer, List<Label>> titleErrorLabelsMap = new HashMap<>();
    private Map<Integer, List<TextArea>> questionTextFieldsMap = new HashMap<>();
    private Map<Integer, List<Label>> questionErrorLabelsMap = new HashMap<>();
    private Map<Integer, List<TextField>> scoreFieldsMap = new HashMap<>();
    private Map<Integer, List<Label>> scoreErrorLabelsMap = new HashMap<>();

    private VBox questionContent;
    private VBox questionCard;

    private GridPane parentContainer;

    public void setParentContainer(GridPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setupInitialStateQuestions(VBox questionsContainer, int moduleNumber) {
        clearModuleLists(moduleNumber);
        loadQuestionFields(questionsContainer, moduleNumber);
        setupValidationListeners(moduleNumber);
    }

    public void clearModuleLists(int moduleId) {
        if (titleFieldsMap.containsKey(moduleId)) {
            titleFieldsMap.get(moduleId).clear();
        }
        if (titleErrorLabelsMap.containsKey(moduleId)) {
            titleErrorLabelsMap.get(moduleId).clear();
        }
        if (questionTextFieldsMap.containsKey(moduleId)) {
            questionTextFieldsMap.get(moduleId).clear();
        }
        if (questionErrorLabelsMap.containsKey(moduleId)) {
            questionErrorLabelsMap.get(moduleId).clear();
        }
        if (scoreFieldsMap.containsKey(moduleId)) {
            scoreFieldsMap.get(moduleId).clear();
        }
        if (scoreErrorLabelsMap.containsKey(moduleId)) {
            scoreErrorLabelsMap.get(moduleId).clear();
        }
    }
    

    private void loadQuestionFields(VBox questionsContainer, int moduleNumber) {
        for (Node questionNode : questionsContainer.getChildren()) {
            if (!(questionNode instanceof VBox))
                continue;
            questionCard = (VBox) questionNode;
            questionContent = (VBox) questionCard.getChildren().get(1);

            if (((VBox) questionContent.getChildren().get(2)).getChildren().get(1) instanceof TextArea) {

                TextField titleField = (TextField) (((VBox) questionContent.getChildren().get(0)).getChildren().get(1));
                Label titleErrorLabel = (Label) questionContent.getChildren().get(1);
                TextArea questionTextArea = (TextArea) ((VBox) questionContent.getChildren().get(2)).getChildren()
                        .get(1);
                Label questionErrorLabel = (Label) questionContent.getChildren().get(3);
                TextField scoreField = (TextField) ((VBox) questionContent.getChildren().get(4)).getChildren().get(1);
                Label scoreErrorLabel = (Label) questionContent.getChildren().get(5);

                titleFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(titleField);
                titleErrorLabelsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(titleErrorLabel);

                questionTextFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(questionTextArea);
                questionErrorLabelsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(questionErrorLabel);

                scoreFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(scoreField);
                scoreErrorLabelsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(scoreErrorLabel);
            }
        }
    }

    private void setupValidationListeners(int moduleNumber) {
        ValidationSupport validationSupport = new ValidationSupport();

        List<TextField> titleFields = titleFieldsMap.get(moduleNumber);
        List<Label> titleErrorLabels = titleErrorLabelsMap.get(moduleNumber);
        List<TextArea> questionTextFields = questionTextFieldsMap.get(moduleNumber);
        List<Label> questionErrorLabels = questionErrorLabelsMap.get(moduleNumber);
        List<TextField> scoreFields = scoreFieldsMap.get(moduleNumber);
        List<Label> scoreErrorLabels = scoreErrorLabelsMap.get(moduleNumber);

        for (int i = 0; i < questionTextFields.size(); i++) {
            setupQuestionTextValidation(questionTextFields.get(i), questionErrorLabels.get(i), validationSupport);
            setupScoreValidation(scoreFields.get(i), scoreErrorLabels.get(i), validationSupport);
            setupTitleValidation(titleFields.get(i), titleErrorLabels.get(i), validationSupport);
        }
    }

    private void setupQuestionTextValidation(TextArea questionField, Label errorLabel,
            ValidationSupport validationSupport) {
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

            if ("0".equals(newText)) {
                scoreField.setText(old);
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

    public boolean validateFields(CheckBox isGradeMiniun) {
        boolean isAllValid = true;

        for (Integer moduleNumber : titleFieldsMap.keySet()) {
            List<TextField> titleFields = titleFieldsMap.get(moduleNumber);
            List<Label> titleErrorLabels = titleErrorLabelsMap.get(moduleNumber);
            List<TextArea> questionTextFields = questionTextFieldsMap.get(moduleNumber);
            List<Label> questionErrorLabels = questionErrorLabelsMap.get(moduleNumber);
            List<TextField> scoreFields = scoreFieldsMap.get(moduleNumber);
            List<Label> scoreErrorLabels = scoreErrorLabelsMap.get(moduleNumber);

            for (int i = 0; i < titleFields.size(); i++) {
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
                    ErrorNotification errorNotification = new ErrorNotification(
                            parentContainer,
                            "Preencha todos os campos corretamente");

                    errorNotification.show();
                    isAllValid = false;
                }

                for (int j = 0; j < questionCard.getChildren().size(); j++) {
                    if (j == 1) {
                        VBox teste = (VBox) questionCard.getChildren().get(j);
                        VBox questionsContent = (VBox) teste.getChildren().get(6);
                        if (questionsContent.getChildren().size() == 0) {
                            ErrorNotification errorNotification = new ErrorNotification(
                                    parentContainer,
                                    "Adicione uma questão ao questionário");

                            errorNotification.show();

                            isAllValid = false;
                        }
                    }
                }
            }

            if (isGradeMiniun.isSelected()) {
                if (getTitleFieldsCount() < 1) {
                    ErrorNotification errorNotification = new ErrorNotification(
                            parentContainer,
                            "Adicione um questionário");

                    errorNotification.show();
                    isAllValid = false;
                }
            }
        }
        return isAllValid;
    }

    public int getTitleFieldsCount() {
        return titleFieldsMap.values().stream().mapToInt(List::size).sum();
    }

    public int getQuestionTextFieldsCount() {
        return questionTextFieldsMap.values().stream().mapToInt(List::size).sum();
    }

    public int getScoreFieldsCount() {
        return scoreFieldsMap.values().stream().mapToInt(List::size).sum();
    }

    public int getValidatedTitleFieldsCount() {
        return (int) titleFieldsMap.values().stream()
                .flatMap(List::stream)
                .filter(field -> field.getText().length() >= MIN_TITLE_LENGTH && field.getText().length() <= 100)
                .count();
    }

    public int getValidatedQuestionTextFieldsCount() {
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
}
