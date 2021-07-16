package com.epam.finalProject.database;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ChangeGoodsDAO {
    private static final Logger log = LogManager.getLogger(ChangeGoodsDAO.class);

    private final String UPDATE_GOODS_ITEM = "UPDATE goods SET name=?, img=? WHERE id=?";
    private final String UPDATE_GOODSPARAM_ITEM = "UPDATE goodsParam SET goodsId=?,genderId=?,ageId=?,sizeId=?,categoryId=?,styleId=? WHERE id=?";
    private final String UPDATE_CATALOG_ITEM = "UPDATE itemsCatalog SET goodsParamId=?, price=?,quantity=?,addDate=? WHERE id=?";
    private final String SELECT_AGEID_BY_NAME = "SELECT id FROM age WHERE ageName=?;";
    private final String SELECT_CATEGORYID_BY_NAME = "SELECT id FROM category WHERE categoryName=?;";
    private final String SELECT_GENDERID_BY_NAME = "SELECT id FROM gender WHERE genderName=?;";
    private final String SELECT_SIZEID_BY_NAME = "SELECT id FROM size WHERE sizeName=?;";
    private final String SELECT_STYLEID_BY_NAME = "SELECT id FROM style WHERE styleName=?;";
    private final String REMOVE_GOODS_BY_ID = "DELETE FROM goodsParam WHERE id=?";
    private final String INSERT_GOODS = "INSERT INTO goods (name, img) VALUES (?,?);";
    private final String INSERD_GOODS_PARAM = "INSERT INTO goodsParam (goodsId, genderId, ageId, sizeId, categoryId, styleId) VALUES (LAST_INSERT_ID(),?,?,?,?,?);";
    private final String INSERT_ITEMCATALOG = "INSERT INTO itemsCatalog (goodsParamId, price, quantity, addDate) VALUES (LAST_INSERT_ID(),?,?,?);";

    public void removeGoods(long id) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            pstm = connection.prepareStatement(REMOVE_GOODS_BY_ID);
            pstm.setLong(1, id);
            pstm.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            dbManager.rollback(connection);
            throw new AppException("Cant remove goods from Database, try later", e);
        } finally {
            dbManager.close(pstm);
            dbManager.close(connection);
        }
    }

    public void updateGoods(CatalogItem catalogItem) throws AppException {
        executeAction(catalogItem, UPDATE_GOODS_ITEM, UPDATE_GOODSPARAM_ITEM, UPDATE_CATALOG_ITEM);
    }

    public void createGoods(CatalogItem catalogItem) throws AppException {
        long num = -999;
        catalogItem.getGoods().setId(num);
        catalogItem.getGoodsParam().setId(num);
        catalogItem.setId(num);
        executeAction(catalogItem, INSERT_GOODS, INSERD_GOODS_PARAM, INSERT_ITEMCATALOG);
    }

    private void executeAction(CatalogItem catalogItem, String sqlGoods, String sqlGoodsParam, String sqlItemCatalog) throws AppException {
        log.error("goods: " + sqlGoods);
        log.error("goodsParam: " + sqlGoodsParam);
        log.error("itemCatalog: " + sqlItemCatalog);
        Map<String, Integer> goodsParamNameMap = new HashMap<>();
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        log.error("execute action catalog item: "+catalogItem);
        int i = 0;
        try {
            connection = dbManager.getConnection();
            pstm = connection.prepareStatement(SELECT_AGEID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getAgeName().toLowerCase());
            rs = pstm.executeQuery();
            while (rs.next()) {
                log.error("AGE");
                goodsParamNameMap.put("age", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_CATEGORYID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getCategoryName().toLowerCase());
            rs = pstm.executeQuery();
            while (rs.next()) {
                log.error("CATEGORY");
                goodsParamNameMap.put("category", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_GENDERID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getGenderName().toLowerCase());
            rs = pstm.executeQuery();
            while (rs.next()) {
                log.error("GENDER");
                goodsParamNameMap.put("gender", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_SIZEID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getSizeName().toLowerCase());
            rs = pstm.executeQuery();
            while (rs.next()) {
                log.error("SIZE");
                goodsParamNameMap.put("size", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_STYLEID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getStyleName().toLowerCase());
            rs = pstm.executeQuery();
            while (rs.next()) {
                log.error("STYLE");
                goodsParamNameMap.put("style", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(sqlGoods);
            pstm.setString(++i, catalogItem.getGoods().getName());
            pstm.setString(++i, catalogItem.getGoods().getImg());
            long goodsId = catalogItem.getGoods().getId();
            if (goodsId > 0) {
                pstm.setLong(++i, goodsId);
            }
            pstm.executeUpdate();
            i = 0;
            log.error("Map is: " + goodsParamNameMap);
            pstm = connection.prepareStatement(sqlGoodsParam);
            if (goodsId > 0) {
                pstm.setLong(++i, goodsId);
            }
            pstm.setInt(++i, goodsParamNameMap.get("gender"));
            pstm.setInt(++i, goodsParamNameMap.get("age"));
            pstm.setInt(++i, goodsParamNameMap.get("size"));
            pstm.setInt(++i, goodsParamNameMap.get("category"));
            pstm.setInt(++i, goodsParamNameMap.get("style"));
            long goodsParamId = catalogItem.getGoodsParam().getId();
            if (goodsParamId > 0) {
                pstm.setLong(++i, goodsParamId);
            }
            pstm.executeUpdate();
            i = 0;
            pstm = connection.prepareStatement(sqlItemCatalog);
            if (goodsParamId > 0) {
                pstm.setLong(++i, catalogItem.getGoodsParam().getId());
            }
            pstm.setDouble(++i, catalogItem.getPrice());
            pstm.setInt(++i, catalogItem.getQuantity());
            pstm.setDate(++i, (Date) catalogItem.getAddDate());
            if (catalogItem.getId() != -999) {
                pstm.setLong(++i, catalogItem.getId());
            }
            pstm.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            dbManager.rollback(connection);
            throw new AppException("Can't update/add goods to Database", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(connection);
            log.error("End of method");
        }
    }
}
