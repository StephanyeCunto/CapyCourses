package com.view.elements;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class Calendario extends Region {
    private LocalDate selectedDate = LocalDate.now();
    private LocalDate minDate = null;
    private LocalDate maxDate = null;
    private TextField dateInputField;
    private Label monthLabel;
    private Label yearLabel;
    private String backgroundColor = "#292f4c";
    private String accentColor = "#6c63ff";
    private String hoverColor = "rgba(255, 255, 255, 0.15)";
    private String textColor = "white";
    private String borderColor = "rgba(255, 255, 255, 0.1)";
    private String disabledTextColor = "rgba(255, 255, 255, 0.3)";
    private String iconColor = "#6c63ff";
    private String inputBorderColor = "rgba(255, 255, 255, 0.1)";
    private List<Popup> activePopups = new ArrayList<>();
    private int yearGridStartYear;
    private GridPane calendarGrid;

    public void setMinDate(LocalDate minDate) {
        this.minDate = minDate;
        validateSelectedDate();
        updateCalendar();
    }

    public void setIconColor(String iconColor) {
        if (iconColor != null && !iconColor.isEmpty()) {
            this.iconColor = iconColor;
        }
    }

    public void setInputBorderColor(String inputBorderColor) {
        if (inputBorderColor != null && !inputBorderColor.isEmpty()) {
            this.inputBorderColor = inputBorderColor;
        }
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
        validateSelectedDate();
        updateCalendar();
    }

    public void setBackgroundColor(String backgroundColor) {
        if (backgroundColor != null && !backgroundColor.isEmpty()) {
            this.backgroundColor = backgroundColor;
        }
    }

    public void setAccentColor(String accentColor) {
        if (accentColor != null && !accentColor.isEmpty()) {
            this.accentColor = accentColor;
        }
    }

    public void setHoverColor(String hoverColor) {
        if (hoverColor != null && !hoverColor.isEmpty()) {
            this.hoverColor = hoverColor;
        }
    }

    public void setTextColor(String textColor) {
        if (textColor != null && !textColor.isEmpty()) {
            this.textColor = textColor;
        }
    }

    public void setBorderColor(String borderColor) {
        if (borderColor != null && !borderColor.isEmpty()) {
            this.borderColor = borderColor;
        }
    }

    public void setDisabledTextColor(String disabledTextColor) {
        if (disabledTextColor != null && !disabledTextColor.isEmpty()) {
            this.disabledTextColor = disabledTextColor;
        }
    }

    private void validateSelectedDate() {
        if (selectedDate != null) {
            if (minDate != null && selectedDate.isBefore(minDate)) {
                selectedDate = minDate;
            }
            if (maxDate != null && selectedDate.isAfter(maxDate)) {
                selectedDate = maxDate;
            }
        }
        updateDateInput(dateInputField);
    }

    @SuppressWarnings("unused")
    private Button createDayButton(int day) {
        Button button = new Button(String.valueOf(day));
        LocalDate date = selectedDate.withDayOfMonth(day);
        boolean isToday = date.equals(LocalDate.now());
        boolean isSelected = date.equals(selectedDate);
        boolean isDisabled = (minDate != null && date.isBefore(minDate)) ||
                (maxDate != null && date.isAfter(maxDate));

        String buttonStyle = """
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-background-radius: 6;
                -fx-min-width: 32px;
                -fx-min-height: 32px;
                -fx-cursor: %s;
                -fx-font-size: 13px;
                %s
                """.formatted(
                isSelected ? accentColor : "transparent",
                isDisabled ? disabledTextColor : textColor,
                isDisabled ? "default" : "hand",
                isToday ? "-fx-border-color: " + accentColor + "; -fx-border-radius: 6; -fx-border-width: 2;" : "");

        button.setStyle(buttonStyle);
        button.setDisable(isDisabled);

        if (!isDisabled) {
            button.setOnMouseEntered(e -> {
                if (!isSelected) {
                    button.setStyle(button.getStyle() + "-fx-background-color: " + hoverColor + ";");
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
        }

        return button;
    }

    @SuppressWarnings("unused")
    private Button createNavigationButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-font-size: 16px;
                -fx-cursor: hand;
                -fx-padding: 6;
                -fx-background-radius: 6;
                """.formatted(accentColor));

        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() +
                "-fx-background-color: " + hoverColor + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-background-color: " + hoverColor + ";", "")));

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        if (text.equals("❮")) {
            if (minDate != null) {
                LocalDate firstOfPreviousMonth = firstOfMonth.minusMonths(1);
                button.setDisable(firstOfPreviousMonth.isBefore(minDate.withDayOfMonth(1)));
            }
            button.setOnAction(e -> {
                selectedDate = selectedDate.minusMonths(1);
                updateCalendar();
            });
        } else {
            if (maxDate != null) {
                LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);
                button.setDisable(firstOfNextMonth.isAfter(maxDate.withDayOfMonth(1)));
            }
            button.setOnAction(e -> {
                selectedDate = selectedDate.plusMonths(1);
                updateCalendar();
            });
        }
        return button;
    }

    public Calendario() {
        monthLabel = new Label();
        yearLabel = new Label();
        calendarGrid = new GridPane();
        dateInputField = createDateInputField();
    }

    @SuppressWarnings("unused")
    public HBox getDateInputField() {
        HBox inputContainer = new HBox(10);
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setPadding(new Insets(5));
        inputContainer.setStyle("""
                -fx-background-radius: 8;
                -fx-border-color: %s;
                -fx-border-radius: 8;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 4);
                """.formatted(inputBorderColor));

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
                -fx-text-fill: %s;
                """.formatted(textColor));

        calendarButton.setStyle(calendarButton.getStyle() + """
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-background-radius: 5;
                -fx-padding: 8 12;
                -fx-font-size: 14px;
                """.formatted(iconColor));

        calendarButton.setOnMouseEntered(e -> calendarButton.setStyle(calendarButton.getStyle() +
                "-fx-background-color: " + hoverColor + ";"));
        calendarButton.setOnMouseExited(e -> calendarButton.setStyle(calendarButton.getStyle().replace(
                "-fx-background-color: " + hoverColor + ";", "-fx-background-color: " + iconColor + ";")));

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

    public LocalDate getLocalDate() {
        return selectedDate;
    }

    public void setselectedDate(LocalDate date) {
        this.selectedDate = date;
        updateCalendar();
    }

    @SuppressWarnings("unused")
    private TextField createDateInputField() {
        TextField field = new TextField();
        field.setEditable(false);
        field.setPromptText("Select a date");
        field.setPrefWidth(200);
        field.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-prompt-text-fill: %s;
                -fx-padding: 8 0;
                -fx-font-size: 14px;
                -fx-focus-color: transparent;
                -fx-faint-focus-color: transparent;
                -fx-cursor: hand;
                """.formatted(textColor,textColor));
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

    @SuppressWarnings("unused")
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
                "-fx-text-fill: " + accentColor + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-text-fill: " + accentColor + ";", "-fx-text-fill: white;")));
        button.setOnAction(e -> showDatePopup());
        return button;
    }

    private void updateDateInput(TextField field) {
        field.setText(getDate());
    }

    @SuppressWarnings("unused")
    private void showDatePopup() {
        closeAllPopups();
        Popup popup = new Popup();
        VBox popupContent = createPopupContent();
        popup.getContent().add(popupContent);
        popup.setAutoHide(true);
        popup.setOnHiding(e -> closeAllPopups());
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

    private VBox createPopupContent() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(12));
        content.setStyle("""
                -fx-background-color: %s;
                -fx-background-radius: 12;
                -fx-border-radius: 12;
                -fx-border-color: %s;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 6);
                """.formatted(backgroundColor, borderColor));

        HBox header = createHeader();
        GridPane weekDaysHeader = createWeekDaysHeader();
        calendarGrid = createCalendarGrid();

        content.getChildren().addAll(header, weekDaysHeader, calendarGrid);
        return content;
    }

    @SuppressWarnings("unused")
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
                """.formatted(textColor);

        monthLabel.setStyle(labelStyle);
        yearLabel.setStyle(labelStyle);

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

        double xOffset = dateInputField.localToScreen(0, 0).getX();
        double yOffset = dateInputField.localToScreen(0, 0).getY() + dateInputField.getHeight() + 5;

        selectionPopup.show(dateInputField.getScene().getWindow(), xOffset, yOffset);
        activePopups.add(selectionPopup);
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
                """.formatted(backgroundColor, borderColor));

        if (isMonthSelection) {
            content.getChildren().add(createMonthGrid());
        } else {
            HBox yearNavigation = createYearNavigationHeader();
            GridPane yearGrid = createYearGrid();
            content.getChildren().addAll(yearNavigation, yearGrid);
        }

        return content;
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

    @SuppressWarnings("unused")
    private Button createSelectionButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: %s;
                -fx-padding: 8;
                -fx-cursor: hand;
                -fx-background-radius: 6;
                -fx-font-size: 12px;
                """.formatted(textColor));

        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() +
                "-fx-background-color: " + hoverColor + ";"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(
                "-fx-background-color: " + hoverColor + ";", "")));

        button.setOnAction(e -> action.run());
        return button;
    }

    int countyearGridStartYear = 0;

    @SuppressWarnings("unused")
    private HBox createYearNavigationHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 8, 0));

        Button prevYear = createNavigationButton("❮");
        Button nextYear = createNavigationButton("❯");

        if (countyearGridStartYear == 0) {
            yearGridStartYear = selectedDate.getYear() - 5;
            countyearGridStartYear = 1;
        }

        Label yearRangeLabel = new Label(yearGridStartYear + " - " + (yearGridStartYear + 11));
        yearRangeLabel.setStyle("""
                -fx-font-weight: bold;
                -fx-font-size: 12px;
                -fx-text-fill: %s;
                """.formatted(textColor));

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

    private void showMonthPopup() {
        showSelectionPopup(true);
    }

    private void showYearPopup() {
        showSelectionPopup(false);
    }

    private void updateCalendar() {
        monthLabel.setText(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        yearLabel.setText(String.valueOf(selectedDate.getYear()));
        updateDateInput(dateInputField);

        // Atualiza o conteúdo do calendarGrid
        calendarGrid.getChildren().clear();
        GridPane newCalendarGrid = createCalendarGrid();
        calendarGrid.getChildren().addAll(newCalendarGrid.getChildren());
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
                        day <= 0 ? selectedDate.minusMonths(1).lengthOfMonth() + day : day - monthLength);
                grid.add(disabledButton, i % 7, i / 7);
            }
        }
        return grid;
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
                """.formatted(disabledTextColor));
        button.setDisable(true);
        return button;
    }

    private GridPane createWeekDaysHeader() {
        GridPane header = new GridPane();
        header.setHgap(5);
        header.setVgap(5);
        header.setAlignment(Pos.CENTER);

        String[] weekDays = new String[] { "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb" };
        for (int i = 0; i < weekDays.length; i++) {
            Label dayLabel = new Label(weekDays[i]);
            dayLabel.setStyle("""
                    -fx-font-size: 12px;
                    -fx-font-weight: bold;
                    -fx-text-fill: %s;
                    -fx-padding: 5;
                    """.formatted(textColor));
            header.add(dayLabel, i, 0);
        }
        return header;
    }
}
