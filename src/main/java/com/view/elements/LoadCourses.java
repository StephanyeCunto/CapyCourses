package com.view.elements;

import java.util.ArrayList;
import java.util.List;

import com.model.Course.Course;
import com.model.Course.CourseSettings;
import com.model.student.MyCourseStudent;
import com.model.Course.CourseReader;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoadCourses {
    @FXML
    private VBox courseContainer;

    private final CourseReader reader = new CourseReader();
    private List<Course> courses = reader.readCourses();
    private final GridPane courseGrid = new GridPane();

    private MyCourseStudent myCourseStudent = new MyCourseStudent();

    @SuppressWarnings("unused")
    public void loadCoursesNotStarted() {
        setupCourseGrid();

        int numColumns = calculateColumns();

        courses = loadListCourses("notStarted");

        for (int i = 0; i < courses.size(); i++) {
            VBox courseBox = createCourseBox(courses.get(i), "notStarted");
            courseGrid.add(courseBox, i % numColumns, i / numColumns);
        }

        courseContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns, "notStarted");
        });

        courseGrid.setAlignment(Pos.CENTER);
        courseContainer.setAlignment(Pos.CENTER);

        courseContainer.getChildren().add(courseGrid);
    }

    public void loadCoursesSelection(String status) {
        setupCourseGrid();

        int numColumns = calculateColumns();

        courses = loadListCourses(status);

        for (int i = 0; i < courses.size(); i++) {
            VBox courseBox = createCourseBox(courses.get(i), status);
            courseGrid.add(courseBox, i % numColumns, i / numColumns);
        }

        courseContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns, status);
        });

        courseGrid.setAlignment(Pos.CENTER);
        courseContainer.setAlignment(Pos.CENTER);

        courseContainer.getChildren().add(courseGrid);
    }

    private List loadListCourses(String status) {
        List<Course> coursesSelection = new ArrayList<>();

        if (status.equals("notStarted")) {
            for (int i = 0; i < courses.size(); i++) {
                if (!myCourseStudent.searhCourse(courses.get(i).getTitle())) {
                    coursesSelection.add(courses.get(i));
                }
            }
        } else {
            for (int i = 0; i < courses.size(); i++) {
                if (myCourseStudent.searhCourseFilter(courses.get(i).getTitle(), status)) {
                    coursesSelection.add(courses.get(i));
                }
            }
        }
        return coursesSelection;
    }

    private void setupCourseGrid() {
        courseGrid.setHgap(20);
        courseGrid.setVgap(20);
        courseGrid.setPadding(new javafx.geometry.Insets(20));
        courseGrid.setMaxWidth(Double.MAX_VALUE);
        courseContainer.setMaxWidth(Double.MAX_VALUE);
    }

    private int calculateColumns() {
        double width = courseContainer.getWidth();
        return Math.max(1, (int) (width / 450));
    }

    private void reorganizeGrid(int numColumns, String status) {
        courseGrid.getChildren().clear();
        for (int i = 0; i < courses.size(); i++) {
            VBox courseBox = createCourseBox(courses.get(i), status);
            courseGrid.add(courseBox, i % numColumns, i / numColumns);
        }
    }

    private VBox createCourseBox(Course course, String status) {
        CourseSettings settings = reader.courseSettings(course.getTitle());
        VBox courseBox = new VBox();
        courseBox.getStyleClass().add("card");
        courseBox.setPrefWidth(500);

        VBox content = new VBox(12);
        content.setStyle("-fx-padding: 20;");

        ImageView courseImage = createCourseImage();

        if (status.equals("started") || status.equals("completed") || status.equals("todos")) {
            HBox tagContainer = new HBox();
            tagContainer.setAlignment(Pos.CENTER_RIGHT);
            tagContainer.setMaxWidth(Double.MAX_VALUE);
            VBox tag = createTag("started");
            tagContainer.getChildren().add(tag);
            content.getChildren().add(tagContainer);
        }

        Label categoryLabel = createStyledLabel(course.getCategoria().toUpperCase(), "Franklin Gothic Medium", 12);
        categoryLabel.getStyleClass().add("category");

        Label titleLabel = createStyledLabel(course.getTitle(), "Franklin Gothic Medium", 24);
        titleLabel.getStyleClass().add("title");

        Label authorLabel = createStyledLabel("Por " + course.getName(), "Franklin Gothic Medium", 14);
        authorLabel.getStyleClass().add("author");

        if (status.equals("notStarted")) {
            HBox courseInfo = createCourseInfo(course, settings);

            Label descLabel = createDescriptionLabel(course.getDescription());
            HBox statusInfo = createStatusInfo(settings);
            HBox buttonContainer = createButtonContainer(course, status);
            content.getChildren().addAll(courseImage, categoryLabel, titleLabel, authorLabel, courseInfo, descLabel,
                    statusInfo, buttonContainer);
        } else if (status.equals("started") || status.equals("completed") || status.equals("todos")) {
            HBox buttonContainer = createButtonContainer(course, status);
            ProgressBar progressBarCourse = new ProgressBar();
            progressBarCourse.getStyleClass().add("progress-bar");
            String cssFile = getClass().getResource("/com/progressbar.css").toExternalForm();
            progressBarCourse.getStylesheets().add(cssFile);
            progressBarCourse.getStyleClass().add("modern-progress-bar");

            progressBarCourse.setProgress(0.5);
            progressBarCourse.setPrefWidth(260);
            progressBarCourse.setPrefHeight(10);

            progressBarCourse.setPadding(new javafx.geometry.Insets(0));
            progressBarCourse.setBorder(null);

            Label progressLabel = createStyledLabel("50% Completo", "Franklin Gothic Medium", 14);
            progressLabel.getStyleClass().add("progress-label");

            content.getChildren().addAll(courseImage, categoryLabel, titleLabel, authorLabel, progressBarCourse,
                    progressLabel,
                    buttonContainer);
        }

        courseBox.getChildren().add(content);
        return courseBox;
    }

    private VBox createTag(String status) {
        VBox tag = new VBox();
        tag.setMaxWidth(Region.USE_PREF_SIZE); 
        Label tagLabel = createStyledLabel("Em Andamento", "Franklin Gothic Medium", 12);
        tagLabel.getStyleClass().add("tag-label");
        tag.getChildren().add(tagLabel);
        tag.getStyleClass().add("tag");
        return tag;
    }

    private ImageView createCourseImage() {
        ImageView courseImage = new ImageView();
        courseImage.setFitWidth(260);
        courseImage.setFitHeight(150);
        courseImage.setStyle(
                "-fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 5);");

        javafx.scene.effect.Reflection reflection = new javafx.scene.effect.Reflection();
        reflection.setFraction(0.2);
        courseImage.setEffect(reflection);
        return courseImage;
    }

    private HBox createCourseInfo(Course course, CourseSettings settings) {
        HBox courseInfo = new HBox(15);
        courseInfo.setAlignment(Pos.CENTER_LEFT);
        courseInfo.getChildren().addAll(
                createInfoLabel("⭐ " + course.getRating()),
                createInfoLabel(settings.getDurationTotal() + " horas"),
                createInfoLabel("Nível: " + course.getNivel()));

        courseInfo.getStyleClass().add("info");
        return courseInfo;
    }

    private Label createDescriptionLabel(String description) {
        Label descLabel = createStyledLabel(description, "Franklin Gothic Medium", 14);
        descLabel.setWrapText(true);

        javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(1000),
                descLabel);
        fade.setFromValue(0.7);
        fade.setToValue(1.0);
        fade.setCycleCount(javafx.animation.Animation.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();

        return descLabel;
    }

    private HBox createStatusInfo(CourseSettings settings) {
        HBox statusInfo = new HBox(15);
        statusInfo.setAlignment(Pos.CENTER_LEFT);

        if (settings.isCertificate()) {
            statusInfo.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.03); -fx-padding: 10; -fx-background-radius: 5;");
            Label certificateLabel = createStyledLabel("✓ Certificado", "Franklin Gothic Medium", 13);
            statusInfo.getChildren().add(certificateLabel);
        } else {
            statusInfo.setStyle(
                    "-fx-padding: 10; -fx-background-radius: 5;");
            Label certificateLabel = createStyledLabel(" ", "Franklin Gothic Medium", 13);
            statusInfo.getChildren().add(certificateLabel);
        }
        return statusInfo;
    }

    private HBox createButtonContainer(Course course, String status) {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));

        if (status.equals("notStarted")) {
            Button startButton = createStartButton(course);
            Button detailsButton = createDetailsButton(course);
            buttonContainer.getChildren().addAll(startButton, detailsButton);
        } else if (status.equals("started")) {
            Button continueButton = createContinueButton(course);
            buttonContainer.getChildren().addAll(continueButton);

        }

        return buttonContainer;
    }

    private Label createInfoLabel(String text) {
        Label label = createStyledLabel(text, "Franklin Gothic Medium", 14);
        label.getStyleClass().add("info-label");
        return label;
    }

    private Button createContinueButton(Course course) {
        Button button = new Button("Continuar Curso");
        button.getStyleClass().add("outline-button");
        button.setOnMouseClicked(e -> {
        });
        button.setPrefHeight(35);
        return button;
    }

    @SuppressWarnings("unused")
    private Button createDetailsButton(Course course) {
        Button button = new Button("Ver Detalhes");

        button.getStyleClass().add("outline-button");
        button.setStyle("-fx-border-radius: 20; -fx-background-radius: 20");
        button.setOnAction(e -> {
            new CourseDetailsModal(getDefaultWindow(), course);
        });
        return button;
    }

    private Window getDefaultWindow() {
        return Stage.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        if (!text.equals("Em Andamento")) {
            label.getStyleClass().add("label");

        }
        return label;
    }

    private Button createStartButton(Course course) {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("simple-button");
        button.setOnMouseClicked(e -> {
            try {
                myCourseStudent.addCourse(course);
            } catch (Exception ex) {
            }
        });
        button.setPrefHeight(35);
        return button;
    }
}
