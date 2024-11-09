package com.view.login_cadastro;

import java.net.URL;
import java.util.ResourceBundle;
import com.UserSession;
import com.controller.login_cadastro.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.css.PseudoClass;

public class Login extends BaseLoginCadastro implements Initializable {
    @FXML private VBox leftSection;
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private Label userErrorLabel;
    @FXML private Label passwordErrorLabel;

    private final ValidationSupport validationSupport = new ValidationSupport();
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final int MIN_PASSWORD_LENGTH = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();
        setupValidators();
        setupInitialState();
    }

    private void setupValidators() {
        validationSupport.registerValidator(user, true, 
            Validator.createPredicateValidator(value -> {
                if (value instanceof String) {
                    String strValue = (String) value;
                    boolean isValid = !strValue.trim().isEmpty() && strValue.matches(EMAIL_REGEX);
                    String message = isValid ? null : "Por favor, insira um email válido";
                    if (!strValue.isEmpty()) {
                        updateErrorDisplay(user, userErrorLabel, isValid, message);
                    }
                    return isValid;
                }
                return false;
            }, "Por favor, insira um email válido")
        );
    
        validationSupport.registerValidator(password, true,
            Validator.createPredicateValidator(value -> {
                if (value instanceof String) {
                    String strValue = (String) value;
                    boolean isValid = !strValue.trim().isEmpty() && strValue.length() >= MIN_PASSWORD_LENGTH;
                    String message = isValid ? null : "A senha deve ter pelo menos " + MIN_PASSWORD_LENGTH + " caracteres";
    
                    if (!strValue.isEmpty()) {
                        updateErrorDisplay(password, passwordErrorLabel, isValid, message);
                    }
                    return isValid;
                }
                return false;
            }, "A senha deve ter pelo menos " + MIN_PASSWORD_LENGTH + " caracteres")
        );
    }
    
    private void setupInitialState() {
        userErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        
        user.textProperty().addListener((obs, oldVal, newVal) -> {
            user.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
            userErrorLabel.setVisible(false);
        });
        
        password.textProperty().addListener((obs, oldVal, newVal) -> {
            password.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
            passwordErrorLabel.setVisible(false);
        });
    }
    

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
        errorLabel.setText(message);
        errorLabel.setVisible(!isValid);
    }

    @FXML
    private void register() {
        Stage stage = (Stage) leftSection.getScene().getWindow();
        super.redirectTo("/com/login_cadastro/paginaCadastro.fxml", stage);
    }

    @FXML
    private void logar() {
        // Força a validação de todos os campos
        validationSupport.setValidationDecorator(new GraphicValidationDecoration());
        
        if (!validateFields()) {
            return;
        }

        LoginController plc = new LoginController();
        UserSession.getInstance().setUserEmail(user.getText());
        String isCheck = plc.isCheck(user.getText(), password.getText());
        Stage stage = (Stage) leftSection.getScene().getWindow();

        switch (isCheck) {
            case "true" -> super.redirectTo("/com/professor/paginaCadastroCurso.fxml", stage);
            case "incomplete student" -> super.redirectTo("/com/login_cadastro/paginaCadastroStudent.fxml", stage);
            case "incomplete teacher" -> super.redirectTo("/com/login_cadastro/paginaCadastroTeacher.fxml", stage);
            default -> {
                UserSession.getInstance().clearSession();
                showErrorAlert("Erro de Login", "Credenciais inválidas", 
                             "Por favor, verifique seu email e senha.");
            }
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        
        if (user.getText().trim().isEmpty() || !user.getText().matches(EMAIL_REGEX)) {
            updateErrorDisplay(user, userErrorLabel, false, 
                             "Por favor, insira um email válido");
            isValid = false;
        }
        
        if (password.getText().trim().isEmpty() || 
            password.getText().length() < MIN_PASSWORD_LENGTH) {
            updateErrorDisplay(password, passwordErrorLabel, false, 
                             "A senha deve ter pelo menos " + MIN_PASSWORD_LENGTH + " caracteres");
            isValid = false;
        }
        
        return isValid;
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}