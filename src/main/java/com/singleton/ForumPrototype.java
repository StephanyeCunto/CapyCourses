package com.singleton;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

public class ForumPrototype extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a1b26;");

        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        VBox mainContent = createMainContent();
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #1e1f2e; -fx-background: #1e1f2e;");
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Fóruns");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: #1a1b26;");

        // Profile section
        Circle profileCircle = new Circle(30, Color.LIGHTBLUE);
        Label nameLabel = new Label("João Silva");
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font(null, FontWeight.BOLD, 16));

        // Menu items
        VBox menuBox = new VBox(10);
        menuBox.getChildren().addAll(
            createMenuButton("Cursos Disponíveis"),
            createMenuButton("Meus Cursos"),
            createMenuButton("Progresso"),
            createMenuButton("Certificados"),
            createMenuButton("Biblioteca"),
            createMenuButton("Forum", true),
            createMenuButton("Configurações")
        );

        Button logoutButton = new Button("Sair");
        logoutButton.setStyle("-fx-background-color: #ff4757; -fx-text-fill: white;");
        logoutButton.setPrefWidth(200);
        logoutButton.setPrefHeight(40);

        sidebar.getChildren().addAll(profileCircle, nameLabel, menuBox, logoutButton);
        return sidebar;
    }

    private VBox createMainContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #1e1f2e;");
    
        // Search bar
        HBox searchBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar nos fóruns...");
        searchField.setStyle("-fx-background-color: #2f3142; -fx-text-fill: white;");
        searchField.setPrefWidth(300);
    
        Button searchButton = new Button("Buscar");
        searchButton.setStyle("-fx-background-color: #4169E1; -fx-text-fill: white;");
    
        searchBox.getChildren().addAll(searchField, searchButton);
    
        // Forums list
        VBox forumsList = new VBox(15);
        forumsList.setPadding(new Insets(20, 0, 0, 0));
    
        // Add forum items
        String[][] forumData = {
            {"React Native", "Discussões e soluções para problemas comuns.", "Mobile", "42", "15", "5 min atrás"},
            {"Projetos Flutter", "Compartilhe seus projetos e receba feedback.", "Mobile", "28", "8", "32 min atrás"},
            {"Arquitetura de Software", "Boas práticas e padrões arquiteturais.", "Backend", "35", "12", "1h atrás"},
            {"Gerenciamento de Estado", "Redux, MobX, Provider e outros.", "General", "56", "23", "2h atrás"},
            {"APIs e Backend", "Conexões com servidores e APIs RESTful.", "Backend", "31", "9", "3h atrás"},
            {"UI/UX Design", "Experiência do usuário e layouts.", "Design", "44", "17", "4h atrás"},
            {"Testes de Software", "Unitários, integração e E2E.", "Quality", "25", "7", "5h atrás"},
            {"Performance e Otimização", "Melhoria de desempenho em apps.", "General", "38", "14", "6h atrás"}
        };
    
        for (String[] data : forumData) {
            forumsList.getChildren().add(createForumItem(data[0], data[1], data[2], 
                    Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5]));
        }
    
        content.getChildren().addAll(searchBox, forumsList);
        return content;
    }
    
    private VBox createForumItem(String title, String description, String category, 
                                 int topics, int active, String lastActivity) {
        VBox item = new VBox(10);
        item.setPadding(new Insets(15));
        item.setStyle("-fx-background-color: #2f3142; -fx-background-radius: 10;");
    
        // Title
        HBox header = new HBox(10);
        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font(null, FontWeight.BOLD, 18));
    
        Label categoryLabel = new Label("[" + category + "]");
        categoryLabel.setTextFill(Color.LIGHTGRAY);
        categoryLabel.setFont(Font.font(null, FontWeight.NORMAL, 14));
    
        header.getChildren().addAll(titleLabel, categoryLabel);
    
        // Description
        Label descLabel = new Label(description);
        descLabel.setTextFill(Color.LIGHTGRAY);
        descLabel.setWrapText(true);
    
        // Stats
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER_LEFT);
    
        Label topicsLabel = new Label(topics + " tópicos");
        topicsLabel.setTextFill(Color.LIGHTGRAY);
    
        Label activeLabel = new Label(active + " ativos");
        activeLabel.setTextFill(Color.LIGHTGRAY);
    
        Label lastActivityLabel = new Label("Última atividade: " + lastActivity);
        lastActivityLabel.setTextFill(Color.LIGHTGRAY);
    
        statsBox.getChildren().addAll(topicsLabel, activeLabel, lastActivityLabel);
    
        // Action buttons
        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER_LEFT);
    
        Button viewButton = new Button("Ver Discussões");
        viewButton.setStyle("-fx-background-color: #4169E1; -fx-text-fill: white;");
    
        Button newTopicButton = new Button("Novo Tópico");
        newTopicButton.setStyle("-fx-background-color: #2f3142; -fx-border-color: #4169E1; " +
                               "-fx-text-fill: white; -fx-border-radius: 3;");
    
        actionBox.getChildren().addAll(viewButton, newTopicButton);
    
        item.getChildren().addAll(header, descLabel, statsBox, actionBox);
        return item;
    }
    
    private Button createMenuButton(String text) {
        return createMenuButton(text, false);
    }

    private Button createMenuButton(String text, boolean isActive) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        
        if (isActive) {
            button.setStyle("-fx-background-color: #4169E1; -fx-text-fill: white;");
        } else {
            button.setStyle("-fx-background-color: #2f3142; -fx-text-fill: white;");
        }
        
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}