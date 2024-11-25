package com.view.professor.valid;

import javafx.scene.control.*;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import com.view.login_cadastro.elements.ErrorNotification;
import javafx.scene.Scene;
import java.util.function.Predicate;

public class CursoValid {
    private static final ValidationSupport validationSupport = new ValidationSupport();
    public boolean validationStarted = false;
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MIN_DESCRIPTION_LENGTH = 10;
    private static final int MIN_MODULES = 1;
    private static final int MIN_LESSONS_PER_MODULE = 1;

    @FXML
    private TextField titleCourse;
    @FXML
    private TextArea descritionCourse;
    @FXML
    private ComboBox<String> categoryCourse;
    @FXML
    private ComboBox<String> levelCourse;
    @FXML
    private FlowPane interestContainer;
    @FXML
    private TextField durationTotal;
    @FXML
    private Label titleErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
    private Label categoryErrorLabel;
    @FXML
    private Label levelErrorLabel;
    @FXML
    private Label tagsErrorLabel;
    @FXML
    private Label durationErrorLabel;
    @FXML
    private Label selectedCount;

    public void setupInitialState(TextField titleCourse, TextArea descritionCourse,
            ComboBox<String> categoryCourse, ComboBox<String> levelCourse,
            FlowPane interestContainer, TextField durationTotal,
            Label titleErrorLabel, Label descriptionErrorLabel,
            Label categoryErrorLabel, Label levelErrorLabel,
            Label tagsErrorLabel, Label durationErrorLabel,
            Label selectedCount) {

        this.selectedCount = selectedCount;
        loadValues(titleCourse, descritionCourse, categoryCourse, levelCourse,
                interestContainer, durationTotal, titleErrorLabel, descriptionErrorLabel,
                categoryErrorLabel, levelErrorLabel, tagsErrorLabel, durationErrorLabel);

        validationStarted = false;
        hideAllErrorLabels();
        setupValidators();
        setupTagListeners();
    }

    private void setupValidators() {
        // Manter apenas os listeners para validação em tempo real
        titleCourse.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validationStarted) {
                updateErrorDisplay(titleCourse, titleErrorLabel, 
                    isValidTitle(newValue), 
                    "O título deve ter entre 5 e 100 caracteres");
            }
        });

        descritionCourse.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validationStarted) {
                updateErrorDisplay(descritionCourse, descriptionErrorLabel, 
                    isValidDescription(newValue), 
                    "A descrição deve ter pelo menos 10 caracteres");
            }
        });

        categoryCourse.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (validationStarted) {
                updateErrorDisplay(categoryCourse, categoryErrorLabel, 
                    newValue != null && !newValue.isEmpty(), 
                    "Selecione uma categoria");
            }
        });

        levelCourse.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (validationStarted) {
                updateErrorDisplay(levelCourse, levelErrorLabel, 
                    newValue != null && !newValue.isEmpty(), 
                    "Selecione um nível");
            }
        });

        durationTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validationStarted) {
                updateErrorDisplay(durationTotal, durationErrorLabel, 
                    isValidDuration(newValue), 
                    "Informe uma duração válida em horas");
            }
        });
    }

    private void hideAllErrorLabels() {
        titleErrorLabel.setVisible(false);
        titleErrorLabel.setManaged(false);
        descriptionErrorLabel.setVisible(false);
        descriptionErrorLabel.setManaged(false);
        categoryErrorLabel.setVisible(false);
        categoryErrorLabel.setManaged(false);
        levelErrorLabel.setVisible(false);
        levelErrorLabel.setManaged(false);
        tagsErrorLabel.setVisible(false);
        tagsErrorLabel.setManaged(false);
        durationErrorLabel.setVisible(false);
        durationErrorLabel.setManaged(false);
    }

    private void loadValues(TextField titleCourse, TextArea descritionCourse,
            ComboBox<String> categoryCourse, ComboBox<String> levelCourse,
            FlowPane interestContainer, TextField durationTotal,
            Label titleErrorLabel, Label descriptionErrorLabel,
            Label categoryErrorLabel, Label levelErrorLabel,
            Label tagsErrorLabel, Label durationErrorLabel) {
        this.titleCourse = titleCourse;
        this.descritionCourse = descritionCourse;
        this.categoryCourse = categoryCourse;
        this.levelCourse = levelCourse;
        this.interestContainer = interestContainer;
        this.durationTotal = durationTotal;
        this.titleErrorLabel = titleErrorLabel;
        this.descriptionErrorLabel = descriptionErrorLabel;
        this.categoryErrorLabel = categoryErrorLabel;
        this.levelErrorLabel = levelErrorLabel;
        this.tagsErrorLabel = tagsErrorLabel;
        this.durationErrorLabel = durationErrorLabel;
    }

    private boolean isValidTitle(String title) {
        return title != null && 
               !title.trim().isEmpty() && 
               title.length() >= MIN_TITLE_LENGTH && 
               title.length() <= MAX_TITLE_LENGTH;
    }

    private boolean isValidDescription(String description) {
        return description != null && 
               !description.trim().isEmpty() && 
               description.length() >= MIN_DESCRIPTION_LENGTH;
    }

    private boolean isValidDuration(String duration) {
        if (duration == null || duration.trim().isEmpty()) {
            return false;
        }
        try {
            int hours = Integer.parseInt(duration);
            return hours > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean hasSelectedTags() {
        return interestContainer.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .filter(button -> !button.getText().equals("+ Adicionar"))
                .anyMatch(button -> button.getStyleClass().contains("selected"));
    }

    private void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
        field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
        
        if (errorLabel != null) {
            if (message != null) {
                errorLabel.setText(message);
            }
            
            errorLabel.setVisible(!isValid);
            errorLabel.setManaged(!isValid);
            
            if (!isValid) {
                if (!field.getStyleClass().contains("error-field")) {
                    field.getStyleClass().add("error-field");
                }
            } else {
                field.getStyleClass().remove("error-field");
            }
        }
    }

    private void setupTagListeners() {
        for (Node node : interestContainer.getChildren()) {
            if (node instanceof Button) {
                Button tagButton = (Button) node;
                
                if (!tagButton.getText().equals("+ Adicionar")) {
                    tagButton.setOnAction(e -> {
                        if (tagButton.getStyleClass().contains("selected")) {
                            tagButton.getStyleClass().remove("selected");
                        } else {
                            tagButton.getStyleClass().add("selected");
                        }
                        validateTags();
                        updateSelectedCount();
                    });
                }
            }
        }
    }

    public void validateTags() {
        if (hasSelectedTags()) {
            tagsErrorLabel.setVisible(false);
            tagsErrorLabel.setManaged(false);
            interestContainer.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
        } else {
            tagsErrorLabel.setVisible(true);
            tagsErrorLabel.setManaged(true);
            tagsErrorLabel.setText("Selecione pelo menos uma tag");
            interestContainer.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, true);
        }
    }

    public ValidationSupport getValidationSupport() {
        return validationSupport;
    }

    public boolean validateFields() {
        validationStarted = true;
        boolean isValid = true;

        // Validações dos campos
        boolean titleValid = isValidTitle(titleCourse.getText());
        boolean descriptionValid = isValidDescription(descritionCourse.getText());
        boolean categoryValid = categoryCourse.getValue() != null && !categoryCourse.getValue().isEmpty();
        boolean levelValid = levelCourse.getValue() != null && !levelCourse.getValue().isEmpty();
        boolean durationValid = isValidDuration(durationTotal.getText());
        boolean tagsValid = hasSelectedTags();
        boolean modulesValid = validateModulesAndLessons();

        // Atualizar display de erro para cada campo
        updateErrorDisplay(titleCourse, titleErrorLabel, titleValid, 
            "O título deve ter entre 5 e 100 caracteres");
        updateErrorDisplay(descritionCourse, descriptionErrorLabel, descriptionValid, 
            "A descrição deve ter pelo menos 10 caracteres");
        updateErrorDisplay(categoryCourse, categoryErrorLabel, categoryValid, 
            "Selecione uma categoria");
        updateErrorDisplay(levelCourse, levelErrorLabel, levelValid, 
            "Selecione um nível");
        updateErrorDisplay(durationTotal, durationErrorLabel, durationValid, 
            "Informe uma duração válida em horas");
        
        if (!tagsValid) {
            tagsErrorLabel.setVisible(true);
            tagsErrorLabel.setManaged(true);
            tagsErrorLabel.setText("Selecione pelo menos uma tag");
            interestContainer.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, true);
        }

        // Verificar se todos os campos são válidos
        isValid = titleValid && descriptionValid && categoryValid && 
                  levelValid && durationValid && tagsValid && modulesValid;

        return isValid;
    }

    private boolean validateModulesAndLessons() {
        VBox modulesList = (VBox) interestContainer.getScene().lookup("#modulesList");
        if (modulesList == null || modulesList.getChildren().isEmpty()) {
            showErrorNotification("Adicione pelo menos um módulo!");
            return false;
        }

        boolean hasValidModule = false;

        for (Node moduleNode : modulesList.getChildren()) {
            if (!(moduleNode instanceof VBox)) continue;
            
            validationStarted = true;
            
            VBox moduleCard = (VBox) moduleNode;
            
            // Validação dos campos do módulo
            VBox moduleContent = (VBox) moduleCard.getChildren().get(1);
            
            // Encontra os campos corretamente usando seus containers
            VBox titleContainer = (VBox) moduleContent.getChildren().get(0);
            TextField titleField = (TextField) titleContainer.lookup(".custom-text-field");
            
            VBox durationContainer = (VBox) moduleContent.getChildren().get(1);
            TextField durationField = (TextField) durationContainer.lookup(".custom-text-field");
            
            VBox detailsContainer = (VBox) moduleContent.getChildren().get(2);
            TextArea detailsField = (TextArea) detailsContainer.lookup(".custom-text-area");
            
            boolean moduleFieldsValid = validateModuleFields(titleField, durationField, detailsField);

            // Validação das aulas do módulo
            VBox lessonsList = (VBox) moduleCard.getChildren().get(2);
            if (lessonsList == null || !hasLessons(lessonsList)) {
                HBox moduleHeader = (HBox) moduleCard.getChildren().get(0);
                StackPane numberContainer = (StackPane) moduleHeader.getChildren().get(0);
                Label moduleNumber = (Label) numberContainer.getChildren().get(0);
                
                showErrorNotification("Adicione pelo menos uma aula no módulo " + moduleNumber.getText());
                return false;
            }

            // Validar campos de cada aula
            boolean allLessonsValid = true;
            for (Node node : lessonsList.getChildren()) {
                if (node instanceof VBox && ((VBox) node).getStyleClass().contains("lesson")) {
                    validateLessonFields((VBox) node);
                }
            }

            // Adicionar validação de questionários
            for (Node node : lessonsList.getChildren()) {
                if (node instanceof VBox && ((VBox) node).getChildren().get(1) instanceof VBox) {
                    VBox content = (VBox) ((VBox) node).getChildren().get(1);
                    if (content.getChildren().size() >= 4) { // Verifica se é um questionário
                        boolean questionaireValid = validateQuestionaireFields((VBox) node);
                        if (!questionaireValid) {
                            return false;
                        }
                    }
                }
            }

            if (moduleFieldsValid && allLessonsValid) {
                hasValidModule = true;
            }
        }

        return hasValidModule;
    }

    private boolean hasLessons(VBox lessonsList) {
        return lessonsList.getChildren().stream()
                .filter(node -> node instanceof VBox)
                .filter(node -> ((VBox) node).getStyleClass().contains("lesson"))
                .count() > 0;
    }

    private void validateLessonFields(VBox lessonCard) {
        try {
            VBox lessonContent = (VBox) lessonCard.getChildren().get(1);
            
            // Validação do título
            VBox titleContainer = (VBox) lessonContent.getChildren().get(0);
            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            Label titleErrorLabel = (Label) titleContainer.getChildren().get(2);
            
            boolean titleValid = titleField != null && 
                               !titleField.getText().trim().isEmpty() && 
                               titleField.getText().length() >= 3;
            updateErrorDisplay(titleField, titleErrorLabel, titleValid, 
                "O título da aula deve ter pelo menos 3 caracteres");

            // Validação do link do vídeo
            VBox videoContainer = (VBox) lessonContent.getChildren().get(1);
            TextField videoField = (TextField) videoContainer.getChildren().get(1);
            Label videoErrorLabel = (Label) videoContainer.getChildren().get(2);
            
            boolean videoValid = videoField != null && 
                               !videoField.getText().trim().isEmpty() && 
                               isValidVideoUrl(videoField.getText());
            updateErrorDisplay(videoField, videoErrorLabel, videoValid, 
                "Informe um link de vídeo válido");

            // Validação da descrição
            VBox descriptionContainer = (VBox) lessonContent.getChildren().get(2);
            TextArea detailsArea = (TextArea) descriptionContainer.getChildren().get(1);
            Label detailsErrorLabel = (Label) descriptionContainer.getChildren().get(2);
            
            boolean detailsValid = detailsArea != null && 
                                 !detailsArea.getText().trim().isEmpty() && 
                                 detailsArea.getText().length() >= 10;
            updateErrorDisplay(detailsArea, detailsErrorLabel, detailsValid, 
                "A descrição deve ter pelo menos 10 caracteres");

            // Validação da duração
            VBox durationContainer = (VBox) lessonContent.getChildren().get(3);
            TextField durationField = (TextField) durationContainer.getChildren().get(1);
            Label durationErrorLabel = (Label) durationContainer.getChildren().get(2);
            
            boolean durationValid = durationField != null && 
                                  !durationField.getText().trim().isEmpty() && 
                                  durationField.getText().matches("\\d+");
            updateErrorDisplay(durationField, durationErrorLabel, durationValid, 
                "Informe uma duração válida em minutos");
            
        } catch (Exception e) {
            System.out.println("Erro ao validar campos da aula: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isValidVideoUrl(String url) {
        // Validação básica de URL de vídeo (pode ser expandida conforme necessário)
        return url != null && 
               !url.trim().isEmpty() && 
               (url.contains("youtube.com") || 
                url.contains("youtu.be") || 
                url.contains("vimeo.com"));
    }

    public void setupModuleValidationListeners(VBox moduleContent) {
        try {
            // Busca mais específica pelos campos usando VBox containers
            VBox titleContainer = (VBox) moduleContent.getChildren().get(0);
            VBox durationContainer = (VBox) moduleContent.getChildren().get(1);
            VBox detailsContainer = (VBox) moduleContent.getChildren().get(2);

            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            TextField durationField = (TextField) durationContainer.getChildren().get(1);
            TextArea detailsField = (TextArea) detailsContainer.getChildren().get(1);

            Label titleErrorLabel = (Label) titleContainer.getChildren().get(2);
            Label durationErrorLabel = (Label) durationContainer.getChildren().get(2);
            Label detailsErrorLabel = (Label) detailsContainer.getChildren().get(2);

            // Configura os listeners
            titleField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (validationStarted) {
                    boolean isValid = newVal != null && !newVal.trim().isEmpty() && newVal.length() >= 3;
                    updateErrorDisplay(titleField, titleErrorLabel, isValid, 
                        "O título do módulo deve ter pelo menos 3 caracteres");
                }
            });

            durationField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (validationStarted) {
                    boolean isValid = newVal != null && !newVal.trim().isEmpty() && newVal.matches("\\d+");
                    updateErrorDisplay(durationField, durationErrorLabel, isValid, 
                        "Informe uma duraão válida em horas");
                }
            });

            detailsField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (validationStarted) {
                    boolean isValid = newVal != null && !newVal.trim().isEmpty() && newVal.length() >= 10;
                    updateErrorDisplay(detailsField, detailsErrorLabel, isValid, 
                        "A descrição deve ter pelo menos 10 caracteres");
                }
            });

        } catch (Exception e) {
            System.err.println("Erro ao configurar validação do módulo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupFieldValidation(TextInputControl field, Label errorLabel, 
        Predicate<String> validator, String errorMessage) {
        
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (validationStarted) {
                boolean isValid = validator.test(newVal);
                updateErrorDisplay(field, errorLabel, isValid, errorMessage);
            }
        });
    }

    private void showErrorNotification(String message) {
        Scene scene = interestContainer.getScene();
        StackPane notificationContainer;
        
        if (scene.getRoot() instanceof StackPane) {
            notificationContainer = (StackPane) scene.getRoot();
        } else {
            notificationContainer = new StackPane();
            Node originalRoot = scene.getRoot();
            notificationContainer.getChildren().addAll(originalRoot);
            scene.setRoot(notificationContainer);
        }
        
        ErrorNotification notification = new ErrorNotification(notificationContainer, message);
        notification.show();
    }

    public void addNewTag(String tagName) {
        if (tagName != null && !tagName.trim().isEmpty()) {
            Button newTagButton = new Button(tagName.trim());
            newTagButton.getStyleClass().add("interest-button");
            
            newTagButton.setOnAction(e -> {
                if (newTagButton.getStyleClass().contains("selected")) {
                    newTagButton.getStyleClass().remove("selected");
                } else {
                    newTagButton.getStyleClass().add("selected");
                }
                validateTags();
            });

            interestContainer.getChildren().add(
                interestContainer.getChildren().size() - 1, 
                newTagButton
            );
            
            validateTags();
        }
    }

    public boolean isValidationStarted() {
        return validationStarted;
    }

    private void updateSelectedCount() {
        int count = (int) interestContainer.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .filter(button -> !button.getText().equals("+ Adicionar"))
                .filter(button -> button.getStyleClass().contains("selected"))
                .count();
        
        selectedCount.setText(count + " tag" + (count != 1 ? "s" : "") + " selecionada" + (count != 1 ? "s" : ""));
    }

    public boolean validateModuleFields(TextField titleField, TextField durationField, TextArea detailsField) {
        if (validationStarted) {
            boolean titleValid = titleField.getText() != null && 
                               !titleField.getText().trim().isEmpty() &&
                               titleField.getText().length() >= 3;
            
            boolean durationValid = durationField.getText() != null && 
                                  !durationField.getText().trim().isEmpty() &&
                                  durationField.getText().matches("\\d+");
            
            boolean detailsValid = detailsField.getText() != null && 
                                 !detailsField.getText().trim().isEmpty() &&
                                 detailsField.getText().length() >= 10;

            updateErrorDisplay(titleField, 
                (Label)titleField.getParent().lookup(".error-label"), 
                titleValid, 
                "O título do módulo deve ter pelo menos 3 caracteres");

            updateErrorDisplay(durationField, 
                (Label)durationField.getParent().lookup(".error-label"), 
                durationValid, 
                "Informe uma duração válida em horas");

            updateErrorDisplay(detailsField, 
                (Label)detailsField.getParent().lookup(".error-label"), 
                detailsValid, 
                "A descrição deve ter pelo menos 10 caracteres");

            return titleValid && durationValid && detailsValid;
        }
        return false;
    }

    public void setupLessonValidationListeners(VBox lessonContent) {
        try {
            // Busca mais específica pelos campos
            VBox titleContainer = (VBox) lessonContent.getChildren().get(0);
            VBox videoContainer = (VBox) lessonContent.getChildren().get(1);
            VBox descriptionContainer = (VBox) lessonContent.getChildren().get(2);
            VBox durationContainer = (VBox) lessonContent.getChildren().get(3);

            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            TextField videoField = (TextField) videoContainer.getChildren().get(1);
            TextArea descriptionField = (TextArea) descriptionContainer.getChildren().get(1);
            TextField durationField = (TextField) durationContainer.getChildren().get(1);

            Label titleErrorLabel = (Label) titleContainer.getChildren().get(2);
            Label videoErrorLabel = (Label) videoContainer.getChildren().get(2);
            Label descriptionErrorLabel = (Label) descriptionContainer.getChildren().get(2);
            Label durationErrorLabel = (Label) durationContainer.getChildren().get(2);

            // Configura os listeners para cada campo
            setupFieldValidation(titleField, titleErrorLabel,
                text -> text != null && !text.trim().isEmpty() && text.length() >= 3,
                "O título da aula deve ter pelo menos 3 caracteres");

            setupFieldValidation(videoField, videoErrorLabel,
                this::isValidVideoUrl,
                "Informe um link de vídeo válido");

            setupFieldValidation(descriptionField, descriptionErrorLabel,
                text -> text != null && !text.trim().isEmpty() && text.length() >= 10,
                "A descrição deve ter pelo menos 10 caracteres");

            setupFieldValidation(durationField, durationErrorLabel,
                text -> text != null && !text.trim().isEmpty() && text.matches("\\d+"),
                "Informe uma duração válida em minutos");

        } catch (Exception e) {
            System.err.println("Erro ao configurar listeners da aula: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean validateQuestionaireFields(VBox questionaireCard) {
        try {
            VBox content = (VBox) questionaireCard.getChildren().get(1);
            
            // Validação do título
            VBox titleContainer = (VBox) content.getChildren().get(0);
            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            Label titleErrorLabel = (Label) titleContainer.getChildren().get(2);
            
            // Validação da descrição
            VBox descriptionContainer = (VBox) content.getChildren().get(1);
            TextArea descriptionField = (TextArea) descriptionContainer.getChildren().get(1);
            Label descriptionErrorLabel = (Label) descriptionContainer.getChildren().get(2);
            
            // Validação da pontuação
            VBox scoreContainer = (VBox) content.getChildren().get(2);
            TextField scoreField = (TextField) scoreContainer.getChildren().get(1);
            Label scoreErrorLabel = (Label) scoreContainer.getChildren().get(2);

            boolean titleValid = titleField.getText() != null && 
                               !titleField.getText().trim().isEmpty() && 
                               titleField.getText().length() >= 3;
                               
            boolean descriptionValid = descriptionField.getText() != null && 
                                     !descriptionField.getText().trim().isEmpty() && 
                                     descriptionField.getText().length() >= 10;
                                     
            boolean scoreValid = scoreField.getText() != null && 
                               !scoreField.getText().trim().isEmpty() && 
                               scoreField.getText().matches("\\d+") && 
                               Integer.parseInt(scoreField.getText().trim()) > 0;

            updateErrorDisplay(titleField, titleErrorLabel, titleValid, 
                "O título da avaliação deve ter pelo menos 3 caracteres");
            updateErrorDisplay(descriptionField, descriptionErrorLabel, descriptionValid, 
                "A descrição deve ter pelo menos 10 caracteres");
            updateErrorDisplay(scoreField, scoreErrorLabel, scoreValid, 
                "Informe uma pontuação válida maior que zero");

            return titleValid && descriptionValid && scoreValid;
            
        } catch (Exception e) {
            System.out.println("Erro ao validar questionário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void setValidationStarted(boolean value) {
        this.validationStarted = value;
    }

    public void setupQuestionaireValidationListeners(VBox questionaireCard) {
        try {
            // Obtém o conteúdo do questionário
            VBox content = (VBox) questionaireCard.getChildren().get(1);
            
            // Título
            VBox titleContainer = (VBox) content.getChildren().get(0);
            TextField titleField = (TextField) titleContainer.getChildren().get(1);
            Label titleErrorLabel = new Label();
            titleErrorLabel.getStyleClass().add("error-label");
            titleErrorLabel.setVisible(false);
            titleErrorLabel.setManaged(false);
            titleContainer.getChildren().add(titleErrorLabel);

            // Descrição
            VBox descriptionContainer = (VBox) content.getChildren().get(1);
            TextArea descriptionField = (TextArea) descriptionContainer.getChildren().get(1);
            Label descriptionErrorLabel = new Label();
            descriptionErrorLabel.getStyleClass().add("error-label");
            descriptionErrorLabel.setVisible(false);
            descriptionErrorLabel.setManaged(false);
            descriptionContainer.getChildren().add(descriptionErrorLabel);

            // Pontuação
            VBox scoreContainer = (VBox) content.getChildren().get(2);
            TextField scoreField = (TextField) scoreContainer.getChildren().get(1);
            Label scoreErrorLabel = new Label();
            scoreErrorLabel.getStyleClass().add("error-label");
            scoreErrorLabel.setVisible(false);
            scoreErrorLabel.setManaged(false);
            scoreContainer.getChildren().add(scoreErrorLabel);

            // Adiciona os listeners
            titleField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (validationStarted) {
                    boolean isValid = newVal != null && !newVal.trim().isEmpty() && newVal.length() >= 3;
                    updateErrorDisplay(titleField, titleErrorLabel, isValid, 
                        "O título da avaliação deve ter pelo menos 3 caracteres");
                }
            });

            descriptionField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (validationStarted) {
                    boolean isValid = newVal != null && !newVal.trim().isEmpty() && newVal.length() >= 10;
                    updateErrorDisplay(descriptionField, descriptionErrorLabel, isValid, 
                        "A descrição deve ter pelo menos 10 caracteres");
                }
            });

            scoreField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (validationStarted) {
                    boolean isValid = newVal != null && !newVal.trim().isEmpty() && 
                                    newVal.matches("\\d+") && 
                                    Integer.parseInt(newVal.trim()) > 0;
                    updateErrorDisplay(scoreField, scoreErrorLabel, isValid, 
                        "Informe uma pontuação válida maior que zero");
                }
            });

        } catch (Exception e) {
            System.out.println("Erro ao configurar validação do questionário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void validateQuestionaireField(Control field, String errorMessage) {
        if (field instanceof TextInputControl) {
            TextInputControl textField = (TextInputControl) field;
            if (textField.getText().trim().isEmpty()) {
                field.getStyleClass().add("error");
            } else {
                field.getStyleClass().remove("error");
            }
        }
    }
}
