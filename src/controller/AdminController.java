package controller;

import db.DBHandler;
import model.Task;

public class AdminController {
    public static void assignTask(String title, String description, int userId) {
        Task task = new Task(0, title, description, "Not Started", userId);
        DBHandler.addTask(task);
        System.out.println("✅ Task assigned to user ID: " + userId);
    }
}
