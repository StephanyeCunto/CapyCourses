package com;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.ColorPicker;

import java.util.Arrays;
import java.util.List;

public class TextEditorApp extends Application {
    private TextFlow textFlow;
    private FontWeight fontWeight = FontWeight.NORMAL;
    private FontPosture fontPosture = FontPosture.REGULAR;
    private Color fontColor = Color.BLACK;
    private double fontSize = 12;

    @Override
    public void start(Stage primaryStage) {
        // Configuração do TextFlow
        textFlow = new TextFlow();
        textFlow.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");

        // Campo de entrada de texto
        TextField inputField = new TextField();
        inputField.setPromptText("Digite o texto aqui");

        // Seletor de fonte
        ComboBox<String> fontSelector = new ComboBox<>();
        List<String> fonts = Font.getFamilies();
        fontSelector.getItems().addAll(fonts);
        fontSelector.setValue("Arial");
        fontSelector.setOnAction(e -> updateFont());

        // Seletor de tamanho de fonte
        ComboBox<Double> sizeSelector = new ComboBox<>();
        List<Double> fontSizes = Arrays.asList(10.0, 12.0, 14.0, 16.0, 18.0, 20.0, 24.0, 28.0, 32.0, 36.0, 40.0);
        sizeSelector.getItems().addAll(fontSizes);
        sizeSelector.setValue(fontSize);
        sizeSelector.setOnAction(e -> {
            fontSize = sizeSelector.getValue();
            updateFont();
        });

        // Botão de negrito
        ToggleButton boldButton = new ToggleButton("B");
        boldButton.setOnAction(e -> {
            fontWeight = boldButton.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
            updateFont();
        });

        // Botão de itálico
        ToggleButton italicButton = new ToggleButton("I");
        italicButton.setOnAction(e -> {
            fontPosture = italicButton.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
            updateFont();
        });

        // Seletor de cor de fonte
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setOnAction(e -> {
            fontColor = colorPicker.getValue();
            updateFont();
        });

        // Botão para adicionar texto formatado ao TextFlow
        Button addButton = new Button("Adicionar Texto");
        addButton.setOnAction(e -> addFormattedText(inputField.getText()));

        // Barra de ferramentas com botões e seletores
        ToolBar toolBar = new ToolBar();
        HBox fontControls = new HBox(10, fontSelector, sizeSelector, boldButton, italicButton, colorPicker, inputField, addButton);
        toolBar.getItems().add(fontControls);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setCenter(textFlow);

        // Cena e exibição da janela
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setTitle("Editor de Texto com Formatação Parcial");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Adiciona o texto formatado ao TextFlow
    private void addFormattedText(String text) {
        if (text.isEmpty()) return;  // Não adiciona texto vazio

        // Cria um objeto Text com as configurações atuais de formatação
        Text formattedText = new Text(text);
        formattedText.setFont(Font.font(((ComboBox<String>) ((HBox) ((ToolBar) ((BorderPane) textFlow.getParent()).getTop()).getItems().get(0)).getChildren().get(0)).getValue(), fontWeight, fontPosture, fontSize));
        formattedText.setFill(fontColor);

        // Adiciona o texto formatado ao TextFlow
        textFlow.getChildren().add(formattedText);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


