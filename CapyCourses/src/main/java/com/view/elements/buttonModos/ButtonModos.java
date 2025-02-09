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
    @FXML private ImageView sunIcon;
    @FXML private ImageView moonIcon;
    @FXML private StackPane thumbContainer;
    @FXML private Rectangle background;
    @FXML private StackPane toggleButtonStackPane;
    @FXML private StackPane container;

    private static final Color LIGHT_MODE_COLOR = Color.web("#FFA500");
    private static final Color DARK_MODE_COLOR = Color.web("#4169E1");
    private static final double ICON_SIZE = 20.0; 
    private String darkStylePath;
    private String ligthStringPath;

    public void initialize() {
        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        setupIcons();
        toggleInitialize();
        loadCSS();
    }

    private void setupIcons() {
        Image sunImage = new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png"));
        Image moonImage = new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png"));

        sunIcon.setImage(sunImage);
        sunIcon.setFitWidth(ICON_SIZE);
        sunIcon.setFitHeight(ICON_SIZE);
        sunIcon.setPreserveRatio(true);
        sunIcon.setSmooth(true);

        moonIcon.setImage(moonImage);
        moonIcon.setFitWidth(ICON_SIZE);
        moonIcon.setFitHeight(ICON_SIZE);
        moonIcon.setPreserveRatio(true);
        moonIcon.setSmooth(true);

        StackPane.setAlignment(sunIcon, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(moonIcon, javafx.geometry.Pos.CENTER);
    }

    public void setContainer(StackPane container) {
        this.container = container;
    }

    private void toggle() {
        Modo.getInstance().setModo();
        
        TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
        thumbTransition.setToX(Modo.isDarkMode() ? -12.0 : 12.0);
        thumbTransition.play();

        FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
        fillTransition.setFromValue(!Modo.isDarkMode() ? LIGHT_MODE_COLOR : DARK_MODE_COLOR);
        fillTransition.setToValue(!Modo.isDarkMode() ? DARK_MODE_COLOR : LIGHT_MODE_COLOR);
        fillTransition.play();

        updateIconsVisibility();
        changeMode();
    }

    private void updateIconsVisibility() {
        sunIcon.setVisible(Modo.isDarkMode());
        moonIcon.setVisible(!Modo.isDarkMode());
        loadColor();
    }

    private void loadColor() {
        Color currentColor = !Modo.isDarkMode() ? DARK_MODE_COLOR : LIGHT_MODE_COLOR;
        background.setFill(currentColor);
    }

    private void toggleInitialize() {
        thumbContainer.setTranslateX(Modo.isDarkMode() ? -12.0 : 12.0);
        thumbContainer.setMinSize(ICON_SIZE + 4, ICON_SIZE + 4); 
        thumbContainer.setMaxSize(ICON_SIZE + 4, ICON_SIZE + 4);
        
        loadColor();
        updateIconsVisibility();
    }

    public void changeMode() {
        container.getStylesheets().clear();
        String stylePath = Modo.isDarkMode() ? darkStylePath : ligthStringPath;
        container.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
    }

    private void loadCSS() {
        thumbContainer.getStylesheets().add(
            getClass().getResource("/com/elements/styleButton/styleButton.css").toExternalForm()
        );
    }
}