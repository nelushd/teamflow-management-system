package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import model.Task;
import model.User;



public class DBHandler {

    private static final String DB_URL = "jdbc:sqlite:teamflow.db";

    // Get DB connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create tables if they don’t exist
    public static void initDatabase() {
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "role TEXT NOT NULL);";

        String createTaskTable = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "description TEXT," +
                "status TEXT," +
                "assigned_user_id INTEGER," +
                "FOREIGN KEY (assigned_user_id) REFERENCES users(id));";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTable);
            stmt.execute(createTaskTable);
            System.out.println("✅ Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("❌ DB Init Error: " + e.getMessage());
        }
    }

    // Login method
    public static User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
        return null;
    }

    // Add user
    public static void addUser(User user) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Add user failed: " + e.getMessage());
        }
    }
    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getInt("assigned_user_id")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching all tasks: " + e.getMessage());
        }

        return tasks;
    }

    // Get all users
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }
        } catch (SQLException e) {
            System.out.println("❌ Fetch users failed: " + e.getMessage());
        }
        return users;
    }

    // Add task
    public static void addTask(Task task) {
        String sql = "INSERT INTO tasks(title, description, status, assigned_user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getAssignedUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Add task failed: " + e.getMessage());
        }
    }

    // Get tasks for user
    public static List<Task> getTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assigned_user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(rs.getInt("id"), rs.getString("title"),
                        rs.getString("description"), rs.getString("status"), userId));
            }
        } catch (SQLException e) {
            System.out.println("❌ Get tasks failed: " + e.getMessage());
        }
        return tasks;
    }

    // Update task status
    public static boolean updateTaskStatus(int taskId, String newStatus) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, taskId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Failed to update task status: " + e.getMessage());
            return false;
        }
    }


    public static boolean deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Failed to delete task: " + e.getMessage());
            return false;
        }
    }

    public static boolean insertTask(Task task) {
        String sql = "INSERT INTO tasks(title, description, status, assigned_user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getAssignedUserId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Failed to insert task: " + e.getMessage());
            return false;
        }
    }
    public static Map<User, Map<String, Integer>> getUserTaskProgress() {
        Map<User, Map<String, Integer>> report = new HashMap<>();

        String sql = "SELECT u.id, u.username, u.role, t.status, COUNT(t.id) as count " +
                "FROM users u LEFT JOIN tasks t ON u.id = t.assigned_user_id " +
                "GROUP BY u.id, t.status";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Map<Integer, User> userCache = new HashMap<>();

            while (rs.next()) {
                int userId = rs.getInt("id");
                User user = userCache.get(userId);

                if (user == null) {
                    user = new User(userId, rs.getString("username"), rs.getString("role"), "");

                    userCache.put(userId, user);
                    report.put(user, new HashMap<>());
                }

                String status = rs.getString("status");
                if (status == null) status = "No Tasks";

                int count = rs.getInt("count");
                report.get(user).put(status, count);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error generating user task progress report: " + e.getMessage());
        }

        return report;
    }
    public static boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, assigned_user_id = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getAssignedUserId());
            stmt.setInt(5, task.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Failed to update task: " + e.getMessage());
            return false;
        }
    }

}
