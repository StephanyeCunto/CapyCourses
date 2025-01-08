package com.singleton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ForumMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #1a1d24;");
        root.setPadding(new Insets(20));
        root.setPrefWidth(300);

        // Back button
        Button backButton = new Button("← VOLTAR");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4169E1; " +
                          "-fx-font-size: 14px;");

        // Progress section
        VBox progressSection = new VBox(10);
        Label progressTitle = new Label("Progresso do Fórum");
        progressTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        
        Label progressPercent = new Label("75% Completo");
        progressPercent.setStyle("-fx-text-fill: #808080;");

        ProgressBar progressBar = new ProgressBar(0.75);
        progressBar.setStyle("-fx-accent: #4169E1;");
        progressBar.setPrefWidth(Double.MAX_VALUE);

        progressSection.getChildren().addAll(progressTitle, progressPercent, progressBar);

        // Navigation menu
        VBox navigationMenu = new VBox(5);
        String[] menuItems = {
            "Informações Básicas",
            "Tópicos",
            "Respostas",
            "Configurações"
        };

        for (String item : menuItems) {
            Label menuItem = new Label(item);
            menuItem.setStyle("-fx-text-fill: " + (item.equals("Informações Básicas") ? "white" : "#808080") + ";");
            menuItem.setPadding(new Insets(8, 0, 8, 0));
            navigationMenu.getChildren().add(menuItem);
        }

        // Quick tips section
        VBox tipsSection = new VBox(10);
        Label tipsTitle = new Label("Dicas Rápidas");
        tipsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        VBox tipsList = new VBox(8);
        String[] tips = {
            "• Mantenha as discussões organizadas e relevantes",
            "• Use tags apropriadas para categorizar tópicos",
            "• Seja claro e objetivo nas perguntas",
            "• Participe ativamente das discussões"
        };

        for (String tip : tips) {
            Label tipLabel = new Label(tip);
            tipLabel.setStyle("-fx-text-fill: #808080; -fx-wrap-text: true;");
            tipsList.getChildren().add(tipLabel);
        }

        tipsSection.getChildren().addAll(tipsTitle, tipsList);

        // Statistics section
        VBox statsSection = new VBox(10);
        Label statsTitle = new Label("Estatísticas");
        statsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(10);
        statsGrid.setVgap(8);

        String[][] stats = {
            {"Tópicos Ativos", "42"},
            {"Total de Respostas", "156"},
            {"Usuários Participando", "89"},
            {"Taxa de Resposta", "85%"}
        };

        for (int i = 0; i < stats.length; i++) {
            Label statLabel = new Label(stats[i][0]);
            Label valueLabel = new Label(stats[i][1]);
            
            statLabel.setStyle("-fx-text-fill: #808080;");
            valueLabel.setStyle("-fx-text-fill: white;");
            
            statsGrid.add(statLabel, 0, i);
            statsGrid.add(valueLabel, 1, i);
        }

        statsSection.getChildren().addAll(statsTitle, statsGrid);

        // Add all sections to root
        root.getChildren().addAll(
            backButton,
            progressSection,
            new Separator(),
            navigationMenu,
            new Separator(),
            tipsSection,
            new Separator(),
            statsSection
        );

        // Style separators
        root.getChildren().stream()
            .filter(node -> node instanceof Separator)
            .forEach(node -> node.setStyle("-fx-background-color: #2a2e35;"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Menu do Fórum");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}