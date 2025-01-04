package com.view.elements.Courses;

import java.io.File;
import java.util.List;

import com.controller.elements.LoadCoursesController;
import com.controller.elements.LoadMyCourses;
import com.dto.PaginaPrincipalDTO;
import com.singleton.UserSession;
import com.view.elements.Certificado.CertificateViewerModal;
import com.view.elements.Certificado.GeradorCertificado;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoadCourses {
    @FXML
    private VBox courseContainer;

    // LoadMyCourses paginaPrincipalController = new LoadMyCourses();
    // private List<PaginaPrincipalDTO> courses =
    // paginaPrincipalController.loadMyCourses();

    LoadCoursesController paginaPrincipalController = new LoadCoursesController();
    List<PaginaPrincipalDTO> courses = paginaPrincipalController.loadCourses();

    private final GridPane courseGrid = new GridPane();

    @SuppressWarnings("unused")
    public void loadCoursesNotStarted() {
        setupCourseGrid();

        int numColumns = calculateColumns();

        // courses = loadListCourses("notStarted");

        int i = 0;
        for (PaginaPrincipalDTO course : courses) {
            VBox courseBox = createCourseBox(course, "notStarted");
            courseGrid.add(courseBox, i % numColumns, i / numColumns);
            i++;
        }

        courseContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns, "notStarted");
        });

        courseGrid.setAlignment(Pos.CENTER);
        courseContainer.setAlignment(Pos.CENTER);

        courseContainer.getChildren().add(courseGrid);
    }

    public void loadCoursesStarted(String status) {
        setupCourseGrid();
        LoadMyCourses paginaMeusCursosController = new LoadMyCourses();

        switch (status) {
            case "todos":
                courses = paginaMeusCursosController.loadMyCourses();
                break;
            case "started":
                courses = paginaMeusCursosController.loadMyCoursesStarted();
                break;
            case "completed":
                courses = paginaMeusCursosController.loadMyCoursesCompleted();
                break;
            case "completedCertificado":
                courses = paginaMeusCursosController.loadMyCoursesCompleted();
                break;
        }

        int numColumns = calculateColumns();

        int i = 0;

        for (PaginaPrincipalDTO course : courses) {
            VBox courseBox = createCourseBox(course, "started");
            courseGrid.add(courseBox, i % numColumns, i / numColumns);
            i++;
        }

        courseContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns, "started");
        });

        courseGrid.setAlignment(Pos.CENTER);
        courseContainer.setAlignment(Pos.CENTER);

        courseContainer.getChildren().add(courseGrid);
    }

    public void loadCoursesSelection(String status) {
        setupCourseGrid();

        int numColumns = calculateColumns();

        // courses = loadListCourses(status);

        int i = 0;
        for (PaginaPrincipalDTO course : courses) {
            VBox courseBox = createCourseBox(course, status);
            courseGrid.add(courseBox, i % numColumns, i / numColumns);
            i++;
        }

        courseContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns, status);
        });

        courseGrid.setAlignment(Pos.CENTER);
        courseContainer.setAlignment(Pos.CENTER);

        courseContainer.getChildren().add(courseGrid);
    }

    /*
     * private List loadListCourses(String status) {
     * List<Course> coursesSelection = new ArrayList<>();
     * 
     * if (status.equals("notStarted")) {
     * for (int i = 0; i < courses.size(); i++) {
     * if (!myCourseStudent.searhCourse(courses.get(i).getTitle())) {
     * coursesSelection.add(courses.get(i));
     * }
     * }
     * } else {
     * for (int i = 0; i < courses.size(); i++) {
     * if (myCourseStudent.searhCourseFilter(courses.get(i).getTitle(), status)) {
     * coursesSelection.add(courses.get(i));
     * }
     * }
     * }
     * return coursesSelection;
     * }
     */

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
        int i = 0;
        for (PaginaPrincipalDTO course : courses) {
            VBox courseBox = createCourseBox(course, status);
            courseGrid.add(courseBox, i % numColumns, i / numColumns);
            i++;
        }
    }

    private VBox createCourseBox(PaginaPrincipalDTO course, String status) {
        VBox courseBox = new VBox();
        courseBox.getStyleClass().add("card");
        courseBox.setPrefWidth(500);

        VBox content = new VBox(12);
        content.setStyle("-fx-padding: 20;");

        ImageView courseImage = createCourseImage();

        if (status.equals("started") || status.equals("completed") || status.equals("todos")
                || status.equals("completedCertificado")) {
            HBox tagContainer = new HBox();
            tagContainer.setAlignment(Pos.CENTER_RIGHT);
            tagContainer.setMaxWidth(Double.MAX_VALUE);
            VBox tag = createTag(course);
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
            HBox courseInfo = createCourseInfo(course);

            Label descLabel = createDescriptionLabel(course.getDescription());
            HBox statusInfo = createStatusInfo(course);
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

            progressBarCourse.setProgress(course.getPercentual() / 100.0);
            progressBarCourse.setPrefWidth(260);
            progressBarCourse.setPrefHeight(10);

            progressBarCourse.setPadding(new javafx.geometry.Insets(0));
            progressBarCourse.setBorder(null);

            Label progressLabel = createStyledLabel(course.getPercentual() + "% Completo", "Franklin Gothic Medium",
                    14);
            progressLabel.getStyleClass().add("progress-label");

            content.getChildren().addAll(courseImage, categoryLabel, titleLabel, authorLabel, progressBarCourse,
                    progressLabel,
                    buttonContainer);
        }

        courseBox.getChildren().add(content);
        return courseBox;
    }

    private VBox createTag(PaginaPrincipalDTO course) {
        VBox tag = new VBox();
        tag.setMaxWidth(Region.USE_PREF_SIZE);
        if (course.getPercentual() == 100) {
            Label tagLabel = createStyledLabel("Concluído", "Franklin Gothic Medium", 12);
            tagLabel.getStyleClass().add("tag-label");
            tag.getChildren().add(tagLabel);
            tag.getStyleClass().add("tag");
            return tag;
        } else {
            Label tagLabel = createStyledLabel("Em Andamento", "Franklin Gothic Medium", 12);
            tagLabel.getStyleClass().add("tag-label");
            tag.getChildren().add(tagLabel);
            tag.getStyleClass().add("tag");
            return tag;
        }
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

    private HBox createCourseInfo(PaginaPrincipalDTO course) {
        HBox courseInfo = new HBox(15);
        courseInfo.setAlignment(Pos.CENTER_LEFT);
        courseInfo.getChildren().addAll(
                createInfoLabel("⭐ " + course.getRating()),
                createInfoLabel(course.getDurationTotal() + " horas"),
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

    private HBox createStatusInfo(PaginaPrincipalDTO course) {
        HBox statusInfo = new HBox(15);
        statusInfo.setAlignment(Pos.CENTER_LEFT);

        if (course.isCertificate()) {
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

    private HBox createButtonContainer(PaginaPrincipalDTO course, String status) {
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));

        if (status.equals("notStarted")) {
            Button startButton = createStartButton(course);
            Button detailsButton = createDetailsButton(course);
            buttonContainer.getChildren().addAll(startButton, detailsButton);
        } else if (status.equals("started")) {
            if (course.getPercentual() == 100) {
                Button certificadoButton = createCertificadoButton(course);
                buttonContainer.getChildren().add(certificadoButton);
            }
            Button continueButton = createContinueButton(course, status);
            buttonContainer.getChildren().addAll(continueButton);
        }

        return buttonContainer;
    }

    private Button createCertificadoButton(PaginaPrincipalDTO course) {
        Button button = new Button("Gerar Certificado");
        button.getStyleClass().add("simple-button");
        button.setOnMouseClicked(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Selecione o local para salvar o certificado");
            File selectedDirectory = directoryChooser.showDialog(getDefaultWindow());

            if (selectedDirectory != null) {
                String path = selectedDirectory.getAbsolutePath();
                GeradorCertificado gerador = new GeradorCertificado();
                gerador.gerarCertificado(UserSession.getInstance().getUserName(), course.getTitle(),
                        Integer.parseInt(course.getDurationTotal()), "Rio Pomba", selectedDirectory);
                loadCertificates(selectedDirectory, course);
            }
        });
        button.setPrefHeight(35);
        return button;
    }

    private void loadCertificates(File selectedDirectory, PaginaPrincipalDTO course) {
        CertificateViewerModal certificateViewer = new CertificateViewerModal(getDefaultWindow(), course,
                selectedDirectory);
    }

    private Label createInfoLabel(String text) {
        Label label = createStyledLabel(text, "Franklin Gothic Medium", 14);
        label.getStyleClass().add("info-label");
        return label;
    }

    private Button createContinueButton(PaginaPrincipalDTO course, String status) {
        if (course.getPercentual() != 100) {
            Button button = new Button("Continuar Curso");
            button.getStyleClass().add("outline-button");
            button.setOnMouseClicked(e -> {
            });
            button.setPrefHeight(35);
            return button;
        } else {
            Button button = new Button("Visualizar Curso");
            button.getStyleClass().add("outline-button");
            button.setOnMouseClicked(e -> {
            });
            button.setPrefHeight(35);
            return button;
        }
    }

    @SuppressWarnings("unused")
    private Button createDetailsButton(PaginaPrincipalDTO course) {
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

    private Button createStartButton(PaginaPrincipalDTO course) {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("simple-button");
        button.setOnMouseClicked(e -> {
            try {
                // myCourseStudent.addCourse(course);
            } catch (Exception ex) {
            }
        });
        button.setPrefHeight(35);
        return button;
    }
}