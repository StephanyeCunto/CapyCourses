package com.view.elements;

import java.util.List;

import com.model.Course;
import com.model.CourseReader;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoadCourses {
    @FXML
    private VBox courseContainer;

    CourseReader reader = new CourseReader();
    List<Course> courses = reader.readCourses();
    GridPane courseGrid = new GridPane();

    public void loadCourses() {
        courseGrid.setHgap(20);
        courseGrid.setVgap(20);
        for (int i = 0; i < courses.size(); i++) {
            VBox courseBox = createCourseBox(courses.get(i));
            courseBox.setPrefWidth(600);
            courseBox.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(courseBox, Priority.ALWAYS);
            courseGrid.add(courseBox, i % 2, i / 2);
        }
        courseContainer.getChildren().add(courseGrid);
    }

    private VBox createCourseBox(Course course) {
        VBox courseBox = new VBox();
        courseBox.getStyleClass().add("card");
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 15;");
        Label nameLabel = createStyledLabel(course.getName(), "Franklin Gothic Medium", 22, Color.WHITE);
        HBox courseInfo = new HBox(8);
        courseInfo.getChildren().addAll(
                createStyledLabel("⭐ " + course.getRating(), "Franklin Gothic Medium", 14, Color.web("#ffffff80")),
                createStyledLabel("•", "Franklin Gothic Medium", 14, Color.web("#ffffff80")),
                createStyledLabel(course.getHours() + " horas", "Franklin Gothic Medium", 14, Color.web("#ffffff80")));
        Label descLabel = createStyledLabel(course.getDescription(), "Franklin Gothic Medium", 14,
                Color.web("#ffffff80"));
        Button startButton = createStartButton();
        content.getChildren().addAll(nameLabel, courseInfo, descLabel, startButton);
        courseBox.getChildren().add(content);
        return courseBox;
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.setTextFill(color);
        return label;
    }

    private Button createStartButton() {
        Button button = new Button("Começar Curso");
        button.getStyleClass().add("modern-button");
        button.setPrefHeight(35);
        button.setStyle(" -fx-min-width: 100; -fx-min-height: 30;-fx-padding: 5 20 5 20;");
        return button;
    }

}