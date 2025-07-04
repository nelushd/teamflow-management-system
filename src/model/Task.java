package model;

public class Task {
    private int id;
    private String title;
    private String description;
    private String dueDate; // For now we'll use String, later we can use LocalDate
    private boolean completed;
    private int assignedUserId;

    public Task(int id, String title, String description, String dueDate, boolean completed, int assignedUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.assignedUserId = assignedUserId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getAssignedUserId() {
        return assignedUserId;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    @Override
    public String toString() {
        return "Task [" + id + "]: " + title + " | Assigned to User ID: " + assignedUserId +
                " | Due: " + dueDate + " | Completed: " + (completed ? "Yes" : "No");
    }
}
