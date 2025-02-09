package com.view.login_cadastro.cadastro;

import java.text.*;
import java.time.LocalDate;
import java.util.*;

import com.controller.login_cadastro.CadastroStudentController;
import com.controller.login_cadastro.CadastroTeacherController;
import com.singleton.UserSession;
import com.view.Modo;
import com.view.elements.Calendario.Calendario;
import com.view.login_cadastro.cadastro.valid.CadastroSecudarioValid;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CadastroSecundario {
    @FXML
    private ComboBox<String> comboBoxEducation;
    @FXML
    private VBox date;
    @FXML
    private FlowPane interestContainer;
    @FXML
    private Label selectedCount;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private Label cpfErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label educationErrorLabel;
    @FXML
    private Label titleText;

    private Calendario dateInputPopup = new Calendario();
    private Set<String> selectedInterests = new HashSet<>();

    private final CadastroSecudarioValid VALIDADOR = new CadastroSecudarioValid();

    private StringProperty page = new SimpleStringProperty("CadastroSecundario");

    public void initial() {
        loadComboBox();
        loadCalendar();
        setupInterestButtons();
        setupTitle();

        VALIDADOR.setupInitialState(comboBoxEducation, textFieldCPF, textFieldPhone, cpfErrorLabel, educationErrorLabel,
                phoneErrorLabel);
    }

    private void loadComboBox() {
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

    private void loadCalendar() {
        if (!Modo.getInstance().getModo()) {
            dateInputPopup.setBackgroundColor("#FFFFFF");
            dateInputPopup.setAccentColor("#3498db");
            dateInputPopup.setHoverColor("#6896c4");
            dateInputPopup.setTextColor("#000000");
            dateInputPopup.setBorderColor("#808080");
            dateInputPopup.setDisabledTextColor("#A9A9A9");
            dateInputPopup.setIconColor("#3498db");
            addDateInputField();
        } else {
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

    private void addDateInputField() {
        VBox dateContainer = new VBox(5);
        dateInputPopup.setMaxDate(LocalDate.now().minusYears(14));
        dateContainer.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.08); -fx-background-radius: 8; -fx-border-radius: 8;");

        dateContainer.getChildren().clear();
        date.getChildren().clear();

        dateContainer.getChildren().add(dateInputPopup.getDateInputField());
        date.getChildren().add(dateContainer);
    }

    private void setupInterestButtons() {
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

     public void create() throws ParseException {
        if (VALIDADOR.validateFields()) {
            String email = UserSession.getInstance().getUserEmail();
            String interests = String.join(". ", selectedInterests);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format.parse(dateInputPopup.getDate());


            if(page.get().equals("StudentRegister")){
                CadastroStudentController controller = new CadastroStudentController();
                boolean success = controller.cadastrarStudent(
                        email,
                        date,
                        textFieldCPF.getText(),
                        textFieldPhone.getText(),
                        comboBoxEducation.getValue(),
                        interests);

                if (success) {
                    UserSession.getInstance().setRegisterIncomplet("false");
                    page.set("Login");
                    UserSession.getInstance().clearSession();
                } 
            } else {
                CadastroTeacherController controller = new CadastroTeacherController();
                boolean success = controller.cadastrarTeacher(
                        email,
                        date,
                        textFieldCPF.getText(),
                        textFieldPhone.getText(),
                        comboBoxEducation.getValue(),
                        interests);

                if (success) {
                    UserSession.getInstance().setRegisterIncomplet("false");
                    page.set("Login");
                    UserSession.getInstance().clearSession();
                } 
            }
            CadastroStudentController controller = new CadastroStudentController();
            boolean success = controller.cadastrarStudent(
                    email,
                    date,
                    textFieldCPF.getText(),
                    textFieldPhone.getText(),
                    comboBoxEducation.getValue(),
                    interests);

            if (success) {
                UserSession.getInstance().setRegisterIncomplet("false");
               page.set("Login");
                UserSession.getInstance().clearSession();
            } 
        }
    }

    private void setupTitle() {
        if(page.get().equals("StudentRegister")){
            titleText.setText("Cadastro de Aluno");
        } else {
            titleText.setText("Cadastro de Professor");
        }
    }

    public StringProperty getPage(){
        return page;
    }

    public void setPage(StringProperty page){
        this.page=page;
    }
}