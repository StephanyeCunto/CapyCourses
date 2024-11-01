package com.view;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.controller.PaginaCadastroController;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PaginaCadastroView implements Initializable{
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldEmail;
    @FXML private PasswordField passworFieldPassword;
    @FXML private PasswordField passworFieldPasswordConfirm;
    @FXML private CheckBox termsCheckBox;
    @FXML private ToggleGroup userType;
    @FXML private RadioButton radioButtonStudent;
    @FXML private RadioButton radioButtonTeacher;
    @FXML private Hyperlink logar;
    private String typeUser;
    
    public void register(){    
        if (radioButtonStudent.isSelected()) {
           typeUser="student";
        } else if (radioButtonTeacher.isSelected()) {
            typeUser="teacher";
        }
        LocalDateTime date = LocalDateTime.now();
        new PaginaCadastroController(textFieldName.getText(),textFieldEmail.getText(),passworFieldPasswordConfirm.getText(),date,typeUser);

        redirectTo("/com/paginaLogin.fxml");
    }



    private void redirectTo(String page) {
        try {
            Stage stage = (Stage) termsCheckBox.getScene().getWindow(); 
            Parent root = FXMLLoader.load(getClass().getResource(page)); 
            stage.setScene(new Scene(root)); 
            stage.setMaximized(true);
            stage.show(); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }


    @FXML private GridPane mainRegisterPane;
    @FXML private VBox leftSection;
    @FXML private VBox rightSection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAnimation();
        
        logar.setOnAction(event->{
            redirectTo("/com/paginaLogin.fxml");
        });
    }

    private void loadAnimation(){
        mainRegisterPane.setTranslateX(-1000);
        mainRegisterPane.setOpacity(0);
        leftSection.setTranslateX(-500);
        leftSection.setOpacity(0);
        rightSection.setTranslateX(-500);
        rightSection.setOpacity(0);

        animateNodeWithDelay(mainRegisterPane, 1000, 100);
        animateNodeWithDelay(leftSection, 500, 300);
        animateNodeWithDelay(rightSection, 500, 500);
    }

    private void animateNode(Node node, double distance, int delay) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(2000), node);
        translate.setDelay(Duration.millis(delay));
        translate.setFromX(-distance);
        translate.setToX(0);
        translate.setInterpolator(Interpolator.EASE_OUT);
        FadeTransition fade = new FadeTransition(Duration.millis(1200), node);
        fade.setDelay(Duration.millis(delay));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);
        translate.play();
        fade.play();
    }

    private void animateNodeWithDelay(Node node, double distance, int delay) {
        animateNode(node, distance, delay);
    }
}
