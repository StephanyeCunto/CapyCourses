package com.view.elements.Perfil;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrivacySection {

    public VBox createPrivacySection() {
        VBox vbox = new VBox(25);
        vbox.getStyleClass().add("content-card");

        Label privacyTitle = new Label("Privacidade");
        privacyTitle.getStyleClass().add("card-title");

        VBox consentSection = new VBox(15);
        Label consentTitle = new Label("Consentimento de Dados");
        consentTitle.getStyleClass().add("card-title");

        HBox analyticsConsentHBox = new HBox(10);
        analyticsConsentHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        CheckBox analyticsCheckBox = new CheckBox();
        analyticsCheckBox.getStyleClass().add("custom-checkbox");
        Label analyticsLabel = new Label("Permitir coleta de dados analíticos");
        analyticsLabel.getStyleClass().add("card-subtitle");
        analyticsConsentHBox.getChildren().addAll(analyticsCheckBox, analyticsLabel);

        HBox shareDataHBox = new HBox(10);
        shareDataHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        CheckBox shareDataCheckBox = new CheckBox();
        shareDataCheckBox.getStyleClass().add("custom-checkbox");
        Label shareDataLabel = new Label("Compartilhar dados para melhoria do serviço");
        shareDataLabel.getStyleClass().add("card-subtitle");
        shareDataHBox.getChildren().addAll(shareDataCheckBox, shareDataLabel);

        VBox consentOptions = new VBox(10);
        consentOptions.getChildren().addAll(analyticsConsentHBox, shareDataHBox);

        consentSection.getChildren().addAll(consentTitle, consentOptions);

        VBox dataManagementSection = new VBox(15);
        Label dataManagementTitle = new Label("Gerenciamento de Dados");
        dataManagementTitle.getStyleClass().add("card-title");

        Button deleteAccountButton = new Button("Solicitar Exclusão de Conta");
        deleteAccountButton.getStyleClass().add("outline-button");

        HBox dataManagementHBox = new HBox(15);
        dataManagementHBox.getChildren().add(deleteAccountButton);

        dataManagementSection.getChildren().addAll(dataManagementTitle, dataManagementHBox);

        VBox privacyInfoSection = new VBox(10);
        Label privacyUpdateLabel = new Label("Última atualização da política de privacidade: 15/12/2024");
        privacyUpdateLabel.getStyleClass().add("card-subtitle");

        Button privacyPolicyButton = new Button("Política de Privacidade");
        privacyPolicyButton.getStyleClass().add("outline-button");
        Stage ownerStage = (Stage) privacyPolicyButton.getScene().getWindow();
        privacyPolicyButton.setOnAction(e -> new PrivacyPolicyModal(ownerStage));

        Button termsOfUseButton = new Button("Termos de Uso");
        termsOfUseButton.getStyleClass().add("outline-button");

        HBox privacyButtonsHBox = new HBox(10);
        privacyButtonsHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        privacyButtonsHBox.getChildren().addAll(privacyPolicyButton, termsOfUseButton);

        privacyInfoSection.getChildren().addAll(privacyUpdateLabel, privacyButtonsHBox);

        vbox.getChildren().addAll(privacyTitle, consentSection, dataManagementSection, privacyInfoSection);

        return vbox;
    }
}