package com.singleton;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
  private static Scene scene;

  @Override
  public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
    Image icon = new Image(getClass().getResourceAsStream("/capyCourses 012.png"));
    stage.getIcons().add(icon);
    stage.setTitle("CapyCourses");
    scene = new Scene(loadFXML("login_cadastro/paginaBase"));
    stage.setMaximized(true);
    stage.setScene(scene);
    stage.show();
  }

  static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/" + fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }
}
