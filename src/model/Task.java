package model;

public class Task {
    private int id;
    private String title;
    private String description;
    private String status; // e.g., "Not Started", "In Progress", "Completed"
    private int assignedUserId;

    public Task(int id, String title, String description, String status, int assignedUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedUserId = assignedUserId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public int getAssignedUserId() { return assignedUserId; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setAssignedUserId(int assignedUserId) { this.assignedUserId = assignedUserId; }
}
