package com.view.elements.Perfil;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class ProfileCard {

    public VBox createProfileCard() {
        // Cria o VBox principal
        VBox vbox = new VBox(25);
        vbox.getStyleClass().add("content-card");

        // Cria o HBox para a foto e botões
        HBox hboxTop = new HBox(20);
        hboxTop.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Cria o StackPane para o círculo e o label "JS"
        StackPane stackPane = new StackPane();
        Circle circle = new Circle(50, Color.web("#6c63ff"));
        Label labelJS = new Label("JS");
        labelJS.getStyleClass().add("card-title");
        stackPane.getChildren().addAll(circle, labelJS);

        // Cria o VBox para os botões e o label de informações
        VBox vboxButtons = new VBox(10);
        HBox hboxButtons = new HBox(15);
        Button btnChangePhoto = new Button("Alterar Foto");
        btnChangePhoto.getStyleClass().add("modern-button");
        Button btnRemove = new Button("Remover");
        btnRemove.getStyleClass().add("outline-button");

        btnChangePhoto.setStyle(
            "-fx-min-height: 50px; " +
            "-fx-pref-height: 50px; " +
            "-fx-max-height: 50px;"
        );
        btnRemove.setStyle(
            "-fx-min-height: 50px; " +
            "-fx-pref-height: 50px; " +
            "-fx-max-height: 50px;"
        );

        hboxButtons.getChildren().addAll(btnChangePhoto, btnRemove);

        Label labelInfo = new Label("Tamanho máximo: 5MB. Formatos: JPG, PNG");
        labelInfo.getStyleClass().add("card-subtitle");

        vboxButtons.getChildren().addAll(hboxButtons, labelInfo);

        // Adiciona o StackPane e o VBox de botões ao HBox superior
        hboxTop.getChildren().addAll(stackPane, vboxButtons);

        // Cria o VBox para as informações pessoais
        VBox vboxPersonalInfo = new VBox(20);
        Label labelPersonalInfo = new Label("Informações Pessoais");
        labelPersonalInfo.getStyleClass().add("card-title");

        HBox hboxFields = new HBox(20);

        // Nome Completo
        VBox vboxNome = new VBox();
        vboxNome.setId("nomeVBox");
        HBox.setHgrow(vboxNome, javafx.scene.layout.Priority.ALWAYS);
        Label labelNome = new Label("Nome Completo");
        labelNome.getStyleClass().add("field-label");
        TextField textFieldNome = new TextField("João Silva");
        textFieldNome.getStyleClass().add("custom-text-field");
        vboxNome.getChildren().addAll(labelNome, textFieldNome);

        // Email
        VBox vboxEmail = new VBox();
        vboxEmail.setId("emailVBox");
        HBox.setHgrow(vboxEmail, javafx.scene.layout.Priority.ALWAYS);
        Label labelEmail = new Label("Email");
        labelEmail.getStyleClass().add("field-label");
        TextField textFieldEmail = new TextField("joao.silva@email.com");
        textFieldEmail.getStyleClass().add("custom-text-field");
        vboxEmail.getChildren().addAll(labelEmail, textFieldEmail);

        // Telefone
        VBox vboxTelefone = new VBox();
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
        labelBioInfo.getStyleClass().add("card-subtitle");
        vboxBio.getChildren().addAll(labelBio, textAreaBio, labelBioInfo);

        // Adiciona todos os componentes ao VBox principal
        vbox.getChildren().addAll(hboxTop, vboxPersonalInfo, vboxBio);

        return vbox;
    }
}