package com.view.aleatorias;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class texte {

    @FXML
    private VBox sideMenu;
    
    @FXML
    private Label courseNameLabel;
    @FXML
    private Label courseSubtitleLabel;
    @FXML
    private Label courseRatingLabel; 
    @FXML
    private Label courseDurationLabel; 
    @FXML
    private Label courseDescriptionLabel;
    @FXML
    private VBox videoClassesContainer; 
    @FXML
    private VBox materialsContainer;
    @FXML
    private VBox evaluationsContainer; 
    
    @FXML
    private Button videoClassesButton;
    @FXML
    private Button materialsButton;
    @FXML
    private Button evaluationsButton;
    @FXML
    private Button certificateButton;

    @SuppressWarnings("unused")
    @FXML
    private void initialize() {
        videoClassesButton.setOnAction(e -> loadVideoClasses());
        materialsButton.setOnAction(e -> loadMaterials());
        evaluationsButton.setOnAction(e -> loadEvaluations());
        certificateButton.setOnAction(e -> loadCertificate());

        loadCourseDetails();
    }

    private void loadCourseDetails() {
        courseNameLabel.setText("Programação em Java");
        courseSubtitleLabel.setText("Aprenda programação Java do básico ao avançado");
        courseRatingLabel.setText("⭐ 4.8");
        courseDurationLabel.setText("40 horas");
        courseDescriptionLabel.setText("Aprenda programação Java do básico ao avançado, incluindo POO, coleções e APIs.");

        loadVideoClasses(); 
    }

    private void loadVideoClasses() {
        videoClassesContainer.getChildren().clear();

        for (int i = 1; i <= 3; i++) {
            Button videoButton = new Button("Aula " + i + ": Título da Aula " + i);
            videoButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5; -fx-alignment: CENTER-LEFT;");
            videoClassesContainer.getChildren().add(videoButton);
        }
    }

    private void loadMaterials() {
        materialsContainer.getChildren().clear();

        for (int i = 1; i <= 3; i++) {
            Button materialButton = new Button("Material " + i + ": Título do Material " + i);
            materialButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5; -fx-alignment: CENTER-LEFT;");
            materialsContainer.getChildren().add(materialButton);
        }
    }

    private void loadEvaluations() {
        evaluationsContainer.getChildren().clear();

        for (int i = 1; i <= 2; i++) {
            Button evaluationButton = new Button("Avaliação " + i + ": Título da Avaliação " + i);
            evaluationButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5; -fx-alignment: CENTER-LEFT;");
            evaluationsContainer.getChildren().add(evaluationButton);
        }
    }

    private void loadCertificate() {
        System.out.println("Exibindo certificado do curso...");
    }
}
