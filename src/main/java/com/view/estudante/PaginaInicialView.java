package com.view.estudante;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.view.elements.Carousel;
import com.view.elements.LoadCourses;
import com.view.elements.Menu;

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

    private static final Duration INITIAL_ANIMATION_DURATION = Duration.millis(1000);
    private static final Interpolator EASE_IN_OUT = Interpolator.SPLINE(0.42, 0.0, 0.58, 1.0);
    private static final Interpolator SMOOTH_STEP = Interpolator.SPLINE(0.4, 0, 0.2, 1);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCarousel();
        loadCourses();
        loadMenu();
        loadEffect();
    }

    private void loadCourses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/courses.fxml"));
            VBox courseList = loader.load();
            LoadCourses course = loader.getController();
            course.loadCourses();
            courseContainer.getChildren().add(courseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/menu.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/carousel.fxml"));
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
}