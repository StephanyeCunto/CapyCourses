package com.view.login_cadastro.cadastro;

import java.net.URL;
import java.util.ResourceBundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.controller.login_cadastro.CadastroStudentController;
import com.view.Modo;
import com.view.login_cadastro.BaseLoginCadastro;

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

    private boolean isLightMode = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();

        super.loadComboBox();
        super.loadCalendar();
        super.setupInterestButtons();

        toggleButtonHBox.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();
    }

    @FXML
    private void changeModeStyle() {
        super.changeMode(true);
    }

    public void createStudent() throws ParseException {
        String interests = String.join(". ", super.getSelectedInterests());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(super.getDateInputPopup().getDate());
        new CadastroStudentController(date, textFieldCPF.getText(), Long.parseLong(textFieldPhone.getText()),
                comboBoxEducation.getValue(), interests);

        super.redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
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
            sunIcon.setVisible(!Modo.getInstance().getModo());
            moonIcon.setVisible(Modo.getInstance().getModo());
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer); 
            thumbTransition.setToX(isLightMode ? 12.0 : -12.0);
            thumbTransition.play();
        }else{
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer); 
            thumbTransition.setToX(isLightMode ? -12.0 : 12.0);
            thumbTransition.play();
            background.getStyleClass().remove("dark");
            sunIcon.setVisible(Modo.getInstance().getModo());
            moonIcon.setVisible(!Modo.getInstance().getModo());
        }
    }
}
