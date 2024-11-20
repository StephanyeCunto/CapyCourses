package com.view.professor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.util.Duration;

import com.controller.professor.CadastroCursoController;
import com.view.Modo;
import com.view.elements.Calendario;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private int lessonCounter = 1;
    private int questionaireCounter = 1;
    private int currentModuleCount = 0;

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

        Timeline timeline0 = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            registrationProgress();
        }));

        timeline0.setCycleCount(Timeline.INDEFINITE);
        timeline0.play();

        toggleButtonHBox.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();

    }

    private static final String STEP_PENDING = "step-pending";
    private static final String STEP_CURRENT = "step-current";
    private static final String STEP_COMPLETED = "step-completed";
    private static final int MIN_MODULES = 1;
    private static final int MIN_LESSONS_PER_MODULE = 1;

    private void registrationProgress() {
        // List<Map<String, Object>> modulesData = saveModulesAndLessonsData();
        // double percentage = calculateTotalProgress(modulesData);

        // updateProgressDisplay(percentage);
        // updateAllLabels(modulesData);
    }

    private double calculateTotalProgress(List<Map<String, Object>> modulesData) {
        int completedFields = countCompletedBaseFields();
        int totalFields = countTotalBaseFields();

        if (modulesData.isEmpty()) {
            totalFields += (3 * MIN_MODULES);
            totalFields += (5 * MIN_LESSONS_PER_MODULE);
        }

        ModuleProgressData moduleProgress = countModuleProgress(modulesData);

        int modulesWithoutLessons = countModulesWithoutLessons(modulesData);
        totalFields += (5 * MIN_LESSONS_PER_MODULE * modulesWithoutLessons);

        completedFields += moduleProgress.completedFields;
        totalFields += moduleProgress.totalFields;

        return totalFields > 0 ? (completedFields * 100.0) / totalFields : 0;
    }

    private int countModulesWithoutLessons(List<Map<String, Object>> modulesData) {
        int count = 0;
        for (Map<String, Object> moduleData : modulesData) {
            Object lessons = moduleData.get("lessons");
            if (lessons == null || !(lessons instanceof List) || ((List<?>) lessons).isEmpty()) {
                count++;
            }
        }
        return count;
    }

    private int countCompletedBaseFields() {
        int completed = 0;
        String[] fields = {
                titleCourse.getText(),
                descritionCourse.getText(),
                categoryCourse.getValue(),
                levelCourse.getValue(),
                durationTotal.getText(),
                String.valueOf(ComboBoxVisibily.getValue())
        };

        for (String field : fields) {
            if (isFieldComplete(field)) {
                completed++;
            }
        }
        if (isImage)
            completed++;
        if (isTag > 0)
            completed++;

        return completed;
    }

    private int countTotalBaseFields() {
        return 8;
    }

    private ModuleProgressData countModuleProgress(List<Map<String, Object>> modulesData) {
        int completedFields = 0;
        int totalFields = 0;

        for (Map<String, Object> moduleData : modulesData) {
            completedFields += countCompletedModuleFields(moduleData);
            totalFields += 3;
            LessonProgressData lessonProgress = countLessonProgress(moduleData.get("lessons"));
            completedFields += lessonProgress.completedFields;
            totalFields += lessonProgress.totalFields;
        }
        return new ModuleProgressData(completedFields, totalFields);
    }

    private int countCompletedModuleFields(Map<String, Object> moduleData) {
        int completed = 0;
        String[] fields = {
                (String) moduleData.get("moduleTitle"),
                (String) moduleData.get("moduleDuration"),
                (String) moduleData.get("moduleDescription")
        };
        for (String field : fields) {
            if (isFieldComplete(field)) {
                completed++;
            }
        }
        return completed;
    }

    private LessonProgressData countLessonProgress(Object lessonsObj) {
        if (!(lessonsObj instanceof List)) {
            return new LessonProgressData(0, 0);
        }
        List<Map<String, String>> lessons = (List<Map<String, String>>) lessonsObj;
        int completedFields = 0;
        int totalFields = 0;
        for (Map<String, String> lesson : lessons) {
            String[] fields = {
                    lesson.get("lessonTitle"),
                    lesson.get("lessonVideoLink"),
                    lesson.get("lessonDetails"),
                    lesson.get("lessonMaterials"),
                    lesson.get("lessonDuration")
            };
            for (String field : fields) {
                if (isFieldComplete(field)) {
                    completedFields++;
                }
            }
            totalFields += 5;
        }

        return new LessonProgressData(completedFields, totalFields);
    }

    private boolean isFieldComplete(String field) {
        return field != null && !field.isEmpty() && !"null".equals(field);
    }

    private void updateProgressDisplay(double percentage) {
        String percentageFormatted = String.format("%.0f", percentage);
        double percentageDouble = percentage / 100;
        progressLabel.setText(percentageFormatted + " % Completo");
        progressBar.setProgress(percentageDouble);
    }

    private void updateAllLabels(List<Map<String, Object>> modulesData) {
        updateBasicInformationLabel();
        updateSettingsLabel();
        updateModulesLabel(modulesData);
    }

    private void updateBasicInformationLabel() {
        String[] fields = {
                titleCourse.getText(),
                descritionCourse.getText(),
                categoryCourse.getValue(),
                levelCourse.getValue()
        };
        int completed = 0;
        for (String field : fields) {
            if (isFieldComplete(field))
                completed++;
        }
        if (isTag > 0)
            completed++;
        updateStepLabel(basicInformation, "Informações Básicas",
                completed, fields.length + 1);
    }

    private void updateSettingsLabel() {
        String[] fields = {
                durationTotal.getText(),
                String.valueOf(ComboBoxVisibily.getValue())
        };
        int completed = 0;
        for (String field : fields) {
            if (isFieldComplete(field))
                completed++;
        }

        updateStepLabel(settings, "Configurações", completed, fields.length);
    }

    private void updateModulesLabel(List<Map<String, Object>> modulesData) {
        if (modulesData.isEmpty()) {
            setLabelPending(modules, "Módulos");
            return;
        }
        ModuleProgressData progress = countModuleProgress(modulesData);
        int modulesWithoutLessons = countModulesWithoutLessons(modulesData);
        int totalRequiredFields = progress.totalFields + (5 * MIN_LESSONS_PER_MODULE * modulesWithoutLessons);
        if (progress.completedFields == 0) {
            setLabelPending(modules, "Módulos");
        } else if (progress.completedFields == totalRequiredFields && allModulesHaveLessons(modulesData)) {
            setLabelCompleted(modules, "Módulos");
        } else {
            setLabelInProgress(modules, "Módulos");
        }
    }

    private boolean allModulesHaveLessons(List<Map<String, Object>> modulesData) {
        for (Map<String, Object> moduleData : modulesData) {
            Object lessons = moduleData.get("lessons");
            if (lessons == null || !(lessons instanceof List) || ((List<?>) lessons).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void updateStepLabel(Label label, String text, int completed, int total) {
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
        label.setStyle("-fx-text-fill: #797E8C;");
    }

    private void setLabelInProgress(Label label, String text) {
        label.getStyleClass().remove(STEP_PENDING);
        label.getStyleClass().add(STEP_CURRENT);
        label.setStyle("-fx-text-fill: rgb(89, 92, 150);");
        label.setText("→ " + text);
    }

    private void setLabelCompleted(Label label, String text) {
        label.getStyleClass().remove(STEP_PENDING);
        label.getStyleClass().remove(STEP_CURRENT);
        label.getStyleClass().add(STEP_COMPLETED);
        label.setStyle("-fx-text-fill: rgb(89, 150, 90);");
        label.setText("✓ " + text);
    }

    private static class ModuleProgressData {
        final int completedFields;
        final int totalFields;

        ModuleProgressData(int completedFields, int totalFields) {
            this.completedFields = completedFields;
            this.totalFields = totalFields;
        }
    }

    private static class LessonProgressData {
        final int completedFields;
        final int totalFields;

        LessonProgressData(int completedFields, int totalFields) {
            this.completedFields = completedFields;
            this.totalFields = totalFields;
        }
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
        new CadastroCursoController(titleCourse.getText(), descritionCourse.getText(), categoryCourse.getValue(),
                levelCourse.getValue(), String.join(". ", getSelectedInterests()), selectedFile,
                saveModulesAndContent(), dateInputPopupStart.getLocalDate(), dateInputPopupEnd.getLocalDate(),
                durationTotal.getText(), dateEnd.isSelected(), isCertificate.isSelected(), isGradeMiniun.isSelected(),
                ComboBoxVisibily.getValue());
    }

    public List<Map<String, Object>> saveModulesAndContent() {
        List<Map<String, Object>> modulesData = new ArrayList<>();
        j=0;
        k=0;
        int cont=0;
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
            
            TextArea detailsField = (TextArea) ((VBox) moduleContent.getChildren().get(2))
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
                        Map<String, Object> lessonData = saveLessonData(contentBox,cont,Integer.parseInt(moduleNumber.getText()));
                        if (lessonData != null) {
                            contentLessonData.add(lessonData);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao processar lição: " + e.getMessage());
                    }
                } else if (contentBox.getStyleClass().contains("questionaire")) {
                    try {
                        Map<String, Object> questionaireData = savequestionaireData(contentBox,cont);
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

    private int k=0;
    private Map<String, Object> saveLessonData(VBox lessonCard,int cont,int moduleNumber) {
        try {
            Map<String, Object> lessonData = new HashMap<>();
            cont++;
            lessonData.put("cont"+k,cont);
            HBox lessonHeader = (HBox) lessonCard.getChildren().get(0);
            Label lessonNumber = (Label) ((StackPane) lessonHeader.getChildren().get(0)).getChildren().get(0);
            
            lessonData.put("type", "lesson");
            lessonData.put("moduleNumber",moduleNumber);
            lessonData.put("lessonNumber"+k, Integer.parseInt(lessonNumber.getText()));
            
            VBox lessonContent = (VBox) lessonCard.getChildren().get(1);
            
            VBox titleContainer = (VBox) lessonContent.getChildren().get(0);
            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            lessonData.put("lessonTitle"+k, titleField.getText());
            
            VBox videoContainer = (VBox) lessonContent.getChildren().get(1);
            TextField videoField = (TextField) videoContainer.getChildren().get(1);
            lessonData.put("lessonVideoLink"+k, videoField.getText());
            
            VBox detailsContainer = (VBox) lessonContent.getChildren().get(2);
            TextArea detailsArea = (TextArea) detailsContainer.getChildren().get(1);
            lessonData.put("lessonDetails"+k, detailsArea.getText());
            
            VBox materialsContainer = (VBox) lessonContent.getChildren().get(3);
            TextArea materialsArea = (TextArea) materialsContainer.getChildren().get(1);
            lessonData.put("lessonMaterials"+k, materialsArea.getText());
            
            VBox durationContainer = (VBox) lessonContent.getChildren().get(4);
            TextField durationField = (TextField) durationContainer.getChildren().get(1);
            lessonData.put("lessonDuration"+k, durationField.getText());
            
            k++;
            return lessonData;
        } catch (Exception e) {
            System.out.println("Erro ao processar dados da lição: " + e.getMessage());
            return null;
        }
    }
    
    private int j=0;
    private Map<String, Object> savequestionaireData(VBox questionaireCard,int cont) {
        try {
            Map<String, Object> questionaireData = new HashMap<>();
            cont++;
            questionaireData.put("cont"+j,cont);
            HBox questionaireHeader = (HBox) questionaireCard.getChildren().get(0);
            Label questionaireNumber = (Label) ((StackPane) questionaireHeader.getChildren().get(0)).getChildren().get(0);
            
            questionaireData.put("type", "questionaire");
            questionaireData.put("questionaireNumber"+j, questionaireNumber.getText());
            
            VBox container = (VBox) questionaireCard.getChildren().get(1);
            
            VBox titleContainer = (VBox) container.getChildren().get(0);
            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            questionaireData.put("questionaireTitle"+j, titleField.getText());
            
            VBox descriptionContainer = (VBox) container.getChildren().get(1);
            TextArea descriptionArea = (TextArea) descriptionContainer.getChildren().get(1);
            questionaireData.put("questionaireDescription"+j, descriptionArea.getText());
            
            VBox scoreContainer = (VBox) container.getChildren().get(2);
            TextField scoreField = (TextField) scoreContainer.getChildren().get(1);
            questionaireData.put("questionaireScore"+j, scoreField.getText());
            
            j++;
            return questionaireData;
        } catch (Exception e) {
            System.out.println("Erro ao processar dados do questionário: " + e.getMessage());
            return null;
        }
    }

    private int z=0;
    private Map<String, Object> saveQuestionData(VBox questionCard,int k) {
        Map<String, Object> questionData = new HashMap<>();

        HBox questionHeader = (HBox) questionCard.getChildren().get(0);
        StackPane numberContainer = (StackPane) questionHeader.getChildren().get(0);
        Label questionNumber = (Label) numberContainer.getChildren().get(0);
        questionData.put("questionNumber"+z, Integer.parseInt(questionNumber.getText()));

        VBox questionContent = (VBox) questionCard.getChildren().get(1);

        HBox scoreBox = (HBox) questionContent.getChildren().get(0);
        TextField scoreField = (TextField) scoreBox.getChildren().get(1);
        questionData.put("questionScore"+z, scoreField.getText());

        TextArea questionText = (TextArea) questionContent.getChildren().get(1);
        questionData.put("questionText"+z, questionText.getText());

        if (questionContent.getChildren().size() > 2) {
            Node additionalContent = questionContent.getChildren().get(2);
            if (additionalContent instanceof VBox) {
                VBox optionsContainer = (VBox) additionalContent;
                List<String> options = new ArrayList<>();
                for (Node optionNode : optionsContainer.getChildren()) {
                    if (optionNode instanceof TextField) {
                        TextField optionField = (TextField) optionNode;
                        options.add(optionField.getText());
                    }
                }
                questionData.put("options"+z, options);
                questionData.put("type"+z,
                        optionsContainer.getStyleClass().contains("multiple-choice") ? "MULTIPLE_CHOICE"
                                : "SINGLE_CHOICE");
            } else {
                questionData.put("type", "DISCURSIVE");
            }
        }

        return questionData;
    }

    @FXML
    private void addNewModule() {
        currentModuleCount++;
        VBox moduleCard = new VBox();
        moduleCard.getStyleClass().addAll("module-card", "fade-in");
        moduleCard.setSpacing(15);
        HBox moduleHeader = createModuleHeader(currentModuleCount);
        VBox moduleContent = createModuleContent(currentModuleCount);
        VBox lessonsList = createLessonsList();
        moduleCard.getChildren().addAll(moduleHeader, moduleContent, lessonsList);
        moduleCard.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), moduleCard);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        modulesList.getChildren().add(moduleCard);
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
        removeButton.setOnAction(e -> removeModuleWithAnimation(header.getParent()));
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
        titleContainer.getChildren().addAll(titleLabel, titleBox);
        HBox detailsContainer = new HBox(15);
        detailsContainer.getChildren().addAll(
                createNumberField("Duração (horas) *", "Ex: 2.5", true));
        VBox descriptionContainer = new VBox(5);
        Label descLabel = new Label("Descrição do Módulo *");
        descLabel.getStyleClass().add("field-label");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Descreva o conteúdo e objetivos deste módulo...");
        descArea.getStyleClass().add("custom-text-area");
        descArea.setPrefRowCount(3);
        descriptionContainer.getChildren().addAll(descLabel, descArea);

        content.getChildren().addAll(titleContainer, detailsContainer, descriptionContainer);
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

    private VBox createLessonsList() {
        VBox lessonsList = new VBox();
        HBox buttonHBox = new HBox();
        lessonsList.setSpacing(15);
        lessonsList.getStyleClass().add("lessons-list");

        Button addLessonButton = new Button("+ Adicionar Aula");
        addLessonButton.getStyleClass().add("outline-button");
        String existingStyles = addLessonButton.getStyle();
        addLessonButton.setStyle(existingStyles + "-fx-max-width: 250;");
        addLessonButton.setOnAction(e -> addNewLesson(lessonsList));

        Button addquestionaire = new Button("+ Adicionar Questionário");
        addquestionaire.getStyleClass().add("outline-button");
        addquestionaire.setStyle(existingStyles + "-fx-max-width: 250;");
        HBox.setMargin(addquestionaire, new javafx.geometry.Insets(0, 0, 0, 20));
        addquestionaire.setOnAction(e -> addNewquestionaire(lessonsList));

        buttonHBox.getChildren().addAll(addLessonButton, addquestionaire);
        lessonsList.getChildren().add(buttonHBox);
        return lessonsList;
    }

    @FXML
    private void addNewquestionaire(VBox lessonsList) {
        VBox moduleContent = (VBox) lessonsList.getParent();

        VBox questionaireCard = new VBox();
        questionaireCard.getStyleClass().addAll("lesson-card", "fade-in","questionaire");
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
        removeButton.setOnAction(e -> removequestionaireWithAnimation(questionaireCard));
        questionaireHeader.getChildren().addAll(numberContainer, spacer, removeButton);

        VBox content = new VBox();
        content.setSpacing(15);

        TextField titleField = new TextField();
        titleField.setPromptText("Título da avaliação");
        titleField.getStyleClass().add("custom-text-field");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Descrição da avaliação e instruções...");
        descriptionArea.setPrefRowCount(3);
        descriptionArea.getStyleClass().add("custom-text-area");

        TextField scoreField = new TextField();
        scoreField.setPromptText("Pontuação máxima");
        scoreField.getStyleClass().add("custom-text-field");
        scoreField.textProperty().addListener((obs, old, newText) -> {
            if (!newText.isEmpty() && !newText.matches("\\d*")) {
                scoreField.setText(old);
            }
        });

        VBox questionsContainer = new VBox(15);
        questionsContainer.getStyleClass().add("questions-container");
        questionsContainer.setUserData(1);

        HBox contextMenu = new HBox();
        Button addDiscursiveButton = new Button("Questão Discursiva");
        addDiscursiveButton.setOnAction(e -> addNewQuestion(questionsContainer, "DISCURSIVE"));
        addDiscursiveButton.getStyleClass().add("simple-button");
        String existingStyles = addDiscursiveButton.getStyle();
        addDiscursiveButton.setStyle(existingStyles + "-fx-max-width: 250;");

        Button addSingleChoiceButton = new Button("Questão Única");
        addSingleChoiceButton.setOnAction(e -> addNewQuestion(questionsContainer, "SINGLE_CHOICE"));
        addSingleChoiceButton.getStyleClass().add("simple-button");
        addSingleChoiceButton.setStyle(existingStyles + "-fx-max-width: 250;");

        Button addMultipleChoiceButton = new Button("Questão Múltipla");
        addMultipleChoiceButton.setOnAction(e -> addNewQuestion(questionsContainer, "MULTIPLE_CHOICE"));
        addMultipleChoiceButton.getStyleClass().add("simple-button");
        addMultipleChoiceButton.setStyle(existingStyles + "-fx-max-width: 250;");

        HBox.setMargin(addMultipleChoiceButton, new javafx.geometry.Insets(0, 0, 0, 20));
        HBox.setMargin(addSingleChoiceButton, new javafx.geometry.Insets(0, 0, 0, 20));
        contextMenu.getChildren().addAll(addDiscursiveButton, addSingleChoiceButton, addMultipleChoiceButton);

        content.getChildren().addAll(
                createFieldWithLabel("Título *", titleField),
                createFieldWithLabel("Descrição *", descriptionArea),
                createFieldWithLabel("Pontuação Total *", scoreField),
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
    private void addNewLesson(VBox lessonsList) {
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
        removeLessonButton.setOnAction(e -> removeLessonWithAnimation(lessonCard));
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
    }

    private VBox createLessonContent(int lessonNumber) {
        VBox content = new VBox();
        content.setSpacing(15);
        content.getStyleClass().add("lesson-content");

        TextField titleField = new TextField();
        titleField.setPromptText("Título da Aula");
        titleField.getStyleClass().add("custom-text-field");

        TextField videoField = new TextField();
        videoField.setPromptText("Link do vídeo da aula");
        videoField.getStyleClass().add("custom-text-field");

        TextArea detailsArea = new TextArea();
        detailsArea.setPromptText("Detalhes e objetivos da aula...");
        detailsArea.setPrefRowCount(3);
        detailsArea.getStyleClass().add("custom-text-area");

        TextArea materialsArea = new TextArea();
        materialsArea.setPromptText("Links para materiais complementares...");
        materialsArea.setPrefRowCount(2);
        materialsArea.getStyleClass().add("custom-text-area");

        TextField durationField = new TextField();
        durationField.setPromptText("Duração (minutos)");
        durationField.getStyleClass().add("custom-text-field");
        durationField.textProperty().addListener((obs, old, newText) -> {
            if (!newText.isEmpty() && !newText.matches("\\d*")) {
                durationField.setText(old);
            }
        });

        content.getChildren().addAll(
                createFieldWithLabel("Título da Aula *", titleField),
                createFieldWithLabel("Link do Vídeo *", videoField),
                createFieldWithLabel("Detalhes da Aula", detailsArea),
                createFieldWithLabel("Materiais Complementares", materialsArea),
                createFieldWithLabel("Duração (minutos) *", durationField));

        return content;
    }

    private VBox createFieldWithLabel(String labelText, Node field) {
        VBox container = new VBox(5);
        Label label = new Label(labelText);
        label.getStyleClass().add("field-label");
        container.getChildren().addAll(label, field);
        return container;
    }

    private void removeLessonWithAnimation(Node lessonCard) {
        VBox lessonsList = (VBox) lessonCard.getParent();
        VBox moduleContent = (VBox) lessonsList.getParent();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), lessonCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            lessonsList.getChildren().remove(lessonCard);
            updateLessonNumbers(lessonsList, moduleContent);
        });
        fadeOut.play();
    }

    private void removequestionaireWithAnimation(Node questionaireCard) {
        VBox lessonsList = (VBox) questionaireCard.getParent();
        VBox moduleContent = (VBox) lessonsList.getParent();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), questionaireCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            lessonsList.getChildren().remove(questionaireCard);
            updatequestionaireNumbers(lessonsList, moduleContent);
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

    private void addNewQuestion(VBox questionsContainer, String type) {
        VBox questionCard = new VBox(10);
        questionCard.getStyleClass().add("question-card");

        HBox questionHeader = new HBox(10);
        questionHeader.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        int questionNumber = (int) questionsContainer.getUserData();
        questionsContainer.setUserData(questionNumber + 1);

        StackPane numberContainer = new StackPane();
        numberContainer.getStyleClass().add("question-number-container");
        Label number = new Label(String.valueOf(questionNumber));
        number.getStyleClass().add("questionaire-number");
        numberContainer.getChildren().add(number);

        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("simple-button");
        removeButton.setOnAction(e -> removeQuestionWithAnimation(questionCard));

        questionHeader.getChildren().addAll(numberContainer, spacer, removeButton);

        VBox questionContent = new VBox(10);
        TextArea questionText = new TextArea();
        questionText.setPromptText("Digite a pergunta da questão...");
        questionText.setPrefRowCount(3);
        questionText.getStyleClass().add("custom-text-area");

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
        scoreHbox.getChildren().addAll(scoreLabel, scoreField);
        questionContent.getChildren().addAll(scoreHbox, questionText);

        switch (type) {
            case "DISCURSIVE":
                addDiscursiveFields(questionContent);
                break;
            case "SINGLE_CHOICE":
                addSingleChoiceFields(questionContent);
                break;
            case "MULTIPLE_CHOICE":
                addMultipleChoiceFields(questionContent);
                break;
        }
        questionCard.getChildren().addAll(questionHeader, questionContent);
        questionsContainer.getChildren().add(questionCard);
    }

    private void removeModuleWithAnimation(Node moduleCard) {
        currentModuleCount--;
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), moduleCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            modulesList.getChildren().remove(moduleCard);
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

        container.getChildren().addAll(
                expectedAnswerLabel,
                expectedAnswer,
                evolutionCriteriaLabel,
                evaluationCriteria);
    }

    private void addSingleChoiceFields(VBox container) {
        VBox optionsContainer = new VBox(5);
        ToggleGroup toggleGroup = new ToggleGroup();

        Button addOptionButton = new Button("+ Adicionar Opção");
        addOptionButton.getStyleClass().add("outline-button");
        addOptionButton.setOnAction(e -> addChoiceOption(optionsContainer, toggleGroup, true));
        Label optionLabel = new Label("Opções (selecione a correta)");
        optionLabel.getStyleClass().add("field-label");

        container.getChildren().addAll(
                optionLabel,
                optionsContainer,
                addOptionButton);

        addChoiceOption(optionsContainer, toggleGroup, true);
        addChoiceOption(optionsContainer, toggleGroup, true);
    }

    private void addMultipleChoiceFields(VBox container) {
        VBox optionsContainer = new VBox(5);

        Button addOptionButton = new Button("+ Adicionar Opção");
        addOptionButton.getStyleClass().add("outline-button");
        addOptionButton.setOnAction(e -> addChoiceOption(optionsContainer, null, false));
        Label optionLabel = new Label("Opções (selecione as corretas)");
        optionLabel.getStyleClass().add("field-label");

        container.getChildren().addAll(
                optionLabel,
                optionsContainer,
                addOptionButton);

        addChoiceOption(optionsContainer, null, false);
        addChoiceOption(optionsContainer, null, false);
    }

    private void addChoiceOption(VBox container, ToggleGroup toggleGroup, boolean singleChoice) {
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

        Button removeOption = new Button("X");
        removeOption.getStyleClass().add("simple-button");
        removeOption.setOnAction(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), optionBox);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> container.getChildren().remove(optionBox));
            fadeOut.play();
        });

        optionBox.getChildren().addAll(selectionControl, optionText, removeOption);

        optionBox.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), optionBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        container.getChildren().add(optionBox);
    }

    private void removeQuestionWithAnimation(Node questionCard) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), questionCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> ((VBox) questionCard.getParent()).getChildren().remove(questionCard));
        fadeOut.play();
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