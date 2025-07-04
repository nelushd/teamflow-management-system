package controller;

import db.DBHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;
import view.AdminDashboardView;
import view.UserDashboardView;

public class LoginController {
    public static void handleLogin(String username, String password, Stage stage, Label messageLabel) {
        User user = DBHandler.getUserByCredentials(username, password);

        if (user == null) {
            messageLabel.setText("❌ Invalid credentials");
            return;
        }

        messageLabel.setText("✅ Welcome, " + user.getName());

        // Navigate to dashboard
        if (user.getRole().equalsIgnoreCase("admin")) {
            new AdminDashboardView(stage, user).show();
        } else {
            new UserDashboardView(stage, user).show();
        }
    }
}
