package com.view.professor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.util.Duration;

import com.controller.professor.CadastroCursoController;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import javafx.scene.layout.Region;

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
    private Button addLessonButton01;

    private Set<String> selectedInterests = new HashSet<>();

    private String title;

    private File selectedFile;

    private int currentModuleCount = 0;
    private static final String DEFAULT_MODULE_TITLE = "Novo Módulo";
    private static final String DEFAULT_LESSON_TITLE = "Nova Aula";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkTitle();
        loadComboBoxCategory();
        loadComboBoxLevel();
        setupInterestButtons();

        uploadButton.setOnAction(event -> uploadImage());

        VBox lessonsList = new VBox();
        lessonsList.setSpacing(10);
        lessonsList.getStyleClass().add("lessons-list");

        modulesList.setSpacing(20);
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
        String title = titleCourse.getText();
        String descrition = descritionCourse.getText();
        String category = categoryCourse.getValue();
        String level = levelCourse.getValue();
        String tags = String.join(". ", getSelectedInterests());

        System.out.println("Título: " + title);
        System.out.println("Descrição: " + descrition);
        System.out.println("Categoria: " + category);
        System.out.println("Nível: " + level);
        System.out.println("Tags: " + tags);

        new CadastroCursoController(titleCourse.getText(), descritionCourse.getText(), categoryCourse.getValue(),
                levelCourse.getValue(), String.join(". ", getSelectedInterests()), selectedFile);
    }

    @FXML
    private void addNewModule() {
        currentModuleCount++;

        VBox moduleCard = new VBox();
        moduleCard.getStyleClass().addAll("module-card", "fade-in");
        moduleCard.setSpacing(15);

        // Header do módulo
        HBox moduleHeader = createModuleHeader(currentModuleCount);

        // Conteúdo do módulo
        VBox moduleContent = createModuleContent(currentModuleCount);

        // Lista de aulas
        VBox lessonsList = createLessonsList();

        moduleCard.getChildren().addAll(moduleHeader, moduleContent, lessonsList);

        // Animação de entrada
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

        // Número do módulo com ícone
        StackPane numberContainer = new StackPane();
        numberContainer.getStyleClass().add("module-number-container");
        Label number = new Label(String.valueOf(moduleNumber));
        number.getStyleClass().add("module-number");
        numberContainer.getChildren().add(number);

        // Botão de remover com tooltip
        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("outline-button");
       
        Tooltip.install(removeButton, new Tooltip("Remover módulo"));
        removeButton.setOnAction(e -> removeModuleWithAnimation(header.getParent()));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(numberContainer,  spacer, removeButton);
        return header;
    }

    private VBox createModuleContent(int moduleNumber) {
        VBox content = new VBox();
        content.setSpacing(15);
        content.getStyleClass().add("module-content");

        // Campo de título com contador de caracteres
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

        // Container para duração e ordem
        HBox detailsContainer = new HBox(15);
        detailsContainer.getChildren().addAll(
                createNumberField("Duração (horas) *", "Ex: 2.5", true));

        // Área de descrição
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

        // Validação de números
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
    
        // Conteúdo da aula
        VBox lessonContent = createLessonContent(lessonNumber);
    
        lessonCard.getChildren().addAll(lessonHeader, lessonContent);
    
        // Animação de entrada
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

        // Título da aula
        TextField titleField = new TextField();
        titleField.setPromptText(DEFAULT_LESSON_TITLE + " " + lessonNumber);
        titleField.getStyleClass().add("custom-text-field");

        // Link do vídeo
        TextField videoField = new TextField();
        videoField.setPromptText("Link do vídeo da aula");
        videoField.getStyleClass().add("custom-text-field");

        // Detalhes da aula
        TextArea detailsArea = new TextArea();
        detailsArea.setPromptText("Detalhes e objetivos da aula...");
        detailsArea.setPrefRowCount(3);
        detailsArea.getStyleClass().add("custom-text-area");

        // Materiais complementares
        TextArea materialsArea = new TextArea();
        materialsArea.setPromptText("Links para materiais complementares...");
        materialsArea.setPrefRowCount(2);
        materialsArea.getStyleClass().add("custom-text-area");

        // Duração da aula
        TextField durationField = new TextField();
        durationField.setPromptText("Duração (minutos)");
        durationField.getStyleClass().add("custom-text-field");

        // Validação para aceitar apenas números
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
}