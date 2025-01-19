package com.view.login_cadastro.elements;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SuccessNotification {
    private final HBox successBox;
    private final StackPane successContainer;
    private Timeline slideIn;
    private Timeline shakeTimeline;
    private PauseTransition pauseTransition;
    private FadeTransition fadeOut;

    @SuppressWarnings("exports")
    public SuccessNotification(StackPane parent, String message) {
        successContainer = new StackPane();
        successContainer.setPickOnBounds(false);
        successContainer.setMouseTransparent(true);
        successContainer.setTranslateY(50);
        successContainer.setStyle("-fx-border-radius: 8px;");

        successBox = createSuccessBox(message);
        
        successContainer.getChildren().add(successBox);
        StackPane.setAlignment(successBox, Pos.TOP_CENTER);
        successBox.setTranslateY(0);

        if (parent != null) {
            parent.getChildren().add(successContainer);
        }

        setupAnimations();
    }

    public SuccessNotification(@SuppressWarnings("exports") GridPane parent, String message) {
        successContainer = new StackPane();
        successContainer.setPickOnBounds(false);
        successContainer.setMouseTransparent(true);
        successContainer.setTranslateY(50);
        successContainer.setStyle("-fx-border-radius: 8px;");

        successBox = createSuccessBox(message);
        
        successContainer.getChildren().add(successBox);
        successBox.setTranslateY(0);
        if (parent != null) {
            parent.add(successContainer, 0, 0, parent.getColumnCount(), 1);
            GridPane.setHalignment(successContainer, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(successContainer, javafx.geometry.VPos.TOP);
            GridPane.setMargin(successContainer, new javafx.geometry.Insets(-750, 0, 0, 0));
        }

        setupAnimations();
    }

    private HBox createSuccessBox(String message) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setVisible(false);
        box.setStyle("-fx-background-color: #E5FFE5;" +
            "-fx-border-color: #44FF44;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 8px;" +
            "-fx-background-radius: 8px;" +
            "-fx-padding: 12px;" +
            "-fx-min-height: 45px;" +
            "-fx-max-height: 45px;" +
            "-fx-max-width: 350px;");

        Label successSymbol = new Label("✓");
        successSymbol.setStyle("-fx-text-fill: #44FF44; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold;");

        Label successMessage = new Label(message);
        successMessage.setStyle("-fx-text-fill: #44FF44; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: normal;");

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        shadow.setRadius(5);
        shadow.setOffsetY(2);
        box.setEffect(shadow);

        box.getChildren().addAll(successSymbol, successMessage);
        return box;
    }

    @SuppressWarnings("unused")
    private void setupAnimations() {
        slideIn = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(successBox.translateYProperty(), -20),
                new KeyValue(successBox.opacityProperty(), 0)
            ),
            new KeyFrame(Duration.millis(300),
                new KeyValue(successBox.translateYProperty(), 20),
                new KeyValue(successBox.opacityProperty(), 1)
            )
        );

        shakeTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(successBox.translateXProperty(), 0)),
            new KeyFrame(Duration.millis(100), new KeyValue(successBox.translateXProperty(), -10)),
            new KeyFrame(Duration.millis(200), new KeyValue(successBox.translateXProperty(), 10)),
            new KeyFrame(Duration.millis(300), new KeyValue(successBox.translateXProperty(), -10)),
            new KeyFrame(Duration.millis(400), new KeyValue(successBox.translateXProperty(), 0))
        );

        fadeOut = new FadeTransition(Duration.millis(500), successBox);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> successBox.setVisible(false));

        pauseTransition = new PauseTransition(Duration.seconds(5));
        pauseTransition.setOnFinished(event -> fadeOut.play());
    }

    public void show() {
        if (pauseTransition != null) pauseTransition.stop();
        if (fadeOut != null) fadeOut.stop();
        successBox.setVisible(true);
        successBox.setOpacity(0);
        slideIn.play();
        slideIn.setOnFinished(e -> {
            shakeTimeline.play();
            pauseTransition.play();
        });
    }

    public void hide() {
        fadeOut.play();
    }

    @SuppressWarnings("exports")
    public StackPane getContainer() {
        return successContainer;
    }
} 