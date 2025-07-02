package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import model.Task;



public class DBHandler {

    private static final String DB_URL = "jdbc:sqlite:teamflow.db";

    // Get DB connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create tables if they don’t exist
    public static void initDatabase() {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            );
        """;

        String createTasksTable = """
                CREATE TABLE IF NOT EXISTS tasks (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      title TEXT NOT NULL,
                      description TEXT,
                      assigned_to INTEGER,
                      due_date TEXT,
                      status TEXT,
                      completed INTEGER DEFAULT 0,
                      FOREIGN KEY (assigned_to) REFERENCES users(id)
                  );
        """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createTasksTable);
            System.out.println("✅ Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error initializing database: " + e.getMessage());
        }
    }

    public static void addTask(Task task) {
        String sql = "INSERT INTO tasks(title, description, due_date, completed) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDueDate());
            stmt.setInt(4, task.isCompleted() ? 1 : 0);
            stmt.executeUpdate();
            System.out.println("✅ Task saved!");
        } catch (SQLException e) {
            System.out.println("❌ Failed to save task: " + e.getMessage());
        }
    }
}
