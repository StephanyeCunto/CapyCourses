package com.view.elements.Perfil;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class SecuritySection {

    public VBox createSecuritySection() {
        // VBox principal
        VBox vbox = new VBox(25);
        vbox.getStyleClass().add("content-card");

        // Título "Segurança"
        Label securityTitle = new Label("Segurança");
        securityTitle.getStyleClass().add("card-title");

        // VBox interna para a seção de alteração de senha
        VBox changePasswordSection = new VBox(20);

        // Título "Alterar Senha"
        Label changePasswordTitle = new Label("Alterar Senha");
        changePasswordTitle.getStyleClass().add("card-title");

        // GridPane para organizar os campos de senha
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(15);

        // Definindo as colunas (33% de largura cada)
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        // Linha única
        RowConstraints row = new RowConstraints();
        gridPane.getRowConstraints().add(row);

        // Campo "Senha Atual"
        VBox currentPasswordBox = new VBox(8);
        Label currentPasswordLabel = new Label("Senha Atual");
        currentPasswordLabel.getStyleClass().add("field-label");
        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.getStyleClass().add("custom-text-field");
        currentPasswordBox.getChildren().addAll(currentPasswordLabel, currentPasswordField);
        GridPane.setColumnIndex(currentPasswordBox, 0);

        // Campo "Nova Senha"
        VBox newPasswordBox = new VBox(8);
        Label newPasswordLabel = new Label("Nova Senha");
        newPasswordLabel.getStyleClass().add("field-label");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.getStyleClass().add("custom-text-field");
        newPasswordBox.getChildren().addAll(newPasswordLabel, newPasswordField);
        GridPane.setColumnIndex(newPasswordBox, 1);

        // Campo "Confirmar Nova Senha"
        VBox confirmPasswordBox = new VBox(8);
        Label confirmPasswordLabel = new Label("Confirmar Nova Senha");
        confirmPasswordLabel.getStyleClass().add("field-label");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("custom-text-field");
        confirmPasswordBox.getChildren().addAll(confirmPasswordLabel, confirmPasswordField);
        GridPane.setColumnIndex(confirmPasswordBox, 2);

        // Adicionando os campos ao GridPane
        gridPane.getChildren().addAll(currentPasswordBox, newPasswordBox, confirmPasswordBox);

        // VBox para os requisitos de senha
        VBox passwordRequirementsBox = new VBox(5);
        passwordRequirementsBox.setPadding(new Insets(10, 0, 0, 0)); // Padding superior de 10
        Label requirementsTitle = new Label("Requisitos de senha:");
        requirementsTitle.getStyleClass().add("card-subtitle");
        Label requirement1 = new Label("• Mínimo de 6 caracteres");
        requirement1.getStyleClass().add("card-subtitle");
        passwordRequirementsBox.getChildren().addAll(requirementsTitle, requirement1);

        // Adicionando tudo à seção de alteração de senha
        changePasswordSection.getChildren().addAll(changePasswordTitle, gridPane, passwordRequirementsBox);

        // Adicionando tudo ao VBox principal
        vbox.getChildren().addAll(securityTitle, changePasswordSection);

        return vbox;
    }
}