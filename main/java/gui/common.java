package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

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
}
