package com.view.elements.Forum;

import com.dto.ForumDTO;
import com.view.Modo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PaginaDoForumView implements Initializable {
  @FXML private Button themeToggleBtn;
  @FXML private StackPane toggleButtonStackPane;
  @FXML private ImageView sunIcon;
  @FXML private ImageView moonIcon;
  @FXML private GridPane container;
  @FXML private Rectangle background;
  @FXML private StackPane thumbContainer;
  @FXML private VBox forumContainer;

  public void initialize(URL location, ResourceBundle resources) {
    changeMode();
    toggleButtonStackPane.setOnMouseClicked(e -> toggle());
    sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
    moonIcon.setImage(
        new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
    toggleInitialize();
  }

  private void changeMode() {
    container.getStylesheets().clear();
    if (!Modo.getInstance().getModo()) {
      background.getStyleClass().add("dark");
      container
          .getStylesheets()
          .add(
              getClass()
                  .getResource("/com/estudante/forum/style/ligth/style.css")
                  .toExternalForm());
    } else {
      background.getStyleClass().remove("dark");
      container
          .getStylesheets()
          .add(
              getClass().getResource("/com/estudante/forum/style/dark/style.css").toExternalForm());
    }
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

  public void back() {
    try {
      FXMLLoader loader =
          new FXMLLoader(LoadForumView.class.getResource("/com/estudante/forum/paginaForum.fxml"));
      Parent root = loader.load();
      Scene scene = container.getScene();
      scene.setRoot(root);
    } catch (Exception e) {
      System.err.println("Error loading forum page: " + e.getMessage());
    }
  }

  public void setForumData(ForumDTO forum) {
    try {
      ForumDetailView forumDetailView = new ForumDetailView(forum);
      forumContainer.getChildren().clear();
      forumContainer.getChildren().add(forumDetailView.getView());
    } catch (Exception e) {
      System.err.println("Erro ao criar ForumDetailView: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
