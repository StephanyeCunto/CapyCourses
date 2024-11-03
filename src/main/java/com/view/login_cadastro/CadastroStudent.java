package com.view.login_cadastro;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.DateInputPopup;
import com.controller.login_cadastro.CadastroStudentController;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastroStudent extends BaseLoginCadastro implements Initializable {
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
    private DatePicker datePicker;
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

    private Set<String> selectedInterests = new HashSet<>();

    private DateInputPopup dateInputPopup = new DateInputPopup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initializeCommon();

        loadComboBox();
        addDateInputField();
        setupInterestButtons();

        logar.setOnAction(event -> super.redirectTo("/com/login_cadastro/paginaLogin.fxml",
                (Stage) leftSection.getScene().getWindow()));
    }

    public void createStudent() throws ParseException{
        String interests = String.join(". ", getSelectedInterests());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(dateInputPopup.getDate());

        new CadastroStudentController(date,textFieldCPF.getText(), Long.parseLong(textFieldPhone.getText()), comboBoxEducation.getValue(),interests);

        super.redirectTo("/com/login_cadastro/paginaLogin.fxml", (Stage) leftSection.getScene().getWindow());
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

    private void addDateInputField() {
        VBox dateContainer = new VBox(5);
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
        if(selectedCount!=null){
            selectedCount.setText(count + (count == 1 ? " área selecionada" : " áreas selecionadas"));
        }
    }

    private Set<String> getSelectedInterests() {
        return new HashSet<>(selectedInterests);
    }
}
