package utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class databaseconnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/laplateforme_tracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "010203";

    public static Connection getconnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
