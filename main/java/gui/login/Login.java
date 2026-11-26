package gui.login;

import database.Database;
import gui.common;
import gui.home.Home;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;

import javafx.event.ActionEvent;

public class Login {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    public static void start(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(Login.class.getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
    @FXML
    public void login(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);
        if(Database.get().getUsers().contains(user)) {
            try {
                Home.start(e);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else{
            common.showAlert("Invalid username or password");
        }
    }
    @FXML
    public void exit(ActionEvent e){
        Platform.exit();
    }

}
