package com;

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

public class DateInputPopup{
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

    public DateInputPopup() {
        dateInputField = createDateInputField();
    }

    public VBox getDateInputField() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("""
            -fx-background-color: %s;
            -fx-background-radius: 12;
            """.formatted(BACKGROUND_COLOR));

        dateInputField = createDateInputField();
        Button calendarButton = createCalendarButton();

        HBox inputContainer = new HBox(0);
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.08);
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-border-color: rgba(255, 255, 255, 0.1);
            -fx-border-width: 1;
            """);
        inputContainer.getChildren().addAll(dateInputField, calendarButton);
        root.getChildren().add(inputContainer);

        return root;
    }

    private void showDatePopup() {
        closeAllPopups();
        Popup popup = new Popup();
        VBox popupContent = createPopupContent();
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
        popup.setOnHiding(e -> closeAllPopups());
        dateInputField.getScene().getWindow().setOnShowing(e -> {
            popup.setX(dateInputField.localToScreen(0, 0).getX());
            popup.setY(dateInputField.localToScreen(0, 0).getY() + dateInputField.getHeight() + 5);
        });
        activePopups.add(popup);
        popup.show(dateInputField.getScene().getWindow());
    }

    private void closeAllPopups() {
        List<Popup> popupsToClose = new ArrayList<>(activePopups);
        for (Popup popup : popupsToClose) {
            popup.hide();
        }
        activePopups.clear();
    }

    private void showSelectionPopup(boolean isMonthSelection) {
        closeAllPopups(); 
        Popup selectionPopup = new Popup();
        VBox content = createSelectionPopupContent(isMonthSelection);
        selectionPopup.getContent().add(content);
        selectionPopup.setAutoHide(false); 
        selectionPopup.setOnHiding(e -> {
            activePopups.remove(selectionPopup);
            showDatePopup();
        });
        activePopups.add(selectionPopup);
        selectionPopup.show(dateInputField.getScene().getWindow());
    }

    private VBox createSelectionPopupContent(boolean isMonthSelection) {
        VBox content = new VBox(10);
        content.setPadding(new Insets(12));
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

    private void updateYearPopup() {
        Popup currentPopup = activePopups.stream()
                .filter(popup -> popup.getContent().size() > 0)
                .findFirst()
                .orElse(null);
        if (currentPopup != null) {
            VBox newContent = createSelectionPopupContent(false);
            currentPopup.getContent().clear();
            currentPopup.getContent().add(newContent);
        } else {
            currentPopup.hide();
            VBox newContent = createSelectionPopupContent(false);
            currentPopup.getContent().clear();
            currentPopup.getContent().add(newContent);
        }
    }

    private int coutYearGridStartYear = 0;

    private HBox createYearNavigationHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 8, 0));
        Button prevYear = createNavigationButton("❮");
        Button nextYear = createNavigationButton("❯");
        if (coutYearGridStartYear == 0) {
            yearGridStartYear = selectedDate.getYear() - 5;
            coutYearGridStartYear = +1;
        }
        Label yearRangeLabel = new Label(yearGridStartYear + " - " + (yearGridStartYear + 11));
        yearRangeLabel.setStyle("""
                -fx-font-weight: bold;
                -fx-font-size: 15px;
                -fx-text-fill: %s;
                """.formatted(TEXT_COLOR));
        prevYear.setOnAction(e -> {
            yearGridStartYear = yearGridStartYear - 12; 
            updateYearRangeLabel(); 
            updateYearPopup(); 
        });
        nextYear.setOnAction(e -> {
            yearGridStartYear += 12;
            updateYearRangeLabel(); 
            updateYearPopup(); 
        });
        header.getChildren().addAll(prevYear, yearRangeLabel, nextYear);
        return header; 
    }

    private void updateYearRangeLabel() {
        yearLabel.setText(yearGridStartYear + " - " + (yearGridStartYear + 11));
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

    private TextField createDateInputField() {
        TextField field = new TextField();
        field.setEditable(false);
        field.setPromptText("Select a date");
        field.setPrefWidth(200);
        field.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-prompt-text-fill: rgba(255, 255, 255, 0.5);
                -fx-padding: 12 16;
                -fx-font-size: 14px;
                -fx-background-radius: 12 0 0 12;
                """);
        updateDateInput(field);
        return field;
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

    private Button createCalendarButton() {
        Button button = new Button("📅");
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-font-size: 16px;
                -fx-cursor: hand;
                -fx-background-radius: 0 12 12 0;
                -fx-padding: 12 0;
                """.formatted(ACCENT_COLOR));
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() +
                "-fx-background-color: " + HOVER_COLOR + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-background-color: " + HOVER_COLOR + ";", "")));
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

    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 8, 0));
        Button prevMonth = createNavigationButton("❮");
        Button nextMonth = createNavigationButton("❯");
        prevMonth.setOnAction(e -> {
            selectedDate = selectedDate.minusMonths(1); 
            updateCalendar();
        });
        nextMonth.setOnAction(e -> {
            selectedDate = selectedDate.plusMonths(1); 
            updateCalendar(); 
        });
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
        monthLabel.setOnMouseClicked(e -> showMonthPopup());
        yearLabel.setOnMouseClicked(e -> showYearPopup());
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(prevMonth, monthLabel, yearLabel, spacer, nextMonth);
        return header;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        field.setText(selectedDate.format(formatter));
    }

    private void updateCalendar() {
        updateMonthYearLabels(); 
        Popup currentPopup = (Popup) monthLabel.getScene().getWindow();
        VBox newContent = createPopupContent(); 
        currentPopup.getContent().clear();
        currentPopup.getContent().add(newContent); 
    }

    private void updateMonthYearLabels() {
        monthLabel.setText(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        yearLabel.setText(String.valueOf(selectedDate.getYear()));
    }

    private void showMonthPopup() {
        showSelectionPopup(true);
    }

    private void showYearPopup() {
        showSelectionPopup(false); 
    }

    private Button createSelectionButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-min-width: 80px;
                -fx-padding: 8;
                -fx-cursor: hand;
                -fx-background-radius: 6;
                -fx-font-size: 13px;
                """.formatted(TEXT_COLOR));

        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() +
                "-fx-background-color: " + HOVER_COLOR + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-background-color: " + HOVER_COLOR + ";", "")));

        button.setOnAction(e -> action.run());
        return button;
    }
}