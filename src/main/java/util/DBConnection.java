package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String DB_URL = getEnv(
            "DB_URL",
            "jdbc:mysql://localhost:3306/ops_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
    );

    private static final String DB_USER = getEnv("DB_USER", "ops_user");
    private static final String DB_PASSWORD = getEnv("DB_PASSWORD", "ops_password");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC driver not found: " + e.getMessage());
        }
    }

    private DBConnection() {
        // Prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}