package com.view.elements.Settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class LoadSettingsPerfil {
    public VBox settingsPerfil() {
        VBox mainContainer = new VBox(20);
        mainContainer.getStyleClass().add("card");
        // Profile Section
        HBox profileSection = new HBox(20);
        profileSection.setAlignment(Pos.CENTER_LEFT);

        StackPane photoStack = new StackPane();
        Circle photoCircle = new Circle(50, Color.web("#6c63ff"));
        Label initials = new Label("JS");
        initials.setTextFill(Color.WHITE);
        initials.setFont(new Font("Arial", 24));
        photoStack.getChildren().addAll(photoCircle, initials);

        VBox photoButtons = new VBox(10);
        HBox buttonGroup = new HBox(15);
        Button changePhotoBtn = createStyledButton("Alterar Foto", "#6c63ff", "white");
        Button removePhotoBtn = createStyledButton("Remover", "rgba(255,0,0,0.1)", "#ff4d4d");
        buttonGroup.getChildren().addAll(changePhotoBtn, removePhotoBtn);

        Label sizeInfo = new Label("Tamanho máximo: 5MB. Formatos: JPG, PNG");
        sizeInfo.setTextFill(Color.web("#ffffff80"));
        sizeInfo.setFont(new Font("Arial", 12));
        photoButtons.getChildren().addAll(buttonGroup, sizeInfo);

        profileSection.getChildren().addAll(photoStack, photoButtons);

        // Personal Information Sectionxq
        VBox infoSection = new VBox(20);
        Label infoTitle = new Label("Informações Pessoais");
        infoTitle.setTextFill(Color.WHITE);
        infoTitle.setFont(new Font("Arial", 18));

        GridPane formGrid = createFormGrid();
        
        // Bio Section
        VBox bioSection = createBioSection();

        mainContainer.getChildren().addAll(profileSection, infoSection, formGrid, bioSection);
        return mainContainer;
    }

    private Button createStyledButton(String text, String bgColor, String textColor) {
        Button button = new Button(text);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s; -fx-background-radius: 12;", bgColor, textColor));
        button.setFont(new Font("Arial", 14));
        return button;
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col1.setPercentWidth(33);
        col2.setPercentWidth(33);
        col3.setPercentWidth(33);
        grid.getColumnConstraints().addAll(col1, col2, col3);

        // Add form fields
        addFormField(grid, "Nome Completo", "João Silva", 0, 0);
        addFormField(grid, "Nome de Usuário", "joaosilva", 1, 0);
        addFormField(grid, "Email", "joao.silva@email.com", 2, 0);
        addFormField(grid, "Telefone", "+55 (11) 99999-9999", 0, 1);
        
        // Add ComboBoxes
        addComboBox(grid, "País", "Brasil", 1, 1);
        addComboBox(grid, "Fuso Horário", "Brasília (GMT-3)", 2, 1);

        return grid;
    }

    private void addFormField(GridPane grid, String label, String defaultText, int col, int row) {
        VBox container = new VBox(8);
        Label fieldLabel = new Label(label);
        fieldLabel.setTextFill(Color.web("#ffffff80"));
        fieldLabel.setFont(new Font("Arial", 14));

        TextField field = new TextField(defaultText);
        field.setStyle("-fx-background-color: rgba(255,255,255,0.08); -fx-text-fill: white; -fx-background-radius: 12; -fx-border-color: rgba(255,255,255,0.1); -fx-border-radius: 12; -fx-padding: 12;");
        field.setFont(new Font("Arial", 14));

        container.getChildren().addAll(fieldLabel, field);
        grid.add(container, col, row);
    }

    private void addComboBox(GridPane grid, String label, String prompt, int col, int row) {
        VBox container = new VBox(8);
        Label fieldLabel = new Label(label);
        fieldLabel.setTextFill(Color.web("#ffffff80"));
        fieldLabel.setFont(new Font("Arial", 14));

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(prompt);
        comboBox.setStyle("-fx-background-color: rgba(255,255,255,0.08); -fx-text-fill: white; -fx-background-radius: 12;");
        comboBox.setPrefHeight(33);
        comboBox.setPrefWidth(295);

        container.getChildren().addAll(fieldLabel, comboBox);
        grid.add(container, col, row);
    }

    private VBox createBioSection() {
        VBox bioContainer = new VBox(8);
        Label bioLabel = new Label("Biografia");
        bioLabel.setTextFill(Color.web("#ffffff80"));
        bioLabel.setFont(new Font("Arial", 14));

        TextArea bioArea = new TextArea();
        bioArea.setWrapText(true);
        bioArea.setPrefRowCount(3);
        bioArea.setStyle("-fx-background-color: rgba(255,255,255,0.08); -fx-text-fill: white; -fx-background-radius: 12; -fx-border-color: rgba(255,255,255,0.1); -fx-border-radius: 12;");
        bioArea.setFont(new Font("Arial", 14));

        Label charLimit = new Label("Máximo de 200 caracteres");
        charLimit.setTextFill(Color.web("#ffffff80"));
        charLimit.setFont(new Font("Arial", 12));

        bioContainer.getChildren().addAll(bioLabel, bioArea, charLimit);
        return bioContainer;
    }
}
