import java.io.IOException;

import database.Database;
import employee.Department;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.User;

public class Main extends Application{
    @Override 
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/gui/login/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Employee Management System");
        stage.show();
    }
    public static void main(String args[]){
        Database.get().load();
        launch(args);
        
    }
}