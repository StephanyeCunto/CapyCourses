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
import com.view.elements.Calendario;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    private Set<String> selectedInterests = new HashSet<>();

    private String title;

    private File selectedFile;

    private Calendario dateInputPopupStart = new Calendario();
    private Calendario dateInputPopupEnd = new Calendario();

    private int currentModuleCount = 0;
    private static final String DEFAULT_MODULE_TITLE = "Novo Módulo";
    private static final String DEFAULT_LESSON_TITLE = "Nova Aula";

    private VBox dateContainerStart = new VBox(5);
    private VBox dateContainerEnd = new VBox(5);

    private String valueComBox = "Visível";
    private LocalDate dateCurrent = dateInputPopupStart.getLocalDate();
    private boolean isImage = false;
    private int isTag = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkTitle();
        loadComboBoxCategory();
        loadComboBoxLevel();
        loadComBoxVisibily(valueComBox);
        setupInterestButtons();
        addDateInputField();

        uploadButton.setOnAction(event -> uploadImage());

        VBox lessonsList = new VBox();
        lessonsList.setSpacing(10);
        lessonsList.getStyleClass().add("lessons-list");

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
    }

    private static final String STEP_PENDING = "step-pending";
    private static final String STEP_CURRENT = "step-current";
    private static final String STEP_COMPLETED = "step-completed";
    private static final int MIN_MODULES = 1;
    private static final int MIN_LESSONS_PER_MODULE = 1;
    
    private void registrationProgress() {
        List<Map<String, Object>> modulesData = saveModulesAndLessonsData();
        double percentage = calculateTotalProgress(modulesData);
        
        updateProgressDisplay(percentage);
        updateAllLabels(modulesData);
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
        if (isImage) completed++;
        if (isTag > 0) completed++;
        
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
            if (isFieldComplete(field)) completed++;
        }
        if (isTag > 0) completed++;
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
            if (isFieldComplete(field)) completed++;
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
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif"));

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
                saveModulesAndLessonsData(), dateInputPopupStart.getLocalDate(), dateInputPopupEnd.getLocalDate(),
                durationTotal.getText(), dateEnd.isSelected(), isCertificate.isSelected(), isGradeMiniun.isSelected(),
                ComboBoxVisibily.getValue());
    }

    public List<Map<String, Object>> saveModulesAndLessonsData() {
        List<Map<String, Object>> modulesData = new ArrayList<>();

        for (Node moduleNode : modulesList.getChildren()) {
            if (moduleNode instanceof VBox) {
                VBox moduleCard = (VBox) moduleNode;
                Map<String, Object> moduleData = new HashMap<>();
                HBox moduleHeader = (HBox) moduleCard.getChildren().get(0);
                StackPane numberContainer = (StackPane) moduleHeader.getChildren().get(0);
                Label moduleNumber = (Label) numberContainer.getChildren().get(0);
                moduleData.put("moduleNumber", Integer.parseInt(moduleNumber.getText()));
                VBox moduleContent = (VBox) moduleCard.getChildren().get(1);
                VBox titleContainer = (VBox) moduleContent.getChildren().get(0);
                HBox titleBox = (HBox) titleContainer.getChildren().get(1);
                TextField titleField = (TextField) titleBox.getChildren().get(0);
                moduleData.put("moduleTitle", titleField.getText());
                HBox detailsContainer = (HBox) moduleContent.getChildren().get(1);
                VBox durationContainer = (VBox) detailsContainer.getChildren().get(0);
                TextField durationField = (TextField) durationContainer.getChildren().get(1);
                moduleData.put("moduleDuration", durationField.getText());
                VBox descriptionContainer = (VBox) moduleContent.getChildren().get(2);
                TextArea descriptionArea = (TextArea) descriptionContainer.getChildren().get(1);
                moduleData.put("moduleDescription", descriptionArea.getText());
                VBox lessonsList = (VBox) moduleCard.getChildren().get(2);
                List<Map<String, Object>> lessonsData = new ArrayList<>();
                for (int i = 0; i < lessonsList.getChildren().size() - 1; i++) {
                    Node lessonNode = lessonsList.getChildren().get(i);
                    if (lessonNode instanceof VBox) {
                        VBox lessonCard = (VBox) lessonNode;
                        Map<String, Object> lessonData = new HashMap<>();
                        HBox lessonHeader = (HBox) lessonCard.getChildren().get(0);
                        StackPane lessonNumberContainer = (StackPane) lessonHeader.getChildren().get(0);
                        Label lessonNumber = (Label) lessonNumberContainer.getChildren().get(0);
                        lessonData.put("lessonNumber", Integer.parseInt(lessonNumber.getText()));
                        VBox lessonContent = (VBox) lessonCard.getChildren().get(1);
                        VBox titleFieldAula = (VBox) lessonContent.getChildren().get(0);
                        TextField lessonTitleField = (TextField) titleFieldAula.getChildren().get(1);
                        lessonData.put("lessonTitle", lessonTitleField.getText());
                        VBox videoField = (VBox) lessonContent.getChildren().get(1);
                        TextField lessonVideoField = (TextField) videoField.getChildren().get(1);
                        lessonData.put("lessonVideoLink", lessonVideoField.getText());
                        VBox detailsField = (VBox) lessonContent.getChildren().get(2);
                        TextArea lessonDetailsArea = (TextArea) detailsField.getChildren().get(1);
                        lessonData.put("lessonDetails", lessonDetailsArea.getText());
                        VBox materialsField = (VBox) lessonContent.getChildren().get(3);
                        TextArea lessonMaterialsArea = (TextArea) materialsField.getChildren().get(1);
                        lessonData.put("lessonMaterials", lessonMaterialsArea.getText());
                        VBox durationFieldAula = (VBox) lessonContent.getChildren().get(4);
                        TextField lessonDurationField = (TextField) durationFieldAula.getChildren().get(1);
                        lessonData.put("lessonDuration", lessonDurationField.getText());

                        lessonsData.add(lessonData);
                    }
                }
                moduleData.put("lessons", lessonsData);
                modulesData.add(moduleData);
            }
        }

        return modulesData;
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
        titleField.setPromptText(DEFAULT_MODULE_TITLE + " " + moduleNumber);
        titleField.getStyleClass().add("custom-text-field");
        titleField.setPrefWidth(600);

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
        lessonsList.setSpacing(15);
        lessonsList.getStyleClass().add("lessons-list");

        Button addLessonButton = new Button("+ Adicionar Aula");
        addLessonButton.getStyleClass().add("modern-button");
        addLessonButton.setOnAction(e -> addNewLesson(lessonsList));

        lessonsList.getChildren().add(addLessonButton);
        return lessonsList;
    }

    @FXML
    private void addNewLesson(VBox lessonsList) {
        int lessonNumber = lessonsList.getChildren().size();

        VBox lessonCard = new VBox();
        lessonCard.getStyleClass().addAll("lesson-card", "fade-in");
        lessonCard.setSpacing(15);
        lessonCard.getStyleClass().add("module-header");

        HBox lessonHeader = new HBox();
        lessonHeader.setAlignment(Pos.CENTER_LEFT);
        lessonHeader.setSpacing(10);
        lessonHeader.getStyleClass().add("lesson-header");

        StackPane numberContainer = new StackPane();
        numberContainer.getStyleClass().add("lesson-number-container");
        Label lessonNumberLabel = new Label(String.valueOf(lessonNumber));
        lessonNumberLabel.getStyleClass().add("lesson-number");
        numberContainer.getChildren().add(lessonNumberLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button removeLessonButton = new Button("X");
        removeLessonButton.getStyleClass().add("outline-button");
        Tooltip.install(removeLessonButton, new Tooltip("Remover aula"));
        removeLessonButton.setOnAction(e -> removeLessonWithAnimation(lessonCard));
        lessonHeader.getChildren().addAll(numberContainer, spacer, removeLessonButton);
        VBox lessonContent = createLessonContent(lessonNumber);
        lessonCard.getChildren().addAll(lessonHeader, lessonContent);
        lessonCard.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), lessonCard);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        lessonsList.getChildren().add(lessonsList.getChildren().size() - 1, lessonCard);
        updateLessonNumbers(lessonsList);
    }

    private void updateLessonNumbers(VBox lessonsList) {
        int lessonCount = 1;
        for (Node node : lessonsList.getChildren()) {
            if (node instanceof VBox) {
                VBox lessonCard = (VBox) node;
                HBox header = (HBox) lessonCard.getChildren().get(0);
                StackPane numberContainer = (StackPane) header.getChildren().get(0);
                Label numberLabel = (Label) numberContainer.getChildren().get(0);
                numberLabel.setText(String.valueOf(lessonCount++));
            }
        }
    }

    private VBox createLessonContent(int lessonNumber) {
        VBox content = new VBox();
        content.setSpacing(15);
        content.getStyleClass().add("lesson-content");
        TextField titleField = new TextField();
        titleField.setPromptText(DEFAULT_LESSON_TITLE + " " + lessonNumber);
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

    private void removeLessonWithAnimation(Node lessonCard) {
        VBox lessonsList = (VBox) lessonCard.getParent();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), lessonCard);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            lessonsList.getChildren().remove(lessonCard);
            updateLessonNumbers(lessonsList);
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

    protected void addDateInputField() {
        Label dateStart = new Label();
        Label dateEnd = new Label();

        dateStart.setText("Data de início");
        dateInputPopupStart.setMinDate(LocalDate.now());
        dateContainerStart.getChildren().add(dateInputPopupStart.getDateInputField());
        dateEnd.setText("Data do fim");
        dateInputPopupEnd.setMinDate(LocalDate.now().plusDays(1));
        dateContainerEnd.getChildren().add(dateInputPopupEnd.getDateInputField());
        dateContainerStart.setStyle("-fx-background-color:rgba(108,99,255,0.2);");
        dateContainerEnd.setStyle("-fx-background-color:rgba(108,99,255,0.2);");

        dateContainerStart.setOnMouseEntered(e -> dateContainerStart.setStyle(
                "-fx-background-color: rgba(108,99,255,0.5); -fx-background-radius: 8; -fx-border-radius: 8;"));
        dateContainerEnd.setOnMouseEntered(e -> dateContainerEnd.setStyle(
                "-fx-background-color: rgba(108,99,255,0.5); -fx-background-radius: 8; -fx-border-radius: 8;"));

        dateContainerStart.setOnMouseExited(e -> dateContainerStart.setStyle(
                "-fx-background-color:rgba(108,99,255,0.2); -fx-background-radius: 8; -fx-border-radius: 8;"));
        dateContainerEnd.setOnMouseExited(e -> dateContainerEnd.setStyle(
                "-fx-background-color:rgba(108,99,255,0.2); -fx-background-radius: 8; -fx-border-radius: 8;"));

        date.getChildren().addAll(dateStart, dateContainerStart, dateEnd, dateContainerEnd);
    }
}