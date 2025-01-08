package com.view.elements.Forum;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.controller.elements.LoadForumController;
import com.singleton.UserSession;
import com.view.Modo;
import com.view.elements.Forum.valid.CreateForumValid;


public class ModalAddForum {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;
    CreateForumValid createForumValid = new CreateForumValid();
    LoadForumController controller = new LoadForumController();
    CreateJsonForum createJsonForum = new CreateJsonForum();

    public void updateDimensions(double WIDTH, double HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public ModalAddForum(Window owner) {
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
        VBox modalContainer = new VBox(20);
        modalContainer.setPadding(new Insets(30));
    
        modalContainer.setMaxWidth(WIDTH * 0.8);
        modalContainer.setMaxHeight(HEIGHT * 0.8);
        modalContainer.setMinHeight(HEIGHT * 0.8);
        modalContainer.setMinWidth(WIDTH * 0.8);
    
        modalContainer.getStyleClass().add("card");
        modalContainer.setEffect(createDropShadow());
    
        VBox header = createHeader();
    
        VBox content = new VBox(20);
        content.setPadding(new Insets(0, 20, 10, 20));
        content.setAlignment(Pos.TOP_LEFT);
    
        Label titleLabel = new Label("Título do Fórum:");
        titleLabel.getStyleClass().add("module-title");
        TextField forumTitleField = new TextField();
        forumTitleField.setPromptText("Digite o título do fórum");
        forumTitleField.getStyleClass().add("custom-text-field");
        Label titleErrorLabel = new Label("Por favor digite um title válido");
        titleErrorLabel.getStyleClass().add("error-label");
        titleErrorLabel.setVisible(false);
        titleErrorLabel.setManaged(false);
        
    
        Label descriptionLabel = new Label("Descrição do Fórum:");
        descriptionLabel.getStyleClass().add("module-title");
        TextField forumDescriptionField = new TextField();
        forumDescriptionField.setPromptText("Digite a descrição do fórum");
        forumDescriptionField.getStyleClass().add("custom-text-field");
        Label descriptionErrorLabel = new Label("Por favor digite uma descrição válida");
        descriptionErrorLabel.getStyleClass().add("error-label");
        descriptionErrorLabel.setVisible(false);
        descriptionErrorLabel.setManaged(false);
    
        Label categoryLabel = new Label("Categoria:");
        categoryLabel.getStyleClass().add("module-title");
        TextField categoryTextField = new TextField();
        categoryTextField.setPromptText("Digite a categoria");
        categoryTextField.getStyleClass().add("custom-text-field");
        Label categoryErrorLabel = new Label("Por favor digite uma categoria válida");
        categoryErrorLabel.getStyleClass().add("error-label");
        categoryErrorLabel.setVisible(false);
        categoryErrorLabel.setManaged(false);
    
        Label questionLabel = new Label("Pergunta:");
        questionLabel.getStyleClass().add("module-title");
        TextArea questionTextArea = new TextArea();
        questionTextArea.setPromptText("Digite a pergunta principal");
        questionTextArea.getStyleClass().add("custom-text-area");
        questionTextArea.setWrapText(true);
        Label questionErrorLabel = new Label("Por favor digite uma pergunta válida");
        questionErrorLabel.getStyleClass().add("error-label");
       questionErrorLabel.setVisible(false);
       questionErrorLabel.setManaged(false);

        createForumValid.setupInitialState(forumTitleField, forumDescriptionField, categoryTextField, questionTextArea,
                titleErrorLabel, descriptionErrorLabel, categoryErrorLabel, questionErrorLabel);
        Button saveButton = new Button("Salvar Fórum");
        saveButton.getStyleClass().add("simple-button");
        saveButton.setOnAction(e -> {
            if(createForumValid.isValid()){
            controller.addForum(forumTitleField.getText(), forumDescriptionField.getText(), categoryTextField.getText(), questionTextArea.getText());
            modalStage.close();
            createJsonForum.saveForum(UserSession.getInstance().getUserName(), forumTitleField.getText(), forumDescriptionField.getText(), categoryTextField.getText(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()),0,0,0,questionLabel.getText(),"capycourses/src/main/resources/com/json/forum.json");
              try {
                Thread.sleep(500); 
                FXMLLoader loader = new FXMLLoader(LoadForumView.class.getResource("/com/estudante/forum/paginaDoForum.fxml"));
                Parent root = loader.load();  
                Scene scene = saveButton.getScene();
                scene.setRoot(root);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted: " + ex.getMessage());
            } catch (IOException ex) {
                System.err.println("Error loading forum page: " + ex.getMessage());
            }
        }
        });
    
        // Adicionando os campos ao formulário
        VBox formFields = new VBox(10);
        formFields.setAlignment(Pos.TOP_LEFT);
        formFields.getChildren().addAll(
            titleLabel, forumTitleField, titleErrorLabel,
            descriptionLabel, forumDescriptionField, descriptionErrorLabel,
            categoryLabel, categoryTextField, categoryErrorLabel,
            questionLabel, questionTextArea, questionErrorLabel,
            saveButton
        );
        
    
        content.getChildren().add(formFields);
    
        VBox footer = new VBox(20);
        footer.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(footer, Priority.ALWAYS);
    
        modalContainer.getChildren().addAll(header, content, footer);
    
        backdrop.getChildren().add(modalContainer);
        Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        modo(scene);
    
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }
    
    private VBox createHeader() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20, 20, 0, 20));
    
        Label titleLabel = createStyledLabel("Criar Fórum", "Segoe UI Bold", 28);
        titleLabel.getStyleClass().add("title");
    
        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("register-button");
        closeButton.setOnAction(e -> modalStage.close());
        
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setAlignment(Pos.TOP_RIGHT);
    
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().add(titleLabel);
    
        HBox mainHeader = new HBox();
        mainHeader.getChildren().addAll(header, closeButtonBox);
        HBox.setHgrow(header, Priority.ALWAYS);
    
        content.getChildren().add(mainHeader);
    
        return content;
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
        String styleSheet = Modo.getInstance().getModo() ? "/com/estudante/forum/style/dark/style.css"
                : "/com/estudante/forum/style/ligth/style.css";
        scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }

    private void setupCloseAnimation() {
        modalStage.setOnCloseRequest(event -> {
            event.consume();
            if (modalStage.getScene() == null)
                return;
            FadeTransition fade = new FadeTransition(Duration.millis(200), modalStage.getScene().getRoot());
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
