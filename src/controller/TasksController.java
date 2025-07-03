package controller;

import db.DBHandler;
import model.Task;
import model.User;

import java.util.List;
import java.util.Map;

public class TasksController {

    public List<Task> getAllTasks() {
        return DBHandler.getAllTasks();
    }

    public List<Task> getTasksByUserId(int userId) {
        return DBHandler.getTasksByUserId(userId);
    }

    public boolean addTask(Task task) {
        return DBHandler.insertTask(task);
    }

    public boolean updateTask(Task task) {
        return DBHandler.updateTask(task);
    }

    public boolean deleteTask(int taskId) {
        return DBHandler.deleteTask(taskId);
    }

    public boolean updateTaskStatus(int taskId, String newStatus) {
        return DBHandler.updateTaskStatus(taskId, newStatus);
    }

    // Report: task progress by user
    public Map<User, Map<String, Integer>> getUserTaskProgress() {
        return DBHandler.getUserTaskProgress();
    }
}
