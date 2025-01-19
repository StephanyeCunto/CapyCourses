package com.view.elements.Perfil;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import com.model.login_cadastro.User;
import com.dao.UserDAO;
import com.singleton.UserSession;
import com.view.login_cadastro.elements.ErrorNotification;
import com.view.login_cadastro.elements.SuccessNotification;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class SecuritySection {
    private PasswordField currentPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private VBox changePasswordSection;
    private ErrorNotification errorNotification;
    private SuccessNotification successNotification;
    private StackPane root;

    public VBox createSecuritySection() {
        // VBox principal
        VBox vbox = new VBox(25);
        vbox.getStyleClass().add("content-card");

        // Título "Segurança"
        Label securityTitle = new Label("Segurança");
        securityTitle.getStyleClass().add("card-title");

        // VBox interna para a seção de alteração de senha
        changePasswordSection = new VBox(20);

        // Título "Alterar Senha"
        Label changePasswordTitle = new Label("Alterar Senha");
        changePasswordTitle.getStyleClass().add("card-title");

        // GridPane para organizar os campos de senha
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(15);

        // Definindo as colunas (33% de largura cada)
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        // Linha única
        RowConstraints row = new RowConstraints();
        gridPane.getRowConstraints().add(row);

        // Campo "Senha Atual"
        VBox currentPasswordBox = new VBox(8);
        Label currentPasswordLabel = new Label("Senha Atual");
        currentPasswordLabel.getStyleClass().add("field-label");
        currentPasswordField = new PasswordField();
        currentPasswordField.getStyleClass().add("custom-text-field");
        currentPasswordBox.getChildren().addAll(currentPasswordLabel, currentPasswordField);
        GridPane.setColumnIndex(currentPasswordBox, 0);

        // Campo "Nova Senha"
        VBox newPasswordBox = new VBox(8);
        Label newPasswordLabel = new Label("Nova Senha");
        newPasswordLabel.getStyleClass().add("field-label");
        newPasswordField = new PasswordField();
        newPasswordField.getStyleClass().add("custom-text-field");
        newPasswordBox.getChildren().addAll(newPasswordLabel, newPasswordField);
        GridPane.setColumnIndex(newPasswordBox, 1);

        // Campo "Confirmar Nova Senha"
        VBox confirmPasswordBox = new VBox(8);
        Label confirmPasswordLabel = new Label("Confirmar Nova Senha");
        confirmPasswordLabel.getStyleClass().add("field-label");
        confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("custom-text-field");
        confirmPasswordBox.getChildren().addAll(confirmPasswordLabel, confirmPasswordField);
        GridPane.setColumnIndex(confirmPasswordBox, 2);

        // Adicionando os campos ao GridPane
        gridPane.getChildren().addAll(currentPasswordBox, newPasswordBox, confirmPasswordBox);

        // VBox para os requisitos de senha
        VBox passwordRequirementsBox = new VBox(5);
        passwordRequirementsBox.setPadding(new Insets(10, 0, 0, 0)); // Padding superior de 10
        Label requirementsTitle = new Label("Requisitos de senha:");
        requirementsTitle.getStyleClass().add("card-subtitle");
        Label requirement1 = new Label("• Mínimo de 6 caracteres");
        requirement1.getStyleClass().add("card-subtitle");
        passwordRequirementsBox.getChildren().addAll(requirementsTitle, requirement1);

        // Adicionando tudo à seção de alteração de senha
        changePasswordSection.getChildren().addAll(changePasswordTitle, gridPane, passwordRequirementsBox);

        // Adicionando tudo ao VBox principal
        vbox.getChildren().addAll(securityTitle, changePasswordSection);

        setupPasswordChangeHandler();

        return vbox;
    }

    private void setupPasswordChangeHandler() {
        Button saveButton = new Button("Salvar Alterações");
        saveButton.getStyleClass().add("outline-button-not-seletion");
        
        saveButton.setOnAction(e -> {
            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            
            // Encontra o StackPane raiz da aplicação
            Scene scene = changePasswordSection.getScene();
            StackPane root = (StackPane) scene.lookup("#mainStackPane"); // Adicione um ID ao seu StackPane principal
            
            if (!newPassword.equals(confirmPassword)) {
                new ErrorNotification(root, "As senhas não coincidem!").show();
                return;
            }
            
            if (newPassword.length() < 6) {
                new ErrorNotification(root, "A senha deve ter no mínimo 6 caracteres!").show();
                return;
            }
            
            UserSession session = UserSession.getInstance();
            UserDAO userDAO = new UserDAO();
            User user = userDAO.buscarPorEmail(session.getUserEmail());
            
            if (!user.checkPassword(currentPassword)) {
                new ErrorNotification(root, "Senha atual incorreta!").show();
                return;
            }
            
            user.setPassword(newPassword);
            userDAO.atualizar(user);
            
            new SuccessNotification(root, "Senha alterada!").show();
            clearFields();
        });
        
        changePasswordSection.getChildren().add(saveButton);
    }
    
    private StackPane findParentStackPane(Node node) {
        Parent parent = node.getParent();
        while (parent != null && !(parent instanceof StackPane)) {
            parent = parent.getParent();
        }
        return (StackPane) parent;
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }
}