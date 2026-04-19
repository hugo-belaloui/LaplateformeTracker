package Model;

import java.sql.*;
import utils.DatabaseConnection;

public class User {
    private Long id ;
    private String email ; 
    private String password ;
    private String role ;

    public User(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getPassword(){return password;}
    public String getRole(){return role;}
    public String getEmail(){return email;}
    public Long getId(){return id;}


    public static User findByEmail(String email){
        try (Connection conn = DatabaseConnection.getconnection()){

            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stml = conn.prepareStatement(sql);
            stml.setString(1, email);

            ResultSet rs = stml.executeQuery();

            if (rs.next()){
                return new User(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
