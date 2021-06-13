package main.database;

import main.entity.CatalogItem;
import main.exception.AppException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CatalogDAOTest {

    static MockedStatic<DBManager> dbManagerMockedStatic;

    @BeforeAll
    public static void setUp() {
        dbManagerMockedStatic = Mockito.mockStatic(DBManager.class);
    }

    @Test
    void getCountOfGoodsTest() throws SQLException, AppException {
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
        CatalogDAO catalogDAO = new CatalogDAO();
        int result = catalogDAO.getCountOfGoods();
        Assert.assertEquals(1, result);
    }

    @Test
    void getListOfSortedItemsTest() throws SQLException, AppException {
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
        CatalogDAO catalogDAO = new CatalogDAO();
        Map<Integer, Object> result = catalogDAO.getListOfSortedItems(new String[]{"size:3M"}, "price", "asc", 0);
        Map<Integer,Object> expected = new HashMap<>();
        assertEquals(expected.getClass(),result.getClass());
    }

    @Test
    void getItemsByGoodsIdTest() throws SQLException, AppException {
        CatalogItem catalogItem;
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt(any(String.class))).thenReturn(1);
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getLong("goodsParamId")).thenReturn(1L);
        when(rs.getDouble(any(String.class))).thenReturn(1.0);
        when(rs.getString(any(String.class))).thenReturn("goods");
        when(rs.getDate(any())).thenReturn(null);
        List<CatalogItem> expected=new ArrayList<>();
        CatalogDAO catalogDAO = new CatalogDAO();
        List<Long> list=new ArrayList<>();
        List<CatalogItem> result=catalogDAO.getItemsByGoodsId(list);
        assertEquals(expected.getClass(),result.getClass());
    }

    @Test
    void getItemByGoodsIdTest() throws SQLException, AppException {
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
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getLong("goodsParamId")).thenReturn(1L);
        when(rs.getDouble(any(String.class))).thenReturn(1.0);
        when(rs.getString(any(String.class))).thenReturn("goods");
        when(rs.getDate(any())).thenReturn(null);
        CatalogItem catalogItem=new CatalogDAO().getItemByGoodsId(1L);
        CatalogItem expected=new CatalogItem();
        assertEquals(expected.getClass(),catalogItem.getClass());
    }

    @Test
    public void updateCatalogItemTest() throws SQLException, AppException {
        DBManager dbManager = mock(DBManager.class);
        Connection con = mock(Connection.class);
        PreparedStatement pstm = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        dbManagerMockedStatic.when(DBManager::getInstance).thenReturn(dbManager);
        when(dbManager.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(pstm);
        when(pstm.executeUpdate()).thenReturn(0);
        CatalogItem catalogItem=new CatalogItem();
        new CatalogDAO().updateCatalogItem(catalogItem);
    }
}