package Utils;

import java.io.IOException; //deals with input errors
import java.io.InputStream; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties; // stores key=value from properties


public class DatabaseConnection {

    private static final Properties properties = new Properties();
       static   //only execute once when launch program and load class

    {
        //classpath java looks for database.properties without absolute path
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties"))   
        {
            if (input == null) 
            {
            System.out.println("Properties file not found"); //if no file print 
            }
            else
            {
                properties.load(input);
            }
        }
        catch (IOException excep) //print error for debugging
        {
            excep.printStackTrace();
        }
    }

    public static Connection getConnection() //use the import seen above
    {
        Connection connection = null;
        try 
        {
            //retrieve data from properties file
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            //connection to db using said data
            connection = DriverManager.getConnection(url, user, password);
            System.out.print("connected");
        }
        catch (SQLException except)
            {
                System.out.println("connection failed");
                except.printStackTrace();
            }
        return connection; //object is either connected or return failure

    }
}
