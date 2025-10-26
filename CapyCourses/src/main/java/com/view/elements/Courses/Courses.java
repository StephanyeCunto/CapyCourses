package com.view.elements.Courses;

import com.dao.LessonProgressDAO;
import com.dao.StudentCourseDAO;
import com.dao.StudentDAO;
import com.model.elements.Course.Course;
import com.model.elements.Course.Lessons;
import com.model.elements.Course.Module;
import com.model.elements.Course.Questionaire;
import com.model.login_cadastro.Student;
import com.model.student.LessonProgress;
import com.model.student.StudentCourse;
import com.singleton.UserSession;
import com.util.JPAUtil;
import com.view.Modo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.persistence.EntityManager;

public class Courses implements Initializable {
  @FXML private VBox mainVBox;
  @FXML private VBox content;
  @FXML private ImageView sunIcon;
  @FXML private ImageView moonIcon;
  @FXML private StackPane thumbContainer;
  @FXML private Rectangle background;
  @FXML private HBox toggleButtonHBox;
  @FXML private Button themeToggleBtn;
  @FXML private StackPane toggleButtonStackPane;
  @FXML private GridPane container;
  ;
  @FXML private Button backButton;

  private Course currentCourse;
  private StudentCourse studentCourse;
  private Student currentStudent;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadCurrentCourseData();
    mainVBox.getChildren().add(createMain());
    content.getChildren().add(createFullCourseVBox());
    changeMode();
    toggleButtonStackPane.setOnMouseClicked(e -> toggle());
    sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
    moonIcon.setImage(
        new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
    toggleInitialize();
  }

  public Courses() {
    // Adicione um print para debug
    System.out.println("Construtor Courses chamado");
  }

  private void loadCurrentCourseData() {
    try {
      EntityManager em = JPAUtil.getEntityManager();
      String courseTitle = UserSession.getInstance().getCurrentCourseTitle();

      // Carrega o curso com seus módulos, aulas e questionários em uma única consulta
      currentCourse =
          em.createQuery(
                  "SELECT DISTINCT c FROM Course c "
                      + "LEFT JOIN FETCH c.modules m "
                      + "LEFT JOIN FETCH m.lessons l "
                      + "LEFT JOIN FETCH m.questionaire q "
                      + "LEFT JOIN FETCH q.questions "
                      + "WHERE c.title = :title",
                  Course.class)
              .setParameter("title", courseTitle)
              .getSingleResult();

      StudentDAO studentDAO = new StudentDAO();
      StudentCourseDAO studentCourseDAO = new StudentCourseDAO();

      currentStudent = studentDAO.buscarPorEmail(UserSession.getInstance().getUserEmail());
      studentCourse = studentCourseDAO.buscarPorAlunoECurso(currentStudent, currentCourse);

      if (currentCourse == null || studentCourse == null) {
        throw new RuntimeException("Curso não encontrado");
      }
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao carregar curso");
      alert.setContentText("Não foi possível carregar os dados do curso: " + e.getMessage());
      alert.showAndWait();
    }
  }

  private void toggle() {
    // Modo.getInstance().getModo() == dark
    Modo.getInstance().setModo();

    TranslateTransition thumbTransition =
        new TranslateTransition(Duration.millis(200), thumbContainer);
    thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
    thumbTransition.play();

    FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
    fillTransition.setFromValue(
        !Modo.getInstance().getModo() ? Color.web("#FFA500") : Color.web("#4169E1"));
    fillTransition.setToValue(
        !Modo.getInstance().getModo() ? Color.web("#4169E1") : Color.web("#FFA500"));
    fillTransition.play();

    changeMode();

    updateIconsVisibility();
  }

  private void updateIconsVisibility() {
    sunIcon.setVisible(Modo.getInstance().getModo());
    moonIcon.setVisible(!Modo.getInstance().getModo());
  }

  private void toggleInitialize() {
    if (!Modo.getInstance().getModo()) {
      background.getStyleClass().add("dark");
      sunIcon.setVisible(Modo.getInstance().getModo());
      moonIcon.setVisible(!Modo.getInstance().getModo());
      TranslateTransition thumbTransition =
          new TranslateTransition(Duration.millis(200), thumbContainer);
      thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
      thumbTransition.play();
    } else {
      TranslateTransition thumbTransition =
          new TranslateTransition(Duration.millis(200), thumbContainer);
      thumbTransition.setToX(Modo.getInstance().getModo() ? -12.0 : 12.0);
      thumbTransition.play();
      background.getStyleClass().remove("dark");
      sunIcon.setVisible(Modo.getInstance().getModo());
      moonIcon.setVisible(!Modo.getInstance().getModo());
    }
  }

  private void changeMode() {
    container.getStylesheets().clear();
    if (!Modo.getInstance().getModo()) {
      background.getStyleClass().add("dark");
      container
          .getStylesheets()
          .add(
              getClass().getResource("/com/elements/curso/style/ligth/style.css").toExternalForm());
    } else {
      background.getStyleClass().remove("dark");
      container
          .getStylesheets()
          .add(getClass().getResource("/com/elements/curso/style/dark/style.css").toExternalForm());
    }
  }

  private VBox createMain() {
    VBox mainVBox = new VBox(20);
    mainVBox.setPadding(new Insets(10));

    VBox courseInfoCard = createCourseInfoCard();
    VBox instructorCard = createInstructorCard();

    List<Module> sortedModules = new ArrayList<>(currentCourse.getModules());
    sortedModules.sort((m1, m2) -> Integer.compare(m1.getModuleNumber(), m2.getModuleNumber()));

    VBox modulesCard = createModulesCard();
    VBox reviewsCard = createReviewsCard();

    mainVBox.getChildren().addAll(courseInfoCard, instructorCard, modulesCard, reviewsCard);
    return mainVBox;
  }

  private VBox createCourseInfoCard() {
    VBox courseInfoCard = new VBox(10);
    courseInfoCard.getStyleClass().add("content-card");

    Label courseTitle = new Label(currentCourse.getTitle());
    courseTitle.getStyleClass().add("card-title");

    HBox courseDetails = new HBox(8);
    courseDetails
        .getChildren()
        .addAll(
            createStyledLabel("⭐ " + currentCourse.getRating(), "card-subtitle"),
            createStyledLabel("•", "card-subtitle"),
            createStyledLabel(
                currentCourse.getCourseSettings().getDurationTotal() + " horas", "card-subtitle"),
            createStyledLabel("•", "card-subtitle"),
            createStyledLabel(currentCourse.getNivel(), "card-subtitle"));

    courseInfoCard.getChildren().addAll(courseTitle, courseDetails);
    return courseInfoCard;
  }

  private VBox createInstructorCard() {
    VBox instructorCard = new VBox(15);
    instructorCard.getStyleClass().add("content-card");

    Label instructorTitle = new Label("Professor");
    instructorTitle.getStyleClass().add("card-title");

    HBox instructorInfo = new HBox(15);
    instructorInfo.setAlignment(Pos.CENTER_LEFT);
    instructorInfo.setMinHeight(60);

    Circle instructorAvatar = new Circle(30, Color.web("#6c63ff"));

    VBox instructorDetails = new VBox(5);

    try {
      // Acessando diretamente através do currentCourse
      if (currentCourse != null && currentCourse.getName() != null) {
        // O nome do instrutor está armazenado no campo 'name' do Course
        instructorDetails
            .getChildren()
            .addAll(
                createStyledLabel(currentCourse.getName(), "card-title"),
                createStyledLabel("Professor do curso", "card-subtitle"));
      } else {
        instructorDetails
            .getChildren()
            .addAll(
                createStyledLabel("Professor não encontrado", "card-title"),
                createStyledLabel("", "card-subtitle"));
      }
    } catch (Exception e) {
      instructorDetails
          .getChildren()
          .addAll(
              createStyledLabel("Erro ao carregar professor", "card-title"),
              createStyledLabel("", "card-subtitle"));
      e.printStackTrace();
    }

    instructorInfo.getChildren().addAll(instructorAvatar, instructorDetails);
    instructorCard.getChildren().addAll(instructorTitle, instructorInfo);

    return instructorCard;
  }

  private VBox createModulesCard() {
    VBox modulesCard = new VBox(15);
    modulesCard.getStyleClass().add("content-card");

    Label modulesTitle = new Label("Módulos do Curso");
    modulesTitle.getStyleClass().add("card-title");

    for (Module module : currentCourse.getModules()) {
      String buttonText = isModuleCompleted(module) ? "Continuar" : "Começar";
      String buttonStyle = isModuleCompleted(module) ? "modern-button" : "simple-button";

      VBox moduleBox = createModuleCard(module);

      modulesCard.getChildren().add(moduleBox);
    }

    return modulesCard;
  }

  private boolean isModuleCompleted(Module module) {
    // Implementar lógica para verificar se o módulo está completo
    return false; // TODO: Implementar verificação real
  }

  private String formatModuleDetails(Module module) {
    return module.getLessons().size() + " aulas • " + module.getDuration() + " horas";
  }

  private Label createStyledLabel(String text, String styleClass) {
    Label label = new Label(text);
    label.getStyleClass().add(styleClass);
    return label;
  }

  private VBox createModuleCard(Module module) {
    VBox moduleBox = new VBox(10);
    moduleBox.getStyleClass().add("module-card");

    HBox moduleHeader = new HBox(15);
    moduleHeader.setAlignment(Pos.CENTER_LEFT);
    moduleHeader.setPadding(new Insets(15));

    // Criando o círculo com o número do módulo
    Label moduleNumberLabel = new Label(String.valueOf(module.getModuleNumber()));
    moduleNumberLabel.getStyleClass().add("module-number");
    Circle moduleCircle = new Circle(15, Color.web("#6c63ff"));
    moduleNumberLabel.setTextFill(Color.WHITE);
    StackPane numberContainer = new StackPane(moduleCircle, moduleNumberLabel);

    VBox moduleDetailsBox = new VBox(5);
    moduleDetailsBox
        .getChildren()
        .addAll(
            createStyledLabel(module.getTitle(), "card-title"),
            createStyledLabel(formatModuleDetails(module), "card-subtitle"));

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Button actionButton = new Button(isModuleCompleted(module) ? "Continuar" : "Começar");
    actionButton.getStyleClass().add(isModuleCompleted(module) ? "modern-button" : "simple-button");

    ImageView arrowIcon =
        new ImageView(new Image(getClass().getResourceAsStream("/com/icons/seta-baixo-dark.png")));
    arrowIcon.setFitWidth(20);
    arrowIcon.setFitHeight(20);
    arrowIcon.setRotate(180);

    moduleHeader
        .getChildren()
        .addAll(arrowIcon, numberContainer, moduleDetailsBox, spacer, actionButton);

    VBox lessonsBox = new VBox(5);
    lessonsBox.setPadding(new Insets(0, 15, 15, 15));
    lessonsBox.setVisible(false);
    lessonsBox.managedProperty().bind(lessonsBox.visibleProperty());

    // Ordenando as aulas
    if (module.getLessons() != null) {
      List<Lessons> sortedLessons = new ArrayList<>(module.getLessons());
      sortedLessons.sort(
          (l1, l2) -> Integer.compare(l1.getNumberOfLesson(), l2.getNumberOfLesson()));

      for (Lessons lesson : sortedLessons) {
        HBox lessonItem = createLessonItem(lesson);
        lessonItem.getStyleClass().add("lesson-item");
        lessonsBox.getChildren().add(lessonItem);
      }
    }

    // Adicionando questionários
    if (module.getQuestionaire() != null) {
      // Adiciona um separador visual
      Separator separator = new Separator();
      separator.setPadding(new Insets(10, 0, 10, 0));
      lessonsBox.getChildren().add(separator);

      // Adiciona o título "Avaliações"
      Label avaliacoesLabel = createStyledLabel("Avaliações", "section-title");
      avaliacoesLabel.setPadding(new Insets(0, 0, 10, 0));
      lessonsBox.getChildren().add(avaliacoesLabel);

      // Cria o item do questionário
      HBox questionaireItem = createQuestionaireItem(module.getQuestionaire());
      questionaireItem.getStyleClass().add("questionaire-item");
      lessonsBox.getChildren().add(questionaireItem);
    }

    moduleHeader.setOnMouseClicked(
        e -> {
          lessonsBox.setVisible(!lessonsBox.isVisible());
          arrowIcon.setRotate(lessonsBox.isVisible() ? 0 : 180);
        });

    moduleBox.getChildren().addAll(moduleHeader, lessonsBox);
    return moduleBox;
  }

  private HBox createLessonItem(Lessons lesson) {
    HBox lessonBox = new HBox(10);
    lessonBox.setAlignment(Pos.CENTER_LEFT);
    lessonBox.setPadding(new Insets(10));

    Button checkButton = new Button("✖");
    checkButton.getStyleClass().add("check-button");

    // Verificar se a aula já foi completada
    LessonProgressDAO progressDAO = new LessonProgressDAO();
    LessonProgress progress = progressDAO.buscarPorAulaECursoAluno(lesson, studentCourse);
    boolean isCompleted = progress != null && progress.isCompleted();

    VBox lessonDetails = new VBox(3);
    Label lessonTitle =
        createStyledLabel(
            "Aula " + lesson.getNumberOfLesson() + ": " + lesson.getTitle(), "lesson-title");

    if (isCompleted) {
      checkButton.setText("✔");
      checkButton.getStyleClass().addAll("check-button", "checked");
      lessonTitle.setStyle("-fx-strikethrough: true");
      lessonTitle.setText(strikeThroughText(lessonTitle.getText()));
    }

    Label durationLabel = createStyledLabel(lesson.getDuration() + " minutos", "lesson-duration");
    lessonDetails.getChildren().addAll(lessonTitle, durationLabel);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Button watchButton = new Button(isCompleted ? "Rever" : "Assistir");
    watchButton.getStyleClass().add("outline-button");
    watchButton.setOnAction(e -> openLesson(lesson));

    checkButton.setOnAction(
        event -> {
          try {
            if (progress == null) {
              // Criar novo progresso
              LessonProgress newProgress = new LessonProgress();
              newProgress.setStudentCourse(studentCourse);
              newProgress.setLesson(lesson);
              newProgress.setCompleted(true);
              newProgress.setCompletionDate(java.time.LocalDateTime.now());

              // Salvar progresso e atualizar StudentCourse
              progressDAO.salvar(newProgress, studentCourse);

              // Atualizar UI
              checkButton.setText("✔");
              checkButton.getStyleClass().addAll("check-button", "checked");
              lessonTitle.setStyle("-fx-strikethrough: true");
              lessonTitle.setText(strikeThroughText(lessonTitle.getText()));
              watchButton.setText("Rever");
            } else {
              // Alternar estado do progresso existente
              progress.setCompleted(!progress.isCompleted());

              // Salvar progresso e atualizar StudentCourse
              progressDAO.salvar(progress, studentCourse);

              // Atualizar UI
              if (progress.isCompleted()) {
                checkButton.setText("✔");
                checkButton.getStyleClass().addAll("check-button", "checked");
                lessonTitle.setStyle("-fx-strikethrough: true");
                lessonTitle.setText(strikeThroughText(lessonTitle.getText()));
                watchButton.setText("Rever");
              } else {
                checkButton.setText("✖");
                checkButton.getStyleClass().remove("checked");
                lessonTitle.setStyle("-fx-strikethrough: false");
                lessonTitle.setText(
                    "Aula " + lesson.getNumberOfLesson() + ": " + lesson.getTitle());
                watchButton.setText("Assistir");
              }
            }

            // Atualizar a exibição do progresso
            updateProgressDisplay();

          } catch (Exception e) {
            e.printStackTrace();
            showError("Erro", "Não foi possível atualizar o progresso da aula");
          }
        });

    lessonBox.getChildren().addAll(checkButton, lessonDetails, spacer, watchButton);
    return lessonBox;
  }

  private void openLesson(Lessons lesson) {
    try {
      LessonModal modal = new LessonModal(backButton.getScene().getWindow());
      modal.setLessonData(
          "Aula " + lesson.getNumberOfLesson() + " - " + lesson.getTitle(),
          lesson.getDescription(),
          lesson.getVideoLink(),
          lesson.getMaterials());
      modal.show();
    } catch (Exception e) {
      e.printStackTrace();
      showError("Erro ao abrir aula", "Não foi possível abrir os detalhes da aula.");
    }
  }

  private String strikeThroughText(String text) {
    StringBuilder strikedText = new StringBuilder();
    strikedText.append(" ");
    for (char c : text.toCharArray()) {
      strikedText.append(c).append('\u0336');
    }
    strikedText.append(" ");
    return strikedText.toString();
  }

  private HBox createQuestionaireItem(Questionaire questionaire) {
    HBox questionaireBox = new HBox(10);
    questionaireBox.setAlignment(Pos.CENTER_LEFT);
    questionaireBox.setPadding(new Insets(10));

    // Ícone do questionário
    Label iconLabel = new Label("📝");
    iconLabel.getStyleClass().add("questionaire-icon");

    VBox questionaireDetails = new VBox(3);
    Label questionaireTitle = createStyledLabel(questionaire.getTitle(), "questionaire-title");
    Label scoreLabel =
        createStyledLabel(
            "Pontuação: " + questionaire.getScore() + " pontos", "questionaire-score");
    questionaireDetails.getChildren().addAll(questionaireTitle, scoreLabel);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    // Verifica se o questionário já foi respondido
    boolean isCompleted = checkQuestionaireCompletion(questionaire);

    Button startButton = new Button(isCompleted ? "Revisar" : "Iniciar");
    startButton.getStyleClass().add("outline-button");
    startButton.setOnAction(e -> openQuestionaire(questionaire));

    questionaireBox.getChildren().addAll(iconLabel, questionaireDetails, spacer, startButton);
    return questionaireBox;
  }

  private boolean checkQuestionaireCompletion(Questionaire questionaire) {
    // Implementar lógica para verificar se o questionário já foi respondido
    // Por enquanto, retorna false como padrão
    return false;
  }

  private void openQuestionaire(Questionaire questionaire) {
    try {
      if (questionaire == null || questionaire.getQuestions().isEmpty()) {
        showError("Questionário não disponível", "Este questionário não possui questões.");
        return;
      }

      QuestionaireModal modal = new QuestionaireModal();
      modal.setQuestionaireData(
          questionaire.getTitle(),
          questionaire.getDescription(),
          questionaire.getScore(),
          questionaire.getQuestions(),
          currentStudent);
      modal.show();
    } catch (Exception e) {
      e.printStackTrace();
      showError("Erro ao abrir questionário", e.getMessage());
    }
  }

  private void showError(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private VBox createCourseDetailsVBox() {
    VBox courseDetailsVBox = new VBox(15);
    courseDetailsVBox.getStyleClass().add("content-card");

    Label detailsTitle = new Label("Detalhes do Curso");
    detailsTitle.getStyleClass().add("card-title");

    VBox detailsContent = new VBox(10);
    detailsContent
        .getChildren()
        .addAll(
            createStyledLabel("Nível: Intermediário", "card-subtitle"),
            createStyledLabel("Certificado: Sim", "card-subtitle"),
            createStyledLabel("Duração: 40 horas", "card-subtitle"));

    courseDetailsVBox.getChildren().addAll(detailsTitle, detailsContent);
    return courseDetailsVBox;
  }

  public VBox createCourseProgressVBox() {
    VBox progressVBox = new VBox(15);
    progressVBox.getStyleClass().add("content-card");

    Label progressTitle = new Label("Progresso do Curso");
    progressTitle.getStyleClass().add("card-title");

    int completedLessons =
        studentCourse.getCompletedLessons() != null ? studentCourse.getCompletedLessons() : 0;
    int totalLessons =
        studentCourse.getTotalLessons() != null ? studentCourse.getTotalLessons() : 0;

    String progressText = completedLessons + "/" + totalLessons;
    String percentageText = "(" + String.format("%.1f", studentCourse.calculateProgress()) + "%)";

    VBox progressContent = new VBox(10);
    progressContent
        .getChildren()
        .addAll(
            createStyledLabel("Aulas Completadas:", "card-subtitle"),
            createProgressDetailsHBox(progressText, percentageText),
            createCustomProgressBar(studentCourse.calculateProgress()));

    int completedQuestionaires =
        studentCourse.getCompletedQuestionaires() != null
            ? studentCourse.getCompletedQuestionaires()
            : 0;
    int totalQuestionaires =
        studentCourse.getTotalQuestionaires() != null ? studentCourse.getTotalQuestionaires() : 0;

    VBox evaluationsContent = new VBox(10);
    evaluationsContent.setStyle("-fx-padding: 10 0 0 0;");
    evaluationsContent
        .getChildren()
        .addAll(
            createStyledLabel("Avaliações Concluídas:", "card-subtitle"),
            createProgressDetailsHBox(completedQuestionaires + "/" + totalQuestionaires, null));

    progressVBox.getChildren().addAll(progressTitle, progressContent, evaluationsContent);
    return progressVBox;
  }

  private HBox createProgressDetailsHBox(String progress, String percentage) {
    HBox progressDetailsHBox = new HBox(10);
    progressDetailsHBox.setAlignment(Pos.CENTER_LEFT);
    if (percentage != null) {
      progressDetailsHBox
          .getChildren()
          .addAll(
              createStyledLabel(progress, "card-subtitle"),
              createStyledLabel(percentage, "card-subtitle"));
    } else {
      progressDetailsHBox.getChildren().add(createStyledLabel(progress, "card-subtitle"));
    }
    return progressDetailsHBox;
  }

  private HBox createCustomProgressBar(double progress) {
    HBox progressBar = new HBox();
    progressBar.getStyleClass().add("progress-bar");
    progressBar.setMinHeight(10);
    progressBar.setPrefHeight(10);

    HBox progressBarFill = new HBox();
    progressBarFill.getStyleClass().add("progress-bar-fill");
    progressBarFill.setMinHeight(10);
    progressBarFill.setPrefHeight(10);
    progressBarFill.setPrefWidth(progress); // Percentagem do progresso

    progressBar.getChildren().add(progressBarFill);
    return progressBar;
  }

  public VBox createNextActivitiesVBox() {
    VBox activitiesVBox = new VBox(15);
    activitiesVBox.getStyleClass().add("content-card");

    Label activitiesTitle = new Label("Próximas Atividades");
    activitiesTitle.getStyleClass().add("card-title");

    VBox activitiesList = new VBox(10);
    activitiesList
        .getChildren()
        .addAll(
            createActivityHBox("📝", "Avaliação Módulo 1", "Próxima atividade do modulo"),
            createActivityHBox("📚", "Aula 3 - Módulo 2", "Próxima aula"));

    activitiesVBox.getChildren().addAll(activitiesTitle, activitiesList);
    return activitiesVBox;
  }

  private HBox createActivityHBox(String icon, String title, String subtitle) {
    HBox activityHBox = new HBox(10);
    activityHBox.setAlignment(Pos.CENTER_LEFT);
    activityHBox.getStyleClass().add("module-card");

    Label activityIcon = new Label(icon);
    VBox activityDetails = new VBox(5);
    activityDetails
        .getChildren()
        .addAll(
            createStyledLabel(title, "card-title"), createStyledLabel(subtitle, "card-subtitle"));

    activityHBox.getChildren().addAll(activityIcon, activityDetails);
    return activityHBox;
  }

  private VBox createFullCourseVBox() {
    VBox fullCourseVBox = new VBox(20);
    fullCourseVBox.getStyleClass().add("full-course-container");

    fullCourseVBox
        .getChildren()
        .addAll(createCourseDetailsVBox(), createCourseProgressVBox(), createNextActivitiesVBox());

    return fullCourseVBox;
  }

  private VBox createReviewsCard() {
    VBox reviewsCard = new VBox(15);
    reviewsCard.getStyleClass().add("content-card");

    HBox reviewHeaderHBox = new HBox(20);
    reviewHeaderHBox
        .getChildren()
        .addAll(
            createStyledLabel("Avaliações do Curso", "card-title"),
            createStyledLabel("⭐ " + currentCourse.getRating(), "card-subtitle"));

    VBox reviewHeaderVBox = new VBox(20);
    reviewHeaderVBox.getChildren().add(reviewHeaderHBox);

    reviewsCard.getChildren().add(reviewHeaderVBox);
    return reviewsCard;
  }

  @FXML
  private void handleBackButton() {
    try {
      // Carrega a página meus cursos - corrigindo o caminho do FXML
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/com/estudante/meusCursos/paginaMeusCursos.fxml"));
      Parent root = loader.load();

      // Obtém a cena atual
      Scene currentScene = backButton.getScene();
      Stage stage = (Stage) currentScene.getWindow();

      // Cria nova cena mantendo as dimensões atuais
      Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());

      // Define a nova cena no palco
      stage.setScene(newScene);
      stage.show();
    } catch (IOException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao voltar");
      alert.setContentText("Não foi possível retornar à página anterior: " + e.getMessage());
      alert.showAndWait();
    }
  }

  private void updateProgressDisplay() {
    // Atualizar o VBox de progresso
    content.getChildren().clear();
    content.getChildren().add(createFullCourseVBox());
  }
}
