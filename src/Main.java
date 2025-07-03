import javafx.application.Application;
import javafx.stage.Stage;
import db.DBHandler;
import view.LoginView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        DBHandler.initDatabase();
        LoginView loginView = new LoginView();
        loginView.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

