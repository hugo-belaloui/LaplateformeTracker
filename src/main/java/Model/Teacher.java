package Model;
import utils.DatabaseConnection;
import java.sql.*;;

public class Teacher {
    private Long id ;
    private String name;
    private Long userId;

    public Teacher (Long id, String name, Long userId){
        this .id = id;
        this.name = name;
        this.userId = userId;
    }

    public Long getId(){return id;}
    public String getName(){return name;}
    public Long getUserId(){return userId;}

    public static Teacher findByUserId(Long userId){
        try (Connection conn = DatabaseConnection.getconnection() ){
            String sql = "SELECT * FROM teachers WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Teacher (
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getLong("user_id")
                );
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
