package com;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Node;

public class TrelloBoard extends Application {

    private static final DataFormat CARD_FORMAT = new DataFormat("application/x-lesson");
    private static final DataFormat COLUMN_FORMAT = new DataFormat("application/x-module");
    private HBox board;
    private VBox draggedModule = null;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // Barra superior com botão e informações
        HBox topBar = new HBox(10);
        topBar.getStyleClass().add("top-bar");
        Button addModuleButton = new Button("+ Adicionar Modulo");
        addModuleButton.setOnAction(e -> showNewModuleDialog()); // Ação do botão
        Label overallProgressLabel = new Label("Progresso Geral: 75%");
        ProgressBar overallProgressBar = new ProgressBar(0.75);
        overallProgressBar.getStyleClass().add("overall-progress-bar");
        topBar.getChildren().addAll(addModuleButton, overallProgressLabel, overallProgressBar);

        // Criação do painel principal (Quadro Kanban)
        board = new HBox(20);
        board.setPadding(new Insets(15, 0, 0, 0));
        board.setSpacing(20); // Garantir espaçamento entre os módulos

        // Adicionando módulos
        board.getChildren().addAll(
                createModule("Módulo 1", "Descrição do Módulo 1", "49", 3, 80.0),
                createModule("Módulo 2", "Descrição do Módulo 2", "10", 5, 50.0),
                createModule("Módulo 3", "Descrição do Módulo 3", "3", 4, 60.0));

        // Definindo o estilo para o quadro Kanban
        board.getStyleClass().add("board");

        // Scroll pane para o quadro
        ScrollPane scrollPane = new ScrollPane(board);
        scrollPane.setFitToWidth(true); // Ajusta a largura ao conteúdo
        scrollPane.getStyleClass().add("scroll-pane");

        // Composição final da interface
        root.getChildren().addAll(topBar, scrollPane);

        // Definindo a cena e o estilo
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Quadro Kanban");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createModule(String title, String description, String duration, int lessonCount,
            double progress) {
        VBox module = new VBox(10);
        module.setPrefWidth(300);
        module.setPadding(new Insets(10));
        module.getStyleClass().add("module");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("module-header");

        TextField titleField = new TextField(title);
        titleField.setBorder(null);
        titleField.getStyleClass().add("module-title");

        MenuButton menuButton = new MenuButton("⋮");
        menuButton.getStyleClass().add("module-menu");

        MenuItem editItem = new MenuItem("Editar");
        editItem.setOnAction(e -> titleField.requestFocus());

        MenuItem deleteItem = new MenuItem("Excluir");
        deleteItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar exclusão");
            alert.setHeaderText("Excluir módulo");
            alert.setContentText("Tem certeza que deseja excluir este módulo e todas as suas aulas?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    board.getChildren().remove(module);
                }
            });
        });

        menuButton.getItems().addAll(editItem, deleteItem);
        header.getChildren().addAll(titleField, menuButton);

        // Módulo detalhes
        VBox detailsBox = new VBox(5);
        detailsBox.getStyleClass().add("module-details");

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);

        HBox datesBox = new HBox(10);

        HBox progressBox = new HBox(10, new Label("Aulas: " + lessonCount), new ProgressBar(progress / 100));
        progressBox.setAlignment(Pos.CENTER_LEFT);

        detailsBox.getChildren().addAll(descriptionLabel, datesBox, progressBox);

        VBox lessonsArea = new VBox(10);
        lessonsArea.setPadding(new Insets(10, 0, 10, 0));
        lessonsArea.getStyleClass().add("lessons-area");

        // Configure lesson drag and drop
        lessonsArea.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof VBox &&
                    event.getDragboard().hasContent(CARD_FORMAT) &&
                    event.getGestureSource() != lessonsArea) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        lessonsArea.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasContent(CARD_FORMAT)) {
                VBox lesson = (VBox) event.getGestureSource(); // O aula que está sendo arrastado
                VBox sourceLessonsArea = (VBox) lesson.getParent(); // Área de cartões da modulo original
                sourceLessonsArea.getChildren().remove(lesson); // Remove o aula da modulo original

                // Adiciona o aula arrastado na posição correta da nova modulo
                int targetIndex = getDropTargetIndex(lessonsArea, event.getY());
                lessonsArea.getChildren().add(targetIndex, lesson);
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        Button addLessonButton = new Button("+ Adicionar Aula");
        addLessonButton.getStyleClass().add("add-lesson-button");
        addLessonButton.setMaxWidth(Double.MAX_VALUE);
        addLessonButton.setOnAction(e -> showNewLessonDialog(lessonsArea));

        module.getChildren().addAll(header, detailsBox, lessonsArea, addLessonButton);

        // Configure module drop
        module.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof VBox &&
                    event.getDragboard().hasContent(COLUMN_FORMAT) &&
                    event.getGestureSource() != module) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        module.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasContent(COLUMN_FORMAT) && draggedModule != null) {
                int draggedIdx = board.getChildren().indexOf(draggedModule);
                int thisIdx = board.getChildren().indexOf(module);

                if (draggedIdx >= 0 && thisIdx >= 0) {
                    board.getChildren().remove(draggedModule);
                    board.getChildren().add(thisIdx, draggedModule);
                    success = true;
                }
            }

            event.setDropCompleted(success);
            draggedModule = null;
            event.consume();
        });

        return module;
    }

    // Função para determinar a posição de drop dentro da lista de cartões
    private int getDropTargetIndex(VBox lessonsArea, double mouseY) {
        int index = 0;
        for (Node child : lessonsArea.getChildren()) {
            if (child instanceof VBox) {
                VBox lesson = (VBox) child;
                if (mouseY < lesson.getLayoutY() + lesson.getHeight() / 2) {
                    break;
                }
                index++;
            }
        }
        return index;
    }

    private void showNewModuleDialog() {
        Dialog<ModuleDetails> dialog = new Dialog<>();
        dialog.setTitle("Novo Módulo");
        dialog.setHeaderText("Adicionar novo módulo");
    
        ButtonType confirmButtonType = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
    
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
    
        TextField titleField = new TextField();
        titleField.setPromptText("Título do módulo");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Descrição do módulo");
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(3);
        TextField durationField = new TextField();
        durationField.setPromptText("Duração do módulo (ex: 10 horas)");
    
        Spinner<Integer> lessonCountSpinner = new Spinner<>(1, 100, 3);
    
        grid.add(new Label("Título:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Descrição:"), 0, 1);
        grid.add(descriptionArea, 1, 1);
        grid.add(new Label("Duração:"), 0, 2);
        grid.add(durationField, 1, 2);
        grid.add(new Label("Número de Aulas:"), 0, 3);
        grid.add(lessonCountSpinner, 1, 3);
    
        dialog.getDialogPane().setContent(grid);
    
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return new ModuleDetails(
                        titleField.getText(),
                        descriptionArea.getText(),
                        durationField.getText(),
                        lessonCountSpinner.getValue(),
                        0.0 // Progresso inicial 0%
                );
            }
            return null;
        });
    
        dialog.showAndWait().ifPresent(moduleDetails -> {
            if (!moduleDetails.getTitle().trim().isEmpty() && 
                !moduleDetails.getDescription().trim().isEmpty() && 
                !moduleDetails.getDuration().trim().isEmpty()) {
                board.getChildren().add(createModule(
                        moduleDetails.getTitle(),
                        moduleDetails.getDescription(),
                        moduleDetails.getDuration(),
                        moduleDetails.getLessonCount(),
                        moduleDetails.getProgress()
                ));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campos Obrigatórios");
                alert.setHeaderText("Preencha todos os campos obrigatórios.");
                alert.setContentText("Por favor, forneça um título, descrição e duração para o módulo.");
                alert.showAndWait();
            }
        });
    }
    

    private void showNewLessonDialog(VBox lessonsArea) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Novo Aula");
        dialog.setHeaderText("Digite o conteúdo do aula:");

        ButtonType confirmButtonType = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefRowCount(3);
        dialog.getDialogPane().setContent(textArea);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return textArea.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(content -> {
            if (!content.trim().isEmpty()) {
                lessonsArea.getChildren().add(createLesson(content));
            }
        });
    }

    private VBox createLesson(String content) {
        VBox lessonCard = new VBox(10);
        lessonCard.getStyleClass().add("lesson-card");
        lessonCard.setPadding(new Insets(10));
        lessonCard.setMaxWidth(230);
    
        // Detalhes da aula
        Label contentLabel = new Label(content);
        contentLabel.getStyleClass().add("lesson-content");
        contentLabel.setWrapText(true);
    
        Label statusLabel = new Label("Não Iniciado");
        statusLabel.getStyleClass().add("lesson-status-not-started");
    
        Label durationLabel = new Label("Duração estimada: 1 hora");
        Label difficultyLabel = new Label("Dificuldade: Básico");
    
        Button editButton = new Button("Editar");
        editButton.getStyleClass().add("lesson-button");
        editButton.setOnAction(e -> editLesson(lessonCard, contentLabel, statusLabel, durationLabel, difficultyLabel));
    
        Button deleteButton = new Button("Excluir");
        deleteButton.getStyleClass().add("lesson-button");
        deleteButton.setOnAction(e -> {
            VBox lessonsArea = (VBox) lessonCard.getParent();
            lessonsArea.getChildren().remove(lessonCard);
        });
    
        HBox buttons = new HBox(10);
        buttons.getStyleClass().add("lesson-buttons");
        buttons.getChildren().addAll(editButton, deleteButton);
    
        lessonCard.getChildren().addAll(contentLabel, statusLabel, durationLabel, difficultyLabel, buttons);
    
        lessonCard.setOnDragDetected(event -> {
            Dragboard db = lessonCard.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content1 = new ClipboardContent();
            content1.put(CARD_FORMAT, "");
            db.setContent(content1);
    
            lessonCard.getStyleClass().add("dragged-card");
            event.consume();
        });
    
        lessonCard.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                lessonCard.getStyleClass().remove("dragged-card");
                lessonCard.getStyleClass().add("lesson-card");
            }
            event.consume();
        });
        return lessonCard;
    }
    
    // Updated edit lesson method to handle new attributes
    private void editLesson(VBox lesson, Label contentLabel, Label statusLabel, 
                            Label durationLabel, Label difficultyLabel) {
        Dialog<LessonDetails> dialog = new Dialog<>();
        dialog.setTitle("Editar Aula");
        dialog.setHeaderText("Edite os detalhes da aula:");
    
        ButtonType confirmButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
    
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
    
        TextArea contentArea = new TextArea(contentLabel.getText());
        contentArea.setWrapText(true);
        contentArea.setPrefRowCount(3);
    
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Não Iniciado", "Em Progresso", "Concluído");
        statusComboBox.setValue(statusLabel.getText());
    
        TextField durationField = new TextField(durationLabel.getText().replace("Duração estimada: ", ""));
        
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Básico", "Intermediário", "Avançado");
        difficultyComboBox.setValue(difficultyLabel.getText().replace("Dificuldade: ", ""));
    
        grid.add(new Label("Conteúdo:"), 0, 0);
        grid.add(contentArea, 1, 0);
        grid.add(new Label("Status:"), 0, 1);
        grid.add(statusComboBox, 1, 1);
        grid.add(new Label("Duração:"), 0, 2);
        grid.add(durationField, 1, 2);
        grid.add(new Label("Dificuldade:"), 0, 3);
        grid.add(difficultyComboBox, 1, 3);
    
        dialog.getDialogPane().setContent(grid);
    
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return new LessonDetails(
                    contentArea.getText(),
                    statusComboBox.getValue(),
                    durationField.getText(),
                    difficultyComboBox.getValue()
                );
            }
            return null;
        });
    
        dialog.showAndWait().ifPresent(details -> {
            if (!details.getContent().trim().isEmpty()) {
                contentLabel.setText(details.getContent());
                
                statusLabel.setText(details.getStatus());
                statusLabel.getStyleClass().removeAll("lesson-status-not-started", "lesson-status-in-progress", "lesson-status-completed");
                statusLabel.getStyleClass().add("lesson-status-" + details.getStatus().toLowerCase().replace(" ", "-"));
                
                durationLabel.setText("Duração estimada: " + details.getDuration());
                difficultyLabel.setText("Dificuldade: " + details.getDifficulty());
            }
        });
    }
    
    // New class to hold lesson details
    public static class LessonDetails {
        private final String content;
        private final String status;
        private final String duration;
        private final String difficulty;
    
        public LessonDetails(String content, String status, String duration, String difficulty) {
            this.content = content;
            this.status = status;
            this.duration = duration;
            this.difficulty = difficulty;
        }
    
        public String getContent() { return content; }
        public String getStatus() { return status; }
        public String getDuration() { return duration; }
        public String getDifficulty() { return difficulty; }
    }
    
    // Utility method to get current module's lesson count (you'll need to implement this)
    private int getCurrentModuleLessonCount() {
        // This is a placeholder. In a real implementation, you'd track the current module's lessons
        return 0;
    }

    public static class ModuleDetails {
        private final String title;
        private final String description;
        private final String duration;
        private final int lessonCount;
        private final double progress;

        public ModuleDetails(String title, String description, String duration, int lessonCount,
                double progress) {
            this.title = title;
            this.description = description;
            this.duration = duration;
            this.lessonCount = lessonCount;
            this.progress = progress;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getDuration() {
            return duration;
        }

        public int getLessonCount() {
            return lessonCount;
        }

        public double getProgress() {
            return progress;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}