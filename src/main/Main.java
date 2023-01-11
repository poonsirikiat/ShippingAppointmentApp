package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is main method. This is the first method to get call when you run the program.
 */
public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        stage.setTitle("Scheduling Desktop Application");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    /**
     * connect to the SQL database, and close the connection when the program is close
     * @param args the main to launch
     */
    public static void main(String[] args){

        DBConnection.openConnection();

        launch(args);

        DBConnection.closeConnection();
    }
}
