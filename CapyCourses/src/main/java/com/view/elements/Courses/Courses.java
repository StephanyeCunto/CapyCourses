package com.view.elements.Courses;

import java.net.URL;
import java.util.ResourceBundle;

import com.view.Modo;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Courses implements Initializable {
    @FXML
    private VBox mainVBox;
    @FXML
    private VBox content;
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
;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainVBox.getChildren().add(createMain());
        content.getChildren().add(createFullCourseVBox());
        changeMode();
        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();
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
                    .add(getClass().getResource("/com/elements/curso/style/ligth/style.css").toExternalForm());
        } else {
            background.getStyleClass().remove("dark");
            container.getStylesheets()
                    .add(getClass().getResource("/com/elements/curso/style/dark/style.css").toExternalForm());
        }
    }

    private VBox createMain() {
        VBox mainVBox = new VBox(20);
        mainVBox.setPadding(new Insets(10));

        VBox courseInfoCard = new VBox(10);
        courseInfoCard.getStyleClass().add("content-card");

        Label courseTitle = new Label("Programação em Java");
        courseTitle.getStyleClass().add("card-title");

        HBox courseDetails = new HBox(8);
        courseDetails.getChildren().addAll(
                createStyledLabel("⭐ 4.8", "card-subtitle"),
                createStyledLabel("•", "card-subtitle"),
                createStyledLabel("40 horas", "card-subtitle"),
                createStyledLabel("•", "card-subtitle"),
                createStyledLabel("Intermediário", "card-subtitle"));

        courseInfoCard.getChildren().addAll(courseTitle, courseDetails);

        VBox instructorCard = new VBox(15);
        instructorCard.getStyleClass().add("content-card");

        Label instructorTitle = new Label("Professor");
        instructorTitle.getStyleClass().add("card-title");

        HBox instructorInfo = new HBox(15);
        instructorInfo.setAlignment(Pos.CENTER_LEFT);
        instructorInfo.setMinHeight(60);

        Circle instructorAvatar = new Circle(30, Color.web("#6c63ff"));

        VBox instructorDetails = new VBox(5);
        instructorDetails.getChildren().addAll(
                createStyledLabel("Dr. João Silva", "card-title"),
                createStyledLabel("PhD em Ciência da Computação • 15 anos de experiência", "card-subtitle"));

        instructorInfo.getChildren().addAll(instructorAvatar, instructorDetails);
        instructorCard.getChildren().addAll(instructorTitle, instructorInfo);

        VBox modulesCard = new VBox(15);
        modulesCard.getStyleClass().add("content-card");

        Label modulesTitle = new Label("Módulos do Curso");
        modulesTitle.getStyleClass().add("card-title");

        VBox module1 = createModuleCard("1", "Introdução ao Java", "4 aulas • 2 horas", "Continuar", "modern-button");
        VBox module2 = createModuleCard("2", "Orientação a Objetos", "6 aulas • 4 horas", "Começar", "simple-button");

        modulesCard.getChildren().addAll(modulesTitle, module1, module2);

        VBox reviewsCard = new VBox(15);
        reviewsCard.getStyleClass().add("content-card");

        HBox reviewHeader = new HBox(20);
        reviewHeader.setAlignment(Pos.CENTER_LEFT);

        HBox ratingBox = new HBox(5);
        ratingBox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 1; i <= 5; i++) {
            Label star = new Label("☆");
            star.getStyleClass().add("star-rating");
            final int rating = i;

            star.setOnMouseEntered(e -> {
                for (int j = 0; j < rating; j++) {
                    ((Label) ratingBox.getChildren().get(j)).setText("★");
                }
            });

            star.setOnMouseExited(e -> {
                ratingBox.getChildren().forEach(node -> {
                    if (node instanceof Label) {
                        ((Label) node).setText("☆");
                    }
                });
            });

            star.setOnMouseClicked(e -> {
                ratingBox.getChildren().forEach(node -> {
                    if (node instanceof Label) {
                        ((Label) node).setText("☆");
                    }
                });
                for (int j = 0; j < rating; j++) {
                    ((Label) ratingBox.getChildren().get(j)).setText("★");
                }
            });
            ratingBox.getChildren().add(star);
        }
        TextArea reviewInput = new TextArea();
        reviewInput.setPromptText("Aperte enter para enviar sua avaliação...");
        reviewInput.setPrefRowCount(2);
        reviewInput.setWrapText(true);
        reviewInput.setMaxWidth(Double.MAX_VALUE);
        reviewInput.getStyleClass().add("custom-text-area");
        HBox.setHgrow(reviewInput, javafx.scene.layout.Priority.ALWAYS);

        reviewInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String review = reviewInput.getText().trim();
                if (!review.isEmpty()) {
                    reviewInput.clear();
                    event.consume();
                }
            }
        });

        HBox reviewHeaderHBox = new HBox(20);
        reviewHeaderHBox.getChildren().addAll(
                createStyledLabel("Avaliações do Curso", "card-title"),
                createStyledLabel("⭐ 4.8", "card-subtitle"));

        VBox reviewHeaderVBox = new VBox(20);
        reviewHeaderVBox.getChildren().addAll(
                reviewHeaderHBox,
                ratingBox,
                reviewInput);

        reviewHeader.getChildren().addAll(
                reviewHeaderVBox);

        VBox reviewDetails = new VBox(10);
        reviewDetails.getStyleClass().add("module-card");

        Label reviewText = new Label("Excelente curso, muito didático e com muitos exemplos práticos!");
        reviewText.getStyleClass().add("card-title");

        HBox reviewerInfo = new HBox(10);
        reviewerInfo.getChildren().addAll(
                createStyledLabel("⭐ 5.0", "card-subtitle"),
                createStyledLabel("• João", "card-subtitle"));

        reviewDetails.getChildren().addAll(reviewText, reviewerInfo);
        reviewsCard.getChildren().addAll(reviewHeader, reviewDetails);

        mainVBox.getChildren().addAll(courseInfoCard, instructorCard, modulesCard, reviewsCard);

        return mainVBox;
    }

    private Label createStyledLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private VBox createModuleCard(String moduleNumber, String moduleTitle, String moduleDetails, String buttonText,
            String buttonStyleClass) {
        VBox moduleCard = new VBox(10);
        moduleCard.getStyleClass().add("module-card");

        HBox moduleInfo = new HBox(15);
        moduleInfo.setAlignment(Pos.CENTER_LEFT);

        Label moduleNumberLabel = createStyledLabel(moduleNumber, "module-number");

        VBox moduleDetailsBox = new VBox(5);
        moduleDetailsBox.getChildren().addAll(
                createStyledLabel(moduleTitle, "card-title"),
                createStyledLabel(moduleDetails, "card-subtitle"));

        Button actionButton = new Button(buttonText);
        actionButton.getStyleClass().add(buttonStyleClass);

        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        moduleInfo.getChildren().addAll(moduleNumberLabel, moduleDetailsBox, spacer, actionButton);
        moduleCard.getChildren().add(moduleInfo);

        return moduleCard;
    }

    public VBox createCourseDetailsVBox() {
        VBox courseDetailsVBox = new VBox(15);
        courseDetailsVBox.getStyleClass().add("content-card");

        Label detailsTitle = new Label("Detalhes do Curso");
        detailsTitle.getStyleClass().add("card-title");

        VBox detailsContent = new VBox(10);
        detailsContent.getChildren().addAll(
                createStyledLabel("Nível: Intermediário", "card-subtitle"),
                createStyledLabel("Certificado: Sim", "card-subtitle"),
                createStyledLabel("Duração: 40 horas", "card-subtitle"));

        courseDetailsVBox.getChildren().addAll(detailsTitle, detailsContent);
        return courseDetailsVBox;
    }

    public VBox createCourseProgressVBox() {
        VBox progressVBox = new VBox(15);
        progressVBox.getStyleClass().add("content-card");

        Label progressTitle = new Label("Progresso do Curso");
        progressTitle.getStyleClass().add("card-title");

        VBox progressContent = new VBox(10);
        progressContent.getChildren().addAll(
                createStyledLabel("Aulas Completadas:", "card-subtitle"),
                createProgressDetailsHBox("2/10", "(20%)"),
                createCustomProgressBar());

        VBox evaluationsContent = new VBox(10);
        evaluationsContent.setStyle("-fx-padding: 10 0 0 0;");
        evaluationsContent.getChildren().addAll(
                createStyledLabel("Avaliações Concluídas:", "card-subtitle"),
                createProgressDetailsHBox("0/3", null));

        progressVBox.getChildren().addAll(progressTitle, progressContent, evaluationsContent);
        return progressVBox;
    }

    public VBox createNextActivitiesVBox() {
        VBox activitiesVBox = new VBox(15);
        activitiesVBox.getStyleClass().add("content-card");

        Label activitiesTitle = new Label("Próximas Atividades");
        activitiesTitle.getStyleClass().add("card-title");

        VBox activitiesList = new VBox(10);
        activitiesList.getChildren().addAll(
                createActivityHBox("📝", "Avaliação Módulo 1", "Vence em 3 dias"),
                createActivityHBox("📚", "Aula 3 - Módulo 2", "Próxima aula"));

        activitiesVBox.getChildren().addAll(activitiesTitle, activitiesList);
        return activitiesVBox;
    }

    private HBox createProgressDetailsHBox(String progress, String percentage) {
        HBox progressDetailsHBox = new HBox(10);
        progressDetailsHBox.setAlignment(Pos.CENTER_LEFT);
        if (percentage != null) {
            progressDetailsHBox.getChildren().addAll(
                    createStyledLabel(progress, "card-subtitle"),
                    createStyledLabel(percentage, "card-subtitle"));
        } else {
            progressDetailsHBox.getChildren().add(createStyledLabel(progress, "card-subtitle"));
        }
        return progressDetailsHBox;
    }

    private HBox createCustomProgressBar() {
        HBox progressBar = new HBox();
        progressBar.getStyleClass().add("progress-bar");
        progressBar.setMinHeight(10);
        progressBar.setPrefHeight(10);

        HBox progressBarFill = new HBox();
        progressBarFill.getStyleClass().add("progress-bar .bar");
        progressBarFill.setMinHeight(10);
        progressBarFill.setPrefHeight(10);
        progressBarFill.setPrefWidth(20);

        progressBar.getChildren().add(progressBarFill);
        return progressBar;
    }

    private HBox createActivityHBox(String icon, String title, String subtitle) {
        HBox activityHBox = new HBox(10);
        activityHBox.setAlignment(Pos.CENTER_LEFT);
        activityHBox.getStyleClass().add("module-card");

        Label activityIcon = new Label(icon);
        VBox activityDetails = new VBox(5);
        activityDetails.getChildren().addAll(
                createStyledLabel(title, "card-title"),
                createStyledLabel(subtitle, "card-subtitle"));

        activityHBox.getChildren().addAll(activityIcon, activityDetails);
        return activityHBox;
    }

    private VBox createFullCourseVBox() {
        VBox fullCourseVBox = new VBox(20);
        fullCourseVBox.getStyleClass().add("full-course-container");

        fullCourseVBox.getChildren().addAll(
                createCourseDetailsVBox(),
                createCourseProgressVBox(),
                createNextActivitiesVBox());

        return fullCourseVBox;
    }
}
