package com.view.professor.valid;

import com.view.login_cadastro.elements.ErrorNotification;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class CursoBasicsValid {
  private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
  private static final ValidationSupport validationSupport = new ValidationSupport();

  @FXML private TextField titleCourse;
  @FXML private Label coutTitleCourse;
  @FXML private TextArea descritionCourse;
  @FXML private ComboBox<String> categoryCourse;
  @FXML private ComboBox<String> levelCourse;
  @FXML private Label basicsTitleErrorLabel;
  @FXML private Label descritionBasicsErrorLabel;
  @FXML private Label categoryCourseErrorLabel;
  @FXML private Label levelCourseErrorLabel;

  private int MIN_TITLE_LENGTH = 5;

  private int MIN_DESCRIPTION_LENGTH = 10;

  private GridPane parentContainer;

  public void setParentContainer(GridPane parentContainer) {
    this.parentContainer = parentContainer;
  }

  public void setupInitialStateBasics(
      TextField titleCourse,
      TextArea descritionCourse,
      ComboBox<String> categoryCourse,
      ComboBox<String> levelCourse,
      Label basicsTitleErrorLabel,
      Label descritionBasicsErrorLabel,
      Label categoryCourseErrorLabel,
      Label levelCourseErrorLabel) {
    loadValues(
        titleCourse,
        descritionCourse,
        categoryCourse,
        levelCourse,
        basicsTitleErrorLabel,
        descritionBasicsErrorLabel,
        categoryCourseErrorLabel,
        levelCourseErrorLabel);

    basicsTitleErrorLabel.setVisible(false);
    descritionBasicsErrorLabel.setVisible(false);
    categoryCourseErrorLabel.setVisible(false);
    levelCourseErrorLabel.setVisible(false);

    titleCourse
        .textProperty()
        .addListener(
            (obs, old, newText) -> {
              if (newText.length() >= MIN_TITLE_LENGTH) {
                updateErrorDisplay(titleCourse, basicsTitleErrorLabel, false, null);
              }
            });

    titleCourse
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              validationSupport.registerValidator(
                  titleCourse,
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

    descritionCourse
        .textProperty()
        .addListener(
            (obs, old, newText) -> {
              if (newText.length() >= MIN_DESCRIPTION_LENGTH) {
                updateErrorDisplay(descritionCourse, descritionBasicsErrorLabel, false, null);
              }
            });

    descritionCourse
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              validationSupport.registerValidator(
                  descritionCourse,
                  false,
                  Validator.createPredicateValidator(
                      value -> {
                        if (value instanceof String) {
                          String strValue = (String) value;
                          return !strValue.trim().isEmpty() && strValue.length() >= 10;
                        }
                        return false;
                      },
                      "Por favor, insira uma descrição válida, com pelo menos 10 caracteres"));
            });

    categoryCourse
        .valueProperty()
        .addListener(
            (obs, old, newValue) -> {
              if (newValue != null && !newValue.isEmpty()) {
                updateErrorDisplay(categoryCourse, categoryCourseErrorLabel, false, null);
              }
            });

    categoryCourse
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              validationSupport.registerValidator(
                  categoryCourse,
                  false,
                  Validator.createPredicateValidator(
                      value -> {
                        if (value instanceof String) {
                          String strValue = (String) value;
                          return strValue != "null";
                        }
                        return false;
                      },
                      "Por favor, selecione uma categoria"));
            });

    levelCourse
        .valueProperty()
        .addListener(
            (obs, old, newValue) -> {
              if (newValue != null && !newValue.isEmpty()) {
                updateErrorDisplay(levelCourse, levelCourseErrorLabel, false, null);
              }
            });

    levelCourse
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              validationSupport.registerValidator(
                  levelCourse,
                  false,
                  Validator.createPredicateValidator(
                      value -> {
                        if (value instanceof String) {
                          String strValue = (String) value;
                          return strValue != "null";
                        }
                        return false;
                      },
                      "Por favor, selecione um nível"));
            });
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

  private void loadValues(
      TextField titleCourse,
      TextArea descritionCourse,
      ComboBox<String> categoryCourse,
      ComboBox<String> levelCourse,
      Label basicsTitleErrorLabel,
      Label descritionBasicsErrorLabel,
      Label categoryCourseErrorLabel,
      Label levelCourseErrorLabel) {
    this.titleCourse = titleCourse;
    this.descritionCourse = descritionCourse;
    this.categoryCourse = categoryCourse;
    this.levelCourse = levelCourse;
    this.basicsTitleErrorLabel = basicsTitleErrorLabel;
    this.descritionBasicsErrorLabel = descritionBasicsErrorLabel;
    this.categoryCourseErrorLabel = categoryCourseErrorLabel;
    this.levelCourseErrorLabel = levelCourseErrorLabel;
  }

  public boolean validateFields() {
    boolean isValid = true;
    if (titleCourse.getText().length() < MIN_TITLE_LENGTH) {
      updateErrorDisplay(
          titleCourse,
          basicsTitleErrorLabel,
          true,
          "Por favor, insira um título válido, entre 5 e 100 caracteres");
      isValid = false;
    }
    if (descritionCourse.getText().length() < MIN_DESCRIPTION_LENGTH) {
      updateErrorDisplay(
          descritionCourse,
          descritionBasicsErrorLabel,
          true,
          "Por favor, insira uma descrição válida, com pelo menos 10 caracteres");
      isValid = false;
    }
    if (categoryCourse.getValue() == null || categoryCourse.getValue().trim().isEmpty()) {
      updateErrorDisplay(
          categoryCourse, categoryCourseErrorLabel, true, "Por favor, selecione uma categoria");
      isValid = false;
    }
    if (levelCourse.getValue() == null || levelCourse.getValue().trim().isEmpty()) {
      updateErrorDisplay(levelCourse, levelCourseErrorLabel, true, "Por favor, selecione um nível");
      isValid = false;
    }

    if (!isValid) {
      ErrorNotification errorNotification =
          new ErrorNotification(parentContainer, "Preencha todos os campos corretamente");
      errorNotification.show();
    }

    return isValid;
  }

  public boolean isTitleValid() {
    return titleCourse.getText() != null
        && titleCourse.getText().length() >= MIN_TITLE_LENGTH
        && titleCourse.getText().length() <= 100;
  }

  public boolean isDescriptionValid() {
    return descritionCourse.getText() != null
        && descritionCourse.getText().length() >= MIN_DESCRIPTION_LENGTH;
  }

  public boolean isCategorySelected() {
    return categoryCourse.getValue() != null
        && !categoryCourse.getValue().trim().isEmpty()
        && !categoryCourse.getValue().equals("null");
  }

  public boolean isLevelSelected() {
    return levelCourse.getValue() != null
        && !levelCourse.getValue().trim().isEmpty()
        && !levelCourse.getValue().equals("null");
  }
}
