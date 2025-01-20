package com.view.elements.Courses;

import com.view.Modo;
import com.model.elements.Course.Question;
import com.model.login_cadastro.Student;
import com.model.elements.Course.StudentAnswer;
import com.util.JPAUtil;
import javafx.scene.control.Alert;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class QuestionaireModal {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;
    private static final double CONTENT_SPACING = 25;
    private static final double PADDING = 30;
    private static final double HEADER_SPACING = 20;
    private VBox questionsContainer;
    private String title;
    private String description;
    private String score;
    private List<Question> questions;
    private Student currentStudent;
    private Map<Question, Object> answers = new HashMap<>(); // Armazena as respostas (RadioButton, CheckBox ou TextArea)

    public QuestionaireModal() {
        // Inicialização básica do modal
        super();
        updateDimensions(0, 0);
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        questionsContainer = new VBox(10);
        loadModel();
        setupCloseAnimation();
    }

    public void updateDimensions(double WIDTH, double HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    private void loadModel() {
        showModel();
    }

    private void showModel() {
        StackPane backdrop = createBackdrop();
        VBox modalContainer = createModalContainer();
        
        // Create main content sections
        VBox header = createHeader();
        VBox content = createContent();
        VBox footer = createFooter();

        modalContainer.getChildren().addAll(header, content, footer);
        backdrop.getChildren().add(modalContainer);

        Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        modo(scene);

        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private VBox createModalContainer() {
        VBox container = new VBox(CONTENT_SPACING);
        container.setPadding(new Insets(PADDING));
        container.setMaxWidth(WIDTH * 0.8);
        container.setMaxHeight(HEIGHT * 0.8);
        container.setMinHeight(HEIGHT * 0.8);
        container.setMinWidth(WIDTH * 0.8);
        container.getStyleClass().add("card");
        container.setEffect(createDropShadow());
        return container;
    }

    private VBox createContent() {
        VBox content = new VBox(CONTENT_SPACING);
        content.setPadding(new Insets(0, PADDING, PADDING, PADDING));
        content.setAlignment(Pos.TOP_LEFT);

        // Cabeçalho do questionário com dados reais
        Label titleLabel = new Label(this.title);
        titleLabel.getStyleClass().add("title");

        Label descriptionLabel = new Label(this.description);
        descriptionLabel.getStyleClass().add("description");

        Label scoreLabel = new Label("Pontuação: " + this.score);
        scoreLabel.getStyleClass().add("score");

        content.getChildren().addAll(titleLabel, descriptionLabel, scoreLabel);

        // Container para as questões
        ScrollPane scrollPane = new ScrollPane();
        questionsContainer.getChildren().clear();

        // Adiciona cada questão
        for (Question question : this.questions) {
            VBox questionBox = createQuestionBox(question);
            questionsContainer.getChildren().add(questionBox);
        }

        scrollPane.setContent(questionsContainer);
        scrollPane.setFitToWidth(true);
        content.getChildren().add(scrollPane);

        Button submitButton = new Button("Enviar Respostas");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(e -> handleSubmit());

        content.getChildren().add(submitButton);

        return content;
    }

    private VBox createHeader() {
        VBox header = new VBox(HEADER_SPACING);
        header.setPadding(new Insets(PADDING, PADDING, 0, PADDING));

        Label titleLabel = createStyledLabel(
            "Questionário 1 - Introdução a Java",
            "Segue UI Bold",
            28
        );
        titleLabel.getStyleClass().add("title");

        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("register-button");
        closeButton.setOnAction(e -> modalStage.close());

        HBox headerContainer = new HBox();
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        headerContainer.getChildren().addAll(titleLabel, spacer, closeButton);

        header.getChildren().add(headerContainer);
        return header;
    }

    private VBox createFooter() {
        VBox footer = new VBox(CONTENT_SPACING);
        footer.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(footer, Priority.ALWAYS);
        return footer;
    }

    private StackPane createBackdrop() {
        StackPane backdrop = new StackPane();
        backdrop.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        backdrop.setOnMouseClicked(event -> {
            if (event.getTarget() == backdrop) {
                modalStage.close();
            }
        });
        return backdrop;
    }

    private DropShadow createDropShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.6));
        shadow.setRadius(25);
        shadow.setSpread(0.2);
        return shadow;
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.setWrapText(true);
        return label;
    }

    private void modo(Scene scene) {
        String styleSheet = Modo.getInstance().getModo() 
            ? "/com/elements/forum/style/dark/style.css"
            : "/com/elements/forum/style/ligth/style.css";
        scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }

    private void setupCloseAnimation() {
        modalStage.setOnCloseRequest(event -> {
            event.consume();
            if (modalStage.getScene() == null) return;
            
            FadeTransition fade = new FadeTransition(
                Duration.millis(200), 
                modalStage.getScene().getRoot()
            );
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> modalStage.hide());
            fade.play();
        });
    }

    public void setQuestionaireData(String title, String description, String score, List<Question> questions, Student student) {
        this.title = title;
        this.description = description;
        this.score = score;
        this.questions = questions;
        this.currentStudent = student;
        loadModel();
    }

    private VBox createQuestionBox(Question question) {
        VBox questionBox = new VBox(10);
        questionBox.getStyleClass().add("question-box");

        // Número e texto da questão
        Label questionLabel = new Label(question.getNumber() + ". " + question.getText());
        questionLabel.getStyleClass().add("question-text");
        questionLabel.setWrapText(true);

        VBox answersBox = new VBox(5);
        
        switch (question.getType().toLowerCase()) {
            case "multiple_choice":
                if ("true".equals(question.getMultipleCorrectAnswers())) {
                    createMultipleAnswerQuestion(answersBox, question);
                } else {
                    createSingleAnswerQuestion(answersBox, question);
                }
                break;
            case "text":
                createTextQuestion(answersBox, question);
                break;
        }

        // Pontuação da questão
        Label scoreLabel = new Label("Pontuação: " + question.getScore());
        scoreLabel.getStyleClass().add("question-score");

        questionBox.getChildren().addAll(questionLabel, answersBox, scoreLabel);
        return questionBox;
    }

    private void createSingleAnswerQuestion(VBox container, Question question) {
        ToggleGroup group = new ToggleGroup();
        String[] answers = question.getAnswers().split(",");
        
        for (String answer : answers) {
            RadioButton rb = new RadioButton(answer.trim());
            rb.setToggleGroup(group);
            rb.setWrapText(true);
            container.getChildren().add(rb);
        }
        
        // Armazena o ToggleGroup para recuperar a resposta depois
        this.answers.put(question, group);
    }

    private void createMultipleAnswerQuestion(VBox container, Question question) {
        List<CheckBox> checkBoxes = new ArrayList<>();
        String[] answers = question.getAnswers().split(",");
        
        for (String answer : answers) {
            CheckBox cb = new CheckBox(answer.trim());
            cb.setWrapText(true);
            container.getChildren().add(cb);
            checkBoxes.add(cb);
        }
        
        // Armazena a lista de CheckBoxes para recuperar as respostas depois
        this.answers.put(question, checkBoxes);
    }

    private void createTextQuestion(VBox container, Question question) {
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefRowCount(3);
        
        if (question.getEvaluationCriteria() != null) {
            Label criteriaLabel = new Label("Critérios de avaliação: " + question.getEvaluationCriteria());
            criteriaLabel.getStyleClass().add("criteria-text");
            container.getChildren().addAll(textArea, criteriaLabel);
        } else {
            container.getChildren().add(textArea);
        }
        
        // Armazena o TextArea para recuperar a resposta depois
        this.answers.put(question, textArea);
    }

    private void handleSubmit() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            
            double totalScore = 0;
            
            for (Question question : questions) {
                StudentAnswer studentAnswer = new StudentAnswer();
                studentAnswer.setStudent(currentStudent);
                studentAnswer.setQuestion(question);
                studentAnswer.setQuestionaire(question.getQuestionaire());
                
                Object answerComponent = answers.get(question);
                String answerText = "";
                boolean isCorrect = false;
                
                if (answerComponent instanceof ToggleGroup) {
                    // Questão de única escolha
                    RadioButton selectedButton = (RadioButton) ((ToggleGroup) answerComponent).getSelectedToggle();
                    if (selectedButton != null) {
                        answerText = selectedButton.getText();
                        isCorrect = answerText.equals(question.getCorrectAnswers());
                    }
                } else if (answerComponent instanceof List) {
                    // Questão de múltipla escolha
                    @SuppressWarnings("unchecked")
                    List<CheckBox> checkBoxes = (List<CheckBox>) answerComponent;
                    List<String> selectedAnswers = checkBoxes.stream()
                        .filter(CheckBox::isSelected)
                        .map(CheckBox::getText)
                        .collect(Collectors.toList());
                    
                    answerText = String.join(", ", selectedAnswers);
                    String[] correctAnswers = question.getCorrectAnswers().split(",");
                    isCorrect = Arrays.asList(correctAnswers).containsAll(selectedAnswers) 
                               && selectedAnswers.size() == correctAnswers.length;
                } else if (answerComponent instanceof TextArea) {
                    // Questão dissertativa
                    answerText = ((TextArea) answerComponent).getText();
                    // Questões dissertativas precisam ser corrigidas manualmente
                    isCorrect = false;
                }
                
                studentAnswer.setAnswer(answerText);
                studentAnswer.setCorrect(isCorrect);
                studentAnswer.setScore(isCorrect ? question.getScore() : 0);
                
                if (isCorrect) {
                    totalScore += question.getScore();
                }
                
                em.persist(studentAnswer);
            }
            
            // Atualiza o progresso do aluno no curso
            updateStudentProgress(totalScore);
            
            em.getTransaction().commit();
            
            showSuccessMessage("Questionário enviado com sucesso!", 
                             "Sua pontuação: " + totalScore + " pontos");
            modalStage.close();
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            showErrorMessage("Erro ao salvar respostas", 
                           "Não foi possível salvar suas respostas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void updateStudentProgress(double score) {
        // Atualiza o progresso do aluno no curso
        // Implementar lógica específica de acordo com suas necessidades
    }

    private void showSuccessMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void show() {
        modalStage.show();
    }
}