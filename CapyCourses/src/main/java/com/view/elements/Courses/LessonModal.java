package com.view.elements.Courses;

import com.view.Modo;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;

public class LessonModal {
  private final Stage modalStage;
  private double WIDTH;
  private double HEIGHT;
  private static final double CONTENT_SPACING = 25;
  private static final double PADDING = 30;
  private static final double HEADER_SPACING = 20;
  private String currentTitle = "";
  private String currentDescription = "";
  private String currentVideoUrl = "";
  private String currentMaterialUrl = "";

  public void updateDimensions(double WIDTH, double HEIGHT) {
    this.WIDTH = WIDTH;
    this.HEIGHT = HEIGHT;
  }

  public LessonModal(Window owner) {
    updateDimensions(owner.getWidth(), owner.getHeight());
    modalStage = new Stage();
    modalStage.initModality(Modality.APPLICATION_MODAL);
    modalStage.initStyle(StageStyle.TRANSPARENT);
    modalStage.initOwner(owner);
    setupCloseAnimation();
  }

  private void loadModel() {
    // Remover este método ou deixá-lo vazio
  }

  private void showModel() {
    StackPane backdrop = createBackdrop();
    VBox modalContainer = createModalContainer();

    // Create main content sections
    VBox header = createHeader();
    VBox content = createContent();
    VBox footer = createFooter();

    modalContainer.getChildren().addAll(header, content, footer);
    backdrop.getChildren().add(modalContainer);

    Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
    scene.setFill(Color.TRANSPARENT);
    modo(scene);

    modalStage.setScene(scene);
    modalStage.showAndWait();
  }

  private VBox createModalContainer() {
    VBox container = new VBox(CONTENT_SPACING);
    container.setPadding(new Insets(PADDING));
    container.setMaxWidth(WIDTH * 0.8);
    container.setMaxHeight(HEIGHT * 0.8);
    container.setMinHeight(HEIGHT * 0.8);
    container.setMinWidth(WIDTH * 0.8);
    container.getStyleClass().add("card");
    container.setEffect(createDropShadow());
    return container;
  }

  private VBox createContent() {
    VBox content = new VBox(CONTENT_SPACING);
    content.setPadding(new Insets(0, PADDING, PADDING, PADDING));
    content.setAlignment(Pos.TOP_LEFT);

    Label descriptionLabel = createStyledLabel(currentDescription, "Segoe UI", 18);
    descriptionLabel.getStyleClass().add("initials-label");

    Label videoAulaLabel = createVideoAulaLabel(currentVideoUrl, "Video Aula");
    videoAulaLabel.getStyleClass().add("page-title");

    Label complementoLabel = createVideoAulaLabel(currentMaterialUrl, "Material Complementar");
    complementoLabel.getStyleClass().add("page-title");

    Button completeButton = new Button("Marca Como Concluída");
    completeButton.getStyleClass().add("simple-button");

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    content
        .getChildren()
        .addAll(
            descriptionLabel,
            createSpacer(15),
            videoAulaLabel,
            createSpacer(10),
            complementoLabel,
            spacer,
            completeButton);

    return content;
  }

  private Region createSpacer(double height) {
    Region spacer = new Region();
    spacer.setMinHeight(height);
    return spacer;
  }

  private Label createVideoAulaLabel(String videoUrl, String title) {
    Label videoAula = new Label(title);

    videoAula.setOnMouseEntered(
        e -> {
          videoAula.getStyleClass().remove("page-title");
          videoAula.getStyleClass().add("page");
        });

    videoAula.setOnMouseExited(
        e -> {
          videoAula.getStyleClass().remove("page");
          videoAula.getStyleClass().add("page-title");
        });

    videoAula.setOnMouseClicked(
        e -> {
          try {
            Desktop.getDesktop().browse(new URI(videoUrl));
          } catch (IOException | URISyntaxException ex) {
            System.err.println("Erro ao abrir o link: " + ex.getMessage());
          }
        });

    return videoAula;
  }

  private VBox createHeader() {
    VBox header = new VBox(HEADER_SPACING);
    header.setPadding(new Insets(PADDING, PADDING, 0, PADDING));

    Label titleLabel = createStyledLabel(currentTitle, "Segue UI Bold", 28);
    titleLabel.getStyleClass().add("title");

    Button closeButton = new Button("X");
    closeButton.getStyleClass().add("register-button");
    closeButton.setOnAction(e -> modalStage.close());

    HBox headerContainer = new HBox();
    headerContainer.setAlignment(Pos.CENTER_LEFT);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    headerContainer.getChildren().addAll(titleLabel, spacer, closeButton);
    header.getChildren().add(headerContainer);
    return header;
  }

  private VBox createFooter() {
    VBox footer = new VBox(CONTENT_SPACING);
    footer.setAlignment(Pos.BOTTOM_CENTER);
    VBox.setVgrow(footer, Priority.ALWAYS);
    return footer;
  }

  private StackPane createBackdrop() {
    StackPane backdrop = new StackPane();
    backdrop.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
    backdrop.setOnMouseClicked(
        event -> {
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

  private Label createStyledLabel(String text, String fontFamily, double fontSize) {
    Label label = new Label(text);
    label.setFont(Font.font(fontFamily, fontSize));
    label.setWrapText(true);
    return label;
  }

  private void modo(Scene scene) {
    String styleSheet =
        Modo.getInstance().getModo()
            ? "/com/estudante/forum/style/dark/style.css"
            : "/com/estudante/forum/style/ligth/style.css";
    scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
  }

  private void setupCloseAnimation() {
    modalStage.setOnCloseRequest(
        event -> {
          event.consume();
          if (modalStage.getScene() == null) return;

          FadeTransition fade =
              new FadeTransition(Duration.millis(200), modalStage.getScene().getRoot());
          fade.setFromValue(1.0);
          fade.setToValue(0.0);
          fade.setOnFinished(e -> modalStage.hide());
          fade.play();
        });
  }

  public void show() {
    // Criar e mostrar o modal apenas quando tivermos os dados
    showModel();
  }

  public void setLessonData(String title, String description, String videoUrl, String materialUrl) {
    this.currentTitle = title;
    this.currentDescription = description;
    this.currentVideoUrl = videoUrl;
    this.currentMaterialUrl = materialUrl;
  }

  private void updateLink(Label label, String url) {
    label.setOnMouseClicked(
        e -> {
          try {
            Desktop.getDesktop().browse(new URI(url));
          } catch (IOException | URISyntaxException ex) {
            System.err.println("Erro ao abrir o link: " + ex.getMessage());
          }
        });
  }
}
