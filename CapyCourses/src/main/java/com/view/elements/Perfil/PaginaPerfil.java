package com.view.elements.Perfil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.view.Modo;
import com.view.elements.MenuEstudante.Menu;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PaginaPerfil implements Initializable {
    @FXML
    private ScrollPane mainContent;
    @FXML
    private VBox sideMenu;
    @FXML
    private VBox container;
    @FXML
    private ImageView sunIcon;
    @FXML
    private ImageView moonIcon;
    @FXML
    private StackPane thumbContainer;
    @FXML
    private Rectangle background;
    @FXML
    private HBox toggleButtonHBox;
    @FXML
    private Button themeToggleBtn;
    @FXML
    private StackPane toggleButtonStackPane;
    @FXML
    private GridPane content;
    @FXML
    private Button perfilBtn;
    @FXML
    private Button segurityBtn;
    @FXML
    private Button preferencesBtn;
    @FXML
    private Button privacyBtn;

    public void initialize(URL location, ResourceBundle resources) {
        changeMode();
        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();

        loadMenu();
        ProfileCard profileCard = new ProfileCard();
        container.getChildren().add(profileCard.createProfileCard());


        segurityBtn.getStyleClass().add("outline-button-not-seletion");
        preferencesBtn.getStyleClass().add("outline-button-not-seletion");
        privacyBtn.getStyleClass().add("outline-button-not-seletion");
        perfilBtn.getStyleClass().add("outline-button-seletion");

        perfilBtn.setOnAction(e -> {
            segurityBtn.getStyleClass().clear();
            preferencesBtn.getStyleClass().clear();
            privacyBtn.getStyleClass().clear();

            segurityBtn.getStyleClass().add("outline-button-not-seletion");
            preferencesBtn.getStyleClass().add("outline-button-not-seletion");
            privacyBtn.getStyleClass().add("outline-button-not-seletion");

            perfilBtn.getStyleClass().clear();
            perfilBtn.getStyleClass().add("outline-button-seletion");

            container.getChildren().clear();
            container.getChildren().add(profileCard.createProfileCard());
        });

        segurityBtn.setOnAction(e -> {
            perfilBtn.getStyleClass().clear();
            preferencesBtn.getStyleClass().clear();
            privacyBtn.getStyleClass().clear();

            perfilBtn.getStyleClass().add("outline-button-not-seletion");
            preferencesBtn.getStyleClass().add("outline-button-not-seletion");
            privacyBtn.getStyleClass().add("outline-button-not-seletion");

            segurityBtn.getStyleClass().clear();
            segurityBtn.getStyleClass().add("outline-button-seletion");

            container.getChildren().clear();
            container.getChildren().add(new SecuritySection().createSecuritySection());
        });

        preferencesBtn.setOnAction(e -> {
            segurityBtn.getStyleClass().clear();
            perfilBtn.getStyleClass().clear();
            privacyBtn.getStyleClass().clear();

            segurityBtn.getStyleClass().add("outline-button-not-seletion");
            perfilBtn.getStyleClass().add("outline-button-not-seletion");
            privacyBtn.getStyleClass().add("outline-button-not-seletion");

            preferencesBtn.getStyleClass().clear();
            preferencesBtn.getStyleClass().add("outline-button-seletion");

            container.getChildren().clear();
            container.getChildren().add(new PreferencesSection().createPreferencesSection());
        });

        privacyBtn.setOnAction(e -> {
            segurityBtn.getStyleClass().clear();
            preferencesBtn.getStyleClass().clear();
            perfilBtn.getStyleClass().clear();

            segurityBtn.getStyleClass().add("outline-button-not-seletion");
            preferencesBtn.getStyleClass().add("outline-button-not-seletion");
            perfilBtn.getStyleClass().add("outline-button-not-seletion");

            privacyBtn.getStyleClass().clear();
            privacyBtn.getStyleClass().add("outline-button-seletion");

            System.out.println("Privacy "+privacyBtn.getStyleClass());
            container.getChildren().clear();
          //  container.getChildren().add(new PrivacySection());
        });

    }

    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/menu.fxml"));
            VBox menu = loader.load();
            Menu menuController = loader.getController();
            menuController.setCurrentPage("/com/elements/perfil");
            menuController.configureMenu();
            sideMenu.getChildren().add(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeMode() {
        content.getStylesheets().clear();
        if (!Modo.getInstance().getModo()) {
            background.getStyleClass().add("dark");
            content.getStylesheets()
                    .add(getClass().getResource("/com/elements/stylePerfil/ligth/style.css").toExternalForm());
        } else {
            background.getStyleClass().remove("dark");
            content.getStylesheets()
                    .add(getClass().getResource("/com/elements/stylePerfil/dark/style.css").toExternalForm());
        }
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

        private void toggle() {
        // Modo.getInstance().getModo() == dark
        Modo.getInstance().setModo();

        TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
        thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
        thumbTransition.play();

        FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
        fillTransition.setFromValue(!Modo.getInstance().getModo() ? Color.web("#FFA500") : Color.web("#4169E1"));
        fillTransition.setToValue(!Modo.getInstance().getModo() ? Color.web("#4169E1") : Color.web("#FFA500"));
        fillTransition.play();

        changeMode();

        updateIconsVisibility();
    }

    private void updateIconsVisibility() {
        sunIcon.setVisible(Modo.getInstance().getModo());
        moonIcon.setVisible(!Modo.getInstance().getModo());
    }
}