package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException; 

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
           Image icon = new Image(App.class.getResourceAsStream("/capyCourses.png"), 128, 128, true, true);
           
           scene = new Scene(loadFXML("/com/professor/paginaCadastroCurso"));
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}