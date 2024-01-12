package com.example.oopapplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelperMethods {

    public static Alert warning = new Alert(Alert.AlertType.WARNING);
    public static Alert info = new Alert(Alert.AlertType.INFORMATION);
    public static Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    public static Alert error = new Alert(Alert.AlertType.ERROR);
    public static TextInputDialog input = new TextInputDialog();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void updateDateTimeLabels(Label lblClock, Label lblDate, Label lblDay) {
        LocalDateTime now = LocalDateTime.now();

        String dayOfWeek = now.getDayOfWeek().toString();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(timeFormatter);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(dateFormatter);

        lblClock.setText(formattedTime);
        if (lblDay != null) {
            lblDate.setText(formattedDate);
            lblDay.setText(dayOfWeek);
        } else {
            lblDate.setText(dayOfWeek + ", " + formattedDate);
        }

        String[] styleClasses = lblClock.getStyleClass().toArray(new String[0]);
        lblClock.getStyleClass().setAll(styleClasses);
        lblDate.getStyleClass().setAll(styleClasses);


    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static DayOfWeek getDayOfWeekFromString(String dayAbbreviation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE");
        return DayOfWeek.from(formatter.parse(dayAbbreviation));
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void addToolTip(Button button) {
        Tooltip tooltip = new Tooltip("Clear selection first.");

        button.setOnMouseEntered(event -> {
            if (button.isDisabled()) {
                Tooltip.install(button, tooltip);
            }
        });

        button.setOnMouseExited(event -> Tooltip.uninstall(button, tooltip));
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static ButtonType showAlert(Alert alert, String title, String contentText) {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        return alert.showAndWait().orElse(ButtonType.CANCEL);
    }

    public static Optional<ButtonType> showInfo(Alert alert, String title, String contentText) {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    public static Optional<ButtonType> showInfo(Alert alert, String title, String contentText, List<String> books) {
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Create a dialog with a TextArea to display books
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(contentText + String.join("\n", books));


        GridPane grid = new GridPane();
        grid.add(textArea, 0, 0);

        dialog.getDialogPane().setContent(grid);

        // Add buttons to the dialog (e.g., OK button)
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        // Show the dialog and wait for user response
        Optional<ButtonType> result = dialog.showAndWait();

        // Handle the result as needed (e.g., perform actions based on user input)

        return result;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<String> createValuesList(Control... controls) {
        return Stream.of(controls)
                .map(HelperMethods::extractValue)
                .map(value -> (value != null) ? value : "")
                .collect(Collectors.toList());
    }


    private static String extractValue(Control control) {
        if (control instanceof TextField) {
            return ((TextField) control).getText();
        } else if (control instanceof ComboBox<?>) {
            return ((ComboBox<?>) control).getValue() != null ?
                    ((ComboBox<?>) control).getValue().toString() : null; // Return null instead of empty string
        } else if (control instanceof Label) {
            return ((Label) control).getText();
        } else if (control instanceof DatePicker) {
            return ((DatePicker) control).getValue() != null ?
                    String.valueOf(((DatePicker) control).getValue()) : null;
        } else {
            // Handle other control types or throw an exception if needed
            throw new IllegalArgumentException("Unsupported control type: " + control.getClass());
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean hasEmptyValues(Object... nodes) {
        for (Object node : nodes) {
            if (node instanceof TextField) {
                if (((TextField) node).getText().isEmpty()) {
                    return true;
                }
            } else if (node instanceof DatePicker) {
                if (((DatePicker) node).getValue() == null) {
                    return true;
                }
            } else if (node instanceof ComboBox) {
                if (((ComboBox<?>) node).getValue() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> extractValues(Object... nodes) {
        List<String> values = new ArrayList<>();
        try {
            for (Object node : nodes) {
                if (node instanceof TextField) {
                    String text = ((TextField) node).getText();
                    if (text.isEmpty()) {
                        showAlert(warning, "Error", "Please fill up all fields!");
                        throw new RuntimeException("Empty TextField");
                    }
                    values.add(text);
                } else if (node instanceof DatePicker) {
                    values.add(((DatePicker) node).getValue().toString()); // Adjust as needed
                } else if (node instanceof ComboBox) {
                    values.add(String.valueOf(((ComboBox<?>) node).getValue())); // Adjust as needed
                }

            }
        } catch (RuntimeException e) {

            throw e;
        }
        return values;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Map<String, String> createHashMap(List<String> keys, List<?> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same size");
        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), String.valueOf(values.get(i)));
        }
        map.entrySet().removeIf(entry -> entry.getValue().isEmpty() || entry.getKey().isEmpty());

        return map;
    }

    private static Map<String, String> removeNullValues(Map<String, String> map) {
        return map.entrySet().stream()
                .filter(entry -> {
                    String value = entry.getValue();
                    return value != null && !value.trim().isEmpty();
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean containsNullValue(Map<String, String> map) {
        return map.values().stream().anyMatch(value ->
                value == null || value.trim().isEmpty());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void change(String file, AnchorPane anchorPaneMain, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelperMethods.class.getResource(file));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Bibliotheca | Log In");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void showPassword(MouseEvent event) {
        var label = (Label) event.getSource();
        var str = label.getText();
        var txtField = (TextField) label.getUserData();
        var passField = (PasswordField) txtField.getUserData();

        if (str.equals("Show")) {
            txtField.setText(passField.getText());
            txtField.setVisible(true);
            passField.setVisible(false);
            label.toFront();
            label.setText("Hide");
        } else {
            txtField.setVisible(false);
            passField.setVisible(true);
            passField.setText(txtField.getText());
            label.setText("Show");
        }
    }


    public static String conditionMaker(Map<String, String> filters) {

        StringBuilder query = new StringBuilder();

        if (filters != null && !filters.isEmpty()) {
            query.append(" WHERE ");

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                query.append(entry.getValue()).append(" LIKE '" + entry.getKey() + "' AND ");
            }

            query.setLength(query.length() - 5);
        }

        return query.toString();
       }










}