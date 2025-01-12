package com.view.elements.Perfil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.view.elements.MenuEstudante.Menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class PaginaPerfil implements Initializable {
    @FXML
    private ScrollPane mainContent;
    @FXML
    private VBox sideMenu;
    @FXML
    private VBox container;
    
    public void initialize(URL location, ResourceBundle resources) {
        loadMenu();
        ProfileCard profileCard = new ProfileCard();
        container.getChildren().add(profileCard.createProfileCard());
    }


    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/menu.fxml"));
            VBox menu = loader.load();
            Menu menuController = loader.getController();
            menuController.setCurrentPage("/com/elements/perfil");
            menuController.configureMenu();
            sideMenu.getChildren().add(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
