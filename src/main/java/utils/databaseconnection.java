package Utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/laplateforme_tracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "010203";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
