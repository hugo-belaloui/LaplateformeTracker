package db;

import java.sql.Connection;
import java.sql.Statement;
import utils.DatabaseConnection;;


public class initdatabase {
    public static void init(){
        createUserTable();
        createStudentTable();
        createTeacherTable();
    }

    private static void createUserTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY ,
                    email VARCHAR(255) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    role VARCHAR(50) NOT NULL
                );
                """;

        execute(sql);
    }

    private static void createStudentTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS students(
                    id SERIAL PRIMARY KEY,
                    user_id INT REFERENCES users(id),
                    name VARCHAR (255)
                );
                """;
        
        execute(sql);
    }

    private static void createTeacherTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS teachers(
                    id SZERIAL PRIMARY KEY,
                    user_id INT REFERENCES users(id),
                    name VARCHAR (255)
                );
                """;

        execute(sql);
    }

    private static void execute(String sql) {
        try (Connection conn = DatabaseConnection.getconnection();
            Statement stmt = conn.createStatement()) {
            
            stmt.execute(sql);

        } catch (Exception e ) {
                e.printStackTrace();
        }
    } 
}
