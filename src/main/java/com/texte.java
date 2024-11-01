package com;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class texte {

    @FXML
    private VBox sideMenu;
    
    @FXML
    private Label courseNameLabel; // Nome do curso
    @FXML
    private Label courseSubtitleLabel; // Subtítulo do curso
    @FXML
    private Label courseRatingLabel; // Rating do curso
    @FXML
    private Label courseDurationLabel; // Duração do curso
    @FXML
    private Label courseDescriptionLabel; // Descrição do curso

    @FXML
    private VBox videoClassesContainer; // Container para vídeo-aulas
    @FXML
    private VBox materialsContainer; // Container para materiais
    @FXML
    private VBox evaluationsContainer; // Container para avaliações
    
    @FXML
    private Button videoClassesButton;
    @FXML
    private Button materialsButton;
    @FXML
    private Button evaluationsButton;
    @FXML
    private Button certificateButton;

    @FXML
    private void initialize() {
        videoClassesButton.setOnAction(e -> loadVideoClasses());
        materialsButton.setOnAction(e -> loadMaterials());
        evaluationsButton.setOnAction(e -> loadEvaluations());
        certificateButton.setOnAction(e -> loadCertificate());

        // Carregar os detalhes do curso
        loadCourseDetails();
    }

    private void loadCourseDetails() {
        // Aqui você irá definir os valores dos labels com os dados do curso
        courseNameLabel.setText("Programação em Java");
        courseSubtitleLabel.setText("Aprenda programação Java do básico ao avançado");
        courseRatingLabel.setText("⭐ 4.8");
        courseDurationLabel.setText("40 horas");
        courseDescriptionLabel.setText("Aprenda programação Java do básico ao avançado, incluindo POO, coleções e APIs.");

        // Carregar vídeo-aulas
        loadVideoClasses(); // Aqui você pode chamar a função para preencher as vídeo-aulas
    }

    private void loadVideoClasses() {
        // Limpa o container antes de adicionar os novos elementos
        videoClassesContainer.getChildren().clear();

        // Exemplo de adição de vídeo-aulas
        for (int i = 1; i <= 3; i++) {
            Button videoButton = new Button("Aula " + i + ": Título da Aula " + i);
            videoButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5; -fx-alignment: CENTER-LEFT;");
            videoClassesContainer.getChildren().add(videoButton);
        }
    }

    private void loadMaterials() {
        // Limpa o container antes de adicionar os novos elementos
        materialsContainer.getChildren().clear();

        // Exemplo de adição de materiais
        for (int i = 1; i <= 3; i++) {
            Button materialButton = new Button("Material " + i + ": Título do Material " + i);
            materialButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5; -fx-alignment: CENTER-LEFT;");
            materialsContainer.getChildren().add(materialButton);
        }
    }

    private void loadEvaluations() {
        // Limpa o container antes de adicionar os novos elementos
        evaluationsContainer.getChildren().clear();

        // Exemplo de adição de avaliações
        for (int i = 1; i <= 2; i++) {
            Button evaluationButton = new Button("Avaliação " + i + ": Título da Avaliação " + i);
            evaluationButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 5; -fx-alignment: CENTER-LEFT;");
            evaluationsContainer.getChildren().add(evaluationButton);
        }
    }

    private void loadCertificate() {
        // Lógica para carregar ou mostrar o certificado do curso
        System.out.println("Exibindo certificado do curso...");
    }
}
