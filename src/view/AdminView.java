package view;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import controller.AdminController;
import db.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;
import model.Task;

import java.util.List;

public class AdminView {
    private final User admin;

    public AdminView(User admin) {
        this.admin = admin;
    }

    public void show(Stage stage) {
        Label title = new Label("👨‍💼 Admin Dashboard - Welcome " + admin.getUsername());

        // User dropdown
        ComboBox<User> userDropdown = new ComboBox<>();
        userDropdown.setItems(FXCollections.observableArrayList(DBHandler.getAllUsers()));
        userDropdown.setPromptText("Select user");

        // Task form
        TextField titleField = new TextField();
        titleField.setPromptText("Task Title");

        TextArea descField = new TextArea();
        descField.setPromptText("Description");
        descField.setPrefRowCount(3);

        Button assignBtn = new Button("Assign Task");

        // Task Table
        TableView<Task> taskTable = new TableView<>();
        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));


        TableColumn<Task, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        taskTable.getColumns().addAll(titleCol, statusCol);

        // Refresh all tasks
        Button refreshBtn = new Button("🔁 Refresh Tasks");

        assignBtn.setOnAction(e -> {
            User selectedUser = userDropdown.getValue();
            if (selectedUser != null && !titleField.getText().isEmpty()) {
                AdminController.assignTask(titleField.getText(), descField.getText(), selectedUser.getId());
                titleField.clear();
                descField.clear();
                taskTable.setItems(FXCollections.observableArrayList(DBHandler.getAllTasks()));
            }
        });

        refreshBtn.setOnAction(e -> {
            taskTable.setItems(FXCollections.observableArrayList(DBHandler.getAllTasks()));
        });

        VBox formBox = new VBox(10, new Label("Assign Task:"), titleField, descField, userDropdown, assignBtn);
        VBox rightBox = new VBox(10, new Label("All Tasks:"), taskTable, refreshBtn);

        HBox layout = new HBox(20, formBox, rightBox);
        VBox root = new VBox(20, title, layout);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 700, 400));
        stage.setTitle("Admin Panel");
        stage.show();
    }
}
