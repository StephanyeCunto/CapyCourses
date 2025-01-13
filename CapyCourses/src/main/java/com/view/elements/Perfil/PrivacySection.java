package com.view.elements.Perfil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PrivacySection {

    private VBox createContent() {
    VBox content = new VBox(20);
    content.setPadding(new Insets(20));
    content.setAlignment(Pos.TOP_CENTER);

    // Título
    Label titleLabel = new Label("Política de Privacidade do CapyCourses");
    titleLabel.setFont(Font.font("Segoe UI Bold", 24));
    titleLabel.setStyle("-fx-text-fill: #2E86C1;");

    // Data de atualização
    Label updateLabel = new Label("Última atualização: [Insira a data]");
    updateLabel.setFont(Font.font("Segoe UI", 14));
    updateLabel.setStyle("-fx-text-fill: #555;");

    // Texto introdutório
    Label introLabel = new Label(
        "Bem-vindo ao CapyCourses! Esta Política de Privacidade explica como coletamos, usamos, compartilhamos e protegemos as informações dos usuários do site https://github.com/StephanyeCunto/CapyCourses (doravante referido como \"Site\" ou \"Serviço\"). Ao utilizar nosso Site, você concorda com as práticas descritas nesta política."
    );
    introLabel.setFont(Font.font("Segoe UI", 14));
    introLabel.setWrapText(true);

    // Subtítulo 1
    Label subtitle1 = new Label("1. Informações que Coletamos");
    subtitle1.setFont(Font.font("Segoe UI Bold", 18));
    subtitle1.setStyle("-fx-text-fill: #2E86C1;");

    // Texto do subtítulo 1
    Label text1 = new Label(
        "- Dados de cadastro: Nome, endereço de e-mail, nome de usuário e senha.\n" +
        "- Informações de perfil: Foto, biografia e outras informações que você optar por compartilhar.\n" +
        "- Conteúdo gerado pelo usuário: Cursos, comentários, avaliações e outras contribuições."
    );
    text1.setFont(Font.font("Segoe UI", 14));
    text1.setWrapText(true);

    // Subtítulo 2
    Label subtitle2 = new Label("2. Como Usamos Suas Informações");
    subtitle2.setFont(Font.font("Segoe UI Bold", 18));
    subtitle2.setStyle("-fx-text-fill: #2E86C1;");

    // Texto do subtítulo 2
    Label text2 = new Label(
        "Utilizamos as informações coletadas para:\n" +
        "- Fornecer, operar e melhorar o Site.\n" +
        "- Personalizar sua experiência no CapyCourses.\n" +
        "- Comunicar-nos com você, respondendo a solicitações e enviando atualizações.\n" +
        "- Analisar o uso do Site e realizar pesquisas.\n" +
        "- Cumprir obrigações legais e proteger nossos direitos."
    );
    text2.setFont(Font.font("Segoe UI", 14));
    text2.setWrapText(true);

    // Adicionando todos os elementos ao conteúdo
    content.getChildren().addAll(
        titleLabel, updateLabel, introLabel,
        subtitle1, text1,
        subtitle2, text2
    );

    // ScrollPane para permitir rolagem
    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

    VBox container = new VBox(scrollPane);
    container.setAlignment(Pos.TOP_CENTER);
    return container;
}
}