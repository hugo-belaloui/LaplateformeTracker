package Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import utils.DatabaseConnection;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private Long userId;
    private byte age;
    private ArrayList<Double> grades;

    public Student(Long id, String firstName, String lastName, byte age, Long userId){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.age = age;
        this.grades = new ArrayList<>();
    }

    public Long getId(){ return id; }
    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }
    public Long getUserId(){ return userId; }
    public byte getAge(){ return age; }
    public ArrayList<Double> getGrades(){ return grades; }

    public void addGrade(double grade){
        this.grades.add(grade);
    }

    public void addGrades(double[] newGrades){
        for (double grade : newGrades) {
            this.grades.add(grade);
        }
    }


    public static Student findByUserId(Long userId){
        try (Connection conn = DatabaseConnection.getconnection() ){
            String sql = "SELECT FROM students WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString(null),
                    rs.getByte("age"),
                    rs.getLong("user_id")
                );
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
