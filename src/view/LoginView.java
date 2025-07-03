package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    public LoginView() {
        // no-arg constructor
    }

    public void show(Stage primaryStage) {
        Label titleLabel = new Label("🗂 Task Manager - Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            LoginController.login(primaryStage, username, password, errorLabel);
        });

        VBox root = new VBox(10, titleLabel, usernameField, passwordField, loginBtn, errorLabel);
        root.setPadding(new Insets(20));
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
