package com.view.elements.buttonModos;

import lombok.Setter;

import javafx.fxml.FXML;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import com.view.Modo;

@Setter
public class ButtonModos {
    @FXML
    private ImageView sunIcon;
    @FXML
    private ImageView moonIcon;
    @FXML
    private StackPane thumbContainer;
    @FXML
    private Rectangle background;
    @FXML
    private StackPane toggleButtonStackPane;
    @FXML
    private StackPane container;

    private static final Color LIGHT_MODE_COLOR = Color.web("#FFA500"); 
    private static final Color DARK_MODE_COLOR = Color.web("#4169E1");  
    private String darkStylePath;
    private String ligthStringPath;

    public void initialize() {
        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();
    }

    public void setContainer(StackPane container) {
        this.container = container;
    }

    private void toggle() {
        Modo.getInstance().setModo();
        
        TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
        thumbTransition.setToX(Modo.getInstance().getModo() ? -12.0 : 12.0);
        thumbTransition.play();

        FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
        fillTransition.setFromValue(!Modo.getInstance().getModo() ? LIGHT_MODE_COLOR : DARK_MODE_COLOR);
        fillTransition.setToValue(!Modo.getInstance().getModo() ? DARK_MODE_COLOR : LIGHT_MODE_COLOR);
        fillTransition.play();

        updateIconsVisibility();
        
        changeMode();
    }

    private void updateIconsVisibility() {
        sunIcon.setVisible(Modo.getInstance().getModo());
        moonIcon.setVisible(!Modo.getInstance().getModo());
        loadColor();
    }

    private void loadColor() {
        Color currentColor = !Modo.getInstance().getModo() ? DARK_MODE_COLOR : LIGHT_MODE_COLOR;
        background.setFill(currentColor);
    }

    private void toggleInitialize() {
        thumbContainer.setTranslateX(Modo.getInstance().getModo() ? -12.0 : 12.0);
        loadColor();
        updateIconsVisibility();
    }

    public void changeMode() {
        container.getStylesheets().clear();
        String stylePath = Modo.getInstance().getModo()
            ? darkStylePath
            : ligthStringPath;
        container.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
    }
}