package com.view.utility;

import org.controlsfx.validation.*;

import javafx.css.PseudoClass;
import javafx.scene.control.*;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidUtility {
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    private static final ValidationSupport validationSupport = new ValidationSupport();

    public static void checkWithValidationSupport(TextField field, String regex) {
        field.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            validationSupport.registerValidator( field,false,
                Validator.createPredicateValidator( value -> value instanceof String && ((String) value).matches(regex), "")
            );
        });
    }

    public static void checkWithLabel(TextField field, Label errorLabel, String regex, String message) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = newValue.matches(regex);
            updateErrorDisplay(field, errorLabel, isValid, message);
        });
    }

    public static boolean isCheck(TextField field,Label errorLabel, String regex, String message) {
        if (field.getText().matches(regex)) return true;
        updateErrorDisplay(field, errorLabel, false, message);
        checkWithLabel(field,errorLabel,regex,message);
        return false;    
    }

    public static boolean areEqual(PasswordField field1, PasswordField field2, Label errorLabel, String message) {
        if(field1.getText().equals(field2.getText())) return true;
        updateErrorDisplay(field2, errorLabel, false, message);
        return false;
    }

    private static void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
        errorLabel.setText(isValid ? "" : message);
        errorLabel.setVisible(!isValid);
        errorLabel.setManaged(!isValid);
    }

}
