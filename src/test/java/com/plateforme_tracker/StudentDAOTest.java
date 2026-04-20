package com.plateforme_tracker;

import model.Student;
import utils.DatabaseConnection;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StudentDAOTest {

    @Test
    public void testSaveStudent() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mock(java.sql.ResultSet.class));

        try (MockedStatic<DatabaseConnection> mockedDb = mockStatic(DatabaseConnection.class)) {
            mockedDb.when(DatabaseConnection::getConnection).thenReturn(mockConnection);

            // Using the correct constructor: Student(Long id, String firstName, String lastName, byte age, Long userId)
            Student student = new Student(null, "Hugo", "Belaloui", (byte) 25, 1L);
            
            student.save();

            verify(mockPreparedStatement, times(1)).setString(1, "Hugo");
            verify(mockPreparedStatement, times(1)).setString(2, "Belaloui");
            verify(mockPreparedStatement, times(1)).setByte(3, (byte) 25);
            
            verify(mockPreparedStatement, times(1)).executeUpdate();
        }
    }
}