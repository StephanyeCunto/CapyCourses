package com.view.elements.Perfil.valid;

import org.controlsfx.validation.ValidationSupport;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfileCardValid {
      private static final ValidationSupport validationSupport = new ValidationSupport();
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField telefone;
    @FXML
    private TextField bio;
}
