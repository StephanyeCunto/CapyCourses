package com; // Altere para o seu pacote adequado

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateInputPopup extends Application {
    private LocalDate selectedDate = LocalDate.now();
    private TextField dateInputField;
    private Label monthLabel;
    private Label yearLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        dateInputField = new TextField();
        dateInputField.setEditable(false);
        dateInputField.setPromptText("Select a date");
        dateInputField.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-padding: 8px;");

        updateDateInput();

        Button calendarButton = new Button("📅");
        calendarButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px;");
        calendarButton.setOnAction(e -> showDatePopup());

        HBox hBox = new HBox(dateInputField, calendarButton);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        Scene scene = new Scene(hBox, 300, 100);
        primaryStage.setTitle("Date Input with Popup");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDatePopup() {
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        popupContent.setPadding(new Insets(10));
        popupContent.setSpacing(10);
        popupContent.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 5;");

        HBox monthYearBox = new HBox();
        monthYearBox.setSpacing(10);

        monthLabel = new Label(selectedDate.getMonth().toString());
        monthLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #007bff;");
        monthLabel.setOnMouseClicked(e -> showMonthPopup(popup));

        yearLabel = new Label(String.valueOf(selectedDate.getYear()));
        yearLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #007bff;");
        yearLabel.setOnMouseClicked(e -> showYearPopup(popup));

        monthYearBox.getChildren().addAll(monthLabel, yearLabel);

        HBox navigationBox = new HBox();
        Button previousMonthButton = new Button("<");
        Button nextMonthButton = new Button(">");
        previousMonthButton.setOnAction(e -> {
            selectedDate = selectedDate.minusMonths(1);
            updateMonthYearLabels();
        });
        nextMonthButton.setOnAction(e -> {
            selectedDate = selectedDate.plusMonths(1);
            updateMonthYearLabels();
        });
        navigationBox.getChildren().addAll(previousMonthButton, monthYearBox, nextMonthButton);
        navigationBox.setSpacing(10);

        GridPane calendarGrid = createCalendarGrid();
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popup.hide());

        popupContent.getChildren().addAll(navigationBox, calendarGrid, closeButton);
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
        popup.show(dateInputField.getScene().getWindow());
    }

    private GridPane createCalendarGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);

        List<LocalDate> daysInMonth = getDaysInMonth(selectedDate);
        for (int i = 0; i < daysInMonth.size(); i++) {
            LocalDate day = daysInMonth.get(i);
            Button dayButton = new Button(String.valueOf(day.getDayOfMonth()));
            dayButton.setMinSize(40, 40);
            dayButton.setOnAction(e -> {
                selectedDate = day;
                updateDateInput();
                ((Popup) grid.getScene().getWindow()).hide();
            });
            dayButton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #ccc; -fx-padding: 10px; -fx-font-weight: bold; -fx-text-fill: #333;");
            grid.add(dayButton, i % 7, i / 7);
        }

        return grid;
    }

    private List<LocalDate> getDaysInMonth(LocalDate date) {
        List<LocalDate> days = new ArrayList<>();
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int daysInMonth = firstOfMonth.lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            days.add(firstOfMonth.withDayOfMonth(i));
        }
        return days;
    }

    private void showMonthPopup(Popup parentPopup) {
        Popup monthPopup = new Popup();
        VBox monthContent = new VBox();
        monthContent.setPadding(new Insets(10));
        monthContent.setSpacing(10);

        Label monthSelectionLabel = new Label("Select a month:");
        monthSelectionLabel.setStyle("-fx-font-weight: bold;");

        ComboBox<Month> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(Month.values());
        monthComboBox.setValue(selectedDate.getMonth());
        monthComboBox.setOnAction(e -> {
            selectedDate = selectedDate.withMonth(monthComboBox.getValue().getValue());
            updateDateInput();
            updateMonthYearLabels();
            monthPopup.hide();
            parentPopup.show(dateInputField.getScene().getWindow());
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            monthPopup.hide();
            parentPopup.show(dateInputField.getScene().getWindow());
        });

        monthContent.getChildren().addAll(monthSelectionLabel, monthComboBox, closeButton);
        monthPopup.getContent().add(monthContent);
        monthPopup.setAutoHide(true);
        monthPopup.show(dateInputField.getScene().getWindow());
    }

    private void showYearPopup(Popup parentPopup) {
        Popup yearPopup = new Popup();
        VBox yearContent = new VBox();
        yearContent.setPadding(new Insets(10));
        yearContent.setSpacing(10);

        Label yearSelectionLabel = new Label("Select a year:");
        yearSelectionLabel.setStyle("-fx-font-weight: bold;");

        ComboBox<Integer> yearComboBox = new ComboBox<>();
        for (int year = selectedDate.getYear() - 10; year <= selectedDate.getYear() + 10; year++) {
            yearComboBox.getItems().add(year);
        }
        yearComboBox.setValue(selectedDate.getYear());
        yearComboBox.setOnAction(e -> {
            selectedDate = selectedDate.withYear(yearComboBox.getValue());
            updateDateInput();
            yearPopup.hide();
            parentPopup.show(dateInputField.getScene().getWindow());
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            yearPopup.hide();
            parentPopup.show(dateInputField.getScene().getWindow());
        });

        yearContent.getChildren().addAll(yearSelectionLabel, yearComboBox, closeButton);
        yearPopup.getContent().add(yearContent);
        yearPopup.setAutoHide(true);
        yearPopup.show(dateInputField.getScene().getWindow());
    }

    private void updateDateInput() {
        dateInputField.setText(selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void updateMonthYearLabels() {
        monthLabel.setText(selectedDate.getMonth().toString());
        yearLabel.setText(String.valueOf(selectedDate.getYear()));
    }
}
