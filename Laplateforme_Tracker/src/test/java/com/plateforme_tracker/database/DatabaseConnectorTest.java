package com.plateforme_tracker.database;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectorTest 
{
    @Test
    public void testGetConnection() 
    {
        Connection connection = DatabaseConnector.getConnection();

        assertNotNull(connection, "connection failed");

        //close connection once test done
        try 
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch (SQLException except)
        {
            fail("error");
        }
    }
}
