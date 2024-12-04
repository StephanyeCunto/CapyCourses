package com.view.elements;

import java.util.List;

import com.model.CourseReader;
import com.model.Course.Course;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Carousel {
    @FXML
    private Button rigthButton;
    @FXML
    private VBox rigthContainer;
    @FXML
    private Button leftButton;
    @FXML
    private HBox indicator;

    private List<Course> courses;
    private int currentIndex = 0;
    private Timeline timeline;

    public void loadCarousel() {
        CourseReader reader = new CourseReader();
        courses = reader.readCourses();
        displayCourses();
        setupCarouselControls();
        startAutoCarousel();
    }

    private void startAutoCarousel() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            if (currentIndex < courses.size() - 1) {
                currentIndex++;
            } else {
                currentIndex = 0; 
            }
            displayCourses();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void displayCourses() {
        if (courses.isEmpty()) return;
        rigthContainer.getChildren().clear();
        rigthContainer.setSpacing(20.0);
        rigthContainer.setAlignment(Pos.CENTER);
        VBox courseBox = createCourseBoxCarousel(courses.get(currentIndex));
        TranslateTransition transition2 = new TranslateTransition(Duration.millis(600));
        ParallelTransition transition = new ParallelTransition(transition2);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), courseBox);
        if (currentIndex < previousIndex) {
            slideIn.setFromX(800);
        } else {
            slideIn.setFromX(-800);
        }

        slideIn.setToX(0);
        slideIn.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), courseBox);
        scaleIn.setFromX(0.8);
        scaleIn.setFromY(0.8);
        scaleIn.setToX(1);
        scaleIn.setToY(1);
        scaleIn.setInterpolator(javafx.animation.Interpolator.EASE_OUT);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), courseBox);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setInterpolator(javafx.animation.Interpolator.EASE_IN);
        if (rigthContainer.getChildren().size() > 1) {
            VBox previousBox = (VBox) rigthContainer.getChildren().get(0);
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), previousBox);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
            fadeOut.play();
        }

        transition.getChildren().addAll(slideIn, scaleIn, fadeIn);
        transition.play();

        rigthContainer.getChildren().add(courseBox);
        rigthContainer.getChildren().add(createIndicatorHBox());
    }

    private int previousIndex = 0;

    private void setupCarouselControls() {
        leftButton.setOnAction(e -> {
            if (currentIndex > 0) {
                previousIndex = currentIndex;
                currentIndex--;
                displayCourses();
            }
        });

        rigthButton.setOnAction(e -> {
            if (currentIndex < courses.size() - 1) {
                previousIndex = currentIndex;
                currentIndex++;
                displayCourses();
            }
        });
    }

    private HBox createIndicatorHBox() {
        HBox indicatorHBox = new HBox(8);
        indicatorHBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < courses.size(); i++) {
            Circle circle = new Circle(4);
            circle.setFill(i == currentIndex ? Color.web("#6c63ff") : Color.web("#ffffff40"));
            indicatorHBox.getChildren().add(circle);
        }
        return indicatorHBox;
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.setTextFill(color);
        return label;
    }

    private Button createStartButton() {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("modern-button");
        button.setPrefHeight(35);
        button.setStyle("-fx-min-width: 100; " +
                "-fx-min-height: 30; " +
                "-fx-padding: 5 20 5 20;");
        return button;
    }

    private VBox createCourseBoxCarousel(Course course) {
        VBox box = new VBox(10);
        box.setPrefWidth(900);
        box.setMaxWidth(900);
        box.setAlignment(Pos.TOP_LEFT);
        box.setPrefHeight(150);
        box.setSpacing(10);
        box.getStyleClass().add("card");

        Label nameLabel = createStyledLabel(course.getName(), "Franklin Gothic Medium", 22, Color.WHITE);
        HBox courseInfo = new HBox(8);
        courseInfo.getChildren().addAll(
                createStyledLabel("⭐ " + course.getRating(), "Franklin Gothic Medium", 14, Color.web("#ffffff80")),
                createStyledLabel("•", "Franklin Gothic Medium", 14, Color.web("#ffffff80")));
              //  createStyledLabel(course.getHours() + " horas", "Franklin Gothic Medium", 14, Color.web("#ffffff80")));

        Label descLabel = createStyledLabel(course.getDescription(), "Franklin Gothic Medium", 14,
                Color.web("#ffffff80"));
        Button startButton = createStartButton();
        box.getChildren().addAll(nameLabel, courseInfo, descLabel, startButton);

        return box;
    }
}
