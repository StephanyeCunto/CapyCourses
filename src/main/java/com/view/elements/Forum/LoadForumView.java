package com.view.elements.Forum;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.*;

import com.controller.student.LoadForumController;
import com.dto.ForumDTO;

public class LoadForumView {
    private static final GridPane forumGrid = new GridPane();
    private static VBox forumContainer = new VBox();
    private static LoadForumController forum = new LoadForumController();
    private static List<ForumDTO> forumList = forum.LoadForum();

    public static VBox loadForum(){
        forumGrid.getChildren().clear();

        setupForumGrid();
        int numColumns = calculateColumns();
        loadList();

        int i = 0;
        for (ForumDTO forumDTO : forumList) {
            VBox forumBox = createForumBox(forumDTO);
            forumGrid.add(forumBox, i % numColumns, i / numColumns);
            i++;
        }

        forumContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            int columns = calculateColumns();
            reorganizeGrid(columns);
        });

        forumGrid.setAlignment(Pos.CENTER);
        forumContainer.setAlignment(Pos.CENTER);

        forumContainer.getChildren().clear();
        forumContainer.getChildren().add(forumGrid);

        return forumContainer;  
    }

    private static void loadList() {
        LoadForumController forum = new LoadForumController();
        forumList = forum.LoadForum();
    }

    private static void setupForumGrid() {
        forumGrid.setHgap(10);
        forumGrid.setVgap(10);
        forumGrid.setPadding(new Insets(10, 10, 10, 10));
    }

    private static int calculateColumns() {
        int columns = 1;
        double width = forumContainer.getWidth();
        if (width > 0) {
            columns = (int) (width / 200);
        }
        return columns;
    }

    private static void reorganizeGrid(int columns) {
        forumGrid.getChildren().clear();
        int i = 0;
        for (ForumDTO forumDTO : forumList) {
            VBox forumBox = createForumBox(forumDTO);
            forumGrid.add(forumBox, i % columns, i / columns);
            i++;
        }
    }

    private static VBox createForumBox(ForumDTO forumDTO) {
        VBox forumBox = new VBox();
        forumBox.getStyleClass().add("card");
        forumBox.setPrefWidth(800);
        forumBox.setAlignment(Pos.TOP_LEFT);

        Label categoryLabel = createStyledLabel(forumDTO.getCategory().toUpperCase(), "Franklin Gothic Medium",
        12);
        categoryLabel.getStyleClass().add("category");

        Label titleLabel = createStyledLabel(forumDTO.getTitle(), "Franklin Gothic Medium", 24);
        titleLabel.getStyleClass().add("title");

        Label authorLabel = createStyledLabel("Por " + forumDTO.getAuthor(), "Franklin Gothic Medium", 14);
        authorLabel.getStyleClass().add("author");

        Label descriptionLabel = createStyledLabel(forumDTO.getDescription(), "Franklin Gothic Medium", 16);
        descriptionLabel.getStyleClass().add("description");

        VBox content = new VBox(12);
        content.setStyle("-fx-padding: 20;");
        content.setAlignment(Pos.TOP_LEFT);

        content.getChildren().addAll(categoryLabel, titleLabel, authorLabel, descriptionLabel);

        forumBox.getChildren().add(content);

        return forumBox;
    }

     private static Label createStyledLabel(String text, String fontFamily, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.getStyleClass().add("label");
        return label;
    }
}
