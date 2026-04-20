package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Utils.DatabaseConnection;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private Long userId;
    private byte age;
    private ArrayList<Double> grades;

    public Student(Long id, String firstName, String lastName, byte age, Long userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.age = age;
        this.grades = new ArrayList<>();
    }

    public Long getId()                  { return id; }
    public String getFirstName()         { return firstName; }
    public String getLastName()          { return lastName; }
    public Long getUserId()              { return userId; }
    public byte getAge()                 { return age; }
    public ArrayList<Double> getGrades() { return grades; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
    public void setAge(byte age)               { this.age = age; }

    public void addGrade(double grade) {
        this.grades.add(grade);
    }

    public void addGrades(double[] newGrades) {
        for (double grade : newGrades) {
            this.grades.add(grade);
        }
    }

    public static ArrayList<Student> findAll() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student s = new Student(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getByte("age"),
                    rs.getLong("user_id")
                );

                java.sql.Array gradesArray = rs.getArray("grades");
                if (gradesArray != null) {
                    Double[] gradesRaw = (Double[]) gradesArray.getArray();
                    for (double grade : gradesRaw) {
                        s.addGrade(grade);
                    }
                }
                students.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public static Student findByUserId(Long userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getByte("age"),
                    rs.getLong("user_id")
                );

                java.sql.Array gradesArray = rs.getArray("grades");
                if (gradesArray != null) {
                    Double[] gradesRaw = (Double[]) gradesArray.getArray();
                    for (double grade : gradesRaw) {
                        student.addGrade(grade);
                    }
                }
                return student;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save() {
        String sql = "INSERT INTO students (first_name, last_name, age, grades) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql,
                 PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setByte(3, this.age);

            Double[] emptyGrades = new Double[0];
            java.sql.Array sqlArray = conn.createArrayOf("float8", emptyGrades);
            stmt.setArray(4, sqlArray);

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                this.id = keys.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGrades() {
        String sql = "UPDATE students SET grades = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Double[] gradesArray = this.grades.toArray(new Double[0]);
            java.sql.Array sqlArray = conn.createArrayOf("float8", gradesArray);
            stmt.setArray(1, sqlArray);
            stmt.setLong(2, this.id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, this.id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}