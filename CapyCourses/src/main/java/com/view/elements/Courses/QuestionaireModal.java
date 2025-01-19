package com.view.elements.Courses;

import com.view.Modo;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class QuestionaireModal {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;
    private static final double CONTENT_SPACING = 25;
    private static final double PADDING = 30;
    private static final double HEADER_SPACING = 20;

    public void updateDimensions(double WIDTH, double HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public QuestionaireModal(Window owner) {
        updateDimensions(owner.getWidth(), owner.getHeight());
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initOwner(owner);
        loadModel();
        setupCloseAnimation();
    }

    private void loadModel() {
        showModel();
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

        Label descriptionLabel = createStyledLabel(
            "Como configurar o ambiente para java no vscode?", 
            "Segoe UI", 
            18
        );
        descriptionLabel.getStyleClass().add("initials-label");

     

        Button completeButton = new Button("Marca Como Concluída");
        completeButton.getStyleClass().add("simple-button");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        content.getChildren().addAll(
            descriptionLabel,
            createSpacer(15),
            createSpacer(10),
            spacer,
            completeButton
        );

        return content;
    }

    private Region createSpacer(double height) {
        Region spacer = new Region();
        spacer.setMinHeight(height);
        return spacer;
    }

    private VBox createHeader() {
        VBox header = new VBox(HEADER_SPACING);
        header.setPadding(new Insets(PADDING, PADDING, 0, PADDING));

        Label titleLabel = createStyledLabel(
            "Aula 1 - Introdução a Java",
            "Segue UI Bold",
            28
        );
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
        backdrop.setOnMouseClicked(event -> {
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
        String styleSheet = Modo.getInstance().getModo() 
            ? "/com/estudante/forum/style/dark/style.css"
            : "/com/estudante/forum/style/ligth/style.css";
        scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }

    private void setupCloseAnimation() {
        modalStage.setOnCloseRequest(event -> {
            event.consume();
            if (modalStage.getScene() == null) return;
            
            FadeTransition fade = new FadeTransition(
                Duration.millis(200), 
                modalStage.getScene().getRoot()
            );
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> modalStage.hide());
            fade.play();
        });
    }

    public void show() {
        modalStage.show();
    }
}
