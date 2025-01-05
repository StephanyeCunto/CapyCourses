package com.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Base {
    protected void redirectTo(String pageNext, Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(pageNext));
            Image icon = new Image("/capyCourses 012.png");
            Scene currentScene = stage.getScene();
            Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());
            stage.setScene(newScene);
            stage.getIcons().add(icon);
            stage.setTitle("CapyCourses");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
