package com.view.login_cadastro.elements;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ErrorNotification {
  private final HBox errorBox;
  private final StackPane errorContainer;
  private Timeline slideIn;
  private Timeline shakeTimeline;
  private PauseTransition pauseTransition;
  private FadeTransition fadeOut;

  @SuppressWarnings("exports")
  public ErrorNotification(StackPane parent, String error) {
    errorContainer = new StackPane();
    errorContainer.setPickOnBounds(false);
    errorContainer.setMouseTransparent(true);
    errorContainer.setTranslateY(50);
    errorContainer.setStyle(" -fx-border-radius: 8px;");

    errorBox = createErrorBox(error);

    errorContainer.getChildren().add(errorBox);
    StackPane.setAlignment(errorBox, Pos.TOP_CENTER);
    errorBox.setTranslateY(0);

    if (parent != null) {
      parent.getChildren().add(errorContainer);
    }

    setupAnimations();
  }

  public ErrorNotification(@SuppressWarnings("exports") GridPane parent, String error) {
    errorContainer = new StackPane();
    errorContainer.setPickOnBounds(false);
    errorContainer.setMouseTransparent(true);
    errorContainer.setTranslateY(50);
    errorContainer.setStyle("-fx-border-radius: 8px;");

    errorBox = createErrorBox(error);

    errorContainer.getChildren().add(errorBox);
    errorBox.setTranslateY(0);
    if (parent != null) {
      parent.add(errorContainer, 0, 0, parent.getColumnCount(), 1);
      GridPane.setHalignment(errorContainer, javafx.geometry.HPos.CENTER);
      GridPane.setValignment(errorContainer, javafx.geometry.VPos.TOP);
      GridPane.setMargin(errorContainer, new javafx.geometry.Insets(-750, 0, 0, 0));
    }

    setupAnimations();
  }

  private HBox createErrorBox(String error) {
    HBox box = new HBox();
    box.setAlignment(Pos.CENTER);
    box.setSpacing(10);
    box.setVisible(false);
    box.setStyle(
        "-fx-background-color: #FFE5E5;"
            + "-fx-border-color: #FF4444;"
            + "-fx-border-width: 1px;"
            + "-fx-border-radius: 8px;"
            + "-fx-background-radius: 8px;"
            + "-fx-padding: 12px;"
            + "-fx-min-height: 45px;"
            + "-fx-max-height: 45px;"
            + "-fx-max-width: 350px;");

    Label errorSymbol = new Label("✕");
    errorSymbol.setStyle(
        "-fx-text-fill: #FF4444; " + "-fx-font-size: 16px; " + "-fx-font-weight: bold;");

    Label errorMessage = new Label(error);
    errorMessage.setStyle(
        "-fx-text-fill: #FF4444; " + "-fx-font-size: 14px; " + "-fx-font-weight: normal;");

    DropShadow shadow = new DropShadow();
    shadow.setColor(Color.rgb(0, 0, 0, 0.2));
    shadow.setRadius(5);
    shadow.setOffsetY(2);
    box.setEffect(shadow);

    box.getChildren().addAll(errorSymbol, errorMessage);
    return box;
  }

  @SuppressWarnings("unused")
  private void setupAnimations() {
    slideIn =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(errorBox.translateYProperty(), -20),
                new KeyValue(errorBox.opacityProperty(), 0)),
            new KeyFrame(
                Duration.millis(300),
                new KeyValue(errorBox.translateYProperty(), 20),
                new KeyValue(errorBox.opacityProperty(), 1)));

    shakeTimeline =
        new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(errorBox.translateXProperty(), 0)),
            new KeyFrame(Duration.millis(100), new KeyValue(errorBox.translateXProperty(), -10)),
            new KeyFrame(Duration.millis(200), new KeyValue(errorBox.translateXProperty(), 10)),
            new KeyFrame(Duration.millis(300), new KeyValue(errorBox.translateXProperty(), -10)),
            new KeyFrame(Duration.millis(400), new KeyValue(errorBox.translateXProperty(), 0)));

    fadeOut = new FadeTransition(Duration.millis(500), errorBox);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setOnFinished(e -> errorBox.setVisible(false));

    pauseTransition = new PauseTransition(Duration.seconds(5));
    pauseTransition.setOnFinished(event -> fadeOut.play());
  }

  @SuppressWarnings("unused")
  public void show() {
    if (pauseTransition != null) pauseTransition.stop();
    if (fadeOut != null) fadeOut.stop();
    errorBox.setVisible(true);
    errorBox.setOpacity(0);
    slideIn.play();
    slideIn.setOnFinished(
        e -> {
          shakeTimeline.play();
          pauseTransition.play();
        });
  }

  public void hide() {
    fadeOut.play();
  }

  @SuppressWarnings("exports")
  public StackPane getContainer() {
    return errorContainer;
  }
}
