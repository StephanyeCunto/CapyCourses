package com.view.elements.Settings;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.image.*;

public class PaginaSettingsView implements Initializable {
    @FXML
    private VBox sideMenu;
    @FXML
    private HBox searchBar;
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
    private GridPane container;
@FXML
private VBox settingsBox;
    @SuppressWarnings("unused")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      /*   Platform.runLater(() -> {
            LoadForumView loadForum = new LoadForumView();
            forumContainer.getChildren().clear();
            forumContainer.getChildren().add(loadForum.loadForum("todos"));

            selectionTodos.setOnMouseClicked(e -> {
                loadClass("todos");
                forumContainer.getChildren().clear();
                forumContainer.getChildren().add(loadForum.loadForum("todos"));
            });

            selectionParticipated.setOnMouseClicked(e -> {
                loadClass("participated");
                forumContainer.getChildren().clear();
                forumContainer.getChildren().add(loadForum.loadForum("myForum"));
            });
        });*/

        changeMode();
        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();
        loadMenu();

        settingsBox.getChildren().add(new LoadSettingsPerfil().settingsPerfil());
    }

    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/estudante/menu/menu.fxml"));
            VBox menu = loader.load();
            Menu menuController = loader.getController();
            menuController.setCurrentPage("/com/elements/settings.fxml");
            menuController.configureMenu();
            sideMenu.getChildren().add(menu);
        } catch (IOException e) {
            e.printStackTrace();
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

    private void changeMode() {
        container.getStylesheets().clear();
        if (!Modo.getInstance().getModo()) {
            background.getStyleClass().add("dark");
            container.getStylesheets()
                    .add(getClass().getResource("/com/elements/forum/style/ligth/style.css").toExternalForm());
        } else {
            background.getStyleClass().remove("dark");
            container.getStylesheets()
                    .add(getClass().getResource("/com/elements/forum/style/dark/style.css").toExternalForm());
        }
    }

    private void loadClass(String selection) {
       /*  selectionTodos.getStyleClass().removeAll("outline-button-seletion", "outline-button-not-seletion");
        selectionParticipated.getStyleClass().removeAll("outline-button-seletion", "outline-button-not-seletion");

        if (selection.equals("todos")) {
            selectionTodos.getStyleClass().add("outline-button-seletion");
            selectionParticipated.getStyleClass().add("outline-button-not-seletion");

        } else if (selection.equals("favorites")) {
            selectionParticipated.getStyleClass().add("outline-button-not-seletion");
            selectionTodos.getStyleClass().add("outline-button-not-seletion");
        } else {
            selectionParticipated.getStyleClass().add("outline-button-seletion");
            selectionTodos.getStyleClass().add("outline-button-not-seletion");
        }*/
    }
}