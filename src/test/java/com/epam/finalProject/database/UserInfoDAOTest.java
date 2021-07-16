package com.epam.finalProject.database;

import com.epam.finalProject.entity.UserInfo;
import com.epam.finalProject.exception.AppException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserInfoDAOTest {

    static MockedStatic<DBManager> dbManagerMockedStatic;

    @BeforeAll
    public static void setUp() {
        dbManagerMockedStatic = Mockito.mockStatic(DBManager.class);
    }

    @AfterAll
    public static void tearDown(){
        dbManagerMockedStatic.close();
    }

    @Test
    void getUserInfoById() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        UserInfoDAO userInfoDAO = new UserInfoDAO();
        UserInfo result = userInfoDAO.getUserInfoById(1L);
        Assert.assertEquals(UserInfo.class, result.getClass());
    }

    @Test
    void insertUserInfo() throws SQLException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeUpdate()).thenReturn(1);
    }
}