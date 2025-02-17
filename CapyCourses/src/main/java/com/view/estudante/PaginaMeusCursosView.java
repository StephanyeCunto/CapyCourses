package com.view.estudante;

import com.view.Modo;
import com.view.elements.Courses.LoadCourses;
import com.view.elements.MenuEstudante.Menu;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PaginaMeusCursosView implements Initializable {
  @FXML private VBox sideMenu;
  @FXML private HBox searchBar;
  @FXML private HBox featuredCourseCarousel;
  @FXML private VBox featuredCourseSection;
  @FXML private VBox courseContainer;
  @FXML private VBox carouselCourse;
  @FXML private ImageView sunIcon;
  @FXML private ImageView moonIcon;
  @FXML private StackPane thumbContainer;
  @FXML private Rectangle background;
  @FXML private HBox toggleButtonHBox;
  @FXML private Button themeToggleBtn;
  @FXML private StackPane toggleButtonStackPane;
  @FXML private GridPane container;
  @FXML private Button selectionTodos;
  @FXML private Button selectionStarted;
  @FXML private Button selectionCompleted;

  @SuppressWarnings("unused")
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    changeMode();
    toggleButtonStackPane.setOnMouseClicked(e -> toggle());
    sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
    moonIcon.setImage(
        new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
    toggleInitialize();

    loadCourses("todos");
    loadMenu();

    selectionTodos.setOnAction(
        e -> {
          buttonSelection("todos");
        });

    selectionStarted.setOnAction(
        e -> {
          buttonSelection("started");
        });

    selectionCompleted.setOnAction(
        e -> {
          buttonSelection("completed");
        });
  }

  private void buttonSelection(String selection) {
    courseContainer.getChildren().clear();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/courses.fxml"));
      VBox courseList = loader.load();
      LoadCourses course = loader.getController();
      // course.loadCourses(selection);
      courseContainer.getChildren().add(courseList);
      loadClass(selection);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadClass(String selection) {
    // Remove all selection classes from all buttons
    selectionTodos
        .getStyleClass()
        .removeAll("outline-button-seletion", "outline-button-not-seletion");
    selectionStarted
        .getStyleClass()
        .removeAll("outline-button-seletion", "outline-button-not-seletion");
    selectionCompleted
        .getStyleClass()
        .removeAll("outline-button-seletion", "outline-button-not-seletion");

    // Add appropriate classes based on selection
    if (selection.equals("todos")) {
      selectionTodos.getStyleClass().add("outline-button-seletion");
      selectionStarted.getStyleClass().add("outline-button-not-seletion");
      selectionCompleted.getStyleClass().add("outline-button-not-seletion");
      loadCourses("todos");

    } else if (selection.equals("started")) {
      selectionStarted.getStyleClass().add("outline-button-seletion");
      selectionTodos.getStyleClass().add("outline-button-not-seletion");
      selectionCompleted.getStyleClass().add("outline-button-not-seletion");

      loadCourses("started");
    } else {
      selectionCompleted.getStyleClass().add("outline-button-seletion");
      selectionTodos.getStyleClass().add("outline-button-not-seletion");
      selectionStarted.getStyleClass().add("outline-button-not-seletion");

      loadCourses("completed");
    }
  }

  /*
   * private void loadCourses(String status) {
   * try {
   * FXMLLoader loader = new
   * FXMLLoader(getClass().getResource("/com/elements/courses.fxml"));
   * VBox courseList = loader.load();
   * LoadCourses course = loader.getController();
   * // if(status.equals("started")) {
   * // course.loadCourses("started");
   * // } else if(status.equals("completed")) {
   * // course.loadCourses("completed");
   * // }else{
   * // course.loadCourses("todos");
   * // }
   *
   * // courseContainer.getChildren().add(courseList);
   *
   * } catch (IOException e) {
   * e.printStackTrace();
   * }
   * }
   */

  private void loadCourses(String status) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/courses.fxml"));
      VBox courseList = loader.load();
      LoadCourses course = loader.getController();
      course.loadCoursesStarted(status);
      courseContainer.getChildren().add(courseList);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadMenu() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/menu.fxml"));
      VBox menu = loader.load();
      Menu menuController = loader.getController();
      menuController.setCurrentPage("/com/estudante/meusCursos/paginaMeusCursos");
      menuController.configureMenu();
      sideMenu.getChildren().add(menu);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void toggle() {
    // Modo.getInstance().getModo() == dark
    Modo.getInstance().setModo();

    TranslateTransition thumbTransition =
        new TranslateTransition(Duration.millis(200), thumbContainer);
    thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
    thumbTransition.play();

    FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
    fillTransition.setFromValue(
        !Modo.getInstance().getModo() ? Color.web("#FFA500") : Color.web("#4169E1"));
    fillTransition.setToValue(
        !Modo.getInstance().getModo() ? Color.web("#4169E1") : Color.web("#FFA500"));
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
      TranslateTransition thumbTransition =
          new TranslateTransition(Duration.millis(200), thumbContainer);
      thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
      thumbTransition.play();
    } else {
      TranslateTransition thumbTransition =
          new TranslateTransition(Duration.millis(200), thumbContainer);
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
      container
          .getStylesheets()
          .add(
              getClass()
                  .getResource("/com/estudante/meusCursos/style/ligth/style.css")
                  .toExternalForm());
    } else {
      background.getStyleClass().remove("dark");
      container
          .getStylesheets()
          .add(
              getClass()
                  .getResource("/com/estudante/meusCursos/style/dark/style.css")
                  .toExternalForm());
    }
  }
}
