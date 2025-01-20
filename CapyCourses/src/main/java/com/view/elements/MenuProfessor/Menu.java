package com.view.elements.MenuProfessor;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.application.Platform;

import java.util.HashMap;

import com.singleton.UserSession;

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

    private double sizeUser = 0.0;

    public void configureMenu() {
        VBox user = perfilMenu();
        sizeUser = user.getHeight();
        sideMenu.getChildren().addAll(user);
    }

    public VBox createVBox() {
        VBox vbox = new VBox(12);
        Button meusCursosBtn = createButton("Meus Cursos");
        Button cadastrarCursoBtn = createButton("Cadastrar Curso");
        Button progressoBtn = createButton("Progresso");
        Button bibliotecaBtn = createButton("Biblioteca");
        Button forumBtn = createButton("Forum");
        Button configuracoesBtn = createButton("Configurações");
        Button sairBtn = createButton("Sair");

        vbox.getChildren().addAll(meusCursosBtn, cadastrarCursoBtn, progressoBtn, bibliotecaBtn, forumBtn, configuracoesBtn);
        Platform.runLater(() -> {
            Scene currentScene = sairBtn.getScene();
            double sceneHeight = currentScene.getHeight();
            VBox.setMargin(sairBtn, new Insets(sceneHeight - sizeUser - 100, 0, 0, 0));
        });

        vbox.getChildren().add(sairBtn);
        return vbox;
    }

    @SuppressWarnings("unused")
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        fillMap();
        if (buttonMenu.get(currentPage) == text) {
            button.getStyleClass().add("outline-button");
        } else if (!text.equals("Sair")) {
            button.getStyleClass().add("simple-button");
        } else {
            button.getStyleClass().add("register-button");
        }

        button.setOnAction(event -> {
            redirectTo(text);
        });
        return button;
    }

    private void fillMap() {
        buttonMenu.put("/com/professor/paginaCursos", "Meus Cursos");
        buttonMenu.put("/com/professor/paginaCadastroCurso", "Cadastrar Curso");
        buttonMenu.put("/com/professor/progresso/paginaProgressoGeral", "Progresso");
        buttonMenu.put("/com/elements/biblioteca/paginaBiblioteca", "Biblioteca");
        buttonMenu.put("/com/professor/forum/paginaForum", "Forum");
        buttonMenu.put("/com/elements/perfil", "Configurações");
        buttonMenu.put("/com/login_cadastro/paginaLogin", "Sair");
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
        Platform.runLater(() -> {
            sizeUser = user.getHeight();
            VBox button2 = createVBox();
            user.getChildren().clear();
            user.getChildren().addAll(stackPane, welcomeLabel, nameLabel, button2);
        });

        return user;
    }

    private StackPane circleName() {
        StackPane avatarCircle = new StackPane();
        avatarCircle.setMinSize(80, 80);
        avatarCircle.setMaxSize(80, 80);
        avatarCircle.getStyleClass().add("avatar-circle");

        Label nameInitial = new Label(initialName());
        nameInitial.setFont(Font.font("Franklin Gothic Medium", 24));
        nameInitial.setStyle("-fx-text-fill: white;");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(avatarCircle, nameInitial);

        return stackPane;
    }

    private String initialName() {
        String name = UserSession.getInstance().getUserName();
        String[] parts = name.split(" ");

        char initialName = Character.toUpperCase(parts[0].charAt(0));

        char initialSurname = Character.toUpperCase(parts[parts.length - 1].charAt(0));
        return initialName + "" + initialSurname;
    }

    private void redirectTo(String button) {
        try {
            String pageNext = getNextPage(button);
            if (pageNext != null) {
                
                Stage stage = (Stage) sideMenu.getScene().getWindow();
                if (pageNext.equals("/com/estudante/paginaInicial.fxml")) {
                    pageNext = "/com/estudante/paginaInicial/paginaInicial.fxml";
                }
                Parent root = FXMLLoader.load(getClass().getResource(pageNext));
                Scene currentScene = stage.getScene();
                Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());

                animateSceneTransition(currentScene, newScene, stage);

                stage.setScene(newScene);
                stage.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
private void animateSceneTransition(Scene currentScene, Scene newScene, Stage stage) {
 
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