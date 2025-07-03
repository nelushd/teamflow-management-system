package controller;

import db.DBHandler;

public class UsersController {
    public static void updateTaskStatus(int taskId, String newStatus) {
        DBHandler.updateTaskStatus(taskId, newStatus);
    }
}
