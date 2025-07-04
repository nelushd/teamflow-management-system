package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import model.User;

public class DBHandler {

    private static final String DB_URL = "jdbc:sqlite:teamflowapp.db";

    // Get DB connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create tables if they don’t exist
    public static void initDatabase() {
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name TEXT NOT NULL,\n" +
                "    username TEXT UNIQUE NOT NULL,\n" +
                "    password TEXT NOT NULL,\n" +
                "    email TEXT,\n" +
                "    role TEXT\n" +
                ");";

        String createTaskTable = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "description TEXT," +
                "due_date TEXT," +
                "completed INTEGER," +
                "assigned_user_id INTEGER," +
                "FOREIGN KEY (assigned_user_id) REFERENCES users(id)" +
                ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTable);
            stmt.execute(createTaskTable);
            System.out.println("✅ Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("❌ DB Init Error: " + e.getMessage());
        }
    }

    // Get user by username and password
    public static User getUserByCredentials(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Login DB Error: " + e.getMessage());
        }

        return null;
    }

    // Get all users (admin view)
    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role")
                );
                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading users: " + e.getMessage());
        }

        return userList;
    }

    // Get all tasks assigned to a specific user
    public static List<Task> getTasksByUserId(int userId) {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assigned_user_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getInt("completed") == 1,
                        rs.getInt("assigned_user_id")
                );
                taskList.add(task);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading tasks: " + e.getMessage());
        }

        return taskList;
    }

    // Mark task as completed
    public static boolean markTaskComplete(int taskId, int userId) {
        String sql = "UPDATE tasks SET completed = 1 WHERE id = ? AND assigned_user_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error updating task: " + e.getMessage());
            return false;
        }
    }
}
