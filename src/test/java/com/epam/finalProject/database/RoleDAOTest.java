package com.epam.finalProject.database;

import com.epam.finalProject.exception.AppException;
import org.junit.Assert;

import org.junit.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoleDAOTest {

    @Test
    public void testGetRoleByName() throws AppException, SQLException {
        DBManager dbManager = mock(DBManager.class);
        MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(any(String.class))).thenReturn(1);
        RoleDAO roleDAO = new RoleDAO();
        int result = roleDAO.getRoleByName("slim.slk@gmail.com");
        Assert.assertEquals(1, result);
        dbManagerMockedStatic.close();
    }
}