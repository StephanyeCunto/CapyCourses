package com.view.professor.valid;

import com.view.login_cadastro.elements.ErrorNotification;
import java.util.*;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class CursoAulasValid {
  private static final int MIN_TITLE_LENGTH = 5;
  private static final int MIN_DETAILS_LENGTH = 10;
  private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

  private Map<Integer, List<TextField>> titleFieldsMap = new HashMap<>();
  private Map<Integer, List<Label>> titleErrorLabelsMap = new HashMap<>();
  private Map<Integer, List<TextField>> videoFieldsMap = new HashMap<>();
  private Map<Integer, List<Label>> videoErrorLabelsMap = new HashMap<>();
  private Map<Integer, List<TextArea>> detailsFieldsMap = new HashMap<>();
  private Map<Integer, List<Label>> detailsErrorLabelsMap = new HashMap<>();
  private Map<Integer, List<TextArea>> materialsFieldsMap = new HashMap<>();
  private Map<Integer, List<Label>> materialsErrorLabelsMap = new HashMap<>();
  private Map<Integer, List<TextField>> durationFieldsMap = new HashMap<>();
  private Map<Integer, List<Label>> durationErrorLabelsMap = new HashMap<>();

  private GridPane parentContainer;

  public void setParentContainer(GridPane parentContainer) {
    this.parentContainer = parentContainer;
  }

  public void setupInitialStateLessons(VBox lessonsList, int moduleNumber) {
    loadValues(lessonsList, moduleNumber);
    setupValidationListeners(moduleNumber);
  }

  public void clearModuleLists(int moduleId) {
    if (titleFieldsMap.containsKey(moduleId)) {
      titleFieldsMap.get(moduleId).clear();
    }
    if (titleErrorLabelsMap.containsKey(moduleId)) {
      titleErrorLabelsMap.get(moduleId).clear();
    }
    if (videoFieldsMap.containsKey(moduleId)) {
      videoFieldsMap.get(moduleId).clear();
    }
    if (videoErrorLabelsMap.containsKey(moduleId)) {
      videoErrorLabelsMap.get(moduleId).clear();
    }
    if (detailsFieldsMap.containsKey(moduleId)) {
      detailsFieldsMap.get(moduleId).clear();
    }
    if (detailsErrorLabelsMap.containsKey(moduleId)) {
      detailsErrorLabelsMap.get(moduleId).clear();
    }
    if (materialsFieldsMap.containsKey(moduleId)) {
      materialsFieldsMap.get(moduleId).clear();
    }
    if (materialsErrorLabelsMap.containsKey(moduleId)) {
      materialsErrorLabelsMap.get(moduleId).clear();
    }
    if (durationFieldsMap.containsKey(moduleId)) {
      durationFieldsMap.get(moduleId).clear();
    }
    if (durationErrorLabelsMap.containsKey(moduleId)) {
      durationErrorLabelsMap.get(moduleId).clear();
    }
  }

  private void loadValues(VBox lessonsList, int moduleNumber) {
    for (Node lessonNode : lessonsList.getChildren()) {
      if (!(lessonNode instanceof VBox)) continue;

      VBox lessonCard = (VBox) lessonNode;
      VBox lessonContent = (VBox) lessonCard.getChildren().get(1);

      if (((VBox) lessonContent.getChildren().get(2)).getChildren().get(1) instanceof TextArea) {
      } else {

        TextField titleField =
            (TextField) lessonContent.getChildren().get(0).lookup(".custom-text-field");
        Label titleErrorLabel = (Label) lessonContent.getChildren().get(1);

        TextField videoField =
            (TextField) lessonContent.getChildren().get(2).lookup(".custom-text-field");
        Label videoErrorLabel = (Label) lessonContent.getChildren().get(3);

        TextArea detailsField =
            (TextArea) lessonContent.getChildren().get(4).lookup(".custom-text-area");
        Label detailsErrorLabel = (Label) lessonContent.getChildren().get(5);

        TextArea materialsField =
            (TextArea) lessonContent.getChildren().get(6).lookup(".custom-text-area");

        Label materialsErrorLabel = (Label) lessonContent.getChildren().get(7);

        TextField durationField =
            (TextField) lessonContent.getChildren().get(8).lookup(".custom-text-field");
        Label durationErrorLabel = (Label) lessonContent.getChildren().get(9);

        clearModuleLists(moduleNumber);

        titleFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(titleField);
        titleErrorLabelsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(titleErrorLabel);
        videoFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(videoField);
        videoErrorLabelsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(videoErrorLabel);
        detailsFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(detailsField);
        detailsErrorLabelsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(detailsErrorLabel);
        materialsFieldsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(materialsField);
        materialsErrorLabelsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(materialsErrorLabel);
        durationFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(durationField);
        durationErrorLabelsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(durationErrorLabel);
      }
    }
  }

  private void setupValidationListeners(int moduleNumber) {
    ValidationSupport validationSupport = new ValidationSupport();

    List<TextField> titleFields = titleFieldsMap.get(moduleNumber);
    List<Label> titleErrorLabels = titleErrorLabelsMap.get(moduleNumber);
    List<TextField> videoFields = videoFieldsMap.get(moduleNumber);
    List<Label> videoErrorLabels = videoErrorLabelsMap.get(moduleNumber);
    List<TextArea> detailsFields = detailsFieldsMap.get(moduleNumber);
    List<Label> detailsErrorLabels = detailsErrorLabelsMap.get(moduleNumber);
    List<TextField> durationFields = durationFieldsMap.get(moduleNumber);
    List<Label> durationErrorLabels = durationErrorLabelsMap.get(moduleNumber);
    List<TextArea> materialsFields = materialsFieldsMap.get(moduleNumber);
    List<Label> materialsErrorLabels = materialsErrorLabelsMap.get(moduleNumber);

    if (titleFields == null || titleErrorLabels == null) return;

    for (int i = 0; i < titleFields.size(); i++) {
      TextField titleField = titleFields.get(i);
      Label titleErrorLabel = titleErrorLabels.get(i);
      TextField videoField = videoFields.get(i);
      Label videoErrorLabel = videoErrorLabels.get(i);
      TextArea detailsField = detailsFields.get(i);
      Label detailsErrorLabel = detailsErrorLabels.get(i);
      TextArea materialsField = materialsFields.get(i);
      Label materialsErrorLabel = materialsErrorLabels.get(i);
      TextField durationField = durationFields.get(i);
      Label durationErrorLabel = durationErrorLabels.get(i);

      titleField
          .textProperty()
          .addListener(
              (obs, old, newText) -> {
                if (newText.length() >= MIN_TITLE_LENGTH) {
                  updateErrorDisplay(titleField, titleErrorLabel, false, null);
                }
              });

      titleField
          .focusedProperty()
          .addListener(
              (obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(
                    titleField,
                    false,
                    Validator.createPredicateValidator(
                        value -> {
                          if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty()
                                && strValue.length() >= 5
                                && strValue.length() <= 100;
                          }
                          return false;
                        },
                        "Por favor, insira um título válido, entre 5 e 100 caracteres"));
              });

      videoField
          .textProperty()
          .addListener(
              (obs, old, newText) -> {
                if (!newText.isEmpty()) {
                  updateErrorDisplay(videoField, videoErrorLabel, false, null);
                }
                if (newText.contains(" ")) {
                  materialsField.setText(old);
                }
              });

      videoField
          .focusedProperty()
          .addListener(
              (obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(
                    videoField,
                    false,
                    Validator.createPredicateValidator(
                        value -> {
                          if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty()
                                && (strValue.startsWith("http://")
                                    || strValue.startsWith("https://"));
                          }
                          return false;
                        },
                        "Por favor, insira um link de vídeo válido"));
              });

      detailsField
          .textProperty()
          .addListener(
              (obs, old, newText) -> {
                if (newText.length() >= MIN_DETAILS_LENGTH) {
                  updateErrorDisplay(detailsField, detailsErrorLabel, false, null);
                }
              });

      detailsField
          .focusedProperty()
          .addListener(
              (obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(
                    detailsField,
                    false,
                    Validator.createPredicateValidator(
                        value -> {
                          if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty() && strValue.length() >= 10;
                          }
                          return false;
                        },
                        "Por favor, insira detalhes válidos, com no mínimo 10 caracteres"));
              });

      materialsField
          .textProperty()
          .addListener(
              (obs, old, newText) -> {
                if (!newText.isEmpty()) {
                  updateErrorDisplay(materialsField, materialsErrorLabel, false, null);
                }
                if (newText.contains(" ")) {
                  materialsField.setText(old);
                }
              });

      materialsField
          .focusedProperty()
          .addListener(
              (obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(
                    materialsField,
                    false,
                    Validator.createPredicateValidator(
                        value -> {
                          if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty()
                                && (strValue.matches("(https?://)[^\\\\s]+$"));
                          }
                          return false;
                        },
                        "Por favor, insira um link válido"));
              });

      durationField
          .textProperty()
          .addListener(
              (obs, old, newText) -> {
                if (!newText.isEmpty()) {
                  updateErrorDisplay(durationField, durationErrorLabel, false, null);
                }
              });

      durationField
          .focusedProperty()
          .addListener(
              (obs, wasFocused, isNowFocused) -> {
                validationSupport.registerValidator(
                    durationField,
                    false,
                    Validator.createPredicateValidator(
                        value -> {
                          if (value instanceof String) {
                            String strValue = (String) value;
                            return !strValue.trim().isEmpty() && strValue.matches("\\d+");
                          }
                          return false;
                        },
                        "Por favor, insira uma duração válida em minutos"));
              });
    }
  }

  private void updateErrorDisplay(
      Control field, Label errorLabel, boolean isValid, String message) {
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

    for (Map.Entry<Integer, List<TextField>> entry : titleFieldsMap.entrySet()) {
      int moduleNumber = entry.getKey();
      List<TextField> titleFields = titleFieldsMap.get(moduleNumber);
      List<Label> titleErrorLabels = titleErrorLabelsMap.get(moduleNumber);
      List<TextField> videoFields = videoFieldsMap.get(moduleNumber);
      List<Label> videoErrorLabels = videoErrorLabelsMap.get(moduleNumber);
      List<TextArea> detailsFields = detailsFieldsMap.get(moduleNumber);
      List<Label> detailsErrorLabels = detailsErrorLabelsMap.get(moduleNumber);
      List<TextArea> materialsFields = materialsFieldsMap.get(moduleNumber);
      List<Label> materialsErrorLabels = materialsErrorLabelsMap.get(moduleNumber);
      List<TextField> durationFields = durationFieldsMap.get(moduleNumber);
      List<Label> durationErrorLabels = durationErrorLabelsMap.get(moduleNumber);

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

        if (videoFields.get(i).getText() == null
            || videoFields.get(i).getText().isEmpty()
            || !videoFields.get(i).getText().matches("^https?://.*")) {
          updateErrorDisplay(
              videoFields.get(i),
              videoErrorLabels.get(i),
              true,
              "Por favor, insira um link de vídeo válido");
          isLessonValid = false;
        }

        if (detailsFields.get(i).getText().length() < MIN_DETAILS_LENGTH
            || detailsFields.get(i).getText().isEmpty()) {
          updateErrorDisplay(
              detailsFields.get(i),
              detailsErrorLabels.get(i),
              true,
              "Por favor, insira detalhes válidos, com no mínimo 10 caracteres");
          isLessonValid = false;
        }

        if (!materialsFields.get(i).getText().matches("^https?://.*")
            || materialsFields.get(i).getText().isEmpty()) {
          updateErrorDisplay(
              materialsFields.get(i),
              materialsErrorLabels.get(i),
              true,
              "Por favor, insira um link válido");
          isLessonValid = false;
        }

        if (durationFields.get(i).getText() == null
            || !durationFields.get(i).getText().matches("\\d+")) {
          updateErrorDisplay(
              durationFields.get(i),
              durationErrorLabels.get(i),
              true,
              "Por favor, insira uma duração válida em minutos");
          isLessonValid = false;
        }

        if (!isLessonValid) {
          ErrorNotification errorNotification =
              new ErrorNotification(parentContainer, "Preencha todos os campos corretamente");

          errorNotification.show();
          isAllValid = false;
        }
      }
    }

    return isAllValid;
  }

  public int getTotalTitleFields() {
    return titleFieldsMap.values().stream().mapToInt(List::size).sum();
  }

  public int getTotalVideoFields() {
    return videoFieldsMap.values().stream().mapToInt(List::size).sum();
  }

  public int getTotalDetailsFields() {
    return detailsFieldsMap.values().stream().mapToInt(List::size).sum();
  }

  public int getTotalMaterialsFields() {
    return materialsFieldsMap.values().stream().mapToInt(List::size).sum();
  }

  public int getTotalDurationFields() {
    return durationFieldsMap.values().stream().mapToInt(List::size).sum();
  }

  public int getValidatedTitleFields() {
    return titleFieldsMap.values().stream()
        .flatMap(List::stream)
        .filter(field -> field.getText().length() >= MIN_TITLE_LENGTH)
        .mapToInt(e -> 1)
        .sum();
  }

  public int getValidatedVideoFields() {
    return videoFieldsMap.values().stream()
        .flatMap(List::stream)
        .filter(
            field -> {
              String text = field.getText();
              return text != null
                  && !text.trim().isEmpty()
                  && (text.startsWith("http://") || text.startsWith("https://"));
            })
        .mapToInt(e -> 1)
        .sum();
  }

  public int getValidatedDetailsFields() {
    return detailsFieldsMap.values().stream()
        .flatMap(List::stream)
        .filter(field -> field.getText().length() >= MIN_DETAILS_LENGTH)
        .mapToInt(e -> 1)
        .sum();
  }

  public int getValidatedMaterialsFields() {
    return materialsFieldsMap.values().stream()
        .flatMap(List::stream)
        .filter(
            field -> {
              String text = field.getText();
              return text != null
                  && !text.trim().isEmpty()
                  && (text.startsWith("http://") || text.startsWith("https://"));
            })
        .mapToInt(e -> 1)
        .sum();
  }

  public int getValidatedDurationFields() {
    return durationFieldsMap.values().stream()
        .flatMap(List::stream)
        .filter(
            field -> {
              String text = field.getText();
              return text != null && text.matches("\\d+");
            })
        .mapToInt(e -> 1)
        .sum();
  }
}
