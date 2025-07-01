import db.DBHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // UI will go here later
        System.out.println("Starting TeamFlow App...");
    }

    public static void main(String[] args) {
        DBHandler.initDatabase(); // Create DB and tables
        launch(args);

    }

}
