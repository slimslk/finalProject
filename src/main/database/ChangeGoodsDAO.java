package main.database;

import main.entity.CatalogItem;
import main.exception.DBException;
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

    public void removeGoods(long id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            pstm = connection.prepareStatement(REMOVE_GOODS_BY_ID);
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
            throw new DBException("Cant remove goods from Database, try later", e);
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
    }

    public void updateGoods(CatalogItem catalogItem) throws DBException {
        executeAction(catalogItem, UPDATE_GOODS_ITEM, UPDATE_GOODSPARAM_ITEM, UPDATE_CATALOG_ITEM);
    }

    public void createGoods(CatalogItem catalogItem) throws DBException {
        long num = -999;
        catalogItem.getGoods().setId(num);
        catalogItem.getGoodsParam().setId(num);
        catalogItem.setId(num);
        executeAction(catalogItem, INSERT_GOODS, INSERD_GOODS_PARAM, INSERT_ITEMCATALOG);
    }

    public void executeAction(CatalogItem catalogItem, String sqlGoods, String sqlGoodsParam, String sqlItemCatalog) throws DBException {
        log.error("goods: "+sqlGoods);
        log.error("goodsParam: "+sqlGoodsParam);
        log.error("itemCatalog: "+sqlItemCatalog);
        Map<String, Integer> goodsParamNameMap = new HashMap<>();
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int i = 0;
        log.error("Catalog item is: " + catalogItem);
        try {
            connection = dbManager.getConnection();
            pstm = connection.prepareStatement(SELECT_AGEID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getAgeName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                goodsParamNameMap.put("age", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_CATEGORYID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getCategoryName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                goodsParamNameMap.put("category", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_GENDERID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getGenderName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                goodsParamNameMap.put("gender", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_SIZEID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getSizeName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                goodsParamNameMap.put("size", rs.getInt(Fields.ENTITY_ID));
            }
            pstm = connection.prepareStatement(SELECT_STYLEID_BY_NAME);
            pstm.setString(1, catalogItem.getGoodsParam().getStyleName());
            rs = pstm.executeQuery();
            while (rs.next()) {
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
            log.error("Map is: "+goodsParamNameMap);
            pstm = connection.prepareStatement(sqlGoodsParam);
            if (goodsId > 0) {
                log.error("GoodsId is: "+goodsId);
                pstm.setLong(++i, goodsId);
            }
            pstm.setInt(++i, goodsParamNameMap.get("gender"));
            pstm.setInt(++i, goodsParamNameMap.get("age"));
            pstm.setInt(++i, goodsParamNameMap.get("size"));
            pstm.setInt(++i, goodsParamNameMap.get("category"));
            pstm.setInt(++i, goodsParamNameMap.get("style"));
            long goodsParamId = catalogItem.getGoodsParam().getId();
            if(goodsParamId>0){
                pstm.setLong(++i,goodsParamId );
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
            if(catalogItem.getId()!=-999){
                pstm.setLong(++i, catalogItem.getId());
            }
            pstm.executeUpdate();
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
            throw new DBException("Can't update/add goods to Database", e);
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
    }
}