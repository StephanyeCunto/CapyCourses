package com.view.professor;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.util.Duration;

import com.controller.professor.CadastroCursoController;
import com.view.Modo;
import com.view.elements.Calendario;
import com.view.professor.valid.*;
import com.view.login_cadastro.elements.ErrorNotification;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CadastroCurso implements Initializable {
    @FXML
    private TextField titleCourse;
    @FXML
    private Label coutTitleCourse;
    @FXML
    private TextArea descritionCourse;
    @FXML
    private ComboBox<String> categoryCourse;
    @FXML
    private ComboBox<String> levelCourse;
    @FXML
    private FlowPane interestContainer;
    @FXML
    private Label selectedCount;
    @FXML
    private ImageView coursePreviewImage;
    @FXML
    private Button uploadButton;
    @FXML
    private VBox modulesList;
    @FXML
    private VBox date;
    @FXML
    private CheckBox dateEnd;
    @FXML
    private ComboBox ComboBoxVisibily;
    @FXML
    private TextField durationTotal;
    @FXML
    private CheckBox isCertificate;
    @FXML
    private CheckBox isGradeMiniun;
    @FXML
    private Label progressLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label basicInformation;
    @FXML
    private Label settings;
    @FXML
    private Label modules;
    @FXML
    private GridPane container;
    @FXML
    private ImageView sunIcon;
    @FXML
    private ImageView moonIcon;
    @FXML
    private HBox toggleButtonHBox;
    @FXML
    private StackPane thumbContainer;
    @FXML
    private Rectangle background;
    @FXML
    private Label basicsTitleErrorLabel;
    @FXML
    private Label descritionBasicsErrorLabel;
    @FXML
    private Label categoryCourseErrorLabel;
    @FXML
    private Label levelCourseErrorLabel;
    @FXML
    Label durationTotalErrorLabel;
    @FXML
    Label comboBoxVisibilityErrorLabel;
    @FXML
    StackPane toggleButtonStackPane;

    private boolean isLightMode = true;

    private Set<String> selectedInterests = new HashSet<>();

    private String title;

    private File selectedFile;

    private Calendario dateInputPopupStart = new Calendario();
    private Calendario dateInputPopupEnd = new Calendario();

    private static final String DEFAULT_MODULE_TITLE = "Novo Módulo";
    private static final String DEFAULT_LESSON_TITLE = "Nova Aula";

    private VBox dateContainerStart = new VBox(5);
    private VBox dateContainerEnd = new VBox(5);

    private String valueComBox = "Visível";
    private LocalDate dateCurrent = dateInputPopupStart.getLocalDate();
    private boolean isImage = false;
    private int isTag = 0;

    private List<Map<String, Object>> modulesData = new ArrayList<>();

    private int lessonCounter = 1;
    private int questionaireCounter = 1;
    private int currentModuleCount = 0;

    private final CursoBasicsValid validatorBasic = new CursoBasicsValid();
    private final CursoModulesValid validatorModules = new CursoModulesValid();
    private final CursoAulasValid validatorAulas = new CursoAulasValid();
    private final CursoQuestionarioValid validatorQuestionario = new CursoQuestionarioValid();
    private final CursoQuestoesValid validatorQuestoes = new CursoQuestoesValid();
    private final CursoOptionsValid validatorOptions = new CursoOptionsValid();
    private final CursoSettingsValidation validatorSettings = new CursoSettingsValidation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkTitle();
        loadComboBoxCategory();
        loadComboBoxLevel();
        loadComBoxVisibily(valueComBox);
        setupInterestButtons();
        // changeModeStyle();

        uploadButton.setOnAction(event -> uploadImage());

        VBox lessonsList = new VBox();
        lessonsList.setSpacing(10);
        lessonsList.getStyleClass().add("lessons-list");

        VBox questionaireList = new VBox();
        questionaireList.setSpacing(10);
        questionaireList.getStyleClass().add("lessons-list");
        modulesList.setSpacing(20);

        dateEnd.setOnAction(event -> {
            if (dateEnd.isSelected()) {
                dateContainerEnd.setMouseTransparent(true);
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setSaturation(-0.5);
                colorAdjust.setBrightness(-0.1);
                dateContainerEnd.setEffect(colorAdjust);
            } else {
                dateContainerEnd.setMouseTransparent(false);
                dateContainerEnd.setEffect(null);
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            dateChange();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Timeline timeline0 = new Timeline(new KeyFrame(Duration.seconds(0.02), event -> {
            registrationProgress();
        }));

        timeline0.setCycleCount(Timeline.INDEFINITE);
        timeline0.play();

        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();

        validatorBasic.setupInitialStateBasics(titleCourse, descritionCourse, categoryCourse, levelCourse,
                basicsTitleErrorLabel, descritionBasicsErrorLabel, categoryCourseErrorLabel, levelCourseErrorLabel);

        validatorSettings.setupInitialState(durationTotal, ComboBoxVisibily, durationTotalErrorLabel,
                comboBoxVisibilityErrorLabel);

        validatorModules.setParentContainer(container);
        validatorBasic.setParentContainer(container);
        validatorSettings.setParentContainer(container);
        validatorAulas.setParentContainer(container);
        validatorOptions.setParentContainer(container);
        validatorQuestionario.setParentContainer(container);
        validatorQuestoes.setParentContainer(container);

    }

    private static final String STEP_PENDING = "step-pending";
    private static final String STEP_CURRENT = "step-current";
    private static final String STEP_COMPLETED = "step-completed";
    private static final int MIN_LESSONS_PER_MODULE = 1;

    private void registrationProgress() {
        double basics = calculeBasics();
        double settingsC = calculeSettings();
        double module = calculeModule();
        double lesson = calculeLesson();
        double questionaire = calculeQuestionaire();
        double question = calculeQuestion();
        int divisor = 4;

        double totalProgress = (basics + module + lesson + settingsC);
        double totalProgressMedia;

        if (questionaire != 0) {
            totalProgress += questionaire;
            divisor++;
            totalProgressMedia = totalProgress / divisor;
        } else {
            totalProgressMedia = (totalProgress) / divisor;
        }

        if (question != 0) {
            totalProgress += question;
            divisor++;
            totalProgressMedia = totalProgress / divisor;
        } else {
            totalProgressMedia = (totalProgress) / divisor;
        }

        updateProgressDisplay(totalProgressMedia);

        updateStepLabel(basicInformation, "Informações Básicas", basics, 100);
        updateStepLabel(settings, "Configurações", settingsC, 100);
        if (module == 100) {
            if (lesson > 0) {
                updateStepLabel(modules, "Módulos", lesson, 100);
            } else {
                updateStepLabel(modules, "Módulos", 1, 100);
            }

            if (lesson == 100 && questionaire != 0) {
                updateStepLabel(modules, "Módulos", questionaire, 100);
            }

            if (lesson == 100 && questionaire == 100) {
                updateStepLabel(modules, "Módulos", question, 100);
            }
        } else {
            updateStepLabel(modules, "Módulos", module, 100);
        }
    }

    private double calculeBasics() {
        int completedFields = 0;

        if (validatorBasic.isTitleValid())
            completedFields++;
        if (validatorBasic.isDescriptionValid())
            completedFields++;
        if (validatorBasic.isCategorySelected())
            completedFields++;
        if (validatorBasic.isLevelSelected())
            completedFields++;

        return (completedFields * 100.0) / 4;
    }

    private double calculeSettings() {
        int completedFields = 0;

        if (validatorSettings.isDurationValid())
            completedFields++;
        if (validatorSettings.isVisibilitySelected())
            completedFields++;

        return (completedFields * 100.0) / 2;
    }

    private double calculeModule() {
        int totalTitleFields = validatorModules.getTotalTitleFields();
        int totalDurationFields = validatorModules.getTotalDurationFields();
        int totalDetailsFields = validatorModules.getTotalDetailsFields();

        int totalValidatedTitle = validatorModules.getValidatedTitleFields();
        int totalValidatedDuration = validatorModules.getValidatedDurationFields();
        int totalValidatedDetails = validatorModules.getValidatedDetailsFields();

        int totalFields = totalTitleFields + totalDurationFields + totalDetailsFields;
        int completedFields = totalValidatedTitle + totalValidatedDuration + totalValidatedDetails;

        if (totalFields == 0) {
            return 0;
        }

        if (totalFields == 3 && completedFields == 0) {
            return 0.01;
        }

        return (completedFields * 100.0) / totalFields;
    }

    private double calculeLesson() {
        int totalTitleFields = validatorAulas.getTotalTitleFields();
        int totalVideoFields = validatorAulas.getTotalVideoFields();
        int totalDetailsFields = validatorAulas.getTotalDetailsFields();
        int totalMaterialsFields = validatorAulas.getTotalMaterialsFields();
        int totalDurationFields = validatorAulas.getTotalDurationFields();

        int totalValidatedTitle = validatorAulas.getValidatedTitleFields();
        int totalValidatedVideo = validatorAulas.getValidatedVideoFields();
        int totalValidatedDetails = validatorAulas.getValidatedDetailsFields();
        int totalValidatedMaterials = validatorAulas.getValidatedMaterialsFields();
        int totalValidatedDuration = validatorAulas.getValidatedDurationFields();

        int totalFields = totalTitleFields + totalVideoFields + totalDetailsFields + totalMaterialsFields
                + totalDurationFields;
        int completedFields = totalValidatedTitle + totalValidatedVideo + totalValidatedDetails
                + totalValidatedMaterials + totalValidatedDuration;
        if (totalFields == 0) {
            totalFields = 5;
        }
        return (completedFields * 100.0) / totalFields;
    }

    private double calculeQuestionaire() {
        int totalTitleFields = validatorQuestionario.getTitleFieldsCount();
        int totalQuestionTextFieldsCount = validatorQuestionario.getQuestionTextFieldsCount();
        int totalScoreFieldsCount = validatorQuestionario.getQuestionTextFieldsCount();

        int totalFields = totalTitleFields + totalScoreFieldsCount + totalQuestionTextFieldsCount;

        if (totalFields != 0) {
            int totalValidatedTitle = validatorQuestionario.getValidatedTitleFieldsCount();
            int totalValidateQuestion = validatorQuestionario.getValidatedQuestionTextFieldsCount();
            int totalValidatedScore = validatorQuestionario.getValidatedScoreFieldsCount();

            int totalValidated = totalValidatedTitle + totalValidateQuestion + totalValidatedScore;

            if (totalFields == 0) {
                return 0;
            }
            if (totalValidated == 0) {
                return 0.01;
            }
            return (totalValidated * 100) / totalFields;
        } else if (isGradeMiniun.isSelected()) {
            return 0.01;
        }
        return 0;
    }

    private double calculeQuestion() {
        int totalQuestionField = validatorQuestoes.getQuestionFieldsCount();
        int totalScoreField = validatorQuestoes.getScoreFieldsCount();
        int totalEvaluationCriteriaFields = validatorQuestoes.getEvaluationCriteriaFieldsCount();

        if (calculeQuestionaire() != 0) {
            int totalFields = 0;

            if (totalQuestionField == 0) {
                totalFields++;
            } else {
                totalFields += totalQuestionField;
            }

            if (totalScoreField == 0) {
                totalFields++;
            } else {
                totalFields += totalScoreField;
            }
            totalFields += totalEvaluationCriteriaFields;
            int totalValidated = validatorQuestoes.getValidatedQuestionFieldsCount()
                    + validatorQuestoes.getValidatedScoreFieldsCount()
                    + validatorQuestoes.getValidatedEvaluationCriteriaFieldsCount();

            if (totalQuestionField != totalEvaluationCriteriaFields) {
                int totalOptionField = validatorOptions.getOptionFieldsCount();
                int totalValidatedOption = validatorOptions.getValidatedOptionFieldsCount();

                totalFields += totalOptionField;
                totalValidated += totalValidatedOption;
            }
            if (totalFields == 0) {
                return 0;
            }
            if (totalValidated == 0) {
                return 0.01;
            }
            return (totalValidated * 100) / totalFields;

        }

        return 0;
    }

    private double calculeOptions() {

        return 0.0;
    }

    private void updateStepLabel(Label label, String text, double completed, int total) {
        if (completed == 0) {
            setLabelPending(label, text);
        } else if (completed == total) {
            setLabelCompleted(label, text);
        } else {
            setLabelInProgress(label, text);
        }
    }

    private void setLabelPending(Label label, String text) {
        label.getStyleClass().remove(STEP_CURRENT);
        label.getStyleClass().add(STEP_PENDING);
        label.setText(" " + text);
        if (!Modo.getInstance().getModo()) {
            label.setStyle("-fx-text-fill:#373a40;");
        } else {
            label.setStyle("-fx-text-fill:#ffffff;");
        }
    }

    private void setLabelInProgress(Label label, String text) {
        label.getStyleClass().remove(STEP_PENDING);
        label.getStyleClass().add(STEP_CURRENT);
        if (Modo.getInstance().getModo()) {
            label.setStyle("-fx-text-fill: #839EFF;");
        } else {
            label.setStyle("-fx-text-fill: #2f3380;");

        }
        label.setText("→ " + text);
    }

    private void setLabelCompleted(Label label, String text) {
        label.getStyleClass().remove(STEP_PENDING);
        label.getStyleClass().remove(STEP_CURRENT);
        label.getStyleClass().add(STEP_COMPLETED);
        if (!Modo.getInstance().getModo()) {
            label.setStyle("-fx-text-fill: #215222;");
        } else {
            label.setStyle("-fx-text-fill:rgb(89, 150, 90);");
        }
        label.setText("✓ " + text);
    }

    private void updateProgressDisplay(double percentage) {
        String percentageFormatted = String.format("%.0f", percentage);
        double percentageDouble = percentage / 100;
        progressLabel.setText(percentageFormatted + " % Completo");
        progressBar.setProgress(percentageDouble);
    }

    private void dateChange() {
        if (dateCurrent != dateInputPopupStart.getLocalDate()) {
            LocalDate selectedDate = dateInputPopupStart.getLocalDate();
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedToday = today.format(formatter);

            if (selectedDate != null) {
                String formattedDate = selectedDate.format(formatter);
                if (!formattedDate.equals(formattedToday)) {
                    valueComBox = "Visível, Somente na data de início";
                } else {
                    valueComBox = "Visível";
                }
                loadComBoxVisibily(valueComBox);
            }
            dateCurrent = dateInputPopupStart.getLocalDate();
            dateInputPopupEnd.setMinDate(dateCurrent.plusDays(1));
            dateInputPopupEnd.setselectedDate(dateCurrent.plusDays(1));
        }
    }

    private void loadComBoxVisibily(String valueCombox) {
        ComboBoxVisibily.getItems().clear();
        ComboBoxVisibily.getItems().addAll(valueCombox.split(","));
    }

    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma imagem de capa");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", ".png", ".jpg", ".jpeg", ".gif"));

        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                coursePreviewImage.setImage(image);
                isImage = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupInterestButtons() {
        interestContainer.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals("+ Adicionar")) {
                    button.setOnAction(e -> addNewInterest());
                } else {
                    button.setOnAction(e -> toggleInterest(button));
                }
            }
        });

        updateSelectedCount();
    }

    private void addNewInterest() {
        TextField interestInput = new TextField();
        interestInput.setPromptText("Digite a nova tag...");
        interestInput.getStyleClass().add("interest-button");
        int addButtonIndex = interestContainer.getChildren().size() - 1;
        interestContainer.getChildren().add(addButtonIndex, interestInput);
        interestInput.requestFocus();
        int inputIndex = interestContainer.getChildren().indexOf(interestInput);
        interestInput.setOnAction(e -> confirmInterestInput(interestInput, inputIndex));
        interestInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                confirmInterestInput(interestInput, inputIndex);
            }
        });
    }

    private void confirmInterestInput(TextField interestInput, int inputIndex) {
        String interest = interestInput.getText().trim();
        if (!interest.isEmpty()) {
            Button newInterestButton = new Button(interest);
            newInterestButton.getStyleClass().add("interest-button");
            newInterestButton.setOnAction(e -> toggleInterest(newInterestButton));
            interestContainer.getChildren().set(inputIndex, newInterestButton);
        } else {
            interestContainer.getChildren().remove(interestInput);
        }
    }

    private void toggleInterest(Button button) {
        String interest = button.getText();

        if (selectedInterests.contains(interest)) {
            selectedInterests.remove(interest);
            button.getStyleClass().remove("selected");
            isTag = isTag - 1;
        } else {
            selectedInterests.add(interest);
            button.getStyleClass().add("selected");
            isTag = isTag + 1;
        }
        updateSelectedCount();
    }

    private void updateSelectedCount() {
        int count = selectedInterests.size();
        if (selectedCount != null) {
            selectedCount.setText(count + (count == 1 ? " área selecionada" : " áreas selecionadas"));
        }
    }

    private Set<String> getSelectedInterests() {
        return new HashSet<>(selectedInterests);
    }

    private void loadComboBoxLevel() {
        levelCourse.getItems().addAll(
                "Iniciante",
                "Intermediario",
                "Avançado");
    }

    private void loadComboBoxCategory() {
        categoryCourse.getItems().addAll(
                "Programação",
                "Dados",
                "Desenvolvimento Web",
                "Mobile",
                "Inteligência Artificial",
                "Cybersegurança");
    }

    private void checkTitle() {
        titleCourse.setOnKeyPressed(event -> {
            titleCourse.setEditable(true);

        });

        titleCourse.setOnKeyReleased(event -> {
            checkSizeTitle(event);
            loadCoutTitleCourse(sizeTitle());
            title = titleCourse.getText();
        });
    }

    private void checkSizeTitle(KeyEvent event) {
        if (sizeTitle() > 100) {
            if (event.getCode() != KeyCode.BACK_SPACE && event.getCode() != KeyCode.DELETE) {
                titleCourse.setText(title);
                titleCourse.positionCaret(titleCourse.getText().length());
            }
        }
    }

    private void loadCoutTitleCourse(int sizeTitle) {
        coutTitleCourse.setText(sizeTitle + "/100");
    }

    private int sizeTitle() {
        return titleCourse.getText().length();
    }

    public void createCourse() {
        boolean isBasics = validatorBasic.validateFields();
        boolean isModule = validatorModules.validateFields();
        boolean isAulas = validatorAulas.validateFields();
        boolean isQuestionario = validatorQuestionario.validateFields(isGradeMiniun);
        boolean isQuestion = validatorQuestoes.validateFields();
        boolean isOptions = validatorOptions.validateFields();
        boolean isSettings = validatorSettings.validateFields();

        if (isBasics && isModule && isAulas && isQuestionario && isQuestion && isOptions && isSettings) {
            ErrorNotification errorNotification = new ErrorNotification(
                container,
                "Curso salvo com sucesso");

        errorNotification.show();  

            new CadastroCursoController(titleCourse.getText(), descritionCourse.getText(), categoryCourse.getValue(),
                    levelCourse.getValue(), String.join(". ", getSelectedInterests()), selectedFile,
                    saveModulesAndContent(), dateInputPopupStart.getLocalDate(), dateInputPopupEnd.getLocalDate(),
                    durationTotal.getText(), dateEnd.isSelected(), isCertificate.isSelected(),
                    isGradeMiniun.isSelected(),
                    ComboBoxVisibily.getValue());
        }
    }

    public List<Map<String, Object>> saveModulesAndContent() {
        j = 0;
        k = 0;
        int cont = 0;
        for (int i = 0; i < modulesList.getChildren().size(); i++) {
            Node moduleNode = modulesList.getChildren().get(i);

            VBox moduleCard = (VBox) moduleNode;
            Map<String, Object> moduleData = new HashMap<>();

            HBox moduleHeader = (HBox) moduleCard.getChildren().get(0);
            Label moduleNumber = (Label) ((StackPane) moduleHeader.getChildren().get(0)).getChildren().get(0);
            moduleData.put("moduleNumber", Integer.parseInt(moduleNumber.getText()));

            VBox moduleContent = (VBox) moduleCard.getChildren().get(1);

            TextField titleField = (TextField) ((HBox) ((VBox) moduleContent.getChildren().get(0))
                    .getChildren().get(1)).getChildren().get(0);
            moduleData.put("moduleTitle", titleField.getText());

            TextField durationField = (TextField) ((VBox) ((HBox) moduleContent.getChildren().get(1))
                    .getChildren().get(0)).getChildren().get(1);
            moduleData.put("moduleDuration", durationField.getText());

            TextArea detailsField = (TextArea) ((VBox) moduleContent.getChildren().get(3))
                    .getChildren().get(1);
            moduleData.put("moduleDescription", detailsField.getText());

            List<Map<String, Object>> contentData = new ArrayList<>();
            List<Map<String, Object>> contentQuestionaireData = new ArrayList<>();
            List<Map<String, Object>> contentLessonData = new ArrayList<>();

            VBox contentContainer = (VBox) moduleCard.getChildren().get(2);

            for (Node contentNode : contentContainer.getChildren()) {
                if (!(contentNode instanceof VBox)) {
                    continue;
                }

                VBox contentBox = (VBox) contentNode;

                if (contentBox.getStyleClass().contains("lesson")) {
                    try {
                        Map<String, Object> lessonData = saveLessonData(contentBox, cont,
                                Integer.parseInt(moduleNumber.getText()));
                        if (lessonData != null) {
                            contentLessonData.add(lessonData);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao processar lição: " + e.getMessage());
                    }
                } else if (contentBox.getStyleClass().contains("questionaire")) {
                    try {
                        Map<String, Object> questionaireData = savequestionaireData(contentBox, cont);
                        if (questionaireData != null) {
                            contentQuestionaireData.add(questionaireData);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao processar questionário: " + e.getMessage());
                    }
                }
            }

            moduleData.put("content", contentData);
            moduleData.put("contentQuestionaire", contentQuestionaireData);
            moduleData.put("contentLesson", contentLessonData);
            modulesData.add(moduleData);
        }

        return modulesData;
    }

    private int k = 0;

    private Map<String, Object> saveLessonData(VBox lessonCard, int cont, int moduleNumber) {
        try {
            Map<String, Object> lessonData = new HashMap<>();
            cont++;
            lessonData.put("cont" + k, cont);
            HBox lessonHeader = (HBox) lessonCard.getChildren().get(0);
            Label lessonNumber = (Label) ((StackPane) lessonHeader.getChildren().get(0)).getChildren().get(0);

            lessonData.put("type", "lesson");
            lessonData.put("moduleNumber", moduleNumber);
            lessonData.put("lessonNumber" + k, Integer.parseInt(lessonNumber.getText()));

            VBox lessonContent = (VBox) lessonCard.getChildren().get(1);

            VBox titleContainer = (VBox) lessonContent.getChildren().get(0);
            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            lessonData.put("lessonTitle" + k, titleField.getText());

            VBox videoContainer = (VBox) lessonContent.getChildren().get(2);
            TextField videoField = (TextField) videoContainer.getChildren().get(1);
            lessonData.put("lessonVideoLink" + k, videoField.getText());

            VBox detailsContainer = (VBox) lessonContent.getChildren().get(4);
            TextArea detailsArea = (TextArea) detailsContainer.getChildren().get(1);
            lessonData.put("lessonDetails" + k, detailsArea.getText());

            VBox materialsContainer = (VBox) lessonContent.getChildren().get(6);
            TextArea materialsArea = (TextArea) materialsContainer.getChildren().get(1);
            lessonData.put("lessonMaterials" + k, materialsArea.getText());

            VBox durationContainer = (VBox) lessonContent.getChildren().get(8);
            TextField durationField = (TextField) durationContainer.getChildren().get(1);
            lessonData.put("lessonDuration" + k, durationField.getText());

            k++;
            return lessonData;
        } catch (Exception e) {
            System.out.println("Erro ao processar dados da lição: " + e.getMessage());
            return null;
        }
    }

    private int j = 0;

    private Map<String, Object> savequestionaireData(VBox questionaireCard, int cont) {
        try {
            Map<String, Object> questionaireData = new HashMap<>();
            cont++;
            questionaireData.put("cont" + j, cont);
            HBox questionaireHeader = (HBox) questionaireCard.getChildren().get(0);
            Label questionaireNumber = (Label) ((StackPane) questionaireHeader.getChildren().get(0)).getChildren().get(0);

            questionaireData.put("type", "questionaire");
            questionaireData.put("questionaireNumber" + j, questionaireNumber.getText());

            VBox container = (VBox) questionaireCard.getChildren().get(1);

            VBox titleContainer = (VBox) container.getChildren().get(0); 
            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            questionaireData.put("questionaireTitle" + j, titleField.getText());

            VBox descriptionContainer = (VBox) container.getChildren().get(2);
            TextArea descriptionArea = (TextArea) descriptionContainer.getChildren().get(1);
            questionaireData.put("questionaireDescription" + j, descriptionArea.getText());

            VBox scoreContainer = (VBox) container.getChildren().get(4);
            TextField scoreField = (TextField) scoreContainer.getChildren().get(1);
            questionaireData.put("questionaireScore" + j, scoreField.getText());

            VBox questionsContainer = (VBox) container.getChildren().get(6);
           Map<String, Object> questionData = new HashMap<>();

            for (int i = 0; i < questionsContainer.getChildren().size(); i++) {
                VBox questionContainer = (VBox) questionsContainer.getChildren().get(i);
                questionaireData.put("questions" + j, saveQuestionData(questionContainer, questionData, cont));
            }
            j++;
            return questionaireData;
        } catch (Exception e) {
            System.out.println("Erro ao processar dados do questionário: " + e.getMessage());
            return null;
        }  
    }

    private int z = 0;

    private Map<String, Object> saveQuestionData(VBox questionCard, Map<String, Object> questionData, int k) {
        HBox questionHeader = (HBox) questionCard.getChildren().get(0);
        StackPane numberContainer = (StackPane) questionHeader.getChildren().get(0);
        Label questionNumber = (Label) numberContainer.getChildren().get(0);
        questionData.put("questionNumber" + z, questionNumber.getText());

        VBox questionContent = (VBox) questionCard.getChildren().get(1);
       HBox scoreBox = (HBox) questionContent.getChildren().get(0);
        TextField scoreField = (TextField) scoreBox.getChildren().get(1);
        questionData.put("questionScore" + z, scoreField.getText());

        TextArea questionText = (TextArea) questionContent.getChildren().get(2);
        questionData.put("questionText" + z, questionText.getText());

        Node node = questionContent.getChildren().get(4);

        Labeled labeled = (Labeled) node;

        int h = 0;
        Map<String, Object> responseData = new HashMap<>();
        if (labeled.getText().equals("Opções (selecione a correta)")) {
            questionData.put("type" + z, "SINGLE_CHOICE");
            VBox optionsContainer = (VBox) questionContent.getChildren().get(5);
            for (int e = 0; e < optionsContainer.getChildren().size(); e++) {
                HBox response = (HBox) optionsContainer.getChildren().get(e);
                RadioButton radioField = (RadioButton) response.getChildren().get(0);
                TextField responseField = (TextField) response.getChildren().get(1);
                responseData.put("responseField" + h, responseField.getText());
                if (radioField.isSelected()) {
                    responseData.put("responseIsTrue" + h, true);
                } else {
                    responseData.put("responseIsTrue" + h, false);
                }
                h++;
            }
        } else if (labeled.getText().equals("Opções (selecione as corretas)")) {
            VBox optionsContainer = (VBox) questionContent.getChildren().get(5);
            questionData.put("type" + z, "MULTIPLE_CHOICE");
            for (int e = 0; e < optionsContainer.getChildren().size(); e++) {
                HBox response = (HBox) optionsContainer.getChildren().get(e);
                CheckBox checkBox = (CheckBox) response.getChildren().get(0);
                TextField responseField = (TextField) response.getChildren().get(1);
                responseData.put("responseField" + h, responseField.getText());
                if (checkBox.isSelected()) {
                    responseData.put("responseIsTrue" + h, true);
                } else {
                    responseData.put("responseIsTrue" + h, false);
                }
                h++;
            }

        } else if (labeled.getText().equals("Resposta Esperada (opcional)")) {
            questionData.put("type" + z, "DISCURSIVE");
            TextArea responseField = (TextArea) questionContent.getChildren().get(5);
            TextArea responseField2 = (TextArea) questionContent.getChildren().get(7);
            responseData.put("responseField" + h, responseField.getText());
            responseData.put("responseField2" + h, responseField2.getText());
            h++;
        }
        questionData.put("response" + z, responseData);
        z++;

        return questionData;
    }

    @FXML
    private void addNewModule() {
        currentModuleCount++;
        VBox moduleCard = new VBox();
        moduleCard.getStyleClass().addAll("module-card", "fade-in", "content-card");
        moduleCard.setSpacing(15);
        HBox moduleHeader = createModuleHeader(currentModuleCount);
        VBox moduleContent = createModuleContent(currentModuleCount);
        VBox lessonsList = createLessonsList(modulesList, moduleHeader);
        moduleCard.getChildren().addAll(moduleHeader, moduleContent, lessonsList);
        moduleCard.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), moduleCard);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        modulesList.getChildren().add(moduleCard);
        validatorModules.setupInitialStateModules(modulesList);
    }

    private HBox createModuleHeader(int moduleNumber) {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(15);
        header.getStyleClass().add("module-header");
        StackPane numberContainer = new StackPane();
        numberContainer.getStyleClass().add("module-number-container");
        Label number = new Label(String.valueOf(moduleNumber));
        number.getStyleClass().add("module-number");
        numberContainer.getChildren().add(number);
        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("outline-button");
        Tooltip.install(removeButton, new Tooltip("Remover módulo"));
        removeButton.setOnAction(e -> {
            removeModuleWithAnimation(header.getParent());
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(numberContainer, spacer, removeButton);
        return header;
    }

    private VBox createModuleContent(int moduleNumber) {
        VBox content = new VBox();
        content.setSpacing(15);
        content.getStyleClass().add("module-content");
        VBox titleContainer = new VBox(5);
        Label titleLabel = new Label("Título do Módulo *");
        titleLabel.getStyleClass().add("field-label");

        HBox titleBox = new HBox(10);
        TextField titleField = new TextField();
        titleField.setPromptText(DEFAULT_MODULE_TITLE);
        titleField.getStyleClass().add("custom-text-field");
        titleField.setPrefWidth(550);

        Label moduleTitleLabelError = new Label("Por favor, insira um título válido, entre 5 e 100 caracteres.");
        moduleTitleLabelError.getStyleClass().add("error-label");
        moduleTitleLabelError.setVisible(false);
        moduleTitleLabelError.setManaged(false);

        Label charCount = new Label("0/100");
        charCount.getStyleClass().add("char-counter");
        titleField.textProperty().addListener((obs, old, newText) -> {
            int count = newText.length();
            charCount.setText(count + "/100");
            if (count > 100) {
                titleField.setText(old);
            }
        });

        titleBox.getChildren().addAll(titleField, charCount);
        titleContainer.getChildren().addAll(titleLabel, titleBox, moduleTitleLabelError);
        HBox detailsContainer = new HBox(15);
        detailsContainer.getChildren().addAll(
                createNumberField("Duração (horas) *", "Ex: 2.5", true));
        Label moduleDurationLabelError = new Label("Por favor, insira uma duração válida.");
        moduleDurationLabelError.getStyleClass().add("error-label");
        moduleDurationLabelError.setVisible(false);
        moduleDurationLabelError.setManaged(false);

        VBox descriptionContainer = new VBox(5);
        Label descLabel = new Label("Descrição do Módulo *");
        descLabel.getStyleClass().add("field-label");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Descreva o conteúdo e objetivos deste módulo...");
        descArea.getStyleClass().add("custom-text-area");
        descArea.setPrefRowCount(3);
        descriptionContainer.getChildren().addAll(descLabel, descArea);
        Label moduleDescripitonLabelError = new Label(
                "Por favor, insira uma descrição válida, com pelo menos 10 caracteres");
        moduleDescripitonLabelError.getStyleClass().add("error-label");
        moduleDescripitonLabelError.setVisible(false);
        moduleDescripitonLabelError.setManaged(false);

        content.getChildren().addAll(titleContainer, detailsContainer, moduleDurationLabelError, descriptionContainer,
                moduleDescripitonLabelError);
        return content;
    }

    private VBox createNumberField(String label, String prompt, boolean allowDecimals) {
        VBox container = new VBox(5);
        container.setVgrow(container, Priority.ALWAYS);

        Label fieldLabel = new Label(label);
        fieldLabel.getStyleClass().add("field-label");

        TextField field = new TextField();
        field.setPromptText(prompt);
        field.getStyleClass().add("custom-text-field");
        field.textProperty().addListener((obs, old, newText) -> {
            if (!newText.isEmpty()) {
                if (allowDecimals) {
                    if (!newText.matches("\\d*\\.?\\d*")) {
                        field.setText(old);
                    }
                } else {
                    if (!newText.matches("\\d*")) {
                        field.setText(old);
                    }
                }
            }
        });

        container.getChildren().addAll(fieldLabel, field);
        return container;
    }

    private VBox createLessonsList(VBox modulesList, HBox moduleHeader) {
        StackPane numberContainer = (StackPane) moduleHeader.getChildren().get(0);
        Label numberLabel = (Label) numberContainer.getChildren().get(0);
        int moduleNumber = Integer.parseInt(numberLabel.getText());

        VBox lessonsList = new VBox();
        HBox buttonHBox = new HBox();
        lessonsList.setSpacing(15);
        lessonsList.getStyleClass().add("lessons-list");

        Button addLessonButton = new Button("+ Adicionar Aula");
        addLessonButton.getStyleClass().add("outline-button");
        String existingStyles = addLessonButton.getStyle();
        addLessonButton.setStyle(existingStyles + "-fx-max-width: 250;");
        addLessonButton.setOnAction(e -> {
            addNewLesson(lessonsList, moduleNumber);
            validatorModules.setupInitialStateModules(modulesList);
        });

        Button addquestionaire = new Button("+ Adicionar Questionário");
        addquestionaire.getStyleClass().add("outline-button");
        addquestionaire.setStyle(existingStyles + "-fx-max-width: 250;");
        HBox.setMargin(addquestionaire, new javafx.geometry.Insets(0, 0, 0, 20));
        addquestionaire.setOnAction(e -> addNewquestionaire(lessonsList,moduleNumber));

        buttonHBox.getChildren().addAll(addLessonButton, addquestionaire);
        lessonsList.getChildren().add(buttonHBox);

        return lessonsList;
    }

    @FXML
    private void addNewquestionaire(VBox lessonsList, int moduleNumber) {
        VBox moduleContent = (VBox) lessonsList.getParent();

        VBox questionaireCard = new VBox();
        questionaireCard.getStyleClass().addAll("lesson-card", "fade-in", "questionaire");
        questionaireCard.setSpacing(15);

        HBox questionaireHeader = new HBox();
        questionaireHeader.setAlignment(Pos.CENTER_LEFT);
        questionaireHeader.setSpacing(10);
        questionaireHeader.getStyleClass().add("lesson-header");

        int moduleSpecificCounter = getModulequestionaireCounter(moduleContent);

        StackPane numberContainer = new StackPane();
        numberContainer.getStyleClass().add("questionaire-number-container");
        Label questionaireLabel = new Label(String.valueOf(moduleSpecificCounter));
        questionaireLabel.getStyleClass().add("questionaire-number");
        numberContainer.getChildren().add(questionaireLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("outline-button");
        removeButton.setOnAction(e -> removequestionaireWithAnimation(questionaireCard, moduleNumber));
        questionaireHeader.getChildren().addAll(numberContainer, spacer, removeButton);

        VBox content = new VBox();
        content.setSpacing(15);

        TextField titleField = new TextField();
        titleField.setPromptText("Título da avaliação");
        titleField.getStyleClass().add("custom-text-field");
        Label titleErrorLabel = new Label("Por favor, insira um título válido, entre 5 e 100 caracteres.");
        titleErrorLabel.getStyleClass().add("error-label");
        titleErrorLabel.setVisible(false);
        titleErrorLabel.setManaged(false);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Descrição da avaliação e instruções...");
        descriptionArea.setPrefRowCount(3);
        descriptionArea.getStyleClass().add("custom-text-area");
        Label descriptionErrorLabel = new Label(
                "Por favor, insira uma descrição válida, com pelo menos 10 caracteres.");
        descriptionErrorLabel.getStyleClass().add("error-label");
        descriptionErrorLabel.setVisible(false);
        descriptionErrorLabel.setManaged(false);

        TextField scoreField = new TextField();
        scoreField.setPromptText("Pontuação máxima");
        scoreField.getStyleClass().add("custom-text-field");
        scoreField.textProperty().addListener((obs, old, newText) -> {
            if (!newText.isEmpty() && !newText.matches("\\d*")) {
                scoreField.setText(old);
            }
        });
        Label scoreErrorLabel = new Label("Por favor, insira uma pontuação válida.");
        scoreErrorLabel.getStyleClass().add("error-label");
        scoreErrorLabel.setVisible(false);
        scoreErrorLabel.setManaged(false);

        VBox questionsContainer = new VBox(15);
        questionsContainer.getStyleClass().add("questions-container");
        questionsContainer.setUserData(1);

        HBox contextMenu = new HBox();
        Button addDiscursiveButton = new Button("Questão Discursiva");
        addDiscursiveButton.setOnAction(e -> addNewQuestion(questionsContainer, lessonsList, "DISCURSIVE",moduleNumber));
        addDiscursiveButton.getStyleClass().add("simple-button");
        String existingStyles = addDiscursiveButton.getStyle();
        addDiscursiveButton.setStyle(existingStyles + "-fx-max-width: 250;");

        Button addSingleChoiceButton = new Button("Questão Única");
        addSingleChoiceButton.setOnAction(e -> addNewQuestion(questionsContainer, lessonsList, "SINGLE_CHOICE",moduleNumber));
        addSingleChoiceButton.getStyleClass().add("simple-button");
        addSingleChoiceButton.setStyle(existingStyles + "-fx-max-width: 250;");

        Button addMultipleChoiceButton = new Button("Questão Múltipla");
        addMultipleChoiceButton.setOnAction(e -> addNewQuestion(questionsContainer, lessonsList, "MULTIPLE_CHOICE",moduleNumber));
        addMultipleChoiceButton.getStyleClass().add("simple-button");
        addMultipleChoiceButton.setStyle(existingStyles + "-fx-max-width: 250;");

        HBox.setMargin(addMultipleChoiceButton, new javafx.geometry.Insets(0, 0, 0, 20));
        HBox.setMargin(addSingleChoiceButton, new javafx.geometry.Insets(0, 0, 0, 20));
        contextMenu.getChildren().addAll(addDiscursiveButton, addSingleChoiceButton, addMultipleChoiceButton);

        content.getChildren().addAll(
                createFieldWithLabel("Título *", titleField),
                titleErrorLabel,
                createFieldWithLabel("Descrição *", descriptionArea),
                descriptionErrorLabel,
                createFieldWithLabel("Pontuação Total *", scoreField),
                scoreErrorLabel,
                questionsContainer,
                contextMenu);

        questionaireCard.getChildren().addAll(questionaireHeader, content);

        questionaireCard.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), questionaireCard);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        setModulequestionaireCounter(moduleContent, moduleSpecificCounter + 1);
        lessonsList.getChildren().add(lessonsList.getChildren().size() - 1, questionaireCard);
        validatorQuestionario.setupInitialStateQuestions(lessonsList,moduleNumber);

    }

    private int getModulequestionaireCounter(VBox moduleContent) {
        Object userData = moduleContent.getProperties().get("questionaireCounter");
        if (userData == null) {
            moduleContent.getProperties().put("questionaireCounter", Integer.valueOf(1));
            return 1;
        }

        if (userData instanceof Integer) {
            return (Integer) userData;
        } else {
            moduleContent.getProperties().put("questionaireCounter", Integer.valueOf(1));
            return 1;
        }
    }

    private void setModulequestionaireCounter(VBox moduleContent, int value) {
        moduleContent.getProperties().put("questionaireCounter", Integer.valueOf(value));
    }

    private int getModuleLessonCounter(VBox moduleContent) {
        if (moduleContent.getUserData() == null) {
            moduleContent.setUserData(1);
            return 1;
        } else {
            try {
                int currentCounter = (int) moduleContent.getUserData();
                if (currentCounter <= 0) {
                    moduleContent.setUserData(1);
                    return 1;
                }

                return currentCounter;
            } catch (ClassCastException e) {
                System.err.println("Erro ao recuperar contador do módulo. Reiniciando contador.");
                moduleContent.setUserData(1);
                return 1;
            }
        }
    }

    public void resetModuleLessonCounter(VBox moduleContent) {
        moduleContent.setUserData(1);
    }

    public void setModuleLessonCounter(VBox moduleContent, int value) {
        if (value > 0) {
            moduleContent.setUserData(value);
        } else {
            moduleContent.setUserData(1);
        }
    }

    public int getCurrentLessonCount(VBox moduleContent) {
        if (moduleContent.getUserData() == null) {
            return 0;
        }
        try {
            return (int) moduleContent.getUserData();
        } catch (ClassCastException e) {
            return 0;
        }
    }

    @FXML
    private void addNewLesson(VBox lessonsList, int moduleNumber) {
        VBox moduleContent = (VBox) lessonsList.getParent();

        VBox lessonCard = new VBox();
        lessonCard.getStyleClass().addAll("lesson-card", "fade-in", "lesson");
        lessonCard.setSpacing(15);

        HBox lessonHeader = new HBox();
        lessonHeader.setAlignment(Pos.CENTER_LEFT);
        lessonHeader.setSpacing(10);
        lessonHeader.getStyleClass().add("lesson-header");

        int moduleSpecificCounter = getModuleLessonCounter(moduleContent);

        StackPane numberContainer = new StackPane();
        numberContainer.getStyleClass().add("lesson-number-container");
        Label lessonNumberLabel = new Label(String.valueOf(moduleSpecificCounter));
        lessonNumberLabel.getStyleClass().add("lesson-number");
        numberContainer.getChildren().add(lessonNumberLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button removeLessonButton = new Button("X");
        removeLessonButton.getStyleClass().add("outline-button");
        Tooltip.install(removeLessonButton, new Tooltip("Remover aula"));
        removeLessonButton.setOnAction(e -> {
            removeLessonWithAnimation(lessonCard,moduleNumber);
        });
        lessonHeader.getChildren().addAll(numberContainer, spacer, removeLessonButton);

        VBox lessonContent = createLessonContent(moduleSpecificCounter);
        lessonCard.getChildren().addAll(lessonHeader, lessonContent);
        lessonCard.setOpacity(0);
        lessonCard.getStyleClass().add("module-header");

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), lessonCard);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        moduleContent.setUserData(moduleSpecificCounter + 1);

        lessonsList.getChildren().add(lessonsList.getChildren().size() - 1, lessonCard);
        validatorAulas.setupInitialStateLessons(lessonsList, moduleNumber);
    }

    private VBox createLessonContent(int lessonNumber) {
        VBox content = new VBox();
        content.setSpacing(15);
        content.getStyleClass().add("lesson-content");

        TextField titleField = new TextField();
        titleField.setPromptText("Título da Aula");
        titleField.getStyleClass().add("custom-text-field");
        Label lessonTitleLabelError = new Label("Por favor, insira um título válido, entre 5 e 100 caracteres");
        lessonTitleLabelError.getStyleClass().add("error-label");
        lessonTitleLabelError.setVisible(false);
        lessonTitleLabelError.setManaged(false);

        TextField videoField = new TextField();
        videoField.setPromptText("Link do vídeo da aula");
        videoField.getStyleClass().add("custom-text-field");
        Label lessonVideoLabelError = new Label("Por favor, insira um link de vídeo válido");
        lessonVideoLabelError.getStyleClass().add("error-label");
        lessonVideoLabelError.setVisible(false);
        lessonVideoLabelError.setManaged(false);

        TextArea detailsArea = new TextArea();
        detailsArea.setPromptText("Detalhes e objetivos da aula...");
        detailsArea.setPrefRowCount(3);
        detailsArea.getStyleClass().add("custom-text-area");
        Label lessonDetailsLabelError = new Label("Por favor, insira detalhes válidos, com no mínimo 10 caracteres");
        lessonDetailsLabelError.getStyleClass().add("error-label");
        lessonDetailsLabelError.setVisible(false);
        lessonDetailsLabelError.setManaged(false);

        TextArea materialsArea = new TextArea();
        materialsArea.setPromptText("Links para materiais complementares...");
        materialsArea.setPrefRowCount(2);
        materialsArea.getStyleClass().add("custom-text-area");
        Label lessonMaterialsLabelError = new Label("Por favor, insira um link para materiais válido");
        lessonMaterialsLabelError.getStyleClass().add("error-label");
        lessonMaterialsLabelError.setVisible(false);
        lessonMaterialsLabelError.setManaged(false);

        TextField durationField = new TextField();
        durationField.setPromptText("Duração (minutos)");
        durationField.getStyleClass().add("custom-text-field");
        durationField.textProperty().addListener((obs, old, newText) -> {
            if (!newText.isEmpty() && !newText.matches("\\d*")) {
                durationField.setText(old);
            }
            if ("0".equals(newText)) {
                durationField.setText(old); 
            }
        });
        Label lessonDurationLabelError = new Label("Por favor, insira uma duração válida");
        lessonDurationLabelError.getStyleClass().add("error-label");
        lessonDurationLabelError.setVisible(false);
        lessonDurationLabelError.setManaged(false);

        content.getChildren().addAll(
                createFieldWithLabel("Título da Aula *", titleField),
                lessonTitleLabelError,
                createFieldWithLabel("Link do Vídeo *", videoField),
                lessonVideoLabelError,
                createFieldWithLabel("Detalhes da Aula", detailsArea),
                lessonDetailsLabelError,
                createFieldWithLabel("Materiais Complementares", materialsArea),
                lessonMaterialsLabelError,
                createFieldWithLabel("Duração (minutos) *", durationField),
                lessonDurationLabelError);

        return content;
    }

    private VBox createFieldWithLabel(String labelText, Node field) {
        VBox container = new VBox(5);
        Label label = new Label(labelText);
        label.getStyleClass().add("field-label");
        container.getChildren().addAll(label, field);
        return container;
    }

    private void removeLessonWithAnimation(Node lessonCard, int moduleNumber) {
        VBox lessonsList = (VBox) lessonCard.getParent();
        VBox moduleContent = (VBox) lessonsList.getParent();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), lessonCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            lessonsList.getChildren().remove(lessonCard);
            updateLessonNumbers(lessonsList, moduleContent);
            validatorAulas.setupInitialStateLessons(lessonsList, moduleNumber);
        });
        fadeOut.play();
    }

    private void removequestionaireWithAnimation(Node questionaireCard,int moduleNumber) {
        VBox lessonsList = (VBox) questionaireCard.getParent();
        VBox moduleContent = (VBox) lessonsList.getParent();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), questionaireCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            lessonsList.getChildren().remove(questionaireCard);
            updatequestionaireNumbers(lessonsList, moduleContent);
            validatorQuestionario.setupInitialStateQuestions(lessonsList,moduleNumber);
        });
        fadeOut.play();
    }

    private void updateLessonNumbers(VBox lessonsList, VBox moduleContent) {
        int tempLessonCounter = 1;
        for (Node node : lessonsList.getChildren()) {
            if (node instanceof VBox) {
                VBox card = (VBox) node;
                HBox header = (HBox) card.getChildren().get(0);
                StackPane numberContainer = (StackPane) header.getChildren().get(0);

                if (numberContainer.getStyleClass().contains("lesson-number-container")) {
                    Label numberLabel = (Label) numberContainer.getChildren().get(0);
                    numberLabel.setText(String.valueOf(tempLessonCounter));
                    tempLessonCounter++;
                }
            }
        }
        moduleContent.setUserData(tempLessonCounter);
    }

    private void updatequestionaireNumbers(VBox lessonsList, VBox moduleContent) {
        int tempquestionaireCounter = 1;
        for (Node node : lessonsList.getChildren()) {
            if (node instanceof VBox) {
                VBox card = (VBox) node;
                HBox header = (HBox) card.getChildren().get(0);
                StackPane numberContainer = (StackPane) header.getChildren().get(0);

                if (numberContainer.getStyleClass().contains("questionaire-number-container")) {
                    Label numberLabel = (Label) numberContainer.getChildren().get(0);
                    numberLabel.setText(String.valueOf(tempquestionaireCounter));
                    tempquestionaireCounter++;
                }
            }
        }
        setModulequestionaireCounter(moduleContent, tempquestionaireCounter);
    }

    private void addNewQuestion(VBox questionsContainer, VBox lessonsList, String type, int moduleNumber) {
        VBox questionCard = new VBox(10);
        questionCard.getStyleClass().add("question-card");

        HBox questionHeader = new HBox(10);
        questionHeader.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        int questionNumber = questionsContainer.getChildren().size() + 1;

        StackPane numberContainer = new StackPane();
        numberContainer.getStyleClass().add("question-number-container");
        Label number = new Label(String.valueOf(questionNumber));
        number.getStyleClass().add("questionaire-number");
        numberContainer.getChildren().add(number);

        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("simple-button");
        removeButton.setOnAction(e -> {
            removeQuestionWithAnimation(questionCard, moduleNumber);
        });

        questionHeader.getChildren().addAll(numberContainer, spacer, removeButton);

        VBox questionContent = new VBox(10);
        TextArea questionText = new TextArea();
        questionText.setPromptText("Digite a pergunta da questão...");
        questionText.setPrefRowCount(3);
        questionText.getStyleClass().add("custom-text-area");
        Label questionErrorLabel = new Label("Por favor, insira uma pergunta válida, com pelo menos 10 caracteres.");
        questionErrorLabel.getStyleClass().add("error-label");
        questionErrorLabel.setVisible(false);
        questionErrorLabel.setManaged(false);

        TextField scoreField = new TextField();
        scoreField.setPromptText("Pontos");
        scoreField.setPrefWidth(80);
        scoreField.getStyleClass().add("custom-text-field");
        scoreField.textProperty().addListener((obs, old, newText) -> {
            if (!newText.isEmpty() && !newText.matches("\\d*\\.?\\d*")) {
                scoreField.setText(old);
            }
        });

        HBox scoreHbox = new HBox(10);
        Label scoreLabel = new Label("Pontos:");
        scoreLabel.getStyleClass().add("field-label");
        Label scoreErrorLabel = new Label("Por favor, insira uma pontuação válida.");
        scoreErrorLabel.getStyleClass().add("error-label");
        scoreErrorLabel.setVisible(false);
        scoreErrorLabel.setManaged(false);

        scoreHbox.getChildren().addAll(scoreLabel, scoreField);
        questionContent.getChildren().addAll(scoreHbox, scoreErrorLabel, questionText, questionErrorLabel);

        switch (type) {
            case "DISCURSIVE":
                addDiscursiveFields(questionContent);
                break;
            case "SINGLE_CHOICE":
                addSingleChoiceFields(questionContent,moduleNumber);
                break;
            case "MULTIPLE_CHOICE":
                addMultipleChoiceFields(questionContent,moduleNumber);
                break;
        }
        questionCard.getChildren().addAll(questionHeader, questionContent);
        questionsContainer.getChildren().add(questionCard);

        validatorQuestoes.setupInitialStateQuestions(questionsContainer,moduleNumber);
        validatorQuestionario.setupInitialStateQuestions(lessonsList,moduleNumber);

        updateQuestionNumbers(questionsContainer);
    }

    private void removeQuestionWithAnimation(Node questionCard, int moduleNumber) {
        VBox questionsContainer = (VBox) questionCard.getParent(); 

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), questionCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            questionsContainer.getChildren().remove(questionCard); 
            updateQuestionNumbers(questionsContainer); 
            validatorQuestoes.setupInitialStateQuestions(questionsContainer,moduleNumber);
            validatorOptions.setupInitialStateOptions(questionsContainer, true,moduleNumber);
            validatorOptions.setupInitialStateOptions(questionsContainer, false,moduleNumber);

        });
        fadeOut.play();
    }

    private void updateQuestionNumbers(VBox questionsContainer) {
        for (int i = 0; i < questionsContainer.getChildren().size(); i++) {
            VBox questionCard = (VBox) questionsContainer.getChildren().get(i);
            HBox questionHeader = (HBox) questionCard.getChildren().get(0);
            StackPane numberContainer = (StackPane) questionHeader.getChildren().get(0);
            Label numberLabel = (Label) numberContainer.getChildren().get(0);
            numberLabel.setText(String.valueOf(i + 1)); 
        }
    }

    private void removeModuleWithAnimation(Node moduleCard) {
        currentModuleCount--;
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), moduleCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            modulesList.getChildren().remove(moduleCard);
            validatorModules.setupInitialStateModules(modulesList);
            updateModuleNumbers();
        });
        fadeOut.play();
    }

    private void updateModuleNumbers() {
        for (int i = 0; i < modulesList.getChildren().size(); i++) {
            VBox moduleCard = (VBox) modulesList.getChildren().get(i);
            HBox moduleHeader = (HBox) moduleCard.getChildren().get(0);
            StackPane numberContainer = (StackPane) moduleHeader.getChildren().get(0);
            Label numberLabel = (Label) numberContainer.getChildren().get(0);
            numberLabel.setText(String.valueOf(i + 1));
        }
    }

    private void addDiscursiveFields(VBox container) {
        TextArea expectedAnswer = new TextArea();
        expectedAnswer.setPromptText("Resposta esperada (opcional)...");
        expectedAnswer.setPrefRowCount(2);
        expectedAnswer.getStyleClass().add("custom-text-area");
        Label expectedAnswerLabel = new Label("Resposta Esperada (opcional)");
        expectedAnswerLabel.getStyleClass().add("field-label");

        TextArea evaluationCriteria = new TextArea();
        evaluationCriteria.setPromptText("Critérios de avaliação...");
        evaluationCriteria.setPrefRowCount(2);
        evaluationCriteria.getStyleClass().add("custom-text-area");
        Label evolutionCriteriaLabel = new Label("Critérios de Avaliação");
        evolutionCriteriaLabel.getStyleClass().add("field-label");
        Label evaluationCriteriaErrorLabel = new Label(
                "Por favor, insira critérios de avaliação válidos, com pelo menos 10 caracteres.");
        evaluationCriteriaErrorLabel.getStyleClass().add("error-label");
        evaluationCriteriaErrorLabel.setVisible(false);
        evaluationCriteriaErrorLabel.setManaged(false);

        container.getChildren().addAll(
                expectedAnswerLabel,
                expectedAnswer,
                evolutionCriteriaLabel,
                evaluationCriteria, evaluationCriteriaErrorLabel);
    }

    private void addSingleChoiceFields(VBox container, int moduleNumber) {
        VBox optionsContainer = new VBox(5);
        ToggleGroup toggleGroup = new ToggleGroup();

        Button addOptionButton = new Button("+ Adicionar Opção");
        addOptionButton.getStyleClass().add("outline-button");
        addOptionButton.setOnAction(e -> addChoiceOption(optionsContainer, container, toggleGroup, true,moduleNumber));
        Label optionLabel = new Label("Opções (selecione a correta)");
        optionLabel.getStyleClass().add("field-label");
        Label optionErrorLabel = new Label("Por favor, selecione uma opção válida.");
        optionErrorLabel.getStyleClass().add("error-label");
        optionErrorLabel.setVisible(false);
        optionErrorLabel.setManaged(false);

        container.getChildren().addAll(
                optionLabel,
                optionsContainer,
                addOptionButton, optionErrorLabel);
        addChoiceOption(optionsContainer, container, toggleGroup, true,moduleNumber);
        addChoiceOption(optionsContainer, container, toggleGroup, true,moduleNumber);

        validatorOptions.setupInitialStateOptions(container, true,moduleNumber);

    }

    private void addMultipleChoiceFields(VBox container,int moduleNumber) {
        VBox optionsContainer = new VBox(5);

        Button addOptionButton = new Button("+ Adicionar Opção");
        addOptionButton.getStyleClass().add("outline-button");
        addOptionButton.setOnAction(e -> addChoiceOption(optionsContainer, container, null, false,moduleNumber));
        Label optionLabel = new Label("Opções (selecione as corretas)");
        optionLabel.getStyleClass().add("field-label");

        Label optionErrorLabel = new Label("Por favor, selecione uma opção válida.");
        optionErrorLabel.getStyleClass().add("error-label");
        optionErrorLabel.setVisible(false);
        optionErrorLabel.setManaged(false);

        container.getChildren().addAll(
                optionLabel,
                optionsContainer,
                addOptionButton, optionErrorLabel);

        addChoiceOption(optionsContainer, container, null, false, moduleNumber);
        addChoiceOption(optionsContainer, container, null, false, moduleNumber);
        validatorOptions.setupInitialStateOptions(container, false,moduleNumber);
    }

    private void addChoiceOption(VBox optionsContainer, VBox container, ToggleGroup toggleGroup, boolean singleChoice, int moduleNumber) {
        HBox optionBox = new HBox(10);
        optionBox.setAlignment(Pos.CENTER_LEFT);

        Node selectionControl;
        if (singleChoice) {
            RadioButton radio = new RadioButton();
            radio.setToggleGroup(toggleGroup);
            selectionControl = radio;
            selectionControl.getStyleClass().add("radio-button");
        } else {
            selectionControl = new CheckBox();
            selectionControl.getStyleClass().add("custom-checkbox");
        }

        TextField optionText = new TextField();
        optionText.setPromptText("Digite a opção...");
        optionText.getStyleClass().add("custom-text-field");
        HBox.setHgrow(optionText, Priority.ALWAYS);

        Label optionErrorLabel = new Label("Por favor, insira uma opção válida, com pelo menos 1 caractere.");
        optionErrorLabel.getStyleClass().add("error-label");
        optionErrorLabel.setVisible(false);
        optionErrorLabel.setManaged(false);

        Button removeOption = new Button("X");
        removeOption.getStyleClass().add("simple-button");
        removeOption.setOnAction(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), optionBox);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> {
                container.getChildren().removeAll(optionBox, optionErrorLabel);
                validatorOptions.setupInitialStateOptions(container, singleChoice,moduleNumber);
                validatorOptions.setupInitialStateOptions(container, !singleChoice,moduleNumber);
            });
            fadeOut.play();
        });

        optionBox.getChildren().addAll(selectionControl, optionText, removeOption);

        optionBox.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), optionBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        int positionOpcion = 0;
        for (int i = 0; i < container.getChildren().size(); i++) {
            if (container.getChildren().get(i) instanceof Button) {
                break;
            } else {
                positionOpcion = i;
            }
        }

        container.getChildren().add(positionOpcion + 1, optionBox);
        container.getChildren().add(positionOpcion + 2, optionErrorLabel);

        validatorOptions.setupInitialStateOptions(container, false,moduleNumber);
    }

    protected void addDateInputField() {
        Label dateStart = new Label();
        Label dateEnd = new Label();
        if (!Modo.getInstance().getModo()) {
            dateStart.setStyle("-fx-text-fill: black;");
            dateEnd.setStyle("-fx-text-fill: black;");
        }

        dateStart.setText("Data de início");
        dateInputPopupStart.setMinDate(LocalDate.now());
        dateEnd.setText("Data do fim");
        dateInputPopupEnd.setMinDate(LocalDate.now().plusDays(1));

        dateContainerStart.getChildren().clear();
        dateContainerEnd.getChildren().clear();
        date.getChildren().clear();

        dateContainerStart.getChildren().add(dateInputPopupStart.getDateInputField());
        dateContainerEnd.getChildren().add(dateInputPopupEnd.getDateInputField());

        date.getChildren().addAll(dateStart, dateContainerStart, dateEnd, dateContainerEnd);
    }

    @FXML
    private void changeModeStyle() {
        container.getStylesheets().clear();
        if (Modo.getInstance().getModo()) {
            container.getStylesheets()
                    .add(getClass().getResource("/com/professor/style/ligth/style.css").toExternalForm());
            Modo.getInstance().setModo();
            loadCalendar();
        } else {
            container.getStylesheets()
                    .add(getClass().getResource("/com/professor/style/dark/style.css").toExternalForm());
            Modo.getInstance().setModo();
            loadCalendar();
        }
    }

    private void loadCalendar() {
        if (!Modo.getInstance().getModo()) {
            dateInputPopupStart.setBackgroundColor("#FFFFFF");
            dateInputPopupStart.setAccentColor("#3498db");
            dateInputPopupStart.setHoverColor("#6896c4");
            dateInputPopupStart.setTextColor("#000000");
            dateInputPopupStart.setBorderColor("#808080");
            dateInputPopupStart.setDisabledTextColor("#A9A9A9");
            dateInputPopupStart.setIconColor("#3498db");

            dateInputPopupEnd.setBackgroundColor("#FFFFFF");
            dateInputPopupEnd.setAccentColor("#3498db");
            dateInputPopupEnd.setHoverColor("#6896c4");
            dateInputPopupEnd.setTextColor("#000000");
            dateInputPopupEnd.setBorderColor("#808080");
            dateInputPopupEnd.setDisabledTextColor("#A9A9A9");
            dateInputPopupEnd.setIconColor("#3498db");
            addDateInputField();
        } else {
            dateInputPopupStart.setBackgroundColor("#1A1F2F");
            dateInputPopupStart.setAccentColor("#748BFF");
            dateInputPopupStart.setHoverColor("#8C87FF");
            dateInputPopupStart.setTextColor("#FFFFFF");
            dateInputPopupStart.setBorderColor("#808080");
            dateInputPopupStart.setDisabledTextColor("#A9A9A9");
            dateInputPopupStart.setIconColor("#728CFF");

            dateInputPopupEnd.setBackgroundColor("#1A1F2F");
            dateInputPopupEnd.setAccentColor("#748BFF");
            dateInputPopupEnd.setHoverColor("#8C87FF");
            dateInputPopupEnd.setTextColor("#FFFFFF");
            dateInputPopupEnd.setBorderColor("#808080");
            dateInputPopupEnd.setDisabledTextColor("#A9A9A9");
            dateInputPopupEnd.setIconColor("#728CFF");
            addDateInputField();
        }
    }

    private void toggle() {
        TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
        thumbTransition.setToX(isLightMode ? 12.0 : -12.0);
        thumbTransition.play();

        FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
        fillTransition.setFromValue(isLightMode ? Color.web("#FFA500") : Color.web("#4169E1"));
        fillTransition.setToValue(isLightMode ? Color.web("#4169E1") : Color.web("#FFA500"));
        fillTransition.play();

        isLightMode = !isLightMode;

        if (isLightMode) {
            changeModeStyle();
            background.getStyleClass().remove("dark");
        } else {
            changeModeStyle();
            background.getStyleClass().add("dark");
        }

        updateIconsVisibility();
    }

    public boolean isLightMode() {
        return isLightMode;
    }

    private void updateIconsVisibility() {
        sunIcon.setVisible(isLightMode);
        moonIcon.setVisible(!isLightMode);
    }

    private void toggleInitialize() {
        if (!Modo.getInstance().getModo()) {
            background.getStyleClass().add("dark");
            sunIcon.setVisible(Modo.getInstance().getModo());
            moonIcon.setVisible(!Modo.getInstance().getModo());
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
            thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
            thumbTransition.play();
        } else {
            TranslateTransition thumbTransition = new TranslateTransition(Duration.millis(200), thumbContainer);
            thumbTransition.setToX(Modo.getInstance().getModo() ? -12.0 : 12.0);
            thumbTransition.play();
            background.getStyleClass().remove("dark");
            sunIcon.setVisible(Modo.getInstance().getModo());
            moonIcon.setVisible(!Modo.getInstance().getModo());
        }
    }
}