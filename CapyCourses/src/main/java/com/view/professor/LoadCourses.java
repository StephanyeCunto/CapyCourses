package com.view.professor;

import com.controller.elements.LoadCoursesController;
import com.controller.elements.LoadMyCourses;
import com.dto.PaginaPrincipalDTO;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoadCourses {

  LoadCoursesController paginaPrincipalController = new LoadCoursesController();
  List<PaginaPrincipalDTO> courses = paginaPrincipalController.loadCourses();

  private final GridPane courseGrid = new GridPane();

  public GridPane loadCoursesStarted() {
    setupCourseGrid();
    LoadMyCourses paginaMeusCursosController = new LoadMyCourses();

    courses = paginaMeusCursosController.loadProfessorCourses();

    int numColumns = calculateColumns();

    int i = 0;

    for (PaginaPrincipalDTO course : courses) {
      VBox courseBox = createCourseBox(course);
      courseGrid.add(courseBox, i % numColumns, i / numColumns);
      i++;
    }

    courseGrid
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              int columns = calculateColumns();
              reorganizeGrid(columns);
            });

    courseGrid.setAlignment(Pos.CENTER);

    return courseGrid;
  }

  private void setupCourseGrid() {
    courseGrid.setHgap(20);
    courseGrid.setVgap(20);
    courseGrid.setPadding(new javafx.geometry.Insets(20));
    courseGrid.setMaxWidth(Double.MAX_VALUE);
  }

  private int calculateColumns() {
    double width = courseGrid.getWidth();
    return Math.max(1, (int) (width / 450));
  }

  private void reorganizeGrid(int numColumns) {
    courseGrid.getChildren().clear();
    int i = 0;
    for (PaginaPrincipalDTO course : courses) {
      VBox courseBox = createCourseBox(course);
      courseGrid.add(courseBox, i % numColumns, i / numColumns);
      i++;
    }
  }

  private VBox createCourseBox(PaginaPrincipalDTO course) {
    VBox courseBox = new VBox();
    courseBox.getStyleClass().add("card");
    courseBox.setPrefWidth(500);

    VBox content = new VBox(12);
    content.setStyle("-fx-padding: 20;");

    ImageView courseImage = createCourseImage();

    Label categoryLabel =
        createStyledLabel(course.getCategoria().toUpperCase(), "Franklin Gothic Medium", 12);
    categoryLabel.getStyleClass().add("category");

    Label titleLabel = createStyledLabel(course.getTitle(), "Franklin Gothic Medium", 24);
    titleLabel.getStyleClass().add("title");

    HBox courseInfo = createCourseInfo(course);
    Label descLabel = createDescriptionLabel(course.getDescription());
    HBox statusInfo = createStatusInfo(course);
    HBox buttonContainer = createButtonContainer(course);
    content
        .getChildren()
        .addAll(
            courseImage,
            categoryLabel,
            titleLabel,
            courseInfo,
            descLabel,
            statusInfo,
            buttonContainer);

    courseBox.getChildren().add(content);
    return courseBox;
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
    courseInfo
        .getChildren()
        .addAll(
            createInfoLabel("⭐ " + course.getRating()),
            createInfoLabel(course.getDurationTotal() + " horas"),
            createInfoLabel("Nível: " + course.getNivel()));

    courseInfo.getStyleClass().add("info");
    return courseInfo;
  }

  private Label createDescriptionLabel(String description) {
    Label descLabel = createStyledLabel(description, "Franklin Gothic Medium", 14);
    descLabel.setWrapText(true);

    javafx.animation.FadeTransition fade =
        new javafx.animation.FadeTransition(javafx.util.Duration.millis(1000), descLabel);
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
      statusInfo.setStyle("-fx-padding: 10; -fx-background-radius: 5;");
      Label certificateLabel = createStyledLabel(" ", "Franklin Gothic Medium", 13);
      statusInfo.getChildren().add(certificateLabel);
    }
    return statusInfo;
  }

  private HBox createButtonContainer(PaginaPrincipalDTO course) {
    HBox buttonContainer = new HBox(15);
    buttonContainer.setAlignment(Pos.CENTER_LEFT);
    buttonContainer.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));

    Button editButton = createEditButton(course);
    Button viewButton = createViewButton(course);
    buttonContainer.getChildren().addAll(viewButton, editButton);

    return buttonContainer;
  }

  private void redirectTo(Button button) {
    try {
      Stage stage = (Stage) button.getScene().getWindow();
      String pageNext = "/com/estudante/progresso/paginaProgressoCurso.fxml";

      Parent root = FXMLLoader.load(getClass().getResource(pageNext));
      Scene currentScene = stage.getScene();
      Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());

      stage.setScene(newScene);
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Label createInfoLabel(String text) {
    Label label = createStyledLabel(text, "Franklin Gothic Medium", 14);
    label.getStyleClass().add("info-label");
    return label;
  }

  private Window getDefaultWindow() {
    return Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
  }

  private Label createStyledLabel(String text, String fontFamily, double fontSize) {
    Label label = new Label(text);
    label.setFont(Font.font(fontFamily, fontSize));
    if (!text.equals("Em Andamento")) {
      label.getStyleClass().add("label");
    }
    return label;
  }

  private Button createEditButton(PaginaPrincipalDTO course) {
    Button button = new Button("Editar Curso");
    button.getStyleClass().add("simple-button");

    button.setPrefHeight(35);
    return button;
  }

  private Button createViewButton(PaginaPrincipalDTO course) {
    Button button = new Button("Ver Curso");
    button.getStyleClass().add("outline-button");

    button.setPrefHeight(35);
    return button;
  }
}
