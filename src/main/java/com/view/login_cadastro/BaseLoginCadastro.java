package com.view.login_cadastro;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;

import com.view.Base;
import com.view.Modo;
import com.view.elements.Calendario.Calendario;

@Getter
public class BaseLoginCadastro extends Base {
    @FXML
    private GridPane mainPane;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passworFieldPassword;
    @FXML
    private PasswordField passworFieldPasswordConfirm;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private ToggleGroup userType;
    @FXML
    private RadioButton radioButtonStudent;
    @FXML
    private RadioButton radioButtonTeacher;
    @FXML
    private Hyperlink logar;
    @FXML
    private VBox leftSection;
    @FXML
    private VBox rightSection;
    @FXML
    private ComboBox<String> comboBoxEducation;
    @FXML
    private FlowPane interestContainer;
    @FXML
    private VBox date;
    @FXML
    private Label selectedCount;
    @FXML
    private StackPane container;

    private Set<String> selectedInterests = new HashSet<>();
    private Calendario dateInputPopup = new Calendario();

    protected void initializeCommon() {
        loadAnimation();
        loadCSS();
    }

    protected void redirectTo(String pageNext, Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(pageNext));
            Image icon = new Image("/capyCourses 012.png");
            Scene currentScene = stage.getScene();
            Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());
            stage.setScene(newScene);
            stage.getIcons().add(icon);
            stage.setTitle("CapyCourses");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAnimation() {
        mainPane.setTranslateX(-1000);
        mainPane.setOpacity(0);
        leftSection.setTranslateX(-500);
        leftSection.setOpacity(0);
        rightSection.setTranslateX(-500);
        rightSection.setOpacity(0);

        animateNodeWithDelay(mainPane, 1000, 100);
        animateNodeWithDelay(leftSection, 500, 300);
        animateNodeWithDelay(rightSection, 500, 500);
    }

    private void animateNode(Node node, double distance, int delay) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(2000), node);
        translate.setDelay(Duration.millis(delay));
        translate.setFromX(-distance);
        translate.setToX(0);
        translate.setInterpolator(Interpolator.EASE_OUT);
        FadeTransition fade = new FadeTransition(Duration.millis(1200), node);
        fade.setDelay(Duration.millis(delay));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);
        translate.play();
        fade.play();
    }

    private void animateNodeWithDelay(Node node, double distance, int delay) {
        animateNode(node, distance, delay);
    }

    @SuppressWarnings("unused")
    private void loadCSS() {
        mainPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                applyStylesheetBasedOnSize(newScene.getWidth(), newScene.getHeight());
                newScene.widthProperty().addListener((observable, oldWidth, newWidth) -> {
                    applyStylesheetBasedOnSize(newWidth.doubleValue(), newScene.getHeight());
                });
                newScene.heightProperty().addListener((observable, oldHeight, newHeight) -> {
                    applyStylesheetBasedOnSize(newScene.getWidth(), newHeight.doubleValue());
                });
            }
        });
    }

    private void applyStylesheetBasedOnSize(double width, double height) {
        Scene scene = mainPane.getScene();
        if (scene != null) {
            container.getStylesheets().clear();
            scene.getStylesheets().clear();
            if (!Modo.getInstance().getModo()) {
                container.getStylesheets()
                        .add(getClass().getResource("/com/login_cadastro/style/ligth/style.css").toExternalForm());
                if (width < 925 || height < 500) {
                    adjustLayout(false);
                    scene.getStylesheets()
                            .add(getClass().getResource("/com/login_cadastro/style/ligth/styleSmall.css")
                                    .toExternalForm());
                } else if (width < 1400 || height < 900) {
                    adjustLayout(true);
                    scene.getStylesheets()
                            .add(getClass().getResource("/com/login_cadastro/style/ligth/styleMedium.css")
                                    .toExternalForm());
                } else {
                    adjustLayout(true);
                    scene.getStylesheets()
                            .add(getClass().getResource("/com/login_cadastro/style/ligth/styleLarge.css")
                                    .toExternalForm());
                }
            } else {
                container.getStylesheets()
                        .add(getClass().getResource("/com/login_cadastro/style/dark/style.css").toExternalForm());
                if (width < 925 || height < 500) {
                    adjustLayout(false);
                    scene.getStylesheets()
                            .add(getClass().getResource("/com/login_cadastro/style/dark/styleSmall.css")
                                    .toExternalForm());
                } else if (width < 1400 || height < 900) {
                    adjustLayout(true);
                    scene.getStylesheets()
                            .add(getClass().getResource("/com/login_cadastro/style/dark/styleMedium.css")
                                    .toExternalForm());
                } else {
                    adjustLayout(true);
                    scene.getStylesheets()
                            .add(getClass().getResource("/com/login_cadastro/style/dark/styleLarge.css")
                                    .toExternalForm());
                }
            }
        }
    }

    private void adjustLayout(boolean visible) {
        leftSection.setVisible(visible);
        if (visible) {
            mainPane.getColumnConstraints().get(0).setPercentWidth(50);
            mainPane.getColumnConstraints().get(1).setPercentWidth(50);
        } else {
            mainPane.getColumnConstraints().get(0).setPercentWidth(0);
            mainPane.getColumnConstraints().get(1).setPercentWidth(100);
        }
    }

    protected void loadComboBox() {
        comboBoxEducation.getItems().addAll(
                "Ensino Fundamental Incompleto",
                "Ensino Fundamental Completo",
                "Ensino Médio Incompleto",
                "Ensino Médio Completo",
                "Ensino Superior Incompleto",
                "Ensino Superior Completo",
                "Pós-graduação",
                "Mestrado",
                "Doutorado");
    }

    protected void addDateInputField() {
        VBox dateContainer = new VBox(5);
        dateInputPopup.setMaxDate(LocalDate.now().minusYears(14));
        dateContainer.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.08); -fx-background-radius: 8; -fx-border-radius: 8;");

        dateContainer.getChildren().clear();
        date.getChildren().clear();

        dateContainer.getChildren().add(dateInputPopup.getDateInputField());
        date.getChildren().add(dateContainer);
    }

    @SuppressWarnings("unused")
    protected void setupInterestButtons() {
        interestContainer.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnAction(e -> toggleInterest(button));
            }
        });

        updateSelectedCount();
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

    protected Set<String> getSelectedInterests() {
        return new HashSet<>(selectedInterests);
    }

    protected void changeMode(boolean calendar) {
        container.getStylesheets().clear();
        if (Modo.getInstance().getModo()) {
            container.getStylesheets()
                    .add(getClass().getResource("/com/login_cadastro/style/ligth/style.css").toExternalForm());
            Modo.getInstance().setModo();
        } else {
            container.getStylesheets()
                    .add(getClass().getResource("/com/login_cadastro/style/dark/style.css").toExternalForm());
            Modo.getInstance().setModo();
        }
        if(calendar){
            loadCalendar();
        }
    }

    protected void loadCalendar() {
        if (!Modo.getInstance().getModo()) {
            dateInputPopup.setBackgroundColor("#FFFFFF");
            dateInputPopup.setAccentColor("#3498db");   
            dateInputPopup.setHoverColor("#6896c4");     
            dateInputPopup.setTextColor("#000000");       
            dateInputPopup.setBorderColor("#808080");     
            dateInputPopup.setDisabledTextColor("#A9A9A9"); 
            dateInputPopup.setIconColor("#3498db");
            addDateInputField();
        }else{
            dateInputPopup.setBackgroundColor("#1A1F2F");
            dateInputPopup.setAccentColor("#748BFF");   
            dateInputPopup.setHoverColor("#8C87FF");     
            dateInputPopup.setTextColor("#FFFFFF");       
            dateInputPopup.setBorderColor("#808080");     
            dateInputPopup.setDisabledTextColor("#A9A9A9"); 
            dateInputPopup.setIconColor("#728CFF");
            addDateInputField();
        }
    }
}