
package gui.home;

import gui.addEmployee.addEmployee;
import gui.login.Login;
import gui.manageDepartments.manageDepartmentsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class Home {
    @FXML
    public static void start(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(Home.class.getResource("home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
    @FXML
    public void addEmployee(ActionEvent event){
        try {
            addEmployee.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void manageEmployee(){
    
    }
    @FXML
    public void logout(ActionEvent event){
        try {
            Login.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void manageDepartments(ActionEvent event){
        try {
            manageDepartmentsController.start(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
