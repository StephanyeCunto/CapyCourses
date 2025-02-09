package com.view.login_cadastro.base;

import java.net.URL;
import java.util.ResourceBundle;

import com.view.elements.buttonModos.ButtonModos;

import javafx.beans.property.*;

import lombok.Getter;
import lombok.Setter;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.layout.*;

@Setter
@Getter
public class Base implements Initializable {
    @FXML
    private GridPane mainPane;
    @FXML
    private StackPane container;
    @FXML
    private VBox leftSection;
    @FXML
    private VBox rightSection;
    @FXML
    private HBox toggleButtonHBox;

    private StringProperty page = new SimpleStringProperty("Login");

    public void initialize(URL location, ResourceBundle resources) {
        Inicializar in = new Inicializar();
        in.loadAnimation(mainPane, container, leftSection, rightSection);

        loadButtonModos();
        loadPage();

        getPage().addListener((observable, oldValue, newValue) -> {
            loadPage();
        });

    }

    private void loadButtonModos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elements/buttonModos.fxml"));
            Parent root = loader.load();
            ButtonModos buttonModos = loader.getController();
            buttonModos.setContainer(container);
            buttonModos.setDarkStylePath("/com/login_cadastro/style/dark/style.css");
            buttonModos.setLigthStringPath("/com/login_cadastro/style/ligth/style.css");
            buttonModos.changeMode();
            buttonModos.initialize();
            toggleButtonHBox.getChildren().add(root);
        } catch (Exception e) {
            System.out.println("Erro ao carregar botão: ");
            e.printStackTrace();
        }

    }

    private void loadPage() {
        try {
            if (getPage().get().equals("Login")) {
                loadLogin();
            }
            if (getPage().get().equals("Cadastro")) {
                rightSection.getChildren().clear();
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar página: ");
            e.printStackTrace();
        }
    }

    private void loadLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/login_cadastro/login/paginaLogin.fxml"));
            Parent root = loader.load();
            Login controller = loader.getController();

            controller.getPage().addListener((observable, oldValue, newValue) -> {
                page.set(controller.getPage().get());
            });

            controller.setLeftSection(leftSection);
            controller.setupErrorNotification();

            rightSection.getChildren().clear();
            rightSection.getChildren().add(root);

        } catch (Exception e) {
            System.out.println("Erro ao carregar página Login");
            e.printStackTrace();
        }
    }

}