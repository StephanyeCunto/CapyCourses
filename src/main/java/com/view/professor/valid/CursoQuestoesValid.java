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
        clearFieldLists();
        loadValues(questionsContainer);
        setupValidationListeners();
    }

    private void clearFieldLists() {
        questionFields.clear();
        questionErrorLabels.clear();
        scoreFields.clear();
        scoreErrorLabels.clear();
        evaluationCriteriaErrorLabels.clear();
        evaluationCriteriaFields.clear();
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

            if(!((Label)questionContent.getChildren().get(4)).getText().equals("Opções (selecione a correta)") && !((Label)questionContent.getChildren().get(4)).getText().equals("Opção (selecione a correta)") ){
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

    private boolean validateOptionsForContainer(VBox optionsContainer, Label optionErrorLabel) {
        System.out.println("optionsContainer.getChildren().size() = "+optionsContainer.getChildren().size());
        for(int i=0; i<optionsContainer.getChildren().size(); i++){
            System.out.println("optionsContainer.getChildren().get("+i+") = "+optionsContainer.getChildren().get(i));
        }
        if (optionsContainer.getChildren().size() < 4) { 
            updateErrorDisplay(
                null, 
                optionErrorLabel, 
                true,
                "Por favor, adicione pelo menos duas opções"
            );
            return false;
        }

        boolean hasSelectedOption = false;
        boolean allOptionsValid = true;
    
        for (int i = 0; i < optionsContainer.getChildren().size(); i += 2) {
            Node optionNode = optionsContainer.getChildren().get(i);
            Node errorNode = optionsContainer.getChildren().get(i + 1);
            
            if (optionNode instanceof HBox) {
                HBox optionHBox = (HBox) optionNode;
                Node selectionControl = optionHBox.getChildren().get(0);
                TextField optionTextField = (TextField) optionHBox.getChildren().get(1);
    
                if (selectionControl instanceof RadioButton && ((RadioButton) selectionControl).isSelected()) {
                    hasSelectedOption = true;
                } else if (selectionControl instanceof CheckBox && ((CheckBox) selectionControl).isSelected()) {
                    hasSelectedOption = true;
                }
    
                if (optionTextField.getText().trim().length() < 5) {
                    optionTextField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, true);
                    if (errorNode instanceof Label) {
                        ((Label) errorNode).setText("Opções devem ter pelo menos 5 caracteres");
                        ((Label) errorNode).setVisible(true);
                    }
                    allOptionsValid = false;
                } else {
                    optionTextField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
                    if (errorNode instanceof Label) {
                        ((Label) errorNode).setVisible(false);
                    }
                }
            }
        }

        if (!hasSelectedOption) {
            updateErrorDisplay(
                null, 
                optionErrorLabel, 
                true,
                "Selecione pelo menos uma opção correta"
            );
            return false;
        }
    
        if (!allOptionsValid) {
            updateErrorDisplay(
                null, 
                optionErrorLabel, 
                true,
                "Corrija as opções inválidas"
            );
            return false;
        }

        updateErrorDisplay(
            null, 
            optionErrorLabel, 
            false, 
            null
        );
    

        return true;
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