package com.view.login_cadastro;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class BaseLoginCadastro {
    @FXML
    private GridPane mainPane;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passworFieldPassword;
    @FXML
    private PasswordField passworFieldPasswordConfirm;
    @FXML
    private CheckBox termsCheckBox;
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
    private VBox rightSection;

    protected void initializeCommon() {
        loadAnimation();
        loadCSS();
    }

    protected void redirectTo(String pageNext, Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(pageNext));
            Scene currentScene = stage.getScene();
            Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());
            stage.setMinWidth(1000); 
            stage.setMinHeight(600);

            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAnimation() {
        mainPane.setTranslateX(-1000);
        mainPane.setOpacity(0);
        leftSection.setTranslateX(-500);
        leftSection.setOpacity(0);
        rightSection.setTranslateX(-500);
        rightSection.setOpacity(0);

        animateNodeWithDelay(mainPane, 1000, 100);
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

    private void loadCSS() {
        mainPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                applyStylesheetBasedOnSize(newScene.getWidth(), newScene.getHeight());
                newScene.widthProperty().addListener((observable, oldWidth, newWidth) -> {
                    applyStylesheetBasedOnSize(newWidth.doubleValue(), newScene.getHeight());
                });
                newScene.heightProperty().addListener((observable, oldHeight, newHeight) -> {
                    applyStylesheetBasedOnSize(newScene.getWidth(), newHeight.doubleValue());
                });
            }
        });
    }

    private void applyStylesheetBasedOnSize(double width, double height) {
        Scene scene = mainPane.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();
            if (width < 925 || height < 500) {
                adjustLayout(false);
                scene.getStylesheets()
                        .add(getClass().getResource("/com/login_cadastro/style/styleSmall.css").toExternalForm());
            } else if (width < 1400 || height < 900) {
                adjustLayout(true);
                scene.getStylesheets()
                        .add(getClass().getResource("/com/login_cadastro/style/styleMedium.css").toExternalForm());
            } else {
                adjustLayout(true);
                scene.getStylesheets()
                        .add(getClass().getResource("/com/login_cadastro/style/styleLarge.css").toExternalForm());
            }
        }
    }

    private void adjustLayout(boolean visible) {
        leftSection.setVisible(visible);
        if (visible) {
            mainPane.getColumnConstraints().get(0).setPercentWidth(50);
            mainPane.getColumnConstraints().get(1).setPercentWidth(50);
        } else {
            mainPane.getColumnConstraints().get(0).setPercentWidth(0);
            mainPane.getColumnConstraints().get(1).setPercentWidth(100);
        }
    }
}