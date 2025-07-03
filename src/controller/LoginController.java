package controller;

import db.DBHandler;
import javafx.stage.Stage;
import model.User;
import view.AdminView;
import view.UsersView;

import javafx.scene.control.Label;

public class LoginController {
    public static void login(Stage stage, String username, String password, Label errorLabel) {
        User user = DBHandler.login(username, password);
        if (user != null) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                new AdminView(user).show(stage);
            } else {
                new UsersView(user).show(stage);
            }
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }
}
