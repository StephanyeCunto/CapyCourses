package com.view.estudante;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class BibliotecaPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox root = new HBox();
        root.setStyle("-fx-background-color: #B4C7DC;");

        VBox sidebar = createSidebar();
        VBox mainContent = createMainContent();

        root.getChildren().addAll(sidebar, mainContent);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Biblioteca");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setPrefWidth(950);

        // Título da página
        Label titleLabel = new Label("Biblioteca");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Barra de pesquisa
        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar na biblioteca...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-color: #E3EEFA; -fx-background-radius: 20;");

        // Filtros
        HBox filters = createFilters();

        // Área de conteúdo principal
        TabPane tabPane = createTabPane();

        mainContent.getChildren().addAll(titleLabel, searchField, filters, tabPane);
        return mainContent;
    }

    private HBox createFilters() {
        HBox filters = new HBox(15);
        filters.setPadding(new Insets(10, 0, 10, 0));

        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.setPromptText("Categoria");
        categoryFilter.setItems(FXCollections.observableArrayList(
            "Todos", "Programação", "Design", "Marketing", "Negócios"
        ));

        ComboBox<String> formatFilter = new ComboBox<>();
        formatFilter.setPromptText("Formato");
        formatFilter.setItems(FXCollections.observableArrayList(
            "Todos", "PDF", "Video", "Áudio", "Artigo"
        ));

        ComboBox<String> sortFilter = new ComboBox<>();
        sortFilter.setPromptText("Ordenar por");
        sortFilter.setItems(FXCollections.observableArrayList(
            "Mais recentes", "Mais antigos", "Mais populares", "A-Z"
        ));

        filters.getChildren().addAll(categoryFilter, formatFilter, sortFilter);
        return filters;
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Tab Todos os Materiais
        Tab allMaterialsTab = new Tab("Todos os Materiais");
        allMaterialsTab.setContent(createMaterialsGrid());

        // Tab Favoritos
        Tab favoritesTab = new Tab("Favoritos");
        favoritesTab.setContent(createMaterialsGrid());

        // Tab Downloads
        Tab downloadsTab = new Tab("Downloads");
        downloadsTab.setContent(createMaterialsGrid());

        tabPane.getTabs().addAll(allMaterialsTab, favoritesTab, downloadsTab);
        return tabPane;
    }

    private ScrollPane createMaterialsGrid() {
        ScrollPane scrollPane = new ScrollPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);

        // Criar cards de materiais
        for (int i = 0; i < 8; i++) {
            grid.add(createMaterialCard(), i % 3, i / 3);
        }

        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        return scrollPane;
    }

    private VBox createMaterialCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(280);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );

        // Ícone do tipo de arquivo
        Label fileTypeIcon = new Label("📄");
        fileTypeIcon.setStyle("-fx-font-size: 24px;");

        // Informações do material
        Label titleLabel = new Label("Guia Completo de JavaScript");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        
        Label categoryLabel = new Label("Programação • PDF");
        categoryLabel.setStyle("-fx-text-fill: #666666;");

        Label descriptionLabel = new Label("Um guia abrangente sobre JavaScript moderno, incluindo ES6+ e boas práticas.");
        descriptionLabel.setWrapText(true);

        // Barra de ações
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button downloadBtn = new Button("Download");
        downloadBtn.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white;");

        Button favoriteBtn = new Button("★");
        favoriteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #666666;");

        actions.getChildren().addAll(downloadBtn, favoriteBtn);

        card.getChildren().addAll(
            fileTypeIcon, 
            titleLabel, 
            categoryLabel, 
            descriptionLabel, 
            actions
        );

        return card;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPrefWidth(250);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #F0F4F8;");

        // Profile section
        Circle profileCircle = new Circle(30);
        profileCircle.setFill(Color.LIGHTBLUE);
        Label initials = new Label("SC");
        initials.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        StackPane profile = new StackPane(profileCircle, initials);

        Label welcomeLabel = new Label("Bem-vindo,");
        Label nameLabel = new Label("ste cunto");
        nameLabel.setStyle("-fx-font-weight: bold;");

        // Navigation buttons
        Button coursesBtn = createNavButton("Cursos Disponíveis", false);
        Button myCoursesBtn = createNavButton("Meus Cursos", false);
        Button progressBtn = createNavButton("Progresso", false);
        Button certificatesBtn = createNavButton("Certificados", false);
        Button libraryBtn = createNavButton("Biblioteca", true);
        Button forumBtn = createNavButton("Forum", false);
        Button settingsBtn = createNavButton("Configurações", false);

        Button logoutBtn = new Button("Sair");
        logoutBtn.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white;");
        logoutBtn.setPrefWidth(200);
        logoutBtn.setPrefHeight(40);

        VBox.setMargin(logoutBtn, new Insets(40, 0, 0, 0));

        sidebar.getChildren().addAll(
            profile, welcomeLabel, nameLabel, coursesBtn, 
            myCoursesBtn, progressBtn, certificatesBtn, 
            libraryBtn, forumBtn, settingsBtn, logoutBtn
        );
        return sidebar;
    }

    private Button createNavButton(String text, boolean isActive) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        if (isActive) {
            button.setStyle("-fx-background-color: #E3EEFA; -fx-text-fill: #3B82F6;");
        } else {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333;");
        }
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}