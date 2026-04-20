package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DatabaseConnection;

public class ClassName {
    private Long id;
    private String name;
    private ArrayList<Student> students;

    public ClassName(Long id, String name) {
        this.id = id;
        this.name = name;
        this.students = new ArrayList<>();
    }

    // getters
    public Long getId()                     { return id; }
    public String getName()                 { return name; }
    public ArrayList<Student> getStudents() { return students; }

    // setter
    public void setName(String name) { this.name = name; }

    // add a student to this class
    public void addStudent(Student student) {
        this.students.add(student);
    }

    // find a class by its id
    public static ClassName findById(Long id) {
        String sql = "SELECT * FROM classes WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ClassName(
                    rs.getLong("id"),
                    rs.getString("name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
