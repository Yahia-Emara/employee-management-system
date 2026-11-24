import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    @Override 
    public void start(Stage stage){
        Label label = new Label("Hi there");
        Button button = new Button("Click me");

        VBox root = new VBox(10, label, button);
        Scene scene = new Scene(root, 300, 150);

        stage.setScene(scene);
        stage.setTitle("First window");
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}