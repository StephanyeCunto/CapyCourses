package com.view.elements;

import java.util.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class CourseDetailsModal {
    private final Stage modalStage;
    private final double WIDTH = 800;
    private final double HEIGHT = 600;

    public CourseDetailsModal() {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT); // Makes the window borderless
        modalStage.setTitle("Detalhes do Curso");
    }

    public void showCourseDetails(
            String courseTitle,
            String createdBy,
            String creationDate,
            int numberOfLessons,
            int numberOfModules,
            Map<String, List<String>> modulesWithLessons,
            List<String> quizzes
    ) {
        // Backdrop effect
        StackPane backdrop = new StackPane();
        backdrop.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Main container with modern styling
        VBox mainContainer = new VBox(20);
        mainContainer.setMaxWidth(WIDTH * 0.9);
        mainContainer.setMaxHeight(HEIGHT * 0.9);
        mainContainer.getStyleClass().addAll("modal-container", "glass-effect");
        mainContainer.setPadding(new Insets(25));
        
        // Add drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.3));
        dropShadow.setRadius(20);
        mainContainer.setEffect(dropShadow);

        // Header section
        VBox header = createHeader(courseTitle, createdBy, creationDate, numberOfLessons, numberOfModules);
        
        // Content section with modules and quizzes
        VBox content = createContent(modulesWithLessons, quizzes);

        // Close button with modern styling
        Button closeButton = new Button("Fechar");
        closeButton.getStyleClass().addAll("modal-close-button", "hoverable");
        closeButton.setOnAction(e -> modalStage.close());

        // Organize layout
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        mainContainer.getChildren().addAll(header, scrollPane, closeButton);
        backdrop.getChildren().add(mainContainer);

        // Scene setup
        Scene modalScene = new Scene(backdrop, WIDTH, HEIGHT);
        modalScene.setFill(Color.TRANSPARENT);
        modalScene.getStylesheets().addAll(
            getClass().getResource("/com/estudante/paginaInicial/style/dark/style.css").toExternalForm(),
            getClass().getResource("/com/estudante/paginaInicial/style/dark/modal.css").toExternalForm()
        );

        // Click outside to close
        backdrop.setOnMouseClicked(e -> {
            if (e.getTarget() == backdrop) {
                modalStage.close();
            }
        });

        modalStage.setScene(modalScene);
        modalStage.centerOnScreen();
    }

    private VBox createHeader(String courseTitle, String createdBy, String creationDate, 
                            int numberOfLessons, int numberOfModules) {
        VBox header = new VBox(10);
        header.getStyleClass().add("modal-header");

        Label titleLabel = new Label(courseTitle);
        titleLabel.getStyleClass().add("modal-title");

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(5);
        infoGrid.addRow(0, new Label("Criado por:"), new Label(createdBy));
        infoGrid.addRow(1, new Label("Data:"), new Label(creationDate));
        infoGrid.addRow(2, new Label("Aulas:"), new Label(String.valueOf(numberOfLessons)));
        infoGrid.addRow(3, new Label("Módulos:"), new Label(String.valueOf(numberOfModules)));

        header.getChildren().addAll(titleLabel, infoGrid);
        return header;
    }

    private VBox createContent(Map<String, List<String>> modulesWithLessons, List<String> quizzes) {
        VBox content = new VBox(20);
        content.getStyleClass().add("modal-content");

        // Modules section
        VBox modulesSection = new VBox(15);
        modulesSection.getStyleClass().add("content-section");
        
        Label modulesTitle = new Label("Módulos");
        modulesTitle.getStyleClass().add("section-title");
        modulesSection.getChildren().add(modulesTitle);

        modulesWithLessons.forEach((module, lessons) -> {
            TitledPane modulePane = new TitledPane();
            modulePane.setText(module);
            VBox lessonsBox = new VBox(5);
            lessons.forEach(lesson -> {
                Label lessonLabel = new Label("• " + lesson);
                lessonLabel.getStyleClass().add("lesson-item");
                lessonsBox.getChildren().add(lessonLabel);
            });
            modulePane.setContent(lessonsBox);
            modulesSection.getChildren().add(modulePane);
        });

        // Quizzes section
        VBox quizzesSection = new VBox(10);
        quizzesSection.getStyleClass().add("content-section");
        
        Label quizzesTitle = new Label("Questionários");
        quizzesTitle.getStyleClass().add("section-title");
        quizzesSection.getChildren().add(quizzesTitle);

        quizzes.forEach(quiz -> {
            Label quizLabel = new Label("• " + quiz);
            quizLabel.getStyleClass().add("quiz-item");
            quizzesSection.getChildren().add(quizLabel);
        });

        content.getChildren().addAll(modulesSection, quizzesSection);
        return content;
    }

    public void openModal() {
        modalStage.showAndWait();
    }
}
