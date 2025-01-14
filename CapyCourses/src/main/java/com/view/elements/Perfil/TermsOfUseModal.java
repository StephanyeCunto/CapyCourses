package com.view.elements.Perfil;

import com.dto.BibliotecaDTO;
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

public class TermsOfUseModal {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;

    public TermsOfUseModal(Window owner) {
        updateDimensions(owner.getWidth(), owner.getHeight());
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initOwner(owner);
        showTermsOfUse();
        setupCloseAnimation();
    }

    private VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_LEFT);

        boolean isModoClaro = !Modo.getInstance().getModo();
        String corTexto = isModoClaro ? "#333333" : "#FFFFFF";

        Label titleLabel = new Label("Termos de Uso do CapyCourses");
        titleLabel.setFont(Font.font("Segoe UI Bold", 30));
        titleLabel.setStyle("-fx-text-fill: #2E86C1; -fx-font-weight: bold;");

        Label updateLabel = new Label("Última atualização: 13/01/2025");
        updateLabel.setFont(Font.font("Segoe UI", 16));
        updateLabel.setStyle("-fx-text-fill: " + corTexto + ";");

        Label introLabel = new Label(
            "Bem-vindo ao CapyCourses! Estes Termos de Uso regem o uso do sistema https://github.com/StephanyeCunto/CapyCourses (doravante referido como \"Sistema\" ou \"Serviço\"). Ao utilizar nosso Sistema, você concorda com estes termos."
        );
        introLabel.setFont(Font.font("Segoe UI", 16));
        introLabel.setWrapText(true);
        introLabel.setStyle("-fx-text-fill: " + corTexto + ";");

        Label subtitle1 = new Label("1. Aceitação dos Termos");
        subtitle1.setFont(Font.font("Segoe UI Bold", 22));
        subtitle1.setStyle("-fx-text-fill: #2E86C1; -fx-font-weight: bold;");

        Label text1 = new Label(
            "Ao acessar ou usar o Sistema, você concorda com estes Termos de Uso e com nossa Política de Privacidade. Se você não concordar com estes termos, não use o Sistema."
        );
        text1.setFont(Font.font("Segoe UI", 16));
        text1.setWrapText(true);
        text1.setStyle("-fx-text-fill: " + corTexto + ";");

        Label subtitle2 = new Label("2. Uso do Sistema");
        subtitle2.setFont(Font.font("Segoe UI Bold", 22));
        subtitle2.setStyle("-fx-text-fill: #2E86C1; -fx-font-weight: bold;");

        Label text2 = new Label(
            "Você concorda em usar o Sistema apenas para fins legais e de acordo com estes Termos de Uso. Você não pode:\n" +
            "- Violar qualquer lei ou regulamento aplicável.\n" +
            "- Infringir os direitos de propriedade intelectual de terceiros.\n" +
            "- Enviar conteúdo ofensivo, difamatório ou prejudicial.\n" +
            "- Interferir na operação do Sistema ou em sua segurança."
        );
        text2.setFont(Font.font("Segoe UI", 16));
        text2.setWrapText(true);
        text2.setStyle("-fx-text-fill: " + corTexto + ";");

        Label subtitle3 = new Label("3. Conta do Usuário");
        subtitle3.setFont(Font.font("Segoe UI Bold", 22));
        subtitle3.setStyle("-fx-text-fill: #2E86C1; -fx-font-weight: bold;");

        Label text3 = new Label(
            "Para acessar certas funcionalidades do Sistema, você pode precisar criar uma conta. Você é responsável por manter a confidencialidade de suas credenciais e por todas as atividades que ocorrerem em sua conta."
        );
        text3.setFont(Font.font("Segoe UI", 16));
        text3.setWrapText(true);
        text3.setStyle("-fx-text-fill: " + corTexto + ";");

        Label subtitle4 = new Label("4. Propriedade Intelectual");
        subtitle4.setFont(Font.font("Segoe UI Bold", 22));
        subtitle4.setStyle("-fx-text-fill: #2E86C1; -fx-font-weight: bold;");

        Label text4 = new Label(
            "Todo o conteúdo do Sistema, incluindo textos, gráficos, logos e software, é propriedade do CapyCourses ou de seus licenciadores e está protegido por leis de propriedade intelectual."
        );
        text4.setFont(Font.font("Segoe UI", 16));
        text4.setWrapText(true);
        text4.setStyle("-fx-text-fill: " + corTexto + ";");

        Label subtitle5 = new Label("5. Limitação de Responsabilidade");
        subtitle5.setFont(Font.font("Segoe UI Bold", 22));
        subtitle5.setStyle("-fx-text-fill: #2E86C1; -fx-font-weight: bold;");

        Label text5 = new Label(
            "O CapyCourses não será responsável por quaisquer danos diretos, indiretos, incidentais ou consequenciais resultantes do uso ou da incapacidade de usar o Sistema."
        );
        text5.setFont(Font.font("Segoe UI", 16));
        text5.setWrapText(true);
        text5.setStyle("-fx-text-fill: " + corTexto + ";");

        content.getChildren().addAll(
            titleLabel, updateLabel, introLabel,
            subtitle1, text1,
            subtitle2, text2,
            subtitle3, text3,
            subtitle4, text4,
            subtitle5, text5
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox container = new VBox(scrollPane);
        container.setAlignment(Pos.TOP_LEFT);
        container.setStyle("-fx-background-color: transparent;");
        return container;
    }

    private void showTermsOfUse() {
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
        VBox content = new VBox(15);
        content.setPadding(new Insets(20, 20, 0, 20));

        Label titleLabel = new Label("Termos de Uso");
        titleLabel.setFont(Font.font("Segoe UI Bold", 28));
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

        content.getChildren().addAll(mainHeader);

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