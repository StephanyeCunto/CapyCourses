package com.view.login_cadastro.cadastro;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import com.controller.login_cadastro.CadastroController;
import com.view.Modo;
import com.view.login_cadastro.BaseLoginCadastro;
import com.view.login_cadastro.cadastro.valid.CadastroValid;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.scene.image.*;

import java.awt.Toolkit;

public class Cadastro extends BaseLoginCadastro implements Initializable {
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldPasswordConfirm;
    @FXML
    private ToggleGroup userType;
    @FXML
    private RadioButton radioButtonStudent;
    @FXML
    private RadioButton radioButtonTeacher;

    @FXML
    private Hyperlink logar;
    @FXML
    private VBox leftSection;
    @FXML
    private Label userNameErrorLabel;
    @FXML
    private Label userEmailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label passwordConfirmErrorLabel;
    @FXML
    private Rectangle background;
    @FXML
    private Circle thumb;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private ImageView sunIcon;
    @FXML
    private ImageView moonIcon;
    @FXML
    private StackPane thumbContainer;
    @FXML
    private HBox toggleButtonHBox;
    @FXML
    private StackPane toggleButtonStackPane;
    private boolean isLightMode = Modo.getInstance().getModo();
    private String typeUser;
    private final CadastroValid validator = new CadastroValid();

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();

        validator.setupInitialState(textFieldName, textFieldEmail, passwordFieldPassword, passwordFieldPasswordConfirm,
                userNameErrorLabel, userEmailErrorLabel, passwordErrorLabel, passwordConfirmErrorLabel);

        logar.setOnAction(event -> {
            redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
        });

        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();
    }

    @FXML
    private void changeModeStyle() {
        super.changeMode(false);
    }

    @FXML
    public void register() {
        validator.getValidationSupport().setValidationDecorator(new GraphicValidationDecoration());
        if (!validator.validateFields()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        Stage stage = (Stage) leftSection.getScene().getWindow();

        if (radioButtonStudent.isSelected()) {
            typeUser = "student";
            super.redirectTo("/com/login_cadastro/paginaCadastroStudent.fxml", stage);
        } else if (radioButtonTeacher.isSelected()) {
            typeUser = "teacher";
            super.redirectTo("/com/login_cadastro/paginaCadastroTeacher.fxml", stage);
        }

        LocalDateTime date = LocalDateTime.now();
        new CadastroController(textFieldName.getText(), textFieldEmail.getText(),
                passwordFieldPasswordConfirm.getText(), date, typeUser);
    }

    private void toggle() {
        TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer); 
        thumbTransition.setToX(isLightMode ? 12.0 : -12.0);
        thumbTransition.play();
    
        FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
        fillTransition.setFromValue(isLightMode ? Color.web("#FFA500") : Color.web("#4169E1"));
        fillTransition.setToValue(isLightMode ? Color.web("#4169E1") : Color.web("#FFA500"));
        fillTransition.play();
    
        isLightMode = !isLightMode;
    
        if (isLightMode) {
            changeModeStyle();
            background.getStyleClass().remove("dark");
        } else {
            changeModeStyle();
            background.getStyleClass().add("dark");
        }
        
        updateIconsVisibility(); 
    }

    public boolean isLightMode() {
        return isLightMode;
    }

    private void updateIconsVisibility() {
        sunIcon.setVisible(isLightMode);
        moonIcon.setVisible(!isLightMode);
    }

    private void toggleInitialize(){
        if(!Modo.getInstance().getModo()){ 
            background.getStyleClass().add("dark");
            sunIcon.setVisible(Modo.getInstance().getModo());
            moonIcon.setVisible(!Modo.getInstance().getModo());
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer); 
            thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
            thumbTransition.play();
        }else{
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer); 
            thumbTransition.setToX(Modo.getInstance().getModo() ? -12.0 : 12.0);
            thumbTransition.play();
            background.getStyleClass().remove("dark");
            sunIcon.setVisible(Modo.getInstance().getModo());
            moonIcon.setVisible(!Modo.getInstance().getModo());
        }
    }
}