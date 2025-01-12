package com.view.elements.Perfil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PreferencesSection {

    public VBox createPreferencesSection() {
        VBox vbox = new VBox(25);
        vbox.getStyleClass().add("content-card");

        Label preferencesTitle = new Label("Preferências");
        preferencesTitle.getStyleClass().add("card-title");

        VBox themeSection = new VBox(15);
        Label themeTitle = new Label("Tema");
        themeTitle.getStyleClass().add("card-title");

        ComboBox<String> themeComboBox = new ComboBox<>();
        ObservableList<String> themes = FXCollections.observableArrayList("Claro", "Escuro", "Sistema");
        themeComboBox.setItems(themes);
        themeComboBox.setValue("Sistema"); 

        HBox themeHBox = new HBox(15);
        themeHBox.getChildren().add(themeComboBox);

        themeSection.getChildren().addAll(themeTitle, themeHBox);

        VBox notificationsSection = new VBox(15);
        Label notificationsTitle = new Label("Notificações");
        notificationsTitle.getStyleClass().add("card-title");

        HBox emailNotificationHBox = new HBox(10);
        emailNotificationHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        CheckBox emailCheckBox = new CheckBox();
        emailCheckBox.getStyleClass().add("custom-checkbox");
        Label emailLabel = new Label("Notificações por Email");
        emailLabel.getStyleClass().add("card-subtitle");
        emailNotificationHBox.getChildren().addAll(emailCheckBox, emailLabel);

        HBox pushNotificationHBox = new HBox(10);
        pushNotificationHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        CheckBox pushCheckBox = new CheckBox();
        pushCheckBox.getStyleClass().add("custom-checkbox");
        Label pushLabel = new Label("Notificações Push");
        pushLabel.getStyleClass().add("card-subtitle");
        pushNotificationHBox.getChildren().addAll(pushCheckBox, pushLabel);

        VBox notificationsOptions = new VBox(10);
        notificationsOptions.getChildren().addAll(emailNotificationHBox, pushNotificationHBox);

        notificationsSection.getChildren().addAll(notificationsTitle, notificationsOptions);

        vbox.getChildren().addAll(preferencesTitle, themeSection, notificationsSection);

        return vbox;
    }
}