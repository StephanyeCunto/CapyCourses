package com.view.login_cadastro.cadastro;

import java.net.URL;
import java.util.ResourceBundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.UserSession;
import com.controller.login_cadastro.CadastroStudentController;
import com.view.Modo;
import com.view.login_cadastro.BaseLoginCadastro;
import com.view.login_cadastro.cadastro.valid.CadastroSecudarioValid;
import com.view.login_cadastro.elements.ErrorNotification;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CadastroStudent extends BaseLoginCadastro implements Initializable {
    @FXML
    private VBox leftSection;
    @FXML
    private ComboBox<String> comboBoxEducation;
    @FXML
    private FlowPane interestContainer;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private ImageView sunIcon;
    @FXML
    private ImageView moonIcon;
    @FXML
    private HBox toggleButtonHBox;
    @FXML
    private StackPane thumbContainer;
    @FXML
    private Rectangle background;
    @FXML
    private StackPane toggleButtonStackPane;
    @FXML
    private Label cpfErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label educationErrorLabel;

    private boolean isLightMode = Modo.getInstance().getModo();

    private ErrorNotification errorNotification;

    private final CadastroSecudarioValid validator = new CadastroSecudarioValid();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();

        super.loadComboBox();
        super.loadCalendar();
        super.setupInterestButtons();

        validator.setupInitialState(comboBoxEducation,textFieldCPF, textFieldPhone,cpfErrorLabel,educationErrorLabel,phoneErrorLabel);

        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();
        if (UserSession.getInstance().getRegisterIncomplet() == "Student") {
            setupErrorNotification();
            UserSession.getInstance().clearSession();
        }
    }

    @FXML
    private void changeModeStyle() {
        super.changeMode(true);
    }

    public void createStudent() throws ParseException {
        if(validator.validateFields()){
            String interests = String.join(". ", super.getSelectedInterests());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format.parse(super.getDateInputPopup().getDate());
            
            new CadastroStudentController(date, textFieldCPF.getText(), textFieldPhone.getText(),
                    comboBoxEducation.getValue(), interests);
                    UserSession.getInstance().setRegisterIncomplet("false");
            super.redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
        }
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

    private void toggleInitialize() {
        if (!Modo.getInstance().getModo()) {
            background.getStyleClass().add("dark");
            sunIcon.setVisible(Modo.getInstance().getModo());
            moonIcon.setVisible(!Modo.getInstance().getModo());
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
            thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
            thumbTransition.play();
        } else {
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
            thumbTransition.setToX(Modo.getInstance().getModo() ? -12.0 : 12.0);
            thumbTransition.play();
            background.getStyleClass().remove("dark");
            sunIcon.setVisible(Modo.getInstance().getModo());
            moonIcon.setVisible(!Modo.getInstance().getModo());
        }
    }

    private void setupErrorNotification() {
        StackPane root = new StackPane();
        leftSection.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                errorNotification = new ErrorNotification(root, "Cadastro incompleto");
                if (newScene.getRoot() instanceof StackPane) {
                    ((StackPane) newScene.getRoot()).getChildren().add(errorNotification.getContainer());
                } else {
                    root.getChildren().addAll(newScene.getRoot(), errorNotification.getContainer());
                }
                showError();
            }
        });
    }

    private void showError() {
        errorNotification.show();
    }
}
