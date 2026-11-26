package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

public class common {
    @FXML
    public static void showAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
    @FXML
    public static void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidName(String str) {
        return str != null && str.matches("^\\p{L}+([ '\\-]\\p{L}+)*$");
    }
    public static void showFieldsFor(GridPane fullTimeBox, GridPane partTimeBox, String type) {
        boolean isPartTime = "Part-Time".equals(type);
        fullTimeBox.setVisible(!isPartTime);
        fullTimeBox.setManaged(!isPartTime);
        partTimeBox.setVisible(isPartTime);
        partTimeBox.setManaged(isPartTime);
    }

    public static String validateEmployeeFields(String name, String position, String ageText,
                                                String departmentName, String type) {
        if (name.isEmpty() || position.isEmpty() || ageText.isEmpty()
                || departmentName == null || type == null) {
            return "Please fill in all required fields.";
        }
        if (!isValidName(name)) {
            return "Full name must contain letters only.";
        }
        if (!isValidName(position)) {
            return "Position must contain letters only.";
        }
        if (!isNumeric(ageText)) {
            return "Age must be a whole number.";
        }
        if (Integer.parseInt(ageText) <= 0) {
            return "Age must be positive.";
        }
        return null;
    }

    public static String validateFullTime(String salaryText, String bonusText) {
        if (!isNumeric(salaryText) || !isNumeric(bonusText)) {
            return "Please enter valid numeric values.";
        }
        if (Integer.parseInt(salaryText) < 0 || Integer.parseInt(bonusText) < 0) {
            return "Salary and bonus cannot be negative.";
        }
        return null;
    }

    public static String validatePartTime(String rateText, String hoursText) {
        if (!isNumeric(rateText) || !isNumeric(hoursText)) {
            return "Please enter valid numeric values.";
        }
        if (Integer.parseInt(rateText) < 0 || Integer.parseInt(hoursText) < 0) {
            return "Rate and hours cannot be negative.";
        }
        return null;
    }
}
