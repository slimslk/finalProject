package com.epam.finalProject.database;

import com.epam.finalProject.entity.GoodsParam;
import com.epam.finalProject.exception.AppException;
import org.junit.Assert;
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

class GoodsParamDAOTest {

    @Test
    void getGoodsParamByID() throws SQLException, AppException {
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
        GoodsParamDAO goodsParamDAO = new GoodsParamDAO();
        GoodsParam result = goodsParamDAO.getGoodsParamByID(1L);
        Assert.assertEquals(GoodsParam.class, result.getClass());
        dbManagerMockedStatic.close();
    }
}