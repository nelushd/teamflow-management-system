package view;

import controller.UsersController;
import db.DBHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Task;
import model.User;

import java.util.List;

public class UsersView {
    private final User user;
    private TableView<Task> taskTable;

    public UsersView(User user) {
        this.user = user;
    }

    public void show(Stage stage) {
        Label title = new Label("👤 User Dashboard - " + user.getUsername());

        taskTable = new TableView<>();
        refreshTasks();


        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Task, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<Task, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        taskTable.getColumns().addAll(titleCol, descCol, statusCol);

        // Status update controls
        ComboBox<String> statusDropdown = new ComboBox<>();
        statusDropdown.getItems().addAll("Not Started", "In Progress", "Completed");
        statusDropdown.setPromptText("Set new status");

        Button updateBtn = new Button("Update Selected Task");

        updateBtn.setOnAction(e -> {
            Task selected = taskTable.getSelectionModel().getSelectedItem();
            String newStatus = statusDropdown.getValue();
            if (selected != null && newStatus != null) {
                UsersController.updateTaskStatus(selected.getId(), newStatus);
                refreshTasks();
            }
        });

        VBox root = new VBox(15, title, taskTable, statusDropdown, updateBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("User Panel");
        stage.show();
    }

    private void refreshTasks() {
        List<Task> userTasks = DBHandler.getTasksByUserId(user.getId());
        taskTable.setItems(FXCollections.observableArrayList(userTasks));
    }
}
