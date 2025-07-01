package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
}
