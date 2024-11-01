package com.view;

import java.net.URL;
import java.util.ResourceBundle;
import com.controller.PaginaLoginController;
import com.UserSession;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PaginaLoginView implements Initializable {
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private GridPane mainLoginPane;
    @FXML private VBox leftSection;
    @FXML private VBox rightSection;

    @FXML
    private void logar() {
        PaginaLoginController plc = new PaginaLoginController();
        if(plc.isCheck(user.getText(), password.getText())) {
            UserSession.getInstance().setUserEmail(user.getText());
            redirectToInitialPage();
        }
    }

    private void redirectToInitialPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/paginaInicial.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) user.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void register() {
        redirectTo("/com/paginaCadastro.fxml");
    }

    private void redirectTo(String page) {
        try {
            Stage stage = (Stage) user.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(page));
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainLoginPane.setTranslateX(-1000);
        mainLoginPane.setOpacity(0);
        leftSection.setTranslateX(-500);
        leftSection.setOpacity(0);
        rightSection.setTranslateX(-500);
        rightSection.setOpacity(0);

        animateNodeWithDelay(mainLoginPane, 1000, 100);
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