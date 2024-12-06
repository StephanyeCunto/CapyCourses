package com.view.elements;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.util.List;
import java.util.Map;

public class CourseDetailsModal {

    private final Stage modalStage;
    private final double WIDTH = 850;
    private final double HEIGHT = 600;

    public CourseDetailsModal() {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT); // Design sem bordas
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
        // Backdrop para foco no modal
        StackPane backdrop = createBackdrop();

        // Container principal
        VBox modalContainer = new VBox(20);
        modalContainer.setPadding(new Insets(20));
        modalContainer.setMaxWidth(WIDTH * 0.8);
        modalContainer.setMaxHeight(HEIGHT * 0.9);
        modalContainer.setStyle("-fx-background-color: #2a2d3e; -fx-background-radius: 15;");
        modalContainer.setEffect(createDropShadow());

        // Adicionando as seções
        VBox header = createHeader(courseTitle, createdBy, creationDate, numberOfLessons, numberOfModules);
        VBox content = createContent(modulesWithLessons, quizzes);
        HBox footer = createFooter();

        modalContainer.getChildren().addAll(header, new ScrollPane(content), footer);
        backdrop.getChildren().add(modalContainer);

        // Configuração da cena
        Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/com/style/modal.css").toExternalForm());

        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private StackPane createBackdrop() {
        StackPane backdrop = new StackPane();
        backdrop.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        backdrop.setOnMouseClicked(event -> modalStage.close()); // Fecha ao clicar fora
        return backdrop;
    }

    private DropShadow createDropShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.5));
        shadow.setRadius(20);
        return shadow;
    }

    private VBox createHeader(String courseTitle, String createdBy, String creationDate, int lessons, int modules) {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #1f2133; -fx-background-radius: 15;");

        Label titleLabel = new Label(courseTitle);
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(15);
        detailsGrid.setVgap(5);
        detailsGrid.addRow(0, createLabel("Criado por:"), createLabel(createdBy));
        detailsGrid.addRow(1, createLabel("Data de Criação:"), createLabel(creationDate));
        detailsGrid.addRow(2, createLabel("Total de Aulas:"), createLabel(String.valueOf(lessons)));
        detailsGrid.addRow(3, createLabel("Total de Módulos:"), createLabel(String.valueOf(modules)));

        header.getChildren().addAll(titleLabel, detailsGrid);
        return header;
    }

    private VBox createContent(Map<String, List<String>> modulesWithLessons, List<String> quizzes) {
        VBox content = new VBox(20);
        content.setPadding(new Insets(10));
        content.setStyle("-fx-background-color: #2a2d3e; -fx-background-radius: 15;");

        // Seção de módulos
        Label modulesLabel = new Label("Módulos e Aulas");
        modulesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox modulesSection = new VBox(15);
        modulesWithLessons.forEach((module, lessons) -> {
            TitledPane modulePane = new TitledPane(module, createLessonList(lessons));
            modulePane.setStyle("-fx-text-fill: white;");
            modulesSection.getChildren().add(modulePane);
        });

        // Seção de quizzes
        Label quizzesLabel = new Label("Questionários");
        quizzesLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox quizzesSection = new VBox(10);
        quizzes.forEach(quiz -> quizzesSection.getChildren().add(createLabel("• " + quiz)));

        content.getChildren().addAll(modulesLabel, modulesSection, quizzesLabel, quizzesSection);
        return content;
    }

    private VBox createLessonList(List<String> lessons) {
        VBox lessonList = new VBox(5);
        lessons.forEach(lesson -> {
            Label lessonLabel = createLabel("• " + lesson);
            lessonList.getChildren().add(lessonLabel);
        });
        return lessonList;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(10));

        Button closeButton = new Button("Fechar");
        closeButton.setStyle("-fx-background-color: #ff4f5a; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");
        closeButton.setOnAction(event -> modalStage.close());

        footer.getChildren().add(closeButton);
        return footer;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        return label;
    }
}
