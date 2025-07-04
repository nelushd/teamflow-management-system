package view;

import controller.LoginController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private Stage stage;

    public LoginView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label title = new Label("TeamFlow Login");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            LoginController.handleLogin(username, password, stage, messageLabel);
        });

        VBox root = new VBox(10, title, usernameField, passwordField, loginButton, messageLabel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 40;");

        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
