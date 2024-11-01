package com;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.view.elements.Menu;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class teste implements Initializable {
    @FXML
    private VBox sideMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMenu();
    }

    private void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/menu.fxml"));
            VBox menu = loader.load();

            Menu menuController = loader.getController();
            menuController.setCurrentPage("/com/paginaMeusCursosAndamento");
            menuController.configureMenu();
            sideMenu.getChildren().add(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
