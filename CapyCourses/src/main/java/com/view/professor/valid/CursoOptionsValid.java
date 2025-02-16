package com.view.professor.valid;

import com.view.login_cadastro.elements.ErrorNotification;
import java.util.*;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class CursoOptionsValid {
  private static final int MIN_OPTION_LENGTH = 1;
  private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

  private Map<Integer, List<TextField>> optionFieldsMap = new HashMap<>();
  private Map<Integer, List<Label>> optionErrorLabelsMap = new HashMap<>();
  private Map<Integer, List<Node>> selectionControlsMap = new HashMap<>();

  private ValidationSupport validationSupport = new ValidationSupport();

  private GridPane parentContainer;

  public void setParentContainer(GridPane parentContainer) {
    this.parentContainer = parentContainer;
  }

  public void setupInitialStateOptions(VBox optionsList, boolean singleChoice, int moduleNumber) {
    clearList(moduleNumber);
    loadValues(optionsList, moduleNumber);
    setupValidationListeners(moduleNumber);
  }

  private void clearList(int moduleNumber) {
    optionFieldsMap.remove(moduleNumber);
    optionErrorLabelsMap.remove(moduleNumber);
    selectionControlsMap.remove(moduleNumber);
  }

  private void loadValues(VBox optionsList, int moduleNumber) {

    for (Node node : optionsList.getChildren()) {
      if (!(node instanceof HBox)) continue;

      HBox optionBox = (HBox) node;
      Node selectionControl = null;
      TextField optionField = null;
      Label optionErrorLabel = null;

      for (Node child : optionBox.getChildren()) {
        if (child instanceof RadioButton || child instanceof CheckBox) {
          selectionControl = child;
        }
        if (child instanceof TextField) {
          optionField = (TextField) child;
        }
      }

      int index = optionsList.getChildren().indexOf(node);
      if (index + 1 < optionsList.getChildren().size()) {
        Node nextNode = optionsList.getChildren().get(index + 1);
        if (nextNode instanceof Label) {
          optionErrorLabel = (Label) nextNode;
        }
      }

      if (selectionControl != null && optionField != null && optionErrorLabel != null) {
        optionFieldsMap.computeIfAbsent(moduleNumber, k -> new ArrayList<>()).add(optionField);
        optionErrorLabelsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(optionErrorLabel);
        selectionControlsMap
            .computeIfAbsent(moduleNumber, k -> new ArrayList<>())
            .add(selectionControl);
      }
    }
  }

  private void setupValidationListeners(int moduleNumber) {
    List<TextField> optionFields = optionFieldsMap.get(moduleNumber);
    List<Label> optionErrorLabels = optionErrorLabelsMap.get(moduleNumber);
    if (optionFields != null) {
      for (int i = 0; i < optionFields.size(); i++) {
        TextField optionField = optionFields.get(i);
        Label optionErrorLabel = optionErrorLabels.get(i);

        optionField
            .textProperty()
            .addListener(
                (obs, old, newText) -> {
                  if (newText.length() >= MIN_OPTION_LENGTH) {
                    updateErrorDisplay(optionField, optionErrorLabel, false, null);
                  }
                });

        optionField
            .focusedProperty()
            .addListener(
                (obs, wasFocused, isNowFocused) -> {
                  validationSupport.registerValidator(
                      optionField,
                      false,
                      Validator.createPredicateValidator(
                          value -> {
                            if (value instanceof String) {
                              String strValue = (String) value;
                              return !strValue.trim().isEmpty()
                                  && strValue.length() >= MIN_OPTION_LENGTH;
                            }
                            return false;
                          },
                          "Por favor, insira uma opção válida, com pelo menos 1 caracter"));
                });
      }
    }
  }

  private void updateErrorDisplay(
      Control field, Label errorLabel, boolean isError, String message) {
    field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, isError);
    errorLabel.setText(isError ? message : "");
    errorLabel.setVisible(isError);
    errorLabel.setManaged(isError);
    if (isError) {
      field.getStyleClass().add("error-field");
    } else {
      field.getStyleClass().remove("error-field");
    }
  }

  public boolean validateFields() {
    boolean isAllValid = true;

    for (Integer moduleNumber : optionFieldsMap.keySet()) {
      List<TextField> optionFields = optionFieldsMap.get(moduleNumber);
      List<Label> optionErrorLabels = optionErrorLabelsMap.get(moduleNumber);

      for (int i = 0; i < optionFields.size(); i++) {

        boolean isFieldValid =
            optionFields.get(i).getText() != null
                && optionFields.get(i).getText().trim().length() >= MIN_OPTION_LENGTH;

        if (!isFieldValid) {
          updateErrorDisplay(
              optionFields.get(i),
              optionErrorLabels.get(i),
              true,
              "Por favor, insira uma opção válida, com pelo menos 1 caracter");
          isAllValid = false;
        } else {
          updateErrorDisplay(optionFields.get(i), optionErrorLabels.get(i), false, null);
        }
      }
    }

    if (!isAllValid) {
      ErrorNotification errorNotification =
          new ErrorNotification(parentContainer, "Preencha todos os campos corretamente");

      errorNotification.show();
    }

    return isAllValid;
  }

  public List<String> getSelectedOptions() {
    List<String> selectedOptions = new ArrayList<>();

    for (Integer moduleNumber : optionFieldsMap.keySet()) {
      List<TextField> optionFields = optionFieldsMap.get(moduleNumber);
      List<Node> selectionControls = selectionControlsMap.get(moduleNumber);

      for (int i = 0; i < optionFields.size(); i++) {
        Node selectionControl = selectionControls.get(i);

        if (selectionControl instanceof RadioButton) {
          RadioButton radio = (RadioButton) selectionControl;
          if (radio.isSelected()) {
            selectedOptions.add(optionFields.get(i).getText());
          }
        } else if (selectionControl instanceof CheckBox) {
          CheckBox checkbox = (CheckBox) selectionControl;
          if (checkbox.isSelected()) {
            selectedOptions.add(optionFields.get(i).getText());
          }
        }
      }
    }

    return selectedOptions;
  }

  public int getOptionFieldsCount() {
    return optionFieldsMap.values().stream().mapToInt(List::size).sum();
  }

  public int getSelectionControlsCount() {
    return selectionControlsMap.values().stream().mapToInt(List::size).sum();
  }

  public int getValidatedOptionFieldsCount() {
    return (int)
        optionFieldsMap.values().stream()
            .flatMap(List::stream)
            .filter(
                field ->
                    field.getText() != null && field.getText().trim().length() >= MIN_OPTION_LENGTH)
            .count();
  }

  public int getSelectedOptionsCount() {
    return (int)
        selectionControlsMap.values().stream()
            .flatMap(List::stream)
            .filter(
                control -> {
                  if (control instanceof RadioButton) {
                    return ((RadioButton) control).isSelected();
                  } else if (control instanceof CheckBox) {
                    return ((CheckBox) control).isSelected();
                  }
                  return false;
                })
            .count();
  }

  public List<Map<String, Object>> getOptionsData(VBox container) {
    List<Map<String, Object>> options = new ArrayList<>();

    VBox optionsContainer = null;
    for (Node node : container.getChildren()) {
      if (node instanceof Label && ((Label) node).getText().contains("Opções")) {
        int index = container.getChildren().indexOf(node);
        if (index + 1 < container.getChildren().size()
            && container.getChildren().get(index + 1) instanceof VBox) {
          optionsContainer = (VBox) container.getChildren().get(index + 1);
          break;
        }
      }
    }

    if (optionsContainer != null) {
      for (Node node : optionsContainer.getChildren()) {
        if (node instanceof HBox) {
          HBox optionBox = (HBox) node;
          Node control = optionBox.getChildren().get(0);
          TextField textField = (TextField) optionBox.getChildren().get(1);

          if (control instanceof RadioButton && textField != null) {
            RadioButton radio = (RadioButton) control;
            String text = textField.getText();

            if (text != null && !text.trim().isEmpty()) {
              Map<String, Object> option = new HashMap<>();
              option.put("optionText", text);
              option.put("isSelected", radio.isSelected());
              options.add(option);
            }
          }
        }
      }
    }

    return options;
  }
}
