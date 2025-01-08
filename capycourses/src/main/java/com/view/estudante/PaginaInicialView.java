package com.view.estudante;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.image.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.singleton.UserSession;
import com.view.Modo;
import com.view.elements.Carousel.Carousel;
import com.view.elements.Courses.LoadCourses;
import com.view.elements.MenuEstudante.Menu;

public class PaginaInicialView implements Initializable {
    @FXML
    private VBox sideMenu;
    @FXML
    private HBox searchBar;
    @FXML
    private HBox featuredCourseCarousel;
    @FXML
    private VBox featuredCourseSection;
    @FXML
    private VBox courseContainer;
    @FXML
    private VBox carouselCourse;
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

        loadCarousel();
        loadMenu();
        if (!UserSession.getInstance().getStarted()) {
            loadEffect();
        }
        
        loadCourses();

        // System.out.println(loadCourses.loadMyCourses(student));
    }

    private void loadCourses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/courses.fxml"));
            VBox courseList = loader.load();
            LoadCourses course = loader.getController();
            course.loadCoursesNotStarted();
            courseContainer.getChildren().add(courseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/estudante/menu/menu.fxml"));
            VBox menu = loader.load();
            Menu menuController = loader.getController();
            menuController.setCurrentPage("/com/estudante/paginaInicial");
            menuController.configureMenu();
            sideMenu.getChildren().add(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCarousel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/estudante/carousel/carousel.fxml"));
            VBox carouselBox = loader.load();
            Carousel carousel = loader.getController();
            carousel.loadCarousel();
            featuredCourseCarousel.getChildren().add(carouselBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEffect() {
        Node[] nodes = { sideMenu, searchBar, carouselCourse, courseContainer };
        double[] distances = { -220, -300, -1000, -1000 };
        int[] delays = { 100, 300, 500, 700 };
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