package com.view.login_cadastro.base;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class Inicializar {
  public void loadAnimation(
      GridPane mainPane, StackPane container, VBox leftSection, VBox rightSection) {
    mainPane.setTranslateX(-1000);
    mainPane.setOpacity(0);
    leftSection.setTranslateX(-500);
    leftSection.setOpacity(0);
    rightSection.setTranslateX(-500);
    rightSection.setOpacity(0);

    animateNodeWithDelay(mainPane, 1000, 100);
    animateNodeWithDelay(leftSection, 500, 300);
    animateNodeWithDelay(rightSection, 500, 500);
  }

  private void animateNode(Node node, double distance, int delay) {
    TranslateTransition translate = new TranslateTransition(Duration.millis(2000), node);
    translate.setDelay(Duration.millis(delay));
    translate.setFromX(-distance);
    translate.setToX(0);
    translate.setInterpolator(Interpolator.EASE_OUT);
    FadeTransition fade = new FadeTransition(Duration.millis(1200), node);
    fade.setDelay(Duration.millis(delay));
    fade.setFromValue(0);
    fade.setToValue(1);
    fade.setInterpolator(Interpolator.EASE_OUT);
    translate.play();
    fade.play();
  }

  private void animateNodeWithDelay(Node node, double distance, int delay) {
    animateNode(node, distance, delay);
  }
}
