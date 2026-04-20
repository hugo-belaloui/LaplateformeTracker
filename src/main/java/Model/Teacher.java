package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Utils.DatabaseConnection;

public class Teacher {
    private Long id;
    private String firstName;
    private String lastName;
    private Long userId;

    public Teacher(Long id, String firstName, String lastName, Long userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    // getters
    public Long getId()          { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public Long getUserId()      { return userId; }

    // setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }

    // find teacher by their user_id
    public static Teacher findByUserId(Long userId) {
        String sql = "SELECT * FROM teachers WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Teacher(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getLong("user_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // save new teacher to database
    public void save() {
        String sql = "INSERT INTO teachers (first_name, last_name, user_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setLong(3, this.userId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // delete this teacher from database
    public void delete() {
        String sql = "DELETE FROM teachers WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, this.id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}