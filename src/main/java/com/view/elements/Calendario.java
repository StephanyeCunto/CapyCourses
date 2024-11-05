package com.view.elements;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Calendario{
    private LocalDate selectedDate = LocalDate.now();
    private TextField dateInputField;
    private Label monthLabel;
    private Label yearLabel;
    private final String BACKGROUND_COLOR = "#292f4c";
    private final String ACCENT_COLOR = "#6c63ff";
    private final String HOVER_COLOR = "rgba(255, 255, 255, 0.15)";
    private final String TEXT_COLOR = "white";
    private final String BORDER_COLOR = "rgba(255, 255, 255, 0.1)";
    private final String DISABLED_TEXT = "rgba(255, 255, 255, 0.3)";
    private List<Popup> activePopups = new ArrayList<>();
    private int yearGridStartYear;

    public Calendario() {
        dateInputField = createDateInputField();
    }

    public HBox getDateInputField() {
        HBox inputContainer = new HBox(10);  
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setPadding(new Insets(5));
        inputContainer.setStyle("""
                -fx-background-radius: 8;
                -fx-border-color: rgba(255, 255, 255, 0.1);
                -fx-border-radius: 8;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 4);
                """); 
        dateInputField = createDateInputField();
        Button calendarButton = createCalendarButton();
        dateInputField.prefWidthProperty().bind(inputContainer.widthProperty().multiply(0.8));
        calendarButton.prefWidthProperty().bind(inputContainer.widthProperty().multiply(0.2));
        dateInputField.setStyle(dateInputField.getStyle() + """
                -fx-background-color: transparent;
              
                -fx-border-width: 1;
                -fx-border-radius: 5;
                -fx-padding: 8 12;
                -fx-font-size: 14px;
                -fx-text-fill: white;
                """);
        calendarButton.setStyle(calendarButton.getStyle() + """
                -fx-background-color: #6c63ff;
                -fx-text-fill: white;
                -fx-background-radius: 5;
                -fx-padding: 8 12;
                -fx-font-size: 14px;
                """);
        calendarButton.setOnMouseEntered(e -> calendarButton.setStyle(calendarButton.getStyle() + """
                -fx-background-color: #5a54cc;
                """));
        calendarButton.setOnMouseExited(e -> calendarButton.setStyle(calendarButton.getStyle().replace(
                "-fx-background-color: #5a54cc;", "-fx-background-color: #6c63ff;")));
        inputContainer.getChildren().addAll(dateInputField, calendarButton);
        return inputContainer;
    }
    
    

    public String getDate() {
        if (selectedDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return selectedDate.format(formatter);
        }
        return "";
    }
    
    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 8, 0));
        
        Button prevMonth = createNavigationButton("❮");
        Button nextMonth = createNavigationButton("❯");
        
        monthLabel = new Label(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        yearLabel = new Label(String.valueOf(selectedDate.getYear()));
        
        String labelStyle = """
                -fx-font-weight: bold;
                -fx-font-size: 15px;
                -fx-text-fill: %s;
                -fx-cursor: hand;
                """.formatted(TEXT_COLOR);
    
        monthLabel.setStyle(labelStyle);
        yearLabel.setStyle(labelStyle);
        
        // Adicionar os event handlers para mês e ano
        monthLabel.setOnMouseClicked(e -> showMonthPopup());
        yearLabel.setOnMouseClicked(e -> showYearPopup());
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(prevMonth, monthLabel, yearLabel, spacer, nextMonth);
        return header;
    }
    
    private void showSelectionPopup(boolean isMonthSelection) {
        closeAllPopups();
        Popup selectionPopup = new Popup();
        VBox content = createSelectionPopupContent(isMonthSelection);
        selectionPopup.getContent().add(content);
        selectionPopup.setAutoHide(true);
        
        // Calcular a posição do popup
        double xOffset = dateInputField.localToScreen(0, 0).getX();
        double yOffset = dateInputField.localToScreen(0, 0).getY() + dateInputField.getHeight() + 5;
        
        selectionPopup.show(dateInputField.getScene().getWindow(), xOffset, yOffset);
        activePopups.add(selectionPopup);
    }
    
    private void showMonthPopup() {
        showSelectionPopup(true);
    }
    
    private void showYearPopup() {
        showSelectionPopup(false);
    }
    
    private VBox createSelectionPopupContent(boolean isMonthSelection) {
        VBox content = new VBox(8);
        content.setPadding(new Insets(20));
        content.setStyle("""
                -fx-background-color: %s;
                -fx-background-radius: 12;
                -fx-border-radius: 12;
                -fx-border-color: %s;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 6);
                """.formatted(BACKGROUND_COLOR, BORDER_COLOR));
    
        if (isMonthSelection) {
            content.getChildren().add(createMonthGrid());
        } else {
            HBox yearNavigation = createYearNavigationHeader();
            GridPane yearGrid = createYearGrid();
            content.getChildren().addAll(yearNavigation, yearGrid);
        }
    
        return content;
    }
    
    private GridPane createMonthGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
    
        int row = 0;
        int col = 0;
        for (Month month : Month.values()) {
            Button button = createSelectionButton(
                    month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    () -> {
                        selectedDate = selectedDate.withMonth(month.getValue());
                        updateCalendar();
                        closeAllPopups();
                        showDatePopup(); 
                    });
            grid.add(button, col, row);
            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }
        return grid;
    }
    
    int countyearGridStartYear=0;
    private HBox createYearNavigationHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 8, 0));
        
        Button prevYear = createNavigationButton("❮");
        Button nextYear = createNavigationButton("❯");
        
        if (countyearGridStartYear == 0) {
            yearGridStartYear = selectedDate.getYear() - 5;
            countyearGridStartYear=1;
        }
        
        Label yearRangeLabel = new Label(yearGridStartYear + " - " + (yearGridStartYear + 11));
        yearRangeLabel.setStyle("""
                -fx-font-weight: bold;
                -fx-font-size: 12px;
                -fx-text-fill: %s;
                """.formatted(TEXT_COLOR));
                
        prevYear.setOnAction(e -> {
            yearGridStartYear -= 12;
            updateYearPopup();
        });
        
        nextYear.setOnAction(e -> {
            yearGridStartYear += 12;
            updateYearPopup();
        });
        
        header.getChildren().addAll(prevYear, yearRangeLabel, nextYear);
        return header;
    }
    
    private void updateYearPopup() {
        Popup currentPopup = activePopups.get(activePopups.size() - 1);
        VBox newContent = createSelectionPopupContent(false);
        currentPopup.getContent().clear();
        currentPopup.getContent().add(newContent);
    }
    
    private GridPane createYearGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        
        int row = 0;
        int col = 0;
        for (int i = 0; i < 12; i++) {
            int year = yearGridStartYear + i;
            Button button = createSelectionButton(
                    String.valueOf(year),
                    () -> {
                        selectedDate = selectedDate.withYear(year);
                        updateCalendar();
                        closeAllPopups();
                        showDatePopup(); 
                    });
            grid.add(button, col, row);
            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }
        return grid;
    }
    
    private Button createSelectionButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-padding: 8;
                -fx-cursor: hand;
                -fx-background-radius: 6;
                -fx-font-size: 12px;
                """.formatted(TEXT_COLOR));
    
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() +
                "-fx-background-color: " + HOVER_COLOR + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-background-color: " + HOVER_COLOR + ";", "")));
    
        button.setOnAction(e -> action.run());
        return button;
    }
    
    private void updateCalendar() {
        monthLabel.setText(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        yearLabel.setText(String.valueOf(selectedDate.getYear()));
        updateDateInput(dateInputField);
    }

    private void showDatePopup() {
        closeAllPopups();
        Popup popup = new Popup();
        VBox popupContent = createPopupContent();
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
        popup.setOnHiding(e -> closeAllPopups());
        
        // Calculate position relative to the input field
        double xOffset = dateInputField.localToScreen(0, 0).getX();
        double yOffset = dateInputField.localToScreen(0, 0).getY() + dateInputField.getHeight() + 5;
        
        popup.show(dateInputField.getScene().getWindow(), xOffset, yOffset);
        activePopups.add(popup);
    }

    private void closeAllPopups() {
        List<Popup> popupsToClose = new ArrayList<>(activePopups);
        for (Popup popup : popupsToClose) {
            popup.hide();
        }
        activePopups.clear();
    }
  
    private TextField createDateInputField() {
        TextField field = new TextField();
        field.setEditable(false);
        field.setPromptText("Select a date");
        field.setPrefWidth(200);
        field.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-prompt-text-fill: rgba(255, 255, 255, 0.5);
                -fx-padding: 8 0;
                -fx-font-size: 14px;
                -fx-focus-color: transparent;
                -fx-faint-focus-color: transparent;
                -fx-cursor: hand;
                """);
        field.setOnMouseClicked(e -> showDatePopup());
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle(field.getStyle() + "-fx-background-color: transparent;");
                Platform.runLater(() -> field.deselect());
            }
        });
        updateDateInput(field);
        return field;
    }
    
    private Button createCalendarButton() {
        Button button = new Button("📅");
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-cursor: hand;
                -fx-padding: 8 12;
                """);
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() + 
                "-fx-text-fill: " + ACCENT_COLOR + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-text-fill: " + ACCENT_COLOR + ";", "-fx-text-fill: white;")));
        button.setOnAction(e -> showDatePopup());
        return button;
    }


    private VBox createPopupContent() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(12));
        content.setStyle("""
                -fx-background-color: %s;
                -fx-background-radius: 12;
                -fx-border-radius: 12;
                -fx-border-color: %s;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 6);
                """.formatted(BACKGROUND_COLOR, BORDER_COLOR));
        
        HBox header = createHeader();
        GridPane weekDaysHeader = createWeekDaysHeader();
        GridPane calendarGrid = createCalendarGrid();
        
        content.getChildren().addAll(header, weekDaysHeader, calendarGrid);
        return content;
    }

    private Button createNavigationButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-font-size: 16px;
                -fx-cursor: hand;
                -fx-padding: 6;
                -fx-background-radius: 6;
                """.formatted(ACCENT_COLOR));
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() +
                "-fx-background-color: " + HOVER_COLOR + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-background-color: " + HOVER_COLOR + ";", "")));
        if (text.equals("❮")) {
            button.setOnAction(e -> {
                selectedDate = selectedDate.minusMonths(1);
                updateCalendar();
            });
        } else {
            button.setOnAction(e -> {
                selectedDate = selectedDate.plusMonths(1);
                updateCalendar();
            });
        }
        return button;
    }

    private GridPane createWeekDaysHeader() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        String[] weekDays = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };
        for (int i = 0; i < weekDays.length; i++) {
            Label label = new Label(weekDays[i]);
            label.setStyle("""
                    -fx-font-weight: bold;
                    -fx-text-fill: %s;
                    -fx-font-size: 12px;
                    -fx-padding: 4;
                    """.formatted(TEXT_COLOR));
            grid.add(label, i, 0);
        }
        return grid;
    }

    private GridPane createCalendarGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int monthLength = selectedDate.lengthOfMonth();
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < 42; i++) {
            int day = i - firstDayOfWeek + 1;
            if (day > 0 && day <= monthLength) {
                Button dayButton = createDayButton(day);
                grid.add(dayButton, i % 7, i / 7);
            } else {
                Button disabledButton = createDisabledDayButton(
                        day <= 0 ? firstOfMonth.minusMonths(1).lengthOfMonth() + day : day - monthLength);
                grid.add(disabledButton, i % 7, i / 7);
            }
        }
        return grid;
    }

    private Button createDayButton(int day) {
        Button button = new Button(String.valueOf(day));
        LocalDate date = selectedDate.withDayOfMonth(day);
        boolean isToday = date.equals(LocalDate.now());
        boolean isSelected = date.equals(selectedDate);
        String buttonStyle = """
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-background-radius: 6;
                -fx-min-width: 32px;
                -fx-min-height: 32px;
                -fx-cursor: hand;
                -fx-font-size: 13px;
                %s
                """.formatted(
                isSelected ? ACCENT_COLOR : "transparent",
                isSelected ? TEXT_COLOR : TEXT_COLOR,
                isToday ? "-fx-border-color: " + ACCENT_COLOR + "; -fx-border-radius: 6; -fx-border-width: 2;" : "");
        button.setStyle(buttonStyle);
        button.setOnMouseEntered(e -> {
            if (!isSelected) {
                button.setStyle(button.getStyle() + "-fx-background-color: " + HOVER_COLOR + ";");
            }
        });
        button.setOnMouseExited(e -> {
            if (!isSelected) {
                button.setStyle(buttonStyle);
            }
        });
        button.setOnAction(e -> {
            selectedDate = date;
            updateDateInput(dateInputField);
            ((Popup) button.getScene().getWindow()).hide();
        });
        return button;
    }

    private Button createDisabledDayButton(int day) {
        Button button = new Button(String.valueOf(day));
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-background-radius: 6;
                -fx-min-width: 32px;
                -fx-min-height: 32px;
                -fx-font-size: 13px;
                """.formatted(DISABLED_TEXT));
        button.setDisable(true);
        return button;
    }

    private void updateDateInput(TextField field) {
        if (selectedDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            field.setText(selectedDate.format(formatter));
        } else {
            field.setText("");
        }
    }
}