package com.view.utility;

import java.io.IOException;

import com.singleton.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ViewLoader {
    public static void load(String page, VBox target) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(page));
        Region newView = loader.load();
        target.getChildren().setAll(newView);
    }
}
