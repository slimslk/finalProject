package com.epam.finalProject.database;

import com.epam.finalProject.database.impl.ChangeGoodsDAOImpl;
import com.epam.finalProject.exception.AppException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChangeGoodsDAOImplImplTest {
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
    void removeGoodsTest() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeUpdate()).thenReturn(1);
        ChangeGoodsDAOImpl changeGoodsDAOImpl = new ChangeGoodsDAOImpl();
        changeGoodsDAOImpl.removeGoods(10);

    }
}