package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class AdminDashboardView {
    private Stage stage;
    private User admin;

    public AdminDashboardView(Stage stage, User admin) {
        this.stage = stage;
        this.admin = admin;
    }

    public void show() {
        Label title = new Label("👤 Admin Dashboard");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Label welcomeLabel = new Label("Welcome, " + admin.getName() + " 👋");

        Button viewUsersButton = new Button("View All Users");
        Button logoutButton = new Button("Logout");

        viewUsersButton.setOnAction(e -> showUsers());
        logoutButton.setOnAction(e -> {
            new LoginView(stage).show(); // Return to login view
        });

        VBox layout = new VBox(15, title, welcomeLabel, viewUsersButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    private void showUsers() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User List");
        alert.setHeaderText("All Registered Users");

        StringBuilder sb = new StringBuilder();
        for (User user : db.DBHandler.getAllUsers()) {
            sb.append(user.toString()).append("\n");
        }

        alert.setContentText(sb.toString());
        alert.showAndWait();
    }
}
