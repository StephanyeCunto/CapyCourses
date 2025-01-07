package com.view.elements.Biblioteca;

import java.util.*;
import java.util.stream.Collectors;

import com.controller.elements.BibliotecaController;
import com.dto.BibliotecaDTO;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Biblioteca {
    private static final GridPane bibliotecaGrid = new GridPane();
    private static VBox bibliotecaContainer = new VBox();
    private static BibliotecaController biblioteca = new BibliotecaController();
    private static List<BibliotecaDTO> bibliotecaList = biblioteca.loadBiblioteca();

    public static VBox loadBiblioteca(String status) {
        bibliotecaGrid.getChildren().clear();

        setupBibliotecaGrid();
        int numColumns = calculateColumns();
        loadList(status);

        int i = 0;
        for (BibliotecaDTO bibliotecaDTO : bibliotecaList) {
            VBox bibliotecaBox = createBibliotecaBox(bibliotecaDTO, status);
            bibliotecaGrid.add(bibliotecaBox, i % numColumns, i / numColumns);
            i++;
        }

        bibliotecaContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns, bibliotecaList, status);
        });

        bibliotecaGrid.setAlignment(Pos.CENTER);
        bibliotecaContainer.setAlignment(Pos.CENTER);

        bibliotecaContainer.getChildren().clear();
        bibliotecaContainer.getChildren().add(bibliotecaGrid);

        return bibliotecaContainer;
    }

    private static void loadList(String status) {
        BibliotecaController biblioteca = new BibliotecaController();
        if (status.equals("todos")) {
            bibliotecaList = biblioteca.loadBiblioteca();
        } else {
            bibliotecaList = biblioteca.loadBiblioteca().stream()
                    .filter(bibliotecaDTO -> bibliotecaDTO.isFavorite())
                    .collect(Collectors.toList());
        }
    }

    private static VBox createBibliotecaBox(BibliotecaDTO bibliotecaDTO, String status) {
        VBox bibliotecaBox = new VBox();
        bibliotecaBox.getStyleClass().add("card");
        bibliotecaBox.setPrefWidth(500);
        bibliotecaBox.setAlignment(Pos.TOP_LEFT);

        Label categoryLabel = createStyledLabel(bibliotecaDTO.getCategory().toUpperCase(), "Franklin Gothic Medium",
                12);
        categoryLabel.getStyleClass().add("category");

        Label titleLabel = createStyledLabel(bibliotecaDTO.getTitle(), "Franklin Gothic Medium", 24);
        titleLabel.getStyleClass().add("title");

        Label authorLabel = createStyledLabel("Por " + bibliotecaDTO.getAuthor(), "Franklin Gothic Medium", 14);
        authorLabel.getStyleClass().add("author");

        Label descriptionLabel = createStyledLabel(bibliotecaDTO.getDescription(), "Franklin Gothic Medium", 16);
        descriptionLabel.getStyleClass().add("description");

        VBox content = new VBox(12);
        content.setStyle("-fx-padding: 20;");
        content.setAlignment(Pos.TOP_LEFT);

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button button = new Button("Visualizar");
        button.setOnAction(e -> {
            BibliotecaModal modal = new BibliotecaModal(getDefaultWindow(),
                    "/Users/stephanye/Downloads/teste/python_para_iniciantes_ste_cunto_certificado.pdf", bibliotecaDTO);
        });
        button.getStyleClass().add("simple-button");
        Button favoriteBtn = new Button();

        if (bibliotecaDTO.isFavorite()) {
            favoriteBtn.setText("Remover dos favoritos");
            favoriteBtn.setOnAction(e -> {
                BibliotecaController bibliotecaController = new BibliotecaController();
                bibliotecaController.removeFavorite(bibliotecaDTO.getTitle());
                int columns = calculateColumns();
                loadList(status);
                reorganizeGrid(columns, bibliotecaList, status);
                favoriteBtn.setText("Adicionar aos favoritos");
            });
        } else {
            favoriteBtn.setText("Adicionar aos favoritos");
            favoriteBtn.setOnAction(e -> {
                BibliotecaController bibliotecaController = new BibliotecaController();
                bibliotecaController.addFavorite(bibliotecaDTO.getTitle());
                int columns = calculateColumns();
                loadList(status);
                reorganizeGrid(columns, bibliotecaList, status);
                favoriteBtn.setText("Remover dos favoritos");
            });
        }
        favoriteBtn.getStyleClass().add("outline-button");

        actions.getChildren().addAll(button, favoriteBtn);

        content.getChildren().addAll(categoryLabel, titleLabel, authorLabel, descriptionLabel, actions);

        bibliotecaBox.getChildren().addAll(content);

        return bibliotecaBox;
    }

    private static Window getDefaultWindow() {
        return Stage.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
    }

    private static Label createStyledLabel(String text, String fontFamily, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.getStyleClass().add("label");
        return label;
    }

    private static void setupBibliotecaGrid() {
        bibliotecaGrid.setHgap(20);
        bibliotecaGrid.setVgap(20);
        bibliotecaGrid.setPadding(new javafx.geometry.Insets(20));
        bibliotecaGrid.setMaxWidth(Double.MAX_VALUE);
        bibliotecaGrid.setMaxWidth(Double.MAX_VALUE);
    }

    private static int calculateColumns() {
        if (bibliotecaContainer == null) {
            return 1;
        }
        double width = bibliotecaContainer.getWidth();
        return Math.max(1, (int) (width / 450));
    }

    private static void reorganizeGrid(int numColumns, List<BibliotecaDTO> bibliotecaList, String status) {
        bibliotecaGrid.getChildren().clear();
        int i = 0;
        for (BibliotecaDTO bibliotecaDTO : bibliotecaList) {
            VBox bibliotecaBox = createBibliotecaBox(bibliotecaDTO, status);
            bibliotecaGrid.add(bibliotecaBox, i % numColumns, i / numColumns);
            i++;
        }
    }
}
