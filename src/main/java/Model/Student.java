package Model;
import java.sql.Array;
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


    public static ArrayList<Student> findAll(){
        ArrayList<Student> students = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getconnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student s = new Student(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getByte("age"),
                    rs.getLong("user_id")
                );

                Array gradesArray = rs.getArray("grades");
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

    public void save(){
        try (Connection conn = DatabaseConnection.getconnection()){
            String sql = "INSERT INTO students (first_name, last_name, age) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setByte(3, this.age);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGrades(){
        try (Connection conn = DatabaseConnection.getconnection()){
            String sql = "UPDATE students SET grades = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            Double[] gradesArray = this.grades.toArray(new Double[0]);
            Array sqlArray = conn.createArrayOf("float8", gradesArray);
            stmt.setArray(1, sqlArray);
            stmt.setLong(2, this.id);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadGrades(){
        try (Connection conn = DatabaseConnection.getconnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT grades FROM students WHERE id = ?");
            stmt.setLong(1, this.id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.grades.clear();
                Array gradesArray = rs.getArray("grades");
                if (gradesArray != null) {
                    Double[] gradesRaw = (Double[]) gradesArray.getArray();
                    for (double grade : gradesRaw) {
                        this.grades.add(grade);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        try (Connection conn = DatabaseConnection.getconnection()){
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE id = ?");
            stmt.setLong(1, this.id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Student findByUserId(Long userId){
        try (Connection conn = DatabaseConnection.getconnection() ){
            String sql = "SELECT * FROM students WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
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

                Array gradesArray = rs.getArray("grades");
                if (gradesArray != null) {
                    Double[] gradesRaw = (Double[]) gradesArray.getArray();
                    for (double grade : gradesRaw) {
                        student.addGrade(grade);
                    }
                }

                return student;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
