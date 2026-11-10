import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


public class DatabaseConnection {

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:sqlite:src/database/database.db"
        );
    }

	//Log in valifation mechanic
    public static int validateUser(String username, String password) {
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Returns the found UID
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Login failed
    }
	
	/**
     * Inserts a new user into the database and returns the generated UID.
     * Returns -1 if the registration fails (e.g., username already exists).
     */
    public static int registerUser(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return generated user ID (uid)
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        String createTasksTable = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                description TEXT,
                completed INTEGER NOT NULL DEFAULT 0
            )
            """;

		String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            )
            """;
//Made a quick change as for the table to made of strings to compare in the log in screen! Alex

        String sql = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                description TEXT,
                completed INTEGER NOT NULL DEFAULT 0
            )
            """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createTasksTable);
            stmt.executeUpdate(createUsersTable);

            stmt.executeUpdate(sql);
            System.out.println("Database connected successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}

	


