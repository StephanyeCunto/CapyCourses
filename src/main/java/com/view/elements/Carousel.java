package com.view.elements;

import java.util.List;

import com.model.Course.Course;
import com.model.Course.CourseSettings;
import com.model.Course.CourseReader;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

    private int currentIndex = 0;
    private Timeline timeline;

    CourseReader reader = new CourseReader();
    List<Course> courses = reader.readCourses();
    GridPane courseGrid = new GridPane();

    public void loadCarousel() {
        CourseReader reader = new CourseReader();
        courses = reader.readCourses();
        displayCourses();
        setupCarouselControls();
        startAutoCarousel();

        rigthButton.getStyleClass().add("outline-button");
        leftButton.getStyleClass().add("outline-button");

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
        if (courses.isEmpty())
            return;
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

    private VBox createCourseBoxCarousel(Course course) {
        CourseSettings settings = reader.courseSettings(course.getTitle());
        VBox courseBox = new VBox();
        courseBox.getStyleClass().add("card");
        courseBox.setPrefWidth(800);
        courseBox.setPrefHeight(300);

        HBox content = new HBox(12);
        VBox contentVBox = new VBox(12);
        content.setStyle("-fx-padding: 20;");

        ImageView courseImage = new ImageView();
        courseImage.setFitWidth(260);
        courseImage.setFitHeight(150);
        courseImage.setStyle(
                "-fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 5);");

        javafx.scene.effect.Reflection reflection = new javafx.scene.effect.Reflection();
        reflection.setFraction(0.2);
        courseImage.setEffect(reflection);

        Label categoryLabel = createStyledLabel(course.getCategoria().toUpperCase(), "Franklin Gothic Medium", 12,
                Color.web("#FFD700"));
        categoryLabel.setEffect(new javafx.scene.effect.Glow(0.4));

        Label titleLabel = createStyledLabel(course.getTitle(), "Franklin Gothic Medium", 24, Color.WHITE);
        Label authorLabel = createStyledLabel("Por " + course.getName(), "Franklin Gothic Medium", 14,
                Color.web("#ffffff90"));

        HBox courseInfo = new HBox(15);

        courseInfo.setAlignment(Pos.CENTER_LEFT);
        courseInfo.getChildren().addAll(
                createInfoLabel("⭐ " + course.getRating()),
                createInfoLabel(settings.getDurationTotal() + " horas"),
                createInfoLabel("Nível: " + course.getNivel()));

        Label descLabel = createStyledLabel(course.getDescription(), "Franklin Gothic Medium", 14,
                Color.web("#ffffff90"));
        descLabel.setWrapText(true);
        javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(1000),
                descLabel);
        fade.setFromValue(0.7);
        fade.setToValue(1.0);
        fade.setCycleCount(javafx.animation.Animation.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();

        HBox statusInfo = new HBox(15);
        statusInfo.setAlignment(Pos.CENTER_LEFT);
        statusInfo.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.03); -fx-padding: 10; -fx-background-radius: 5;");

        Label certificateLabel = createStyledLabel(
                settings.isCertificate() ? "✓ Certificado" : "✗ Sem Certificado",
                "Franklin Gothic Medium", 13,
                settings.isCertificate() ? Color.web("#90EE90") : Color.web("#ffffff60"));

        statusInfo.getChildren().addAll(certificateLabel);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));

        Button startButton = createStartButton();
        Button detailsButton = createDetailsButton();

        buttonContainer.getChildren().addAll(startButton, detailsButton);

        contentVBox.getChildren().addAll(
                titleLabel,
                categoryLabel,
                authorLabel,
                courseInfo,
                descLabel,
                statusInfo,
                buttonContainer);
        content.getChildren().addAll(
                courseImage,
                contentVBox);

        courseBox.getChildren().add(content);
        return courseBox;
    }

    private Label createInfoLabel(String text) {
        Label label = createStyledLabel(text, "Franklin Gothic Medium", 14, Color.web("#ffffff90"));
        label.setStyle(
                "-fx-padding: 5 10; -fx-background-color: rgba(255, 255, 255, 0.03); -fx-background-radius: 15;");
        return label;
    }

    private Button createDetailsButton() {
        Button button = new Button("Ver Detalhes");
        button.getStyleClass().add("outline-button");
        button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;"));
        return button;
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.setTextFill(color);
        return label;
    }

    private Button createStartButton() {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("simple-button");
        button.setPrefHeight(35);
        return button;
    }
}
