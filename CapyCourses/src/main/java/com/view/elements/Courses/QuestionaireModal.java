package com.view.elements.Courses;

import com.view.Modo;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
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


        Label pergunta1 = createStyledLabel(
            "Como configurar o ambiente para java no vscode?", 
            "Segoe UI", 
            18
        );

        HBox questionNumberBox1 = new HBox(10);
        questionNumberBox1.setAlignment(Pos.CENTER_LEFT);

        StackPane circleContainer = new StackPane();
        Region circle = new Region();
        circle.setStyle(" -fx-background-radius: 50%; -fx-min-width: 30; -fx-min-height: 30; -fx-max-width: 30; -fx-max-height: 30;");
        circle.getStyleClass().add("avatar-circle");

        Label numberLabel = new Label("1");
        numberLabel.setTextFill(Color.WHITE);
        numberLabel.setStyle("-fx-font-weight: bold;");

        circleContainer.getChildren().addAll(circle, numberLabel);
        questionNumberBox1.getChildren().addAll(circleContainer, pergunta1);


        pergunta1.getStyleClass().add("initials-label");

        Label valor1= createStyledLabel(
            "1 pts", 
            "Segoe UI", 
            14
        );

        valor1.getStyleClass().add("progress-label");

        TextArea resposta1_1 = new TextArea();
        resposta1_1.getStyleClass().add("custom-text-area");

        Label pergunta2 = createStyledLabel("Qual a máquina virtual do Java?","Segoe UI", 18);
        pergunta2.getStyleClass().add("initials-label");

        HBox questionNumberBox2 = new HBox(10);
        questionNumberBox2.setAlignment(Pos.CENTER_LEFT);

        StackPane circleContainer2 = new StackPane();
        Region circle2 = new Region();
        circle2.setStyle(" -fx-background-radius: 50%; -fx-min-width: 30; -fx-min-height: 30; -fx-max-width: 30; -fx-max-height: 30;");
        circle2.getStyleClass().add("avatar-circle");

        Label numberLabel2 = new Label("2");
        numberLabel2.setTextFill(Color.WHITE);
        numberLabel2.setStyle("-fx-font-weight: bold;");

        circleContainer2.getChildren().addAll(circle2, numberLabel2);
        questionNumberBox2.getChildren().addAll(circleContainer2, pergunta2);


        Label valor2= createStyledLabel(
            "2 pts", 
            "Segoe UI", 
            14
        );

        valor2.getStyleClass().add("progress-label");

        HBox resposta1_2HBox = new HBox(10);
        CheckBox resposta1_2CheckBox = new CheckBox();
        resposta1_2CheckBox.getStyleClass().add("custom-checkbox");

        Label resposta1_2Label = createStyledLabel("JVM (Java Virtual Machine)", "Segoe UI", 14);
        resposta1_2Label.getStyleClass().add("professor-name");
        resposta1_2HBox.getChildren().addAll(resposta1_2CheckBox, resposta1_2Label);

        HBox resposta2_2HBox = new HBox(10);
        CheckBox resposta2_2CheckBox = new CheckBox();
        resposta2_2CheckBox.getStyleClass().add("custom-checkbox");

        Label resposta2_2Label = createStyledLabel("JRE (Java Runtime Environment)", "Segoe UI", 14);
        resposta2_2Label.getStyleClass().add("professor-name");
        resposta2_2HBox.getChildren().addAll(resposta2_2CheckBox, resposta2_2Label);

        HBox resposta3_2HBox = new HBox(10);
        CheckBox resposta3_2CheckBox = new CheckBox();
        resposta3_2CheckBox.getStyleClass().add("custom-checkbox");

        Label resposta3_2Label = createStyledLabel("JDK (Java Development Kit)", "Segoe UI", 14);
        resposta3_2Label.getStyleClass().add("professor-name");
        resposta3_2HBox.getChildren().addAll(resposta3_2CheckBox, resposta3_2Label);

        VBox alternativasBox = new VBox(10);
        alternativasBox.getChildren().addAll(resposta1_2HBox, resposta2_2HBox, resposta3_2HBox);

        Label pergunta3 = createStyledLabel("Qual a máquina virtual do Java?","Segoe UI", 18);
        pergunta3.getStyleClass().add("initials-label");


        HBox questionNumberBox3 = new HBox(10);
        questionNumberBox3.setAlignment(Pos.CENTER_LEFT);

        StackPane circleContainer3 = new StackPane();
        Region circle3 = new Region();
        circle3.setStyle(" -fx-background-radius: 50%; -fx-min-width: 30; -fx-min-height: 30; -fx-max-width: 30; -fx-max-height: 30;");
        circle3.getStyleClass().add("avatar-circle");

        Label numberLabel3 = new Label("3");
        numberLabel3.setTextFill(Color.WHITE);
        numberLabel3.setStyle("-fx-font-weight: bold;");

        circleContainer3.getChildren().addAll(circle3, numberLabel3);
        questionNumberBox3.getChildren().addAll(circleContainer3, pergunta3);
        

        Label valor3= createStyledLabel(
            "2 pts", 
            "Segoe UI", 
            14
        );

        valor3.getStyleClass().add("progress-label");

        HBox resposta1_3HBox = new HBox(10);
        RadioButton resposta1_3Radio = new RadioButton();
        resposta1_3Radio.getStyleClass().add("custom-radio");
        Label resposta1_3Label = createStyledLabel("JVM (Java Virtual Machine)", "Segoe UI", 14);
        resposta1_3Label.getStyleClass().add("professor-name");
        resposta1_3HBox.getChildren().addAll(resposta1_3Radio, resposta1_3Label);

        HBox resposta2_3HBox = new HBox(10);
        RadioButton resposta2_3Radio = new RadioButton();
        resposta2_3Radio.getStyleClass().add("custom-radio");
        Label resposta2_3Label = createStyledLabel("JRE (Java Runtime Environment)", "Segoe UI", 14);
        resposta2_3Label.getStyleClass().add("professor-name");
        resposta2_3HBox.getChildren().addAll(resposta2_3Radio, resposta2_3Label);

        HBox resposta3_3HBox = new HBox(10);
        RadioButton resposta3_3Radio = new RadioButton();
        resposta3_3Radio.getStyleClass().add("custom-radio");
        Label resposta3_3Label = createStyledLabel("JDK (Java Development Kit)", "Segoe UI", 14);
        resposta3_3Label.getStyleClass().add("professor-name");
        resposta3_3HBox.getChildren().addAll(resposta3_3Radio, resposta3_3Label);

        ToggleGroup toggleGroup = new ToggleGroup();
        resposta1_3Radio.setToggleGroup(toggleGroup);
        resposta2_3Radio.setToggleGroup(toggleGroup);
        resposta3_3Radio.setToggleGroup(toggleGroup);

        VBox alternativasBox2 = new VBox(10);
        alternativasBox2.getChildren().addAll(resposta1_3HBox, resposta2_3HBox, resposta3_3HBox);


        Button completeButton = new Button("Enviar Respostas");
        completeButton.getStyleClass().add("simple-button");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        ScrollPane sPane = new ScrollPane();
        sPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox contentBox = new VBox(10);
        contentBox.getChildren().addAll(
            questionNumberBox1,
            valor1,
            resposta1_1,
            questionNumberBox2,
            valor2,
            alternativasBox,
            questionNumberBox3,
            valor3,
            alternativasBox2,
            spacer,
            completeButton
        );
        sPane.setContent(contentBox);

        content.getChildren().add(sPane);
        return content;
    }

    private VBox createHeader() {
        VBox header = new VBox(HEADER_SPACING);
        header.setPadding(new Insets(PADDING, PADDING, 0, PADDING));

        Label titleLabel = createStyledLabel(
            "Questionário 1 - Introdução a Java",
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
            ? "/com/elements/forum/style/dark/style.css"
            : "/com/elements/forum/style/ligth/style.css";
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
