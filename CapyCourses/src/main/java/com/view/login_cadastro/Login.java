package com.view.login_cadastro;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;

import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.*;

import com.controller.login_cadastro.LoginController;
import com.singleton.UserSession;
import com.view.Modo;
import com.view.login_cadastro.elements.ErrorNotification;
import com.view.login_cadastro.valid.LoginValid;

public class Login extends BaseLoginCadastro implements Initializable {
    @FXML
    private VBox leftSection;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Button themeToggleBtn;
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
    private StackPane toggleButtonStackPane;
    @FXML
    private CheckBox lembrar;
    @FXML
    private StackPane container;

    private boolean isLightMode = Modo.getInstance().getModo();

    private final LoginValid validator = new LoginValid();
    private ErrorNotification errorNotification;

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  validator = new LoginValidator(user, password);
        super.initializeCommon();
        validator.setupInitialState(user, password, userErrorLabel, passwordErrorLabel);
        setupErrorNotification();
        initializeCommon();
        

        toggleButtonStackPane.setOnMouseClicked(e -> toggle());
        sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
        moonIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
        toggleInitialize();

        loadInformation();
    }

    private void loadInformation() {
        String filePath = "capycourses/src/main/resources/com/json/login.json";
        File file = new File(filePath);

        if (file.exists()) {
            if (file.length() > 0) {
                String email = CreateJson.getSavedName(filePath);
                String senha = CreateJson.getSavedPassword(filePath);
                if (email != null && senha != null) {
                    user.setText(email);
                    password.setText(senha);
                }
            }
        } else {
            System.out.println("Arquivo não existe: " + filePath);
        }
    }

    @FXML
    private void logar() {
        validator.getValidationSupport().setValidationDecorator(new GraphicValidationDecoration());
        if (!validator.validateFields()) {
            return;
        }

        LoginController plc = new LoginController();
        UserSession.getInstance().setUserEmail(user.getText());
        String isCheck = plc.isCheck(user.getText(), password.getText());
        Stage stage = (Stage) leftSection.getScene().getWindow();

        

        if (isCheck.equals("true")) {
            CreateJson json = new CreateJson();
         
            if (lembrar.isSelected()) {
                json.saveLoginData(user.getText(), password.getText(),
                        "capycourses/src/main/resources/com/json/login.json");
            } else {
                json.verifyAndDeleteLoginData(user.getText(), password.getText(),
                        "capycourses/src/main/resources/com/json/login.json");
            }
        
            String userType = plc.getUserType(user.getText());
            UserSession.getInstance().setUserType(userType);
        
            // Redirecionar baseado no tipo
            switch(userType.toUpperCase()) {
                case "STUDENT":
                    super.redirectTo("/com/estudante/paginaInicial/paginaInicial.fxml", stage);
                    break;
                case "TEACHER":
                    super.redirectTo("/com/professor/paginaCadastroCurso.fxml", stage);
                    break;
                default:
                    showError();
                    break;
            }
        } else if (isCheck.equals("incomplete student")) {
            UserSession.getInstance().setRegisterIncomplet("Student");
            UserSession.getInstance().setUserType("STUDENT");
            super.redirectTo("/com/login_cadastro/paginaCadastroStudent.fxml", stage);
        } else if (isCheck.equals("incomplete teacher")) {
            UserSession.getInstance().setRegisterIncomplet("Teacher"); 
            UserSession.getInstance().setUserType("TEACHER");
            super.redirectTo("/com/login_cadastro/paginaCadastroTeacher.fxml", stage);
        } else {
            UserSession.getInstance().clearSession();
            showError();
            password.clear();
            user.requestFocus();
        }
    }
    private void createJson() {
        if (lembrar.isSelected()) {
            CreateJson.saveLoginData(user.getText(), password.getText(),
                    "capycourses/src/main/resources/com/json/login.json");
        } else {
            CreateJson.verifyAndDeleteLoginData(user.getText(), password.getText(),
                    "capycourses/src/main/resources/com/json/login.json");
        }
    }

    @FXML
    private void changeModeStyle() {
        super.changeMode(false);
    }

    @SuppressWarnings("unused")
    private void setupErrorNotification() {
        StackPane root = new StackPane();
        leftSection.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                errorNotification = new ErrorNotification(root, "Usuário ou senhas incorretos");
                if (newScene.getRoot() instanceof StackPane) {
                    ((StackPane) newScene.getRoot()).getChildren().add(errorNotification.getContainer());
                } else {
                    root.getChildren().addAll(newScene.getRoot(), errorNotification.getContainer());
                    newScene.setRoot(root);
                }
            }
        });
    }

    private void showError() {
        errorNotification.show();
    }

    @FXML
    private void register() {
        Stage stage = (Stage) leftSection.getScene().getWindow();
        super.redirectTo("/com/login_cadastro/paginaCadastro.fxml", stage);
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

    @FXML
    private void abrirRecuperarSenha() {
        Stage stage = (Stage) leftSection.getScene().getWindow();
        super.redirectTo("/com/login_cadastro/recuperarSenha.fxml", stage);
    }
}