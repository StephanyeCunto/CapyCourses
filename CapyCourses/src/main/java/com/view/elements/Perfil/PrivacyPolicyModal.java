package com.view.elements.Perfil;

import com.view.Modo;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class PrivacyPolicyModal {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;

    public PrivacyPolicyModal(Window owner) {
        updateDimensions(owner.getWidth(), owner.getHeight());
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initOwner(owner);
        showPrivacyPolicy();
        setupCloseAnimation();
    }

    private void showPrivacyPolicy() {
        StackPane backdrop = createBackdrop();
        VBox modalContainer = createModalContainer();

        VBox header = createHeader();
        VBox content = createContent();
        VBox footer = createFooter();

        modalContainer.getChildren().addAll(header, content, footer);
        backdrop.getChildren().add(modalContainer);

        Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        applyTheme(scene);

        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private VBox createModalContainer() {
        VBox modalContainer = new VBox(20);
        modalContainer.setPadding(new Insets(30));
        modalContainer.setMaxWidth(WIDTH * 0.8);
        modalContainer.setMaxHeight(HEIGHT * 0.8);
        modalContainer.setMinHeight(HEIGHT * 0.8);
        modalContainer.setMinWidth(WIDTH * 0.8);
        modalContainer.getStyleClass().add("card");
        modalContainer.setEffect(createDropShadow());
        return modalContainer;
    }

    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setPadding(new Insets(20, 20, 0, 20));

        Label titleLabel = new Label("Política de Privacidade");
        titleLabel.setFont(Font.font("Segoe UI Bold", 28));
        titleLabel.getStyleClass().add("title");

        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("register-button");
        closeButton.setOnAction(e -> modalStage.close());
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setAlignment(Pos.TOP_RIGHT);

        HBox mainHeader = new HBox();
        mainHeader.getChildren().addAll(titleLabel, closeButtonBox);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        header.getChildren().add(mainHeader);
        return header;
    }

    private VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        // Texto da Política de Privacidade
        String privacyPolicyText = 
        "**Política de Privacidade do CapyCourses**\n\n" +
        "**Última atualização:** [Insira a data]\n\n" +
        "Bem-vindo ao **CapyCourses**! Esta Política de Privacidade explica como coletamos, usamos, compartilhamos e protegemos as informações dos usuários do site **https://github.com/StephanyeCunto/CapyCourses** (doravante referido como \"Site\" ou \"Serviço\"). Ao utilizar nosso Site, você concorda com as práticas descritas nesta política.\n\n" +
        "**1. Informações que Coletamos**\n" +
        "- **Dados de cadastro:** Nome, endereço de e-mail, nome de usuário e senha.\n" +
        "- **Informações de perfil:** Foto, biografia e outras informações que você optar por compartilhar.\n" +
        "- **Conteúdo gerado pelo usuário:** Cursos, comentários, avaliações e outras contribuições.\n\n" +
        "**2. Como Usamos Suas Informações**\n" +
        "Utilizamos as informações coletadas para:\n" +
        "- Fornecer, operar e melhorar o Site.\n" +
        "- Personalizar sua experiência no CapyCourses.\n" +
        "- Comunicar-nos com você, respondendo a solicitações e enviando atualizações.\n" +
        "- Analisar o uso do Site e realizar pesquisas.\n" +
        "- Cumprir obrigações legais e proteger nossos direitos.\n\n" +
        "**3. Compartilhamento de Informações**\n" +
        "Não vendemos suas informações pessoais. Podemos compartilhar dados com:\n" +
        "- **Prestadores de serviços:** Parceiros que nos auxiliam na operação do Site (como hospedagem e análise de dados).\n" +
        "- **Autoridades legais:** Quando necessário para cumprir leis, regulamentos ou processos legais.\n" +
        "- **Com seu consentimento:** Em situações onde você autoriza o compartilhamento.\n\n" +
        "**4. Segurança das Informações**\n" +
        "Implementamos medidas de segurança para proteger suas informações contra acesso não autorizado, alteração, divulgação ou destruição. No entanto, nenhum sistema é 100% seguro, e não podemos garantir segurança absoluta.\n\n" +
        "**5. Seus Direitos**\n" +
        "Dependendo da sua localização, você pode ter os seguintes direitos:\n" +
        "- **Acesso:** Solicitar uma cópia das informações que temos sobre você.\n" +
        "- **Correção:** Solicitar a correção de dados incorretos ou incompletos.\n" +
        "- **Exclusão:** Solicitar a exclusão de suas informações pessoais.\n" +
        "- **Revogação de consentimento:** Retirar o consentimento para o uso de seus dados.\n\n" +
        "**6. Links para Terceiros**\n" +
        "O Site pode conter links para outros sites. Esta Política de Privacidade não se aplica a sites de terceiros, e recomendamos que você revise as políticas de privacidade desses sites.\n\n" +
        "**7. Alterações nesta Política**\n" +
        "Podemos atualizar esta Política de Privacidade periodicamente. Notificaremos você sobre mudanças significativas através do Site ou por e-mail. O uso contínuo do Site após as alterações constitui aceitação da nova política.\n\n" +
        "**8. Contato**\n" +
        "Se tiver dúvidas ou preocupações sobre esta Política de Privacidade, entre em contato conosco através do e-mail: [inserir e-mail de contato].";

        Label policyTextLabel = new Label(privacyPolicyText);
        policyTextLabel.setWrapText(true);
        policyTextLabel.setFont(Font.font("Segoe UI", 14));

        ScrollPane scrollPane = new ScrollPane(policyTextLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        content.getChildren().add(scrollPane);
        return content;
    }

    private VBox createFooter() {
        VBox footer = new VBox(20);
        footer.setAlignment(Pos.BOTTOM_CENTER);
        footer.setPadding(new Insets(20));

        Button closeButton = new Button("Fechar");
        closeButton.getStyleClass().add("simple-button");
        closeButton.setOnAction(e -> modalStage.close());

        footer.getChildren().add(closeButton);
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

    private void setupCloseAnimation() {
        modalStage.setOnCloseRequest(event -> {
            event.consume();
            if (modalStage.getScene() != null) {
                FadeTransition fade = new FadeTransition(Duration.millis(200), modalStage.getScene().getRoot());
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setOnFinished(e -> modalStage.hide());
                fade.play();
            }
        });
    }

    private void applyTheme(Scene scene) {
        String styleSheet = Modo.getInstance().getModo()
                ? "/com/estudante/paginaInicial/style/dark/style.css"
                : "/com/estudante/paginaInicial/style/ligth/style.css";
        scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }

    public void updateDimensions(double WIDTH, double HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
}