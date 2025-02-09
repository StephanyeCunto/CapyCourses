package com.view.professor;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.singleton.UserSession;
import com.view.Modo;
import com.view.elements.MenuProfessor.Menu;
import com.view.professor.LoadCourses;

public class PaginaInicialView implements Initializable {
    @FXML
    private VBox sideMenu;
    @FXML
    private HBox searchBar;
    @FXML
    private VBox courseContainer;
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
    private Button addButton;

    private static final Duration INITIAL_ANIMATION_DURATION = Duration.millis(1000);
    private static final Interpolator EASE_IN_OUT = Interpolator.SPLINE(0.42, 0.0, 0.58, 1.0);
    private static final Interpolator SMOOTH_STEP = Interpolator.SPLINE(0.4, 0, 0.2, 1);

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeMode();
        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();
        
        loadCourses();

        loadMenu();
        if (!UserSession.getInstance().getStarted()) {
            loadEffect();
        }

        addButton.getStyleClass().add("outline-button-not-seletion");

        addButton.setOnMouseEntered(event -> {
            addButton.setText("Criar Curso");
            addButton.getStyleClass().clear();
            addButton.getStyleClass().add("outline-button-not-seletion");

            javafx.animation.Timeline timeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(addButton.prefWidthProperty(), 40)),
            new javafx.animation.KeyFrame(Duration.millis(200), new javafx.animation.KeyValue(addButton.prefWidthProperty(), 120))
            );
            timeline.play();
        });
        
        addButton.setOnMouseExited(event -> {
            addButton.getStyleClass().clear();
            addButton.setText("+");
            addButton.getStyleClass().add("outline-button-not-seletion");

            javafx.animation.Timeline timeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(addButton.prefWidthProperty(), 120)),
            new javafx.animation.KeyFrame(Duration.millis(200), new javafx.animation.KeyValue(addButton.prefWidthProperty(), 40))
            );
            timeline.play();
        });

        addButton.setOnMouseClicked(event->redirectTo("/com/professor/paginaCadastroCurso") );

    }

      private void redirectTo(String pageNext) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(pageNext));
            Image icon = new Image("/capyCourses 012.png");
            Stage stage = (Stage) addButton.getScene().getWindow();
            Scene currentScene = stage.getScene();
            Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());
            stage.setScene(newScene);
            stage.getIcons().add(icon);
            stage.setTitle("CapyCourses");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCourses(){
        try {
            courseContainer.getChildren().clear();
            LoadCourses loadCourses = new LoadCourses();
            GridPane coursesGrid = loadCourses.loadCoursesStarted();
            courseContainer.getChildren().add(coursesGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/professor/menu.fxml"));
            VBox menu = loader.load();
            Menu menuController = loader.getController();
            menuController.setCurrentPage("/com/professor/paginaCursos");
            menuController.configureMenu();
            sideMenu.getChildren().add(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEffect() {
        Node[] nodes = { sideMenu, searchBar, courseContainer };
        double[] distances = { -220, -300,  -1000 };
        int[] delays = { 100, 300,  700 };
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].setTranslateX(distances[i]);
            nodes[i].setOpacity(0);
        }
        for (int i = 0; i < nodes.length; i++) {
            animateInitialElement(nodes[i], distances[i], delays[i]);
        }
    }

    private void animateInitialElement(Node node, double distance, int delay) {
        TranslateTransition translate = new TranslateTransition(INITIAL_ANIMATION_DURATION, node);
        translate.setDelay(Duration.millis(delay));
        translate.setFromX(distance);
        translate.setToX(0);
        translate.setInterpolator(SMOOTH_STEP);
        FadeTransition fade = new FadeTransition(INITIAL_ANIMATION_DURATION, node);
        fade.setDelay(Duration.millis(delay));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(EASE_IN_OUT);
        new ParallelTransition(translate, fade).play();
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
                    .add(getClass().getResource("/com/estudante/paginaInicial/style/ligth/style.css").toExternalForm());
        } else {
            background.getStyleClass().remove("dark");
            container.getStylesheets()
                    .add(getClass().getResource("/com/estudante/paginaInicial/style/dark/style.css").toExternalForm());
        }
    }
}