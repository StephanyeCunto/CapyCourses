package com.view.professor.valid;

import com.view.login_cadastro.elements.ErrorNotification;
import java.util.*;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class CursoModulesValid {
  private static final int MIN_TITLE_LENGTH = 5;
  private static final int MIN_DETAILS_LENGTH = 10;
  private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

  private Map<Integer, TextField> titleFields = new HashMap<>();
  private Map<Integer, Label> titleErrorLabels = new HashMap<>();
  private Map<Integer, TextField> durationFields = new HashMap<>();
  private Map<Integer, Label> durationErrorLabels = new HashMap<>();
  private Map<Integer, TextArea> detailsFields = new HashMap<>();
  private Map<Integer, Label> detailsErrorLabels = new HashMap<>();

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

  private void moduleClear() {
    titleFields.clear();
    titleErrorLabels.clear();
    durationFields.clear();
    durationErrorLabels.clear();
    detailsFields.clear();
    detailsErrorLabels.clear();
  }

  private void loadValues(VBox modulesList) {
    moduleList = modulesList;
    moduleClear();

    for (int moduleIndex = 0; moduleIndex < modulesList.getChildren().size(); moduleIndex++) {
      Node moduleNode = modulesList.getChildren().get(moduleIndex);
      if (!(moduleNode instanceof VBox)) continue;

      VBox moduleCard = (VBox) moduleNode;
      lessonList = (VBox) moduleCard.getChildren().get(2);

      VBox moduleContent = (VBox) moduleCard.getChildren().get(1);

      TextField titleField =
          (TextField)
              ((HBox) ((VBox) moduleContent.getChildren().get(0)).getChildren().get(1))
                  .getChildren()
                  .get(0);
      Label titleErrorLabel =
          (Label) ((VBox) moduleContent.getChildren().get(0)).getChildren().get(2);

      TextField durationField =
          (TextField)
              ((VBox) ((HBox) moduleContent.getChildren().get(1)).getChildren().get(0))
                  .getChildren()
                  .get(1);
      Label durationErrorLabel = (Label) moduleContent.getChildren().get(2);

      TextArea detailsField =
          (TextArea) ((VBox) moduleContent.getChildren().get(3)).getChildren().get(1);
      Label detailsErrorLabel = (Label) moduleContent.getChildren().get(4);

      titleFields.put(moduleIndex, titleField);
      titleErrorLabels.put(moduleIndex, titleErrorLabel);
      durationFields.put(moduleIndex, durationField);
      durationErrorLabels.put(moduleIndex, durationErrorLabel);
      detailsFields.put(moduleIndex, detailsField);
      detailsErrorLabels.put(moduleIndex, detailsErrorLabel);
    }
  }

  private void setupValidationListeners() {
    ValidationSupport validationSupport = new ValidationSupport();

    for (Integer moduleIndex : titleFields.keySet()) {
      TextField titleField = titleFields.get(moduleIndex);
      Label titleErrorLabel = titleErrorLabels.get(moduleIndex);
      TextField durationField = durationFields.get(moduleIndex);
      Label durationErrorLabel = durationErrorLabels.get(moduleIndex);
      TextArea detailsField = detailsFields.get(moduleIndex);
      Label detailsErrorLabel = detailsErrorLabels.get(moduleIndex);

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

      durationField
          .textProperty()
          .addListener(
              (obs, old, newText) -> {
                if (newText.length() > 0) {
                  updateErrorDisplay(durationField, durationErrorLabel, false, null);
                }
                if ("0".equals(newText)) {
                  durationField.setText(old);
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
                            return !strValue.trim().isEmpty();
                          }
                          return false;
                        },
                        "Por favor, insira uma duração válida"));
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
                        "Por favor, insira uma descrição válida, no mínimo de 10 caracteres"));
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

    for (Integer moduleIndex : titleFields.keySet()) {
      boolean isModuleValid = true;

      if (titleFields.get(moduleIndex).getText().length() < MIN_TITLE_LENGTH) {
        updateErrorDisplay(
            titleFields.get(moduleIndex),
            titleErrorLabels.get(moduleIndex),
            true,
            "Por favor, insira um título válido, entre 5 e 100 caracteres");
        isModuleValid = false;
      }

      if (durationFields.get(moduleIndex).getText() == null
          || durationFields.get(moduleIndex).getText().isEmpty()) {
        updateErrorDisplay(
            durationFields.get(moduleIndex),
            durationErrorLabels.get(moduleIndex),
            true,
            "Por favor, insira uma duração válida");
        isModuleValid = false;
      }

      if (detailsFields.get(moduleIndex).getText().length() < MIN_DETAILS_LENGTH
          || detailsFields.get(moduleIndex).getText().isEmpty()) {
        updateErrorDisplay(
            detailsFields.get(moduleIndex),
            detailsErrorLabels.get(moduleIndex),
            true,
            "Por favor, insira uma descrição válida, no mínimo 10 caracteres");
        isModuleValid = false;
      }
      int moduleIndexTrue = moduleIndex + 1;

      for (Node moduleNode : moduleList.getChildren()) {
        VBox moduleCard = (VBox) moduleNode;
        VBox lessonList = (VBox) moduleCard.getChildren().get(2);
        if (lessonList.getChildren().size() < 2) {
          ErrorNotification errorNotification =
              new ErrorNotification(
                  parentContainer, "Adicione uma aula no módulo " + (moduleIndexTrue));
          errorNotification.show();
        }
      }

      if (!isModuleValid) {
        ErrorNotification errorNotification =
            new ErrorNotification(parentContainer, "Preencha todos os campos corretamente");

        errorNotification.show();
        isAllValid = false;

        for (Node moduleNode : moduleList.getChildren()) {
          VBox moduleCard = (VBox) moduleNode;
          VBox lessonList = (VBox) moduleCard.getChildren().get(2);
          if (lessonList.getChildren().size() < 2) {
            isAllValid = false;
          }
        }
      }
    }

    if (getTotalTitleFields() == 0) {
      ErrorNotification errorNotification =
          new ErrorNotification(parentContainer, "Deve haver um módulo no curso");

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
    return (int)
        titleFields.values().stream()
            .filter(field -> field.getText().length() >= MIN_TITLE_LENGTH)
            .count();
  }

  public int getValidatedDurationFields() {
    return (int)
        durationFields.values().stream()
            .filter(field -> field.getText() != null && !field.getText().trim().isEmpty())
            .count();
  }

  public int getValidatedDetailsFields() {
    return (int)
        detailsFields.values().stream()
            .filter(field -> field.getText().length() >= MIN_DETAILS_LENGTH)
            .count();
  }
}
