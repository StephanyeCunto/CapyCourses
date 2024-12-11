package com.view.estudante;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.image.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.view.Modo;
import com.view.elements.LoadCourses;
import com.view.elements.Menu;

public class PaginaMeusCursosView implements Initializable {
    @FXML
    private VBox sideMenu;
    @FXML
    private HBox searchBar;
    @FXML
    private HBox featuredCourseCarousel;
    @FXML
    private VBox featuredCourseSection;
    @FXML
    private VBox courseContainer;
    @FXML
    private VBox carouselCourse;
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

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeMode();
        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();

        loadCourses();
        loadMenu();
    }

    private void loadCourses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/courses.fxml"));
            VBox courseList = loader.load();
            LoadCourses course = loader.getController();
            course.loadCoursesStarted("started");
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
                    .add(getClass().getResource("/com/estudante/paginaInicial/style/ligth/style.css").toExternalForm());
        } else {
            background.getStyleClass().remove("dark");
            container.getStylesheets()
                    .add(getClass().getResource("/com/estudante/paginaInicial/style/dark/style.css").toExternalForm());
        }
    }
}