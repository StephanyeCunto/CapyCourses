package com.view.elements.Perfil;

import com.singleton.UserSession;
import com.dao.UserDAO;
import com.model.login_cadastro.User;
import com.dao.TeacherDAO;
import com.dao.StudentDAO;
import com.model.login_cadastro.Teacher;
import com.model.login_cadastro.Student;
import com.view.login_cadastro.elements.ErrorNotification;
import com.view.login_cadastro.elements.SuccessNotification;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.Node;

public class ProfileCard {
    private UserDAO userDAO;
    private User currentUser;
    private VBox vbox;

    public ProfileCard() {
        this.userDAO = new UserDAO();
        loadCurrentUser();
    }

    private void loadCurrentUser() {
        String userEmail = UserSession.getInstance().getUserEmail();
        this.currentUser = userDAO.buscarPorEmail(userEmail);
    }

    public VBox createProfileCard() {
        VBox vbox = new VBox(20);
        vbox.getStyleClass().add("content-card");
        vbox.setPadding(new Insets(20));

        HBox hboxTop = new HBox(20);
        hboxTop.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        StackPane stackPane = circleName();

        VBox vboxButtons = new VBox(10);
        HBox hboxButtons = new HBox(15);
        hboxButtons.setAlignment(javafx.geometry.Pos.CENTER);
        Button btnChangePhoto = new Button("Alterar Foto");
        btnChangePhoto.getStyleClass().add("outline-button-not-seletion");
        Button btnRemove = new Button("Remover");
        btnRemove.getStyleClass().add("outline-button-seletion");

        hboxButtons.getChildren().addAll(btnChangePhoto, btnRemove);

        Label labelInfo = new Label("Tamanho máximo: 5MB. Formatos: JPG, PNG");
        labelInfo.getStyleClass().add("section-title");

        vboxButtons.getChildren().addAll(hboxButtons, labelInfo);

        hboxTop.getChildren().addAll(stackPane, vboxButtons);

        VBox vboxPersonalInfo = new VBox(15);
        Label labelPersonalInfo = new Label("Informações Pessoais");
        labelPersonalInfo.getStyleClass().add("card-title");

        HBox hboxFields = new HBox(15);

        VBox vboxNome = new VBox(5);
        vboxNome.setId("nomeVBox");
        HBox.setHgrow(vboxNome, javafx.scene.layout.Priority.ALWAYS);
        Label labelNome = new Label("Nome Completo");
        labelNome.getStyleClass().add("field-label");
        TextField textFieldNome = new TextField();
        textFieldNome.getStyleClass().add("custom-text-field");
        textFieldNome.setText(currentUser.getName());
        vboxNome.getChildren().addAll(labelNome, textFieldNome);

        VBox vboxEmail = new VBox(5);
        vboxEmail.setId("emailVBox");
        HBox.setHgrow(vboxEmail, javafx.scene.layout.Priority.ALWAYS);
        Label labelEmail = new Label("Email");
        labelEmail.getStyleClass().add("field-label");
        TextField textFieldEmail = new TextField();
        textFieldEmail.getStyleClass().add("custom-text-field");
        textFieldEmail.setText(currentUser.getEmail());
        vboxEmail.getChildren().addAll(labelEmail, textFieldEmail);

        VBox vboxTelefone = new VBox(5);
        vboxTelefone.setId("telefoneVBox");
        HBox.setHgrow(vboxTelefone, javafx.scene.layout.Priority.ALWAYS);
        Label labelTelefone = new Label("Telefone");
        labelTelefone.getStyleClass().add("field-label");
        TextField textFieldTelefone = new TextField();
        textFieldTelefone.getStyleClass().add("custom-text-field");
        textFieldTelefone.setText(getTelefoneByUserType());
        vboxTelefone.getChildren().addAll(labelTelefone, textFieldTelefone);

        hboxFields.getChildren().addAll(vboxNome, vboxEmail, vboxTelefone);
        vboxPersonalInfo.getChildren().addAll(labelPersonalInfo, hboxFields);

        VBox vboxBio = new VBox(10);
        Label labelBio = new Label("Biografia");
        labelBio.getStyleClass().add("field-label");
        TextArea textAreaBio = new TextArea();
        textAreaBio.setWrapText(true);
        textAreaBio.getStyleClass().add("custom-text-area");
        textAreaBio.setMinHeight(100);
        textAreaBio.setMaxHeight(100);
        Label labelBioInfo = new Label("Máximo de 200 caracteres");
        labelBioInfo.getStyleClass().add("section-title");
        vboxBio.getChildren().addAll(labelBio, textAreaBio, labelBioInfo);

        vbox.getChildren().addAll(hboxTop, vboxPersonalInfo, vboxBio);

        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.getStyleClass().add("outline-button-not-seletion");
        btnSalvar.setOnAction(e -> salvarAlteracoes(textFieldNome.getText(), 
                                                   textFieldEmail.getText(), 
                                                   textFieldTelefone.getText(),
                                                   textAreaBio.getText()));

        vbox.getChildren().add(btnSalvar);

        this.vbox = vbox;
        return vbox;
    }

    private String getTelefoneByUserType() {
        String userEmail = UserSession.getInstance().getUserEmail();
        String userType = UserSession.getInstance().getUserType();
        
        if (userType.equals("TEACHER")) {
            TeacherDAO teacherDAO = new TeacherDAO();
            Teacher teacher = teacherDAO.buscarPorEmail(userEmail);
            return teacher != null ? teacher.getTelephone() : "";
        } else if (userType.equals("STUDENT")) {
            StudentDAO studentDAO = new StudentDAO();
            Student student = studentDAO.buscarPorEmail(userEmail);
            return student != null ? student.getTelephone() : "";
        }
        return "";
    }

    private void salvarAlteracoes(String nome, String email, String telefone, String bio) {
        try {
            // Encontra o StackPane raiz
            Scene scene = vbox.getScene();
            StackPane root = (StackPane) scene.lookup("#mainStackPane");
            
            if (nome.isEmpty() || email.isEmpty()) {
                new ErrorNotification(root, "Nome e email são campos obrigatórios!").show();
                return;
            }
            
            currentUser.setName(nome);
            currentUser.setEmail(email);
            
            userDAO.editar(currentUser);
            
            String userType = UserSession.getInstance().getUserType();
            if (userType.equals("TEACHER")) {
                TeacherDAO teacherDAO = new TeacherDAO();
                Teacher teacher = teacherDAO.buscarPorEmail(email);
                if (teacher != null) {
                    teacher.setTelephone(telefone);
                    teacherDAO.editar(teacher);
                }
            } else if (userType.equals("STUDENT")) {
                StudentDAO studentDAO = new StudentDAO();
                Student student = studentDAO.buscarPorEmail(email);
                if (student != null) {
                    student.setTelephone(telefone);
                    studentDAO.editar(student);
                }
            }
            
            UserSession.getInstance().setUserName(nome);
            UserSession.getInstance().setUserEmail(email);
            
            new SuccessNotification(root, "Alterações salvas com sucesso!").show();
            
        } catch (Exception e) {
            Scene scene = vbox.getScene();
            StackPane root = (StackPane) scene.lookup("#mainStackPane");
            new ErrorNotification(root, "Erro ao salvar alterações: " + e.getMessage()).show();
        }
    }

    private StackPane circleName() {
        StackPane avatarCircle = new StackPane();
        avatarCircle.setMinSize(100, 100);
        avatarCircle.setMaxSize(100, 100);
        avatarCircle.getStyleClass().add("avatar-circle");

        Label nameInitial = new Label(initialName());
        nameInitial.setFont(Font.font("Franklin Gothic Medium", 32));
        nameInitial.setStyle("-fx-text-fill: white;");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(avatarCircle, nameInitial);

        return stackPane;
    }

    private String initialName() {
        String name = UserSession.getInstance().getUserName();
        String[] parts = name.split(" ");

        char initialName = Character.toUpperCase(parts[0].charAt(0));
        char initialSurname = Character.toUpperCase(parts[parts.length - 1].charAt(0));
        return initialName + "" + initialSurname;
    }
}