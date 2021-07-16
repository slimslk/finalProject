package com.epam.finalProject.database;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.entity.Goods;
import com.epam.finalProject.entity.GoodsParam;
import com.epam.finalProject.exception.AppException;
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
import static org.mockito.Mockito.*;

class ChangeGoodsDAOTest {
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
        ChangeGoodsDAO changeGoodsDAO = new ChangeGoodsDAO();
        changeGoodsDAO.removeGoods(10);

    }

    //    @Test
//    void updateGoodsTest() throws SQLException {
//
//    }
//
    @Test
    void createGoodsTest() throws SQLException, AppException {
        CatalogItem catalogItem = new CatalogItem();
        GoodsParam goodsParam = new GoodsParam();
        DBManager dbManager = mock(DBManager.class);
        goodsParam.setId(2L);
        Goods goods = new Goods();
        catalogItem.setGoodsParam(goodsParam);
        catalogItem.setGoods(goods);
        Connection con = mock(Connection.class);
        PreparedStatement pstm1 = mock(PreparedStatement.class);
        PreparedStatement pstm2 = mock(PreparedStatement.class);
        PreparedStatement pstm3 = mock(PreparedStatement.class);
        PreparedStatement pstm4 = mock(PreparedStatement.class);
        PreparedStatement pstm5 = mock(PreparedStatement.class);
        PreparedStatement pstm6 = mock(PreparedStatement.class);
        PreparedStatement pstm7 = mock(PreparedStatement.class);
        PreparedStatement pstm8 = mock(PreparedStatement.class);
        ResultSet rs1 = mock(ResultSet.class);
        ResultSet rs2 = mock(ResultSet.class);
        ResultSet rs3 = mock(ResultSet.class);
        ResultSet rs4 = mock(ResultSet.class);
        ResultSet rs5 = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement("SELECT id FROM age WHERE ageName=?;")).thenReturn(pstm1);
        when(con.prepareStatement("SELECT id FROM category WHERE categoryName=?;")).thenReturn(pstm2);
        when(con.prepareStatement("SELECT id FROM gender WHERE genderName=?;")).thenReturn(pstm3);
        when(con.prepareStatement("SELECT id FROM size WHERE sizeName=?;")).thenReturn(pstm4);
        when(con.prepareStatement("SELECT id FROM style WHERE styleName=?;")).thenReturn(pstm5);
        when(con.prepareStatement("INSERT INTO goods (name, img) VALUES (?,?);")).thenReturn(pstm6);
        when(con.prepareStatement("INSERT INTO goodsParam (goodsId, genderId, ageId, sizeId, categoryId, styleId) VALUES (LAST_INSERT_ID(),?,?,?,?,?);")).thenReturn(pstm7);
        when(con.prepareStatement("INSERT INTO itemsCatalog (goodsParamId, price, quantity, addDate) VALUES (LAST_INSERT_ID(),?,?,?);")).thenReturn(pstm8);
        when(pstm1.executeQuery()).thenReturn(rs1);
        when(pstm2.executeQuery()).thenReturn(rs2);
        when(pstm3.executeQuery()).thenReturn(rs3);
        when(pstm4.executeQuery()).thenReturn(rs4);
        when(pstm5.executeQuery()).thenReturn(rs5);
        when(rs1.next()).thenReturn(true).thenReturn(false);
        when(rs1.getInt(any(String.class))).thenReturn(1);
        when(rs2.next()).thenReturn(true).thenReturn(false);
        when(rs2.getInt(any(String.class))).thenReturn(1);
        when(rs3.next()).thenReturn(true).thenReturn(false);
        when(rs3.getInt(any(String.class))).thenReturn(1);
        when(rs4.next()).thenReturn(true).thenReturn(false);
        when(rs4.getInt(any(String.class))).thenReturn(1);
        when(rs5.next()).thenReturn(true).thenReturn(false);
        when(rs5.getInt(any(String.class))).thenReturn(1);
        ChangeGoodsDAO changeGoodsDAO = new ChangeGoodsDAO();
        changeGoodsDAO.createGoods(catalogItem);
    }
}