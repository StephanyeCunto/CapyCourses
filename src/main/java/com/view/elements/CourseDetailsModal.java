package com.view.elements;

import javafx.animation.FadeTransition;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.time.LocalDate;
import java.util.*;

import javafx.util.Duration;

import com.Course.CourseReader;
import com.Course.Module;
import com.dto.PaginaPrincipalDto;
import com.view.Modo;

public class CourseDetailsModal {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;

    public void updateDimensions(double WIDTH, double HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public CourseDetailsModal(Window owner, PaginaPrincipalDto course) {
        updateDimensions(owner.getWidth(), owner.getHeight());
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initOwner(owner);
        loadValues(course);
        setupCloseAnimation();
    }

    private void loadValues(PaginaPrincipalDto course) {

        showCourseDetails(
                course.getTitle(),
                course.getName(),
                course.getDescription(),
                course.getCategoria(),
                course.getNivel(),
                course.getRating(),
                course.getDateEnd(),
                course.getDurationTotal(),
                course.isDateEnd(),
                course.isCertificate());
    }

    private StackPane createBackdrop() {
        StackPane backdrop = new StackPane();
        backdrop.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        backdrop.setOnMouseClicked(event -> {
            if (event.getTarget() == backdrop) {
                modalStage.close();
            }
        });
        return backdrop;
    }

    private DropShadow createDropShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.6));
        shadow.setRadius(25);
        shadow.setSpread(0.2);
        return shadow;
    }

    private void showCourseDetails(String courseTitle, String name,
            String description, String categoria, String nivel, double rating,LocalDate dateEnd, String durationTotal,Boolean isDateEnd, Boolean isCertificate) {

        StackPane backdrop = createBackdrop();
        VBox modalContainer = new VBox(20);
        modalContainer.setPadding(new Insets(30));

        modalContainer.setMaxWidth(WIDTH * 0.8);
        modalContainer.setMaxHeight(HEIGHT * 0.8);
        modalContainer.setMinHeight(HEIGHT * 0.8);
        modalContainer.setMinWidth(WIDTH * 0.8);

        modalContainer.getStyleClass().add("card");
        modalContainer.setEffect(createDropShadow());

        VBox header = createHeader(categoria, courseTitle, name, nivel, rating, durationTotal);

        VBox content = new VBox(20);
        content.setPadding(new Insets(0, 20, 10, 20));
        content.setAlignment(Pos.CENTER);

        ScrollPane module = createModule(courseTitle);
        content.getChildren().add(module);

        VBox footer = new VBox(20);
        footer.setAlignment(Pos.BOTTOM_LEFT);
        VBox.setVgrow(footer, Priority.ALWAYS);

        footer.getChildren().add(createStartButton());

        modalContainer.getChildren().addAll(header, content,footer);

        backdrop.getChildren().add(modalContainer);
        Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        modo(scene);

        modalStage.setScene(scene);
        modalStage.showAndWait();
        }

        private ScrollPane createModule(String title) {
        VBox module = new VBox(15);

        ScrollPane scrollPane = new ScrollPane(module);
        scrollPane.setMaxHeight(HEIGHT * 0.5); 
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        module.setMaxHeight(Double.MAX_VALUE);

        module.setPadding(new Insets(20,20,0,0));
        module.getStyleClass().add("modules-container");

        CourseReader courseReader = new CourseReader();
        List<Module> modules = courseReader.courseModule(title);

        for (int i = 0; i < modules.size(); i++) {
            Module moduleGet = modules.get(i);
            VBox moduleBox = new VBox();
            moduleBox.getStyleClass().add("module-box");
            moduleBox.setPadding(new Insets(0,0,15,0));
            moduleBox.setStyle("-fx-background-radius: 10;");

            HBox header = new HBox(15);
            header.setAlignment(Pos.CENTER_LEFT);
            header.getStyleClass().add("module-header");
            header.setPadding(new Insets(0, 0, 10, 0));

            Label moduleNumber = createStyledLabel("Módulo " + moduleGet.getModuleNumber(), "Segoe UI Semibold", 16);
            moduleNumber.getStyleClass().add("module-number");

            Label moduleTitle = createStyledLabel(moduleGet.getTitle(), "Segoe UI Bold", 18);
            moduleTitle.getStyleClass().add("module-title");
            moduleTitle.setWrapText(true);

            Label duration = createStyledLabel("⏱ " + moduleGet.getDuration() + " min", "Segoe UI", 14);
            duration.getStyleClass().add("module-duration");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            header.getChildren().addAll(moduleNumber, moduleTitle, spacer, duration);

            VBox content = new VBox(10);
            content.setPadding(new Insets(0,0,10,0));
            content.getStyleClass().add("module-content");

            Label description = createStyledLabel(moduleGet.getDescription(), "Segoe UI", 14);
            description.setWrapText(true);
            description.getStyleClass().add("module-description");
            content.getChildren().add(description);

            moduleBox.getChildren().addAll(header, content);

            // Add shadow effect
            DropShadow shadow = new DropShadow();
            shadow.setRadius(5);
            shadow.setSpread(0.1);
            shadow.setColor(Color.rgb(0, 0, 0, 0.2));
            moduleBox.setEffect(shadow);

            module.getChildren().add(moduleBox);
        }

        return scrollPane;
        }

        @SuppressWarnings("unused")
        private VBox createHeader(String categoria, String title, String name, String nivel, double rating,
            String durationTotal) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20,20,0,20));

        Label categoryLabel = createStyledLabel(categoria.toUpperCase(), "Segoe UI", 14);
        categoryLabel.getStyleClass().add("category");

        Label titleLabel = createStyledLabel(title, "Segoe UI Bold", 28);
        titleLabel.getStyleClass().add("title");

        HBox professorInfo = avatar(name);

        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("register-button");
        closeButton.setOnAction(e -> modalStage.close());
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setAlignment(Pos.TOP_RIGHT);

        HBox courseInfo = createCourseInfo(rating, durationTotal, nivel);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(titleLabel, courseInfo);

        HBox mainHeader = new HBox();
        mainHeader.getChildren().addAll(header, closeButtonBox);
        HBox.setHgrow(header, Priority.ALWAYS);

        content.getChildren().addAll(mainHeader, categoryLabel, professorInfo);

        return content;
    }

    private HBox avatar(String name) {
        StackPane avatarCircle = new StackPane();
        avatarCircle.setMinSize(60, 60);
        avatarCircle.setMaxSize(60, 60);
        avatarCircle.getStyleClass().add("avatar-circle");

        String[] nameParts = name.split(" ");
        String initials = String.valueOf(nameParts[0].charAt(0));
        if (nameParts.length > 1) {
            String lastName = nameParts[nameParts.length - 1];
            initials += String.valueOf(lastName.charAt(0));
        }

        Label initialsLabel = new Label(initials.toUpperCase());
        initialsLabel.getStyleClass().add("initials-label");
        avatarCircle.getChildren().add(initialsLabel);

        VBox professorDetails = new VBox(5);
        Label titleLabel = createStyledLabel("Professor", "Segoe UI", 12);
        titleLabel.getStyleClass().add("professor-title");

        Label nameLabel = createStyledLabel(name, "Segoe UI Bold", 16);
        nameLabel.getStyleClass().add("professor-name");
        Label credentialsLabel = createStyledLabel("PhD em Ciência da Computação", "Segoe UI", 12);
        credentialsLabel.getStyleClass().add("professor-credentials");

        Label experienceLabel = createStyledLabel("10+ anos de experiência", "Segoe UI", 12);
        experienceLabel.getStyleClass().add("professor-experience");

        professorDetails.getChildren().addAll(
                titleLabel,
                nameLabel,
                credentialsLabel,
                experienceLabel);

        HBox professorInfo = new HBox(15);
        professorInfo.setAlignment(Pos.CENTER_LEFT);
        professorInfo.setPadding(new Insets(10, 0, 10, 0));
        professorInfo.getChildren().addAll(avatarCircle, professorDetails);

        return professorInfo;
    }

    private HBox createCourseInfo(double rating, String durationTotal, String nivel) {
        HBox courseInfo = new HBox(15);
        courseInfo.setPadding(new Insets(0, 20, 0, 20));
        courseInfo.setAlignment(Pos.CENTER_LEFT);
        courseInfo.getChildren().addAll(
                createInfoLabel("⭐ " + rating),
                createInfoLabel(durationTotal + " horas"),
                createInfoLabel("Nível: " + nivel));
        return courseInfo;
    }

    private Label createInfoLabel(String text) {
        Label label = createStyledLabel(text, "Franklin Gothic Medium", 14);
        label.getStyleClass().add("info-label-modal");
        return label;
    }

    private void modo(Scene scene) {
        String styleSheet = Modo.getInstance().getModo() ? "/com/estudante/paginaInicial/style/dark/style.css"
                : "/com/estudante/paginaInicial/style/ligth/style.css";
        scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.setWrapText(true);

        return label;
    }

    @SuppressWarnings("unused")
    private void setupCloseAnimation() {
        modalStage.setOnCloseRequest(event -> {
            event.consume();
            if (modalStage.getScene() == null) {
                return;
            }
            FadeTransition fade = new FadeTransition(Duration.millis(200), modalStage.getScene().getRoot());
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> modalStage.hide());
            fade.play();
        });
    }

    private Button createStartButton() {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("outline-button");
        button.setPrefHeight(40);
        return button;
    }
}

/*
 * public void showCourseDetails(
 * String courseTitle,
 * String createdBy,
 * String creationDate,
 * int numberOfLessons,
 * int numberOfModules,
 * Map<String, List<String>> modulesWithLessons,
 * List<String> quizzes
 * ) {
 * // Backdrop para foco no modal
 * StackPane backdrop = createBackdrop();
 * backdrop.getStyleClass().add("card");
 * // Container principal
 * VBox modalContainer = new VBox(20);
 * modalContainer.setPadding(new Insets(20));
 * modalContainer.setMaxWidth(WIDTH * 0.8);
 * modalContainer.setMaxHeight(HEIGHT * 0.9);
 * modalContainer.getStyleClass().add("card");
 * modalContainer.setEffect(createDropShadow());
 * 
 * // Adicionando as seções
 * VBox header = createHeader(courseTitle, createdBy, creationDate,
 * numberOfLessons, numberOfModules);
 * VBox content = createContent(modulesWithLessons, quizzes);
 * HBox footer = createFooter();
 * 
 * modalContainer.getChildren().addAll(header, new ScrollPane(content), footer);
 * backdrop.getChildren().add(modalContainer);
 * 
 * // Configuração da cena
 * Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
 * scene.setFill(Color.TRANSPARENT);
 * if(Modo.getInstance().getModo()){
 * scene.getStylesheets().add(getClass().getResource(
 * "/com/estudante/paginaInicial/style/dark/style.css").toExternalForm());
 * }else{
 * scene.getStylesheets().add(getClass().getResource(
 * "/com/estudante/paginaInicial/style/ligth/style.css").toExternalForm());
 * }
 * 
 * modalStage.setScene(scene);
 * modalStage.showAndWait();
 * }
 * 
 * private StackPane createBackdrop() {
 * StackPane backdrop = new StackPane();
 * backdrop.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
 * backdrop.setOnMouseClicked(event -> modalStage.close()); // Fecha ao clicar
 * fora
 * return backdrop;
 * }
 * 
 * private DropShadow createDropShadow() {
 * DropShadow shadow = new DropShadow();
 * shadow.setColor(Color.color(0, 0, 0, 0.5));
 * shadow.setRadius(20);
 * return shadow;
 * }
 * 
 * private VBox createHeader(String courseTitle, String createdBy, String
 * creationDate, int lessons, int modules) {
 * VBox header = new VBox(10);
 * header.setAlignment(Pos.CENTER);
 * header.setPadding(new Insets(10));
 * header.setStyle("-fx-background-color: #1f2133; -fx-background-radius: 15;");
 * 
 * Label titleLabel = new Label(courseTitle);
 * titleLabel.
 * setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;"
 * );
 * 
 * GridPane detailsGrid = new GridPane();
 * detailsGrid.setHgap(15);
 * detailsGrid.setVgap(5);
 * detailsGrid.addRow(0, createLabel("Criado por:"), createLabel(createdBy));
 * detailsGrid.addRow(1, createLabel("Data de Criação:"),
 * createLabel(creationDate));
 * detailsGrid.addRow(2, createLabel("Total de Aulas:"),
 * createLabel(String.valueOf(lessons)));
 * detailsGrid.addRow(3, createLabel("Total de Módulos:"),
 * createLabel(String.valueOf(modules)));
 * 
 * header.getChildren().addAll(titleLabel, detailsGrid);
 * return header;
 * }
 * 
 * private VBox createContent(Map<String, List<String>> modulesWithLessons,
 * List<String> quizzes) {
 * VBox content = new VBox(20);
 * content.setPadding(new Insets(10));
 * content.setStyle("-fx-background-color: #2a2d3e; -fx-background-radius: 15;"
 * );
 * 
 * // Seção de módulos
 * Label modulesLabel = new Label("Módulos e Aulas");
 * modulesLabel.
 * setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;"
 * );
 * 
 * VBox modulesSection = new VBox(15);
 * modulesWithLessons.forEach((module, lessons) -> {
 * TitledPane modulePane = new TitledPane(module, createLessonList(lessons));
 * modulePane.setStyle("-fx-text-fill: white;");
 * modulesSection.getChildren().add(modulePane);
 * });
 * 
 * // Seção de quizzes
 * Label quizzesLabel = new Label("Questionários");
 * quizzesLabel.
 * setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;"
 * );
 * 
 * VBox quizzesSection = new VBox(10);
 * quizzes.forEach(quiz -> quizzesSection.getChildren().add(createLabel("• " +
 * quiz)));
 * 
 * content.getChildren().addAll(modulesLabel, modulesSection, quizzesLabel,
 * quizzesSection);
 * return content;
 * }
 * 
 * private VBox createLessonList(List<String> lessons) {
 * VBox lessonList = new VBox(5);
 * lessons.forEach(lesson -> {
 * Label lessonLabel = createLabel("• " + lesson);
 * lessonList.getChildren().add(lessonLabel);
 * });
 * return lessonList;
 * }
 * 
 * private HBox createFooter() {
 * HBox footer = new HBox();
 * footer.setAlignment(Pos.CENTER_RIGHT);
 * footer.setPadding(new Insets(10));
 * 
 * Button closeButton = new Button("Fechar");
 * closeButton.
 * setStyle("-fx-background-color: #ff4f5a; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;"
 * );
 * closeButton.setOnAction(event -> modalStage.close());
 * 
 * footer.getChildren().add(closeButton);
 * return footer;
 * }
 * 
 * private Label createLabel(String text) {
 * Label label = new Label(text);
 * label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
 * return label;
 * }
 */
