package com.view.auth;

import java.io.IOException;

import com.view.utility.ViewLoader;

import javafx.fxml.*;
import javafx.scene.layout.*;


public class AuthPage {
    @FXML
    private VBox formSection;

    @FXML
    public void initialize() throws IOException{
        ViewLoader.load("/com/auth/LoginPage.fxml", formSection);
    }
}
