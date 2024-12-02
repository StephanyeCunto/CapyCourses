package com.view.professor.valid;

import java.util.*;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.css.PseudoClass;

import com.view.login_cadastro.elements.ErrorNotification;

public class CursoAulasValid {
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MIN_DETAILS_LENGTH = 10;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    private List<TextField> titleFields = new ArrayList<>();
    private List<Label> titleErrorLabels = new ArrayList<>();
    private List<TextField> videoFields = new ArrayList<>();
    private List<Label> videoErrorLabels = new ArrayList<>();
    private List<TextArea> detailsFields = new ArrayList<>();
    private List<Label> detailsErrorLabels = new ArrayList<>();
    private List<TextArea> materialsFields = new ArrayList<>();
    private List<Label> materialsErrorLabels = new ArrayList<>();
    private List<TextField> durationFields = new ArrayList<>();
    private List<Label> durationErrorLabels = new ArrayList<>();

    private GridPane parentContainer;

    public void setParentContainer(GridPane parentContainer) {
        this.parentContainer = parentContainer;
    }

    public void setupInitialStateLessons(VBox lessonsList) {
        loadValues(lessonsList);

        setupValidationListeners();
    }

    private void loadValues(VBox lessonsList) {
        for (Node lessonNode : lessonsList.getChildren()) {
            if (!(lessonNode instanceof VBox))
                continue;

            VBox lessonCard = (VBox) lessonNode;
            VBox lessonContent = (VBox) lessonCard.getChildren().get(1);

            if (((VBox) lessonContent.getChildren().get(2)).getChildren().get(1) instanceof TextArea) {
            } else {

                TextField titleField = (TextField) lessonContent.getChildren().get(0)
                        .lookup(".custom-text-field");
                Label titleErrorLabel = (Label) lessonContent.getChildren().get(1);

                TextField videoField = (TextField) lessonContent.getChildren().get(2)
                        .lookup(".custom-text-field");
                Label videoErrorLabel = (Label) lessonContent.getChildren().get(3);

                TextArea detailsField = (TextArea) lessonContent.getChildren().get(4)
                        .lookup(".custom-text-area");
                Label detailsErrorLabel = (Label) lessonContent.getChildren().get(5);

                TextArea materialsField = (TextArea) lessonContent.getChildren().get(6)
                        .lookup(".custom-text-area");

                Label materialsErrorLabel = (Label) lessonContent.getChildren().get(7);

                TextField durationField = (TextField) lessonContent.getChildren().get(8)
                        .lookup(".custom-text-field");
                Label durationErrorLabel = (Label) lessonContent.getChildren().get(9);

                titleFields.add(titleField);
                titleErrorLabels.add(titleErrorLabel);
                videoFields.add(videoField);
                videoErrorLabels.add(videoErrorLabel);
                detailsFields.add(detailsField);
                detailsErrorLabels.add(detailsErrorLabel);
                materialsFields.add(materialsField);
                materialsErrorLabels.add(materialsErrorLabel);
                durationFields.add(durationField);
                durationErrorLabels.add(durationErrorLabel);
            }
        }
    }

    private void setupValidationListeners() {
        ValidationSupport validationSupport = new ValidationSupport();

        for (int i = 0; i < titleFields.size(); i++) {
            TextField titleField = titleFields.get(i);
            Label titleErrorLabel = titleErrorLabels.get(i);
            TextField videoField = videoFields.get(i);
            Label videoErrorLabel = videoErrorLabels.get(i);
            TextArea detailsField = detailsFields.get(i);
            Label detailsErrorLabel = detailsErrorLabels.get(i);
            TextField durationField = durationFields.get(i);
            Label durationErrorLabel = durationErrorLabels.get(i);
            TextArea materialsField = materialsFields.get(i);
            Label materialsErrorLabel = materialsErrorLabels.get(i);

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

            videoField.textProperty().addListener((obs, old, newText) -> {
                if (!newText.isEmpty()) {
                    updateErrorDisplay(videoField, videoErrorLabel, false, null);
                }
            });

            videoField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(videoField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty()
                                        && (strValue.startsWith("http://") || strValue.startsWith("https://"));
                            }
                            return false;
                        }, "Por favor, insira um link de vídeo válido"));
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
                        }, "Por favor, insira detalhes válidos, com no mínimo 10 caracteres"));
            });

            materialsField.textProperty().addListener((obs, old, newText) -> {
                if (!newText.isEmpty()) {
                    updateErrorDisplay(materialsField, materialsErrorLabel, false, null);
                }
            });

            materialsField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(materialsField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty()
                                        && (strValue.startsWith("http://") || strValue.startsWith("https://"));
                            }
                            return false;
                        }, "Por favor, insira um link válido"));
            });

            durationField.textProperty().addListener((obs, old, newText) -> {
                if (!newText.isEmpty()) {
                    updateErrorDisplay(durationField, durationErrorLabel, false, null);
                }
            });

            durationField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(durationField, false,
                        Validator.createPredicateValidator(value -> {
                            if (value instanceof String) {
                                String strValue = (String) value;
                                return !strValue.trim().isEmpty() && strValue.matches("\\d+");
                            }
                            return false;
                        }, "Por favor, insira uma duração válida em minutos"));
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
            boolean isLessonValid = true;

            if (titleFields.get(i).getText().length() < MIN_TITLE_LENGTH) {
                updateErrorDisplay(
                        titleFields.get(i),
                        titleErrorLabels.get(i),
                        true,
                        "Por favor, insira um título válido, entre 5 e 100 caracteres");
                isLessonValid = false;
            }

            if (videoFields.get(i).getText() == null ||
                    videoFields.get(i).getText().isEmpty() ||
                    !videoFields.get(i).getText().matches("^https?://.*")) {
                updateErrorDisplay(
                        videoFields.get(i),
                        videoErrorLabels.get(i),
                        true,
                        "Por favor, insira um link de vídeo válido");
                isLessonValid = false;
            }

            if (detailsFields.get(i).getText().length() < MIN_DETAILS_LENGTH ||
                    detailsFields.get(i).getText().isEmpty()) {
                updateErrorDisplay(
                        detailsFields.get(i),
                        detailsErrorLabels.get(i),
                        true,
                        "Por favor, insira detalhes válidos, com no mínimo 10 caracteres");
                isLessonValid = false;
            }

            if (!materialsFields.get(i).getText().matches("^https?://.*") ||
                    materialsFields.get(i).getText().isEmpty()) {
                updateErrorDisplay(
                        materialsFields.get(i),
                        materialsErrorLabels.get(i),
                        true,
                        "Por favor, insira um link de vídeo válido");
                isLessonValid = false;
            }

            if (durationFields.get(i).getText() == null ||
                    !durationFields.get(i).getText().matches("\\d+")) {
                updateErrorDisplay(
                        durationFields.get(i),
                        durationErrorLabels.get(i),
                        true,
                        "Por favor, insira uma duração válida em minutos");
                isLessonValid = false;
            }

            if (!isLessonValid) {
                ErrorNotification errorNotification = new ErrorNotification(
                    parentContainer, 
                    "Preencha todos os campos corretamente" 
                );

                errorNotification.show();
                isAllValid = false;
            }
        }

        return isAllValid;
    }

    public int getTotalTitleFields() {
        return titleFields.size();
    }

    public int getTotalVideoFields() {
        return videoFields.size();
    }

    public int getTotalDetailsFields() {
        return detailsFields.size();
    }

    public int getTotalMaterialsFields() {
        return materialsFields.size();
    }

    public int getTotalDurationFields() {
        return durationFields.size();
    }

    public int getValidatedTitleFields() {
        return (int) titleFields.stream()
                .filter(field -> field.getText().length() >= MIN_TITLE_LENGTH)
                .count();
    }

    public int getValidatedVideoFields() {
        return (int) videoFields.stream()
                .filter(field -> {
                    String text = field.getText();
                    return text != null && !text.trim().isEmpty()
                            && (text.startsWith("http://") || text.startsWith("https://"));
                })
                .count();
    }

    public int getValidatedDetailsFields() {
        return (int) detailsFields.stream()
                .filter(field -> field.getText().length() >= MIN_DETAILS_LENGTH)
                .count();
    }

    public int getValidatedMaterialsFields() {
        return (int) materialsFields.stream()
                .filter(field -> {
                    String text = field.getText();
                    return text != null && !text.trim().isEmpty()
                            && (text.startsWith("http://") || text.startsWith("https://"));
                })
                .count();
    }

    public int getValidatedDurationFields() {
        return (int) durationFields.stream()
                .filter(field -> {
                    String text = field.getText();
                    return text != null && text.matches("\\d+");
                })
                .count();
    }

    public int getModulesWithLessonsCount() {
        return 0;
    }

}
