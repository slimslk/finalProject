package com.epam.finalProject.database;

import com.epam.finalProject.entity.User;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDAOTest {

    static MockedStatic<DBManager> dbManagerMockedStatic;

    @BeforeAll
    public static void setUp() {
        dbManagerMockedStatic = Mockito.mockStatic(DBManager.class);
    }

    @AfterAll
    public static void tearDown() {
        dbManagerMockedStatic.close();
    }

    @Test
    void updateUserStatus() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeUpdate()).thenReturn(1);
        new UserDAO().updateUserStatus("lili@gmail.com",3);
    }

    @Test
    void updateUserLocale() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeUpdate()).thenReturn(1);
        new UserDAO().updateUserLocale("lili@gmail.com","ru");
    }

    @Test
    void getUsers() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(any(String.class))).thenReturn(1);
        List<User> expected=new ArrayList<>();
        List<User> result = new UserDAO().getUsers();
        Assert.assertEquals(expected.getClass(), result.getClass());
    }

    @Test
    void getUserByUsername() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(any(String.class))).thenReturn(1);
        User result = new UserDAO().getUserByUsername("lili@gmail.com");
        Assert.assertEquals(User.class, result.getClass());
    }

    @Test
    void insertUser() throws SQLException, AppException {
        User user=new User();
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeUpdate()).thenReturn(1);
        when(pstm.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(any(String.class))).thenReturn(1);
        new UserDAO().insertUser(user,"user","active");
    }

}