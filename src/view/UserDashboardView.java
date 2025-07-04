package view;

import db.DBHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Task;
import model.User;

import java.util.List;

public class UserDashboardView {
    private Stage stage;
    private User user;

    public UserDashboardView(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() {
        Label title = new Label("User Dashboard");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Label welcomeLabel = new Label("Welcome, " + user.getName());

        Button viewTasksButton = new Button("View My Tasks");
        Button markCompleteButton = new Button("Mark Task as Completed");
        Button logoutButton = new Button("Logout");

        viewTasksButton.setOnAction(e -> showUserTasks());
        markCompleteButton.setOnAction(e -> markTaskCompleteDialog());
        logoutButton.setOnAction(e -> {
            new LoginView(stage).show(); // Go back to login screen
        });

        VBox layout = new VBox(15, title, welcomeLabel, viewTasksButton, markCompleteButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("User Dashboard");
        stage.show();
    }

    private void showUserTasks() {
        List<Task> tasks = DBHandler.getTasksByUserId(user.getId());

        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task.toString()).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("My Tasks");
        alert.setHeaderText("Here are your tasks:");
        alert.setContentText(sb.length() > 0 ? sb.toString() : "No tasks assigned.");
        alert.showAndWait();
    }

    private void markTaskCompleteDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("✅ Mark Task as Completed");
        dialog.setHeaderText("Enter Task ID you want to mark as completed:");
        dialog.setContentText("Task ID:");

        dialog.showAndWait().ifPresent(taskIdText -> {
            try {
                int taskId = Integer.parseInt(taskIdText);
                boolean success = DBHandler.markTaskComplete(taskId, user.getId());

                Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
                resultAlert.setTitle("Update Result");

                if (success) {
                    resultAlert.setHeaderText("Task marked as completed! ");
                } else {
                    resultAlert.setHeaderText("❌ Task not found or not assigned to you.");
                }
                resultAlert.showAndWait();
            } catch (NumberFormatException e) {
                showError("Invalid Task ID. Please enter a number.");
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("❗ Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
