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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkTitle();
        loadComboBoxCategory();
        loadComboBoxLevel();
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

        

        ComboBoxVisibily.setOnMouseEntered(event -> {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = today.format(formatter);

            if (!dateInputPopupStart.getDate().equals(formattedDate)) {
                valueComBox = "Visível,Somente na data de início";
            } else {
                valueComBox = "Visível";
            }
            loadComBoxVisibily(valueComBox);
        });
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
        } else {
            selectedInterests.add(interest);
            button.getStyleClass().add("selected");
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
                saveModulesAndLessonsData());
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
        dateContainerStart.getChildren().add(dateInputPopupStart.getDateInputField());
        dateEnd.setText("Data do fim");
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