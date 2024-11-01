package com.view.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import com.UserSession;

import java.util.HashMap;

public class Menu {
    private String currentPage;
    private HashMap<String, String> buttonMenu = new HashMap<>();

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    @FXML
    private VBox sideMenu;

    public void configureMenu() {
        VBox user = perfilMenu();
        sideMenu.getChildren().addAll(user);
    }

    public VBox createVBox() {
        VBox vbox = new VBox(12);
        Button cursosDisponiveisBtn = createButton("Cursos Disponíveis");
        Button meusCursosBtn = createButton("Meus Cursos");
        Button progressoBtn = createButton("Progresso");
        Button certificadosBtn = createButton("Certificados");
        Button configuracoesBtn = createButton("Configurações");
        Button sairBtn = createButton("Sair");
        VBox.setMargin(sairBtn, new Insets(150, 0, 0, 0));
        vbox.getChildren().addAll(cursosDisponiveisBtn, meusCursosBtn, progressoBtn, certificadosBtn, configuracoesBtn,
                sairBtn);
        return vbox;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        fillMap();
        if (buttonMenu.get(currentPage) == text) {
            button.getStyleClass().add("modern-button");
        } else if (!text.equals("Sair")) {
            button.getStyleClass().add("modern-button");
            button.setStyle("-fx-background-color:  rgba(255,255,255,0.08);");
        } else {
            button.getStyleClass().add("exit-button");
        }
        button.setOnAction(event -> {
            redirectTo(text);
        });
        return button;
    }

    private void fillMap() {
        buttonMenu.put("/com/paginaInicial", "Cursos Disponíveis");
        buttonMenu.put("/com/paginaMeusCursosAndamento", "Meus Cursos");
        buttonMenu.put("/com/paginaProgresso", "Progresso");
        buttonMenu.put("/com/paginaMeusCursosFinalizados", "Certificados");
        buttonMenu.put("/com/paginaPerfil", "Configurações");
        buttonMenu.put("/com/paginaLogin", "Sair");
    }

    private VBox perfilMenu() {
        VBox user = new VBox();
        user.setAlignment(Pos.CENTER);
        user.setSpacing(12);
        StackPane stackPane = circleName();

        Label welcomeLabel = new Label("Bem-vindo,");
        welcomeLabel.setTextFill(Color.web("#ffffff80"));
        welcomeLabel.setFont(Font.font("Franklin Gothic Medium", 14));
        Label nameLabel = new Label(UserSession.getInstance().getUserName());
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("Franklin Gothic Medium", 22));

        VBox button = createVBox();
        user.getChildren().addAll(stackPane, welcomeLabel, nameLabel, button);
        return user;
    }

    private StackPane circleName() {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, null, new Stop(0, Color.web("#6c63ff")),
                new Stop(1, Color.web("#8d86ff")));
        Circle circle = new Circle(40);
        circle.setFill(gradient);

        Label nameInitial = new Label(initialName());
        nameInitial.setFont(Font.font("Franklin Gothic Medium", 24));
        nameInitial.setStyle("-fx-text-fill: white;");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(circle, nameInitial);

        return stackPane;
    }

    private String initialName(){
        String name= UserSession.getInstance().getUserName();
        String[] parts = name.split(" ");

        char initialName = Character.toUpperCase(parts[0].charAt(0));
   
        char initialSurname =  Character.toUpperCase(parts[parts.length - 1].charAt(0));
        return  initialName+""+initialSurname;
    }

    private void redirectTo(String button) {
        try {
            String pageNext = getNextPage(button);
            if (pageNext != null) {
                Parent currentNode = sideMenu.getScene().getRoot();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(pageNext));
                Parent newRoot = loader.load();
                Scene currentScene = currentNode.getScene();
                currentScene.setRoot(newRoot);
                Stage stage = (Stage) currentScene.getWindow();
                stage.setMaximized(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getNextPage(String button) {
        for (HashMap.Entry<String, String> entry : buttonMenu.entrySet()) {
            if (entry.getValue().equals(button)) {
                return entry.getKey() + ".fxml";
            }
        }
        return null;
    }
}
