package com.view.elements.Forum.valid;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateForumValid {
    private static final ValidationSupport validationSupport = new ValidationSupport();
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField category;
    @FXML
    private TextArea question;
    @FXML
    private Label titleErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
    private Label categoryErrorLabel;
    @FXML
    private Label questionErrorLabel;

    public void setupInitialState(TextField title,TextField description, TextField category, TextArea question,Label titleErrorLabel, Label descriptionErrorLabel, Label categoryErrorLabel, Label questionErrorLabel) {
        loadValues(title, description, category, question, titleErrorLabel, descriptionErrorLabel, categoryErrorLabel, questionErrorLabel);
        title.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
        description.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
        category.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
        question.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);

        title.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateErrorDisplay(title, titleErrorLabel, true, "");
            }
        });

        description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateErrorDisplay(description, descriptionErrorLabel, true, "");
            }
        });

        category.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateErrorDisplay(category, categoryErrorLabel, true, "");
            }
        });

        question.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateErrorDisplay(question, questionErrorLabel, true, "");
            }
        });

        title.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(title, false,
            Validator.createPredicateValidator(value -> {
            if (value instanceof String) {
                String strValue = (String) value;
                return !strValue.trim().isEmpty();
            }
            return false;
            }, "Por favor, insira um título válido"));
        });

        description.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(description, false,
            Validator.createPredicateValidator(value -> {
            if (value instanceof String) {
                String strValue = (String) value;
                return !strValue.trim().isEmpty();
            }
            return false;
            }, "Por favor, insira uma descrição válida"));
        });

        category.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(category, false,
            Validator.createPredicateValidator(value -> {
            if (value instanceof String) {
                String strValue = (String) value;
                return !strValue.trim().isEmpty();
            }
            return false;
            }, "Por favor, insira uma categoria válida"));
        });

        question.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator(question, false,
            Validator.createPredicateValidator(value -> {
            if (value instanceof String) {
                String strValue = (String) value;
                return !strValue.trim().isEmpty();
            }
            return false;
            }, "Por favor, insira uma pergunta válida"));
        });
    }

    private void loadValues(TextField title,TextField description, TextField category, TextArea question,Label titleErrorLabel, Label descriptionErrorLabel, Label categoryErrorLabel, Label questionErrorLabel) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.question = question;
        this.titleErrorLabel = titleErrorLabel;
        this.descriptionErrorLabel = descriptionErrorLabel;
        this.categoryErrorLabel = categoryErrorLabel;
        this.questionErrorLabel = questionErrorLabel;
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
        errorLabel.setText(isValid ? "" : message);
        errorLabel.setVisible(!isValid);
        errorLabel.setManaged(!isValid);
        if(!isValid){
            field.getStyleClass().add("error-field");
        }else{
            field.getStyleClass().remove("error-field");
        }
    }

    private boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    private boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }

    private boolean isValidCategory(String category) {
        return category != null && !category.trim().isEmpty();
    }

    private boolean isValidQuestion(String question) {
        return question != null && !question.trim().isEmpty();
    }

    public boolean isValid() {
        boolean isValid = true;
        if (!isValidTitle(title.getText())) {
            updateErrorDisplay(title, titleErrorLabel, false, "Por favor, digite um title válido");
            isValid = false;
        }
        if (!isValidDescription(description.getText())) {
            updateErrorDisplay(description, descriptionErrorLabel, false, "Por favor, digite uma descrição válida");
            isValid = false;
        }
        if (!isValidCategory(category.getText())) {
            updateErrorDisplay(category, categoryErrorLabel, false, "Por favor, digite uma categoria válida");
            isValid = false;
        }
        if (!isValidQuestion(question.getText())) {
            updateErrorDisplay(question, questionErrorLabel, false, "Por favor, digite uma pergunta válida");
            isValid = false;
        }
        return isValid;
    }

}