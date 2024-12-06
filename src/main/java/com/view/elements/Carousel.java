package com.view.elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Course.Course;
import com.model.Course.CourseSettings;
import com.model.Course.CourseReader;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

        rigthButton.getStyleClass().add("outline-button-seta");
        leftButton.getStyleClass().add("outline-button-seta");

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

        rigthContainer.setOnMouseEntered(event -> {
            timeline.stop();
        });
    
        rigthContainer.setOnMouseExited(event -> {
            timeline.stop(); 
            timeline.play();
        });
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
            }else{
                currentIndex = courses.size() - 1;
                previousIndex = 0;
            }
            displayCourses();
        });

        rigthButton.setOnAction(e -> {
            if (currentIndex < courses.size() - 1) {
                previousIndex = currentIndex;
                currentIndex++;
            }else{
                currentIndex = 0;
                previousIndex = courses.size() - 1;
            }
            displayCourses();

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
    VBox.setVgrow(courseBox, Priority.ALWAYS);
    courseBox.setFillWidth(true);
    courseBox.setPrefWidth(2000);
    courseBox.setMaxWidth(Double.MAX_VALUE);
    courseBox.setPrefHeight(300);
    courseBox.setMaxHeight(300);

    HBox content = new HBox();
    content.setFillHeight(true);
    HBox.setHgrow(content, Priority.ALWAYS);
    content.setPadding(new Insets(20));
    content.setSpacing(20);

    VBox imageContainer = new VBox();
    imageContainer.setAlignment(Pos.CENTER);
    imageContainer.setPrefWidth(260);
    imageContainer.setMaxWidth(260);

    ImageView courseImage = new ImageView();
    courseImage.setFitWidth(260);
    courseImage.setFitHeight(150);
    courseImage.setPreserveRatio(true);
    courseImage.setStyle("-fx-background-radius: 12;");
    
    javafx.scene.effect.Reflection reflection = new javafx.scene.effect.Reflection();
    reflection.setFraction(0.2);
    courseImage.setEffect(reflection);
    
    imageContainer.getChildren().add(courseImage);

    VBox detailsContainer = new VBox(10);
    detailsContainer.setFillWidth(true);
    HBox.setHgrow(detailsContainer, Priority.ALWAYS);
    detailsContainer.setPrefWidth(480);

    Label titleLabel = createFixedLabel(
        course.getTitle(), 
        "Franklin Gothic Medium", 
        24, 
        Color.WHITE
    );

    Label categoryLabel = createFixedLabel(
        course.getCategoria().toUpperCase(), 
        "Franklin Gothic Medium", 
        12, 
        Color.web("#FFD700")
    );

    Label authorLabel = createFixedLabel(
        "Por " + course.getName(), 
        "Franklin Gothic Medium", 
        14, 
        Color.web("#ffffff90")
    );

    HBox courseInfo = new HBox(15);
    courseInfo.setAlignment(Pos.CENTER_LEFT);
    courseInfo.getChildren().addAll(
        createInfoLabel("⭐ " + course.getRating()),
        createInfoLabel(settings.getDurationTotal() + " horas"),
        createInfoLabel("Nível: " + course.getNivel())
    );

    Label descLabel = createFixedLabel(
        course.getDescription(), 
        "Franklin Gothic Medium", 
        14, 
        Color.web("#ffffff90")
    );
    descLabel.setWrapText(true);
    descLabel.setMaxHeight(60);

    HBox statusInfo = new HBox(15);
    statusInfo.setAlignment(Pos.CENTER_LEFT);
    statusInfo.setStyle(
        "-fx-background-color: rgba(255, 255, 255, 0.03); " +
        "-fx-padding: 10; " +
        "-fx-background-radius: 5;"
    );

    Label certificateLabel = createFixedLabel(
        settings.isCertificate() ? "✓ Certificado" : "✗ Sem Certificado",
        "Franklin Gothic Medium", 
        13,
        settings.isCertificate() ? Color.web("#90EE90") : Color.web("#ffffff60")
    );

    statusInfo.getChildren().add(certificateLabel);

    HBox buttonContainer = new HBox(15);
    buttonContainer.setAlignment(Pos.CENTER_LEFT);
    buttonContainer.setPadding(new Insets(10, 0, 0, 0));

    Button startButton = createStartButton();
    Button detailsButton = createDetailsButton(course);

    buttonContainer.getChildren().addAll(startButton, detailsButton);

    detailsContainer.getChildren().addAll(
        titleLabel,
        categoryLabel,
        authorLabel,
        courseInfo,
        descLabel,
        statusInfo,
        buttonContainer
    );

    content.getChildren().addAll(imageContainer, detailsContainer);
    courseBox.getChildren().add(content);

    return courseBox;
}

private Label createFixedLabel(String text, String fontFamily, double fontSize, Color color) {
    Label label = new Label(text);
    label.setFont(Font.font(fontFamily, fontSize));
    label.setTextFill(color);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setWrapText(true);
    return label;
}

private Label createInfoLabel(String text) {
    Label label = createFixedLabel(text, "Franklin Gothic Medium", 14, Color.web("#ffffff90"));
    label.setStyle(
        "-fx-padding: 5 10; " +
        "-fx-background-color: rgba(255, 255, 255, 0.03); " +
        "-fx-background-radius: 15;"
    );
    return label;
}
    
   

    private Button createDetailsButton(Course course) {
        Button button = new Button("Ver Detalhes");
        button.getStyleClass().add("outline-button");
        button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;"));
        button.onActionProperty().set(e -> {
            CourseDetailsModal modal = new CourseDetailsModal();
           modalTeste();
        });
        return button;
    }

    private Button createStartButton() {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("simple-button");
        button.setPrefHeight(35);
        return button;
    }

    private void  modalTeste(){
        CourseDetailsModal modal = new CourseDetailsModal();

        // Dados fictícios do curso
        String courseTitle = "Desenvolvimento Web";
        String createdBy = "Professor João";
        String creationDate = "05/12/2024";
        int numberOfLessons = 10;
        int numberOfModules = 3;

        // Módulos e aulas
        Map<String, List<String>> modulesWithLessons = new HashMap<>();
        modulesWithLessons.put("Módulo 1: Fundamentos", Arrays.asList("Introdução ao HTML", "Introdução ao CSS"));
        modulesWithLessons.put("Módulo 2: JavaScript", Arrays.asList("Variáveis", "Funções", "Eventos"));
        modulesWithLessons.put("Módulo 3: Projeto Final", Arrays.asList("Estruturação do projeto", "Entrega"));

        // Questionários
        List<String> quizzes = Arrays.asList("Quiz 1: HTML", "Quiz 2: CSS", "Quiz 3: JavaScript");

        // Exibir modal
        modal.showCourseDetails(courseTitle, createdBy, creationDate, numberOfLessons, numberOfModules, modulesWithLessons, quizzes);
        modal.openModal();
    }
}
