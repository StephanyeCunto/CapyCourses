package com.view.auth;

import java.io.IOException;

import com.singleton.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;


public class AuthPage {
    @FXML
    VBox formSection;
    @FXML 
    StackPane container;

    @FXML
    public void initialize() throws IOException{
        loadView("RegisterPageSecundary");
    }

    private void loadView(String page) throws IOException{
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/auth/" + page + ".fxml"));
        Pane newView = loader.load();
        formSection.getChildren().setAll(newView);
    }
}
