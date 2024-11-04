package com.view.professor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.controller.professor.CadastroCursoController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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

        addLessonButton01.setOnAction(e -> addNewLesson(lessonsList));
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
        VBox moduleCard = new VBox();
        moduleCard.getStyleClass().add("module-card");
        moduleCard.setSpacing(15);

        HBox moduleHeader = new HBox();
        moduleHeader.setSpacing(15);

        Label moduleNumber = new Label(String.valueOf(modulesList.getChildren().size() + 1));
        moduleNumber.getStyleClass().add("module-number");

        VBox moduleContent = new VBox();
        moduleContent.setSpacing(15);

        TextField titleField = new TextField();
        titleField.setPromptText("Título do Módulo");
        titleField.getStyleClass().add("custom-text-field");

        HBox durationOrderContainer = new HBox(15);
        
        VBox durationBox = new VBox(8);
        Label durationLabel = new Label("Duração (horas)");
        durationLabel.getStyleClass().add("field-label");
        TextField durationField = new TextField();
        durationField.setPromptText("Ex: 2.5");
        durationField.getStyleClass().add("custom-text-field");
        durationBox.getChildren().addAll(durationLabel, durationField);
        
        VBox orderBox = new VBox(8);
        Label orderLabel = new Label("Ordem");
        orderLabel.getStyleClass().add("field-label");
        TextField orderField = new TextField(String.valueOf(modulesList.getChildren().size() + 1));
        orderField.getStyleClass().add("custom-text-field");
        orderBox.getChildren().addAll(orderLabel, orderField);
        
        durationOrderContainer.getChildren().addAll(durationBox, orderBox);
        
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Descrição do módulo...");
        descriptionArea.setPrefRowCount(3);
        descriptionArea.getStyleClass().add("custom-text-area");

        moduleContent.getChildren().addAll(titleField, durationOrderContainer, descriptionArea);

        Button removeModuleButton = new Button("X");
        removeModuleButton.getStyleClass().add("outline-button");
        removeModuleButton.setOnAction(e -> removeModule(moduleCard));

        moduleHeader.getChildren().addAll(moduleNumber, moduleContent, removeModuleButton);
        moduleCard.getChildren().add(moduleHeader);

        VBox lessonsList = new VBox();
        lessonsList.setSpacing(10);
        lessonsList.getStyleClass().add("lessons-list");

        Button addLessonButton = new Button("+ Adicionar Aula");
        addLessonButton.getStyleClass().add("modern-button");
        addLessonButton.setOnAction(e -> addNewLesson(lessonsList));

        lessonsList.getChildren().add(addLessonButton);
        moduleCard.getChildren().add(lessonsList);

        modulesList.getChildren().add(moduleCard);
    }

    private int cout=0;

    private void addNewLesson(VBox lessonsList) {
        VBox lessonCard = new VBox();
        lessonCard.setSpacing(10);
        
        HBox lessonHeader = new HBox();
        lessonHeader.setStyle("-fx-background-color: #444;"); 
        lessonHeader.setSpacing(10);
        lessonHeader.setPrefHeight(30); 
        Label lessonNumber;
        if(cout==0){
            lessonNumber = new Label("1");
        }else{
            lessonNumber = new Label(String.valueOf(lessonsList.getChildren().size()));
        }

        lessonNumber.setTextFill(javafx.scene.paint.Color.WHITE); 
        lessonHeader.getChildren().add(lessonNumber);
    
        TextField titleField = new TextField();
        titleField.setPromptText("Título da Aula");
        titleField.getStyleClass().add("custom-text-field");
    
        TextField videoField = new TextField();
        videoField.setPromptText("Link do vídeo");
        videoField.getStyleClass().add("custom-text-field");
    
        TextArea detailsArea = new TextArea();
        detailsArea.setPromptText("Detalhes da aula...");
        detailsArea.setPrefRowCount(3);
        detailsArea.getStyleClass().add("custom-text-area");
    
        TextArea materialsArea = new TextArea();
        materialsArea.setPromptText("Materiais da aula...");
        materialsArea.setPrefRowCount(3);
        materialsArea.getStyleClass().add("custom-text-area");
    
        TextField durationField = new TextField();
        durationField.setPromptText("Duração (horas)");
        durationField.getStyleClass().add("custom-text-field");
    
        Button removeLessonButton = new Button("X");
        removeLessonButton.getStyleClass().add("outline-button");
        removeLessonButton.setOnAction(e -> lessonsList.getChildren().remove(lessonCard));
    
        lessonCard.getChildren().addAll(lessonHeader, titleField, videoField, detailsArea, materialsArea, durationField, removeLessonButton);
        if(cout==0){
            lessonsList.getChildren().add(0, lessonCard); 
            cout=1;
        }else{  
            lessonsList.getChildren().add(lessonsList.getChildren().size() - 1, lessonCard); 
        }
      
    }
    

    private void removeModule(VBox moduleCard) {
        modulesList.getChildren().remove(moduleCard);
        updateModuleNumbers();
    }

    private void updateModuleNumbers() {
        for (int i = 0; i < modulesList.getChildren().size(); i++) {
            VBox moduleCard = (VBox) modulesList.getChildren().get(i);
            HBox moduleHeader = (HBox) moduleCard.getChildren().get(0);
            Label moduleNumber = (Label) moduleHeader.getChildren().get(0);
            moduleNumber.setText(String.valueOf(i + 1));
        }
    }
}