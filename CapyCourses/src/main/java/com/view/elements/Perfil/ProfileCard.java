package com.view.elements.Perfil;

import com.singleton.UserSession;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
public class ProfileCard {

    public VBox createProfileCard() {
        VBox vbox = new VBox(20); // Reduzi o espaçamento para 20
        vbox.getStyleClass().add("content-card");

        HBox hboxTop = new HBox(20);
        hboxTop.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        StackPane stackPane = circleName();

        VBox vboxButtons = new VBox(10);
        HBox hboxButtons = new HBox(15);
        Button btnChangePhoto = new Button("Alterar Foto");
        btnChangePhoto.getStyleClass().add("modern-button");
        Button btnRemove = new Button("Remover");
        btnRemove.getStyleClass().add("outline-button");

        hboxButtons.getChildren().addAll(btnChangePhoto, btnRemove);

        Label labelInfo = new Label("Tamanho máximo: 5MB. Formatos: JPG, PNG");
        labelInfo.getStyleClass().add("section-title");

        vboxButtons.getChildren().addAll(hboxButtons, labelInfo);

        hboxTop.getChildren().addAll(stackPane, vboxButtons);

        // Cria o VBox para as informações pessoais
        VBox vboxPersonalInfo = new VBox(15); // Reduzi o espaçamento para 15
        Label labelPersonalInfo = new Label("Informações Pessoais");
        labelPersonalInfo.getStyleClass().add("card-title");

        HBox hboxFields = new HBox(15); // Reduzi o espaçamento para 15

        // Nome Completo
        VBox vboxNome = new VBox(5); // Adicionei espaçamento entre o label e o campo de texto
        vboxNome.setId("nomeVBox");
        HBox.setHgrow(vboxNome, javafx.scene.layout.Priority.ALWAYS);
        Label labelNome = new Label("Nome Completo");
        labelNome.getStyleClass().add("field-label");
        TextField textFieldNome = new TextField("João Silva");
        textFieldNome.getStyleClass().add("custom-text-field");
        vboxNome.getChildren().addAll(labelNome, textFieldNome);

        // Email
        VBox vboxEmail = new VBox(5); // Adicionei espaçamento entre o label e o campo de texto
        vboxEmail.setId("emailVBox");
        HBox.setHgrow(vboxEmail, javafx.scene.layout.Priority.ALWAYS);
        Label labelEmail = new Label("Email");
        labelEmail.getStyleClass().add("field-label");
        TextField textFieldEmail = new TextField("joao.silva@email.com");
        textFieldEmail.getStyleClass().add("custom-text-field");
        vboxEmail.getChildren().addAll(labelEmail, textFieldEmail);

        // Telefone
        VBox vboxTelefone = new VBox(5); // Adicionei espaçamento entre o label e o campo de texto
        vboxTelefone.setId("telefoneVBox");
        HBox.setHgrow(vboxTelefone, javafx.scene.layout.Priority.ALWAYS);
        Label labelTelefone = new Label("Telefone");
        labelTelefone.getStyleClass().add("field-label");
        TextField textFieldTelefone = new TextField("+55 (11) 99999-9999");
        textFieldTelefone.getStyleClass().add("custom-text-field");
        vboxTelefone.getChildren().addAll(labelTelefone, textFieldTelefone);

        hboxFields.getChildren().addAll(vboxNome, vboxEmail, vboxTelefone);
        vboxPersonalInfo.getChildren().addAll(labelPersonalInfo, hboxFields);

        // Cria o VBox para a biografia
        VBox vboxBio = new VBox(8);
        Label labelBio = new Label("Biografia");
        labelBio.getStyleClass().add("field-label");
        TextArea textAreaBio = new TextArea();
        textAreaBio.setWrapText(true);
        textAreaBio.getStyleClass().add("custom-text-area");
        Label labelBioInfo = new Label("Máximo de 200 caracteres");
        labelBioInfo.getStyleClass().add("section-title");
        vboxBio.getChildren().addAll(labelBio, textAreaBio, labelBioInfo);

        // Adiciona todos os componentes ao VBox principal
        vbox.getChildren().addAll(hboxTop, vboxPersonalInfo, vboxBio);

        return vbox;
    }

    private StackPane circleName() {
        StackPane avatarCircle = new StackPane();
        avatarCircle.setMinSize(100, 100); // Aumentei o tamanho do círculo
        avatarCircle.setMaxSize(100, 100); // Aumentei o tamanho do círculo
        avatarCircle.getStyleClass().add("avatar-circle");

        Label nameInitial = new Label(initialName());
        nameInitial.setFont(Font.font("Franklin Gothic Medium", 32)); // Aumentei o tamanho da fonte
        nameInitial.setStyle("-fx-text-fill: white;");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(avatarCircle, nameInitial);

        return stackPane;
    }

    private String initialName() {
        String name = UserSession.getInstance().getUserName();
        String[] parts = name.split(" ");

        char initialName = Character.toUpperCase(parts[0].charAt(0));
        char initialSurname = Character.toUpperCase(parts[parts.length - 1].charAt(0));
        return initialName + "" + initialSurname;
    }
}