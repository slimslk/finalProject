package com.epam.finalProject.database;

import com.epam.finalProject.entity.Order;
import com.epam.finalProject.exception.AppException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderDAOTest {

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
    void getMaxIdTest() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        Statement st = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(st);
        when(st.executeQuery(any(String.class))).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getLong(any(Integer.class))).thenReturn(1L);
        long result = new OrderDAO().getMaxId();
        assertEquals(1L,result);
    }

    @Test
    void insertOrderTest() throws SQLException, AppException {
        Order order=new Order();
        order.setOrderNumber(2L);
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
        long result = new OrderDAO().insertOrder(order,"");
        assertEquals(2L,result);
    }
}