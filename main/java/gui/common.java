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
}
