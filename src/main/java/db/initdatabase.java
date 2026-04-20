package Db;

import java.sql.Connection;
import java.sql.Statement;
import Utils.DatabaseConnection;

public class InitDatabase {

    public static void init() {
        createUserTable();
        createTeacherTable();
        createClassTable();
        createStudentTable();
    }

    private static void createUserTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id       SERIAL PRIMARY KEY,
                    email    VARCHAR(255) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    role     VARCHAR(50) NOT NULL
                );
                """;
        execute(sql);
    }

    private static void createTeacherTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS teachers (
                    id         SERIAL PRIMARY KEY,
                    user_id    INT REFERENCES users(id),
                    first_name VARCHAR(255),
                    last_name  VARCHAR(255)
                );
                """;
        execute(sql);
    }

    private static void createClassTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS classes (
                    id         SERIAL PRIMARY KEY,
                    name       VARCHAR(255) NOT NULL,
                    teacher_id INT REFERENCES teachers(id)
                );
                """;
        execute(sql);
    }

    private static void createStudentTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS students (
                    id         SERIAL PRIMARY KEY,
                    user_id    INT REFERENCES users(id),
                    class_id   INT REFERENCES classes(id),
                    first_name VARCHAR(255),
                    last_name  VARCHAR(255),
                    age        SMALLINT,
                    grades     DOUBLE PRECISION[]
                );
                """;
        execute(sql);
    }

    private static void execute(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}