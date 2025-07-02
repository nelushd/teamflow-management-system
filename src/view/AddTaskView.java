package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import model.Task;
import db.DBHandler;


public class AddTaskView extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        TextField titleField = new TextField();
        TextArea descriptionField = new TextArea();
        DatePicker dueDatePicker = new DatePicker();
        Button saveButton = new Button("Save Task");

        saveButton.setOnAction(e -> {
            String title = titleField.getText();
            String desc = descriptionField.getText();
            LocalDate dueDate = dueDatePicker.getValue();

            Task task = new Task(title, desc, dueDate.toString(), false);
            DBHandler.addTask(task);
        });

        root.getChildren().addAll(titleField, descriptionField, dueDatePicker, saveButton);
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Task");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
