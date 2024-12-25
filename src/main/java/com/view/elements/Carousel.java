package com.view.elements;

import java.io.InputStream;
import java.util.*;

import com.controller.student.LoadCoursesController;
import com.dto.paginaPrincipalDto;
import com.view.Modo;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    @FXML
    private ImageView rightButtonImage;
    @FXML
    private ImageView leftButtonImage;

    private int currentIndex = 0;
    private Timeline timeline;

    LoadCoursesController paginaPrincipalController = new LoadCoursesController();
    List<paginaPrincipalDto> courses = paginaPrincipalController.loadCourses();

    @SuppressWarnings("unused")
    public void loadCarousel() {
        displayCourses();
        setupCarouselControls();
        startAutoCarousel();
        try {
            Thread effectThread = new Thread(() -> {
                while (true) {
                    try {
                        if (!Modo.getInstance().getModo()) {
                            ColorAdjust colorAdjust = new ColorAdjust();
                            colorAdjust.setHue(-0.05);
                            colorAdjust.setSaturation(0.6);
                            colorAdjust.setBrightness(-0.3);

                            rightButtonImage.setEffect(colorAdjust);
                            leftButtonImage.setEffect(colorAdjust);
                        } else {
                            rightButtonImage.setEffect(null);
                            leftButtonImage.setEffect(null);
                        }
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            effectThread.setDaemon(true);
            effectThread.start();
        } catch (Exception e) {
            System.err.println("Error setting up effect thread: " + e.getMessage());
        }

        rigthButton.setOnMouseEntered(event -> {
            try {
                InputStream stream = getClass().getResourceAsStream("/com/icons/seta-carrosel-hover.png");
                if (stream == null) {
                    System.err.println("Resource not found: /com/icons/seta-carrosel.png");
                } else {
                    Image normalImage = new Image(stream);
                    rightButtonImage.setRotate(270);
                    rightButtonImage.setImage(normalImage);
                }
            } catch (Exception ex) {
                System.err.println("Error loading hover image: " + ex.getMessage());
            }
        });

        rigthButton.setOnMouseExited(event -> {
            try {
                InputStream stream = getClass().getResourceAsStream("/com/icons/seta-carrosel.png");
                if (stream == null) {
                    System.err.println("Resource not found: /com/icons/seta-carrosel.png");
                } else {
                    Image normalImage = new Image(stream);
                    rightButtonImage.setRotate(0);
                    rightButtonImage.setImage(normalImage);
                }
            } catch (Exception ex) {
                System.err.println("Error loading normal image: " + ex.getMessage());
            }
        });

        leftButton.setOnMouseEntered(event -> {
            try {
                InputStream stream = getClass().getResourceAsStream("/com/icons/seta-carrosel-hover.png");
                if (stream == null) {
                    System.err.println("Resource not found: /com/icons/seta-carrosel.png");
                } else {
                    Image normalImage = new Image(stream);
                    leftButtonImage.setRotate(90);
                    leftButtonImage.setImage(normalImage);
                }
            } catch (Exception ex) {
                System.err.println("Error loading hover image: " + ex.getMessage());
            }
        });

        leftButton.setOnMouseExited(event -> {
            try {
                InputStream stream = getClass().getResourceAsStream("/com/icons/seta-carrosel.png");
                if (stream == null) {
                    System.err.println("Resource not found: /com/icons/seta-carrosel.png");
                } else {
                    Image normalImage = new Image(stream);
                    leftButtonImage.setRotate(180);
                    leftButtonImage.setImage(normalImage);
                }
            } catch (Exception ex) {
                System.err.println("Error loading normal image: " + ex.getMessage());
            }
        });

    }

    @SuppressWarnings("unused")
    private void startAutoCarousel() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            if (currentIndex < courses.size() - 1) {
                currentIndex++;
            } else {
                currentIndex = 0;
            }
            displayCourses();
        }));

        try {
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            rigthContainer.setOnMouseEntered(event -> {
                try {
                    timeline.stop();
                } catch (Exception e) {
                    System.err.println("Error stopping timeline: " + e.getMessage());
                }
            });

            rigthContainer.setOnMouseExited(event -> {
                try {
                    timeline.stop();
                    timeline.play();
                } catch (Exception e) {
                    System.err.println("Error restarting timeline: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Error setting up timeline: " + e.getMessage());
        }
    }

    private void displayCourses() {
        try {
            if (courses.isEmpty())
                return;
            rigthContainer.getChildren().clear();
            rigthContainer.setSpacing(20.0);
            rigthContainer.setAlignment(Pos.CENTER);

            VBox courseBox = createCourseBoxCarousel(courses.get(currentIndex));
            TranslateTransition transition2 = new TranslateTransition(Duration.millis(600));
            ParallelTransition transition = new ParallelTransition(transition2);

            try {
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
            } catch (Exception e) {
                System.err.println("Error during transition animation: " + e.getMessage());
            }

            rigthContainer.getChildren().add(courseBox);
            rigthContainer.getChildren().add(createIndicatorHBox());
        } catch (Exception e) {
            System.err.println("Error displaying courses: " + e.getMessage());
        }
    }

    private int previousIndex = 0;

    @SuppressWarnings("unused")
    private void setupCarouselControls() {
        leftButton.setOnAction(e -> {
            try {
                if (currentIndex > 0) {
                    previousIndex = currentIndex;
                    currentIndex--;
                } else {
                    currentIndex = courses.size() - 1;
                    previousIndex = 0;
                }
                displayCourses();
            } catch (Exception ex) {
                System.err.println("Error handling left button action: " + ex.getMessage());
            }
        });

        rigthButton.setOnAction(e -> {
            try {
                if (currentIndex < courses.size() - 1) {
                    previousIndex = currentIndex;
                    currentIndex++;
                } else {
                    currentIndex = 0;
                    previousIndex = courses.size() - 1;
                }
                displayCourses();
            } catch (Exception ex) {
                System.err.println("Error handling right button action: " + ex.getMessage());
            }
        });
    }

    private HBox createIndicatorHBox() {
        try {
            HBox indicatorHBox = new HBox(8);
            indicatorHBox.setAlignment(Pos.CENTER);
            for (int i = 0; i < courses.size(); i++) {
                Circle circle = new Circle(4);
                circle.setFill(i == currentIndex ? Color.web("#6c63ff") : Color.web("#ffffff40"));
                indicatorHBox.getChildren().add(circle);
            }
            return indicatorHBox;
        } catch (Exception e) {
            System.err.println("Error creating indicator: " + e.getMessage());
            return new HBox();
        }
    }

    private VBox createCourseBoxCarousel(paginaPrincipalDto course) {
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
        try {

            javafx.scene.effect.Reflection reflection = new javafx.scene.effect.Reflection();
            reflection.setFraction(0.2);
            courseImage.setEffect(reflection);

            imageContainer.getChildren().add(courseImage);
        } catch (Exception e) {
            System.out.println("houve um equivco");
        }

        VBox detailsContainer = new VBox(10);
        detailsContainer.setFillWidth(true);
        HBox.setHgrow(detailsContainer, Priority.ALWAYS);
        detailsContainer.setPrefWidth(480);

        Label titleLabel = createFixedLabel(
                course.getTitle(),
                "Franklin Gothic Medium",
                24,
                Color.WHITE);

        Label categoryLabel = createFixedLabel(
                course.getCategoria().toUpperCase(),
                "Franklin Gothic Medium",
                12,
                Color.web("#FFD700"));
        categoryLabel.getStyleClass().add("category");

        Label authorLabel = createFixedLabel(
                "Por " + course.getName(),
                "Franklin Gothic Medium",
                14,
                Color.web("#ffffff90"));
        authorLabel.getStyleClass().add("author");
        titleLabel.getStyleClass().add("title");

        HBox courseInfo = new HBox(15);
        courseInfo.setAlignment(Pos.CENTER_LEFT);
        courseInfo.getChildren().addAll(
                createInfoLabel("⭐ " + course.getRating()),
                createInfoLabel(course.getDurationTotal() + " horas"),
                createInfoLabel("Nível: " + course.getNivel()));

        Label descLabel = createFixedLabel(
                course.getDescription(),
                "Franklin Gothic Medium",
                14,
                Color.web("#ffffff90"));
        descLabel.setWrapText(true);
        descLabel.setMaxHeight(60);

        HBox statusInfo = new HBox(15);
        statusInfo.setAlignment(Pos.CENTER_LEFT);
        statusInfo.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.03); " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 5;");

        Label certificateLabel = createFixedLabel(
                course.isCertificate() ? "✓ Certificado" : "✗ Sem Certificado",
                "Franklin Gothic Medium",
                13,
                course.isCertificate() ? Color.web("#90EE90") : Color.web("#ffffff60"));

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
                buttonContainer);

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
        label.getStyleClass().add("info-label");
        return label;
    }

    @SuppressWarnings("unused")
    private Button createDetailsButton(paginaPrincipalDto course) {
        Button button = new Button("Ver Detalhes");
        button.getStyleClass().add("outline-button");
        button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20;"));
        button.onActionProperty().set(e -> {
            modal(course);
        });
        return button;
    }

    private Button createStartButton() {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("simple-button");
        button.setPrefHeight(35);
        return button;
    }

    private void modal(paginaPrincipalDto course) {
        try {
            if (rigthContainer != null && rigthContainer.getScene() != null) {
                Platform.runLater(() -> {
                   new CourseDetailsModal(getDefaultWindow(), course);
                });
            } else {
                System.out.println("rigthContainer or Scene is null");
            }
        } catch (Exception e) {
            System.err.println("Error creating modal: " + e.getMessage());
        }
    }

    private Window getDefaultWindow() {
        return Stage.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
    }
}
