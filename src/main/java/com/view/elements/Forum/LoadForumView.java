package com.view.elements.Forum;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.*;

public class LoadForumView {
    private static final double CARD_WIDTH = 900;
    private static final double GRID_SPACING = 15;
    private static final double CONTENT_SPACING = 16;
    private static final String FONT_FAMILY = "Franklin Gothic Medium";
    
    // Dados de exemplo para protótipo
    private static final List<Map<String, Object>> SAMPLE_DATA = Arrays.asList(
        new HashMap<String, Object>() {{
            put("title", "Como implementar Clean Architecture em projetos Java?");
            put("category", "Arquitetura de Software");
            put("description", "Gostaria de discutir as melhores práticas para implementar Clean Architecture em projetos Java. Quais são os principais desafios e como vocês costumam organizar as camadas do projeto?");
            put("author", "Maria Silva");
            put("authorRole", "Tech Lead");
            put("topics", 15);
            put("views", 234);
            put("likes", 45);
            put("comments", 28);
            put("createdAt", LocalDateTime.now().minusDays(2));
        }},
        new HashMap<String, Object>() {{
            put("title", "Dicas para otimização de consultas SQL em grande volume de dados");
            put("category", "Banco de Dados");
            put("description", "Estou trabalhando com um banco que possui milhões de registros e preciso otimizar algumas consultas. Alguém tem experiência com indexação e particionamento de tabelas?");
            put("author", "João Santos");
            put("authorRole", "DBA Senior");
            put("topics", 8);
            put("views", 567);
            put("likes", 89);
            put("comments", 42);
            put("createdAt", LocalDateTime.now().minusDays(1));
        }},
        new HashMap<String, Object>() {{
            put("title", "React vs Angular: análise comparativa para projetos enterprise");
            put("category", "Frontend");
            put("description", "Iniciando um novo projeto e precisamos decidir entre React e Angular. Quais são os pros e contras de cada um para aplicações corporativas de grande porte?");
            put("author", "Ana Costa");
            put("authorRole", "Frontend Specialist");
            put("topics", 23);
            put("views", 789);
            put("likes", 156);
            put("comments", 67);
            put("createdAt", LocalDateTime.now().minusHours(12));
        }},
        new HashMap<String, Object>() {{
            put("title", "Melhores práticas de segurança em APIs REST");
            put("category", "Segurança");
            put("description", "Vamos discutir sobre implementação de autenticação JWT, rate limiting, validação de inputs e outras práticas essenciais de segurança em APIs REST.");
            put("author", "Pedro Oliveira");
            put("authorRole", "Security Engineer");
            put("topics", 12);
            put("views", 432);
            put("likes", 78);
            put("comments", 35);
            put("createdAt", LocalDateTime.now().minusHours(6));
        }}
    );

    private static final GridPane forumGrid = new GridPane();
    private static final VBox forumContainer = new VBox();

    public static VBox loadForum() {
        initializeGrid();
        displayForums();
        setupResponsiveBehavior();
        return forumContainer;
    }

    private static void initializeGrid() {
        forumGrid.getChildren().clear();
        forumGrid.setHgap(GRID_SPACING);
        forumGrid.setVgap(GRID_SPACING);
        forumGrid.setPadding(new Insets(GRID_SPACING));
        forumGrid.setAlignment(Pos.CENTER);
        
        forumContainer.setAlignment(Pos.CENTER);
        forumContainer.getChildren().clear();
        forumContainer.getChildren().add(forumGrid);
    }

    private static void displayForums() {
        int numColumns = 1;
        int index = 0;
        
        for (Map<String, Object> forumData : SAMPLE_DATA) {
            VBox forumBox = createForumBox(forumData);
            addFadeInAnimation(forumBox);
            forumGrid.add(forumBox, index % numColumns, index / numColumns);
            index++;
        }
    }

    private static void setupResponsiveBehavior() {
        forumContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns);
        });
    }

    private static VBox createForumBox(Map<String, Object> forumData) {
        VBox forumBox = new VBox();
        forumBox.getStyleClass().add("card");
        forumBox.setPrefWidth(CARD_WIDTH);
        forumBox.setAlignment(Pos.TOP_LEFT);
        forumBox.setPadding(new Insets(20));
        forumBox.setSpacing(CONTENT_SPACING);

        VBox content = createForumContent(forumData);
        HBox footer = createFooter(forumData);

        forumBox.getChildren().addAll(content, footer);
        return forumBox;
    }

    private static VBox createForumContent(Map<String, Object> forumData) {
        HBox header = createHeader(forumData);
        Label titleLabel = createStyledLabel((String) forumData.get("title"), 24);
        titleLabel.getStyleClass().add("title");
        
        HBox authorSection = createAuthorSection(forumData);
        
        Label descriptionLabel = createStyledLabel((String) forumData.get("description"), 16);
        descriptionLabel.getStyleClass().add("description");

        VBox content = new VBox(CONTENT_SPACING);
        content.setAlignment(Pos.TOP_LEFT);
        content.getChildren().addAll(header, titleLabel, authorSection, descriptionLabel);
        
        return content;
    }

    private static HBox createHeader(Map<String, Object> forumData) {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label categoryLabel = createCategoryLabel((String) forumData.get("category"));
        Label topicsLabel = createStyledLabel(forumData.get("topics") + " tópicos", 12);
        topicsLabel.getStyleClass().add("category");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label dateLabel = createDateLabel((LocalDateTime) forumData.get("createdAt"));

        header.getChildren().addAll(categoryLabel, topicsLabel, spacer, dateLabel);
        return header;
    }

    private static Label createCategoryLabel(String category) {
        Label label = createStyledLabel(category.toUpperCase(), 12);
        label.getStyleClass().addAll("category", "category-tag");
        return label;
    }

    private static HBox createAuthorSection(Map<String, Object> forumData) {
        HBox authorBox = new HBox(12);
        authorBox.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = createAuthorAvatar((String) forumData.get("author"));
        
        VBox authorInfo = new VBox(4);
        Label authorLabel = createStyledLabel("Por " + forumData.get("author"), 14);
        authorLabel.getStyleClass().add("author");
        Label roleLabel = createStyledLabel((String) forumData.get("authorRole"), 12);
        roleLabel.getStyleClass().add("author-role");
        
        authorInfo.getChildren().addAll(authorLabel, roleLabel);
        authorBox.getChildren().addAll(avatar, authorInfo);
        
        return authorBox;
    }

    private static StackPane createAuthorAvatar(String author) {
        StackPane avatar = new StackPane();
        Circle circle = new Circle(20);
        circle.getStyleClass().add("author-avatar");
        
        String initials = getInitials(author);
        Label initialsLabel = createStyledLabel(initials, 14);
        initialsLabel.getStyleClass().add("avatar-text");
        
        avatar.getChildren().addAll(circle, initialsLabel);
        return avatar;
    }

    private static HBox createFooter(Map<String, Object> forumData) {
        HBox footer = new HBox(15);
        footer.setAlignment(Pos.CENTER_LEFT);

        Button viewButton = createViewButton();
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox stats = createForumStats(forumData);
        
        footer.getChildren().addAll(viewButton, spacer, stats);
        return footer;
    }

    private static HBox createForumStats(Map<String, Object> forumData) {
        HBox stats = new HBox(20);
        stats.setAlignment(Pos.CENTER_LEFT);
        
        Label viewsLabel = createStatsLabel("👁 " + forumData.get("views"), "views-count");
        Label likesLabel = createStatsLabel("👍 " + forumData.get("likes"), "likes-count");
        Label commentsLabel = createStatsLabel("💬 " + forumData.get("comments"), "comments-count");
        
        stats.getChildren().addAll(viewsLabel, likesLabel, commentsLabel);
        return stats;
    }

    private static Label createStatsLabel(String text, String styleClass) {
        Label label = createStyledLabel(text, 14);
        label.getStyleClass().addAll("stats-label", styleClass);
        return label;
    }

    private static Label createDateLabel(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        Label label = createStyledLabel(formattedDate, 12);
        label.getStyleClass().add("date-label");
        return label;
    }

    private static Button createViewButton() {
        Button button = new Button("Ver Detalhes");
        button.getStyleClass().add("outline-button");
        button.setPrefWidth(150);
        return button;
    }

    private static Label createStyledLabel(String text, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(FONT_FAMILY, fontSize));
        label.getStyleClass().add("label");
        label.setWrapText(true);
        return label;
    }

    private static String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "";
        String[] parts = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (int i = 0; i < Math.min(2, parts.length); i++) {
            if (!parts[i].isEmpty()) {
                initials.append(parts[i].charAt(0));
            }
        }
        return initials.toString().toUpperCase();
    }

    private static void addFadeInAnimation(VBox forumBox) {
        FadeTransition fade = new FadeTransition(Duration.millis(400), forumBox);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private static int calculateColumns() {
        double width = forumContainer.getWidth();
        return width > 0 ? Math.max(1, (int) (width / 200)) : 1;
    }

    private static void reorganizeGrid(int columns) {
        forumGrid.getChildren().clear();
        displayForums();
    }
}