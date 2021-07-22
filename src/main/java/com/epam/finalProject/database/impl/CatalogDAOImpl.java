package com.epam.finalProject.database.impl;

import com.epam.finalProject.database.CatalogDAO;
import com.epam.finalProject.database.DBManager;
import com.epam.finalProject.database.Fields;
import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogDAOImpl implements CatalogDAO {
    private static final Logger log = LogManager.getLogger(CatalogDAOImpl.class);

    private final int COUNT_OF_ROWS = 3;
    private final DBManager dbManager = DBManager.getInstance();
    private final String GET_ITEM_BY_GOODS_ID = "SELECT * FROM itemsCatalog WHERE goodsParamId=?";
    private final String UPDATE_ITEM_BY_GOODS_ID = "UPDATE itemsCatalog SET price=?, quantity=?, addDate=? WHERE goodsParamId=?;";
    private final String GET_COUNT_OF_GOODS = "SELECT COUNT(id) FROM itemsCatalog";
    private final String COUNT_OF_GOODS = "SELECT COUNT(iC.id)";

    private final String GET_LIST_OF_GOODS_1 = "SELECT name,\n" +
            "       genderName,\n" +
            "       ageName,\n" +
            "       sizeName,\n" +
            "       categoryName,\n" +
            "       styleName,\n" +
            "       price,\n" +
            "       quantity,\n" +
            "       addDate,\n" +
            "       iC.id,\n" +
            "       goodsParamId\n";
    private final String GET_LIST_OF_GOODS_2 = "FROM goodsParam\n" +
            "         INNER JOIN goods g ON g.id = goodsId\n" +
            "         INNER JOIN gender g2 on goodsParam.genderId = g2.id\n" +
            "         INNER JOIN age a on goodsParam.ageId = a.id\n" +
            "         INNER JOIN size s on goodsParam.sizeId = s.id\n" +
            "         INNER JOIN category c on goodsParam.categoryId = c.id\n" +
            "         INNER JOIN style s2 on goodsParam.styleId = s2.id\n" +
            "         INNER JOIN itemsCatalog iC on goodsParam.id = iC.goodsParamId\n";

    /**
     * This method creating a SQL expression with multiple parameters of sort and
     *
     * @return List of Catalog items
     */

    public int getCountOfGoods() throws AppException {
        int count = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_COUNT_OF_GOODS);
            rs = pstm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count(id)");
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            e.printStackTrace();
            throw new AppException("Database in't response", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return count;
    }

    public Map<Integer, Object> getListOfSortedItems(String[] param, String sort, String dir, int start) throws AppException {
        StringBuilder expression = new StringBuilder();
        Map<Integer, Object> map = new HashMap<>();
        Map<Integer, Object> returnedMap = new HashMap<>();
        int mapCounter = 1;
        expression.append(GET_LIST_OF_GOODS_2);
        if (param != null) {
            int i = 0;
            expression.append("WHERE ");
            while (i < param.length) {
                if (mapCounter > 1) {
                    expression.append(" AND ");
                }
                String[] split = param[i].split(":");
                String s = Fields.PARAM_MAP.get(split[0]);
                if (s != null && split.length > 1) {
                    if (split[0].equals("price")) {
                        if (split.length > 2) {
                            expression.append(s);
                            try {
                                map.put(mapCounter++, Integer.parseInt(split[1]));
                                map.put(mapCounter++, Integer.parseInt(split[2]));
                            } catch (NumberFormatException e) {
                                log.error("One of a value of a parameter price is not a integer number");
                                throw new AppException("One of a value of a parameter price is not a integer number", e);
                            }
                        }
                    } else {
                        expression.append(s);
                        for (int j = 1; j < split.length; j++) {
                            if (Fields.VALUES_SET.contains(split[j])) {
                                expression.append("?,");
                                map.put(mapCounter, split[j]);
                                mapCounter++;
                            }
                        }
                        expression.setLength(expression.length() - 1);
                        expression.append(")");
                    }
                }
                i++;
            }
        }
        if (Fields.SORT_MAP.containsKey(sort) && Fields.SORT_MAP.containsKey(dir)) {
            expression.append(" ORDER BY CASE WHEN quantity = 0 THEN quantity END, ").append(Fields.SORT_MAP.get(sort))
                    .append(" ").append(Fields.SORT_MAP.get(dir));
        }

        StringBuilder countSB_SQL = new StringBuilder();
        countSB_SQL.append(COUNT_OF_GOODS).append(expression);
        map.put(0, countSB_SQL.toString());
        Integer count = getCountOfItemsFromDB(map);
        log.error("CatalogDAOImpl -> count is: " + count);
        if (start >= 0) {
            expression.append(" LIMIT ?,?;");
            map.put(mapCounter++, start);
            map.put(mapCounter, COUNT_OF_ROWS);
        }
        expression.insert(0, GET_LIST_OF_GOODS_1);
        map.put(0, expression.toString());
        List<CatalogItem> catalogItemList = getItemsFromDB(map);
        returnedMap.put(0, count);
        returnedMap.put(1, catalogItemList);
        return returnedMap;
    }

    public int getCountOfItemsFromDB(Map<Integer, Object> stringMap) throws AppException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            int count = 0;
            pstm = connection.prepareStatement((String) stringMap.get(0));
            for (Map.Entry<Integer, Object> entry : stringMap.entrySet()) {
                if (entry.getKey() != 0) {
                    if (entry.getValue().getClass().equals(Integer.class)) {
                        pstm.setInt(entry.getKey(), (Integer) entry.getValue());
                        log.trace("Key: " + entry.getKey() + " = " + entry.getValue());
                    }
                    if (entry.getValue().getClass().equals(String.class)) {
                        pstm.setString(entry.getKey(), (String) entry.getValue());
                        log.trace("Key: " + entry.getKey() + " = " + entry.getValue());
                    }
                }
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            connection.commit();
            return count;
        } catch (SQLException ex) {
            log.error("cant get count of items from DB");
            dbManager.rollback(connection);
            throw new AppException("Database is not available now. Try later.", ex);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(connection);
        }
    }


    public List<CatalogItem> getItemsFromDB(Map<Integer, Object> stringMap) throws AppException {
        log.error("In getItemsFromDB");
        List<CatalogItem> catalogItemList = new ArrayList<>();
        List<CatalogItem> catalogItemListWithZeroQuantity = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            CatalogItem catalogItem;
            pstm = con.prepareStatement((String) stringMap.get(0));
            for (Map.Entry<Integer, Object> entry : stringMap.entrySet()) {
                if (entry.getKey() != 0) {
                    if (entry.getValue().getClass().equals(String.class)) {
                        pstm.setString(entry.getKey(), (String) entry.getValue());
                        log.trace("Key: " + entry.getKey() + " = " + entry.getValue());
                    }
                    if (entry.getValue().getClass().equals(Integer.class)) {
                        pstm.setInt(entry.getKey(), (Integer) entry.getValue());
                        log.trace("Key: " + entry.getKey() + " = " + entry.getValue());
                    }
                }
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                catalogItem = mapRow(rs);
                if (catalogItem.getQuantity() == 0) {
                    catalogItemListWithZeroQuantity.add(catalogItem);
                } else {
                    catalogItemList.add(catalogItem);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cant get item list from DB");
            dbManager.rollback(con);
            throw new AppException("Can't get items from Database", ex);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
//        log.error("==> List of goods: " + catalogItemList);
        catalogItemList.addAll(catalogItemListWithZeroQuantity);
        return catalogItemList;
    }

    public CatalogItem getItemByGoodsId(long id) throws AppException {
        CatalogItem cItem = new CatalogItem();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ITEM_BY_GOODS_ID);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                cItem = mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Can't get items from Database", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return cItem;
    }

    public List<CatalogItem> getItemsByGoodsId(List<Long> list) throws AppException {
        List<CatalogItem> catalogList = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ITEM_BY_GOODS_ID);
            for (long id : list) {
                pstm.setLong(1, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    catalogList.add(mapRow(rs));
                }
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Can't get items from Database", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return catalogList;
    }

    public void updateCatalogItem(CatalogItem catalogItem) throws AppException {
        Connection connection = null;
        int i = 0;
        try {
            connection = dbManager.getConnection();
            try (PreparedStatement pstm = connection.prepareStatement(UPDATE_ITEM_BY_GOODS_ID)) {
                pstm.setDouble(++i, catalogItem.getPrice());
                pstm.setInt(++i, catalogItem.getQuantity());
                pstm.setDate(++i, (Date) catalogItem.getAddDate());
                pstm.setLong(++i, catalogItem.getGoodsParamId());
                pstm.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            dbManager.rollback(connection);
            throw new AppException("Can't update catalog items to Database", e);
        } finally {
            dbManager.close(connection);
        }
    }

    private CatalogItem mapRow(ResultSet rs) {
        try {
            CatalogItem catalogItem = new CatalogItem();
            catalogItem.setId(rs.getLong("id"));
            long gpId = rs.getLong("goodsParamId");
            catalogItem.setGoodsParamId(gpId);
            catalogItem.setPrice(rs.getDouble("price"));
            catalogItem.setQuantity(rs.getInt("quantity"));
            catalogItem.setAddDate(rs.getDate("addDate"));
            catalogItem.setGoodsParam(new GoodsParamDAOImpl().getGoodsParamByID(gpId));
            long goodsId = catalogItem.getGoodsParam().getGoodsId();
            catalogItem.setGoods(new GoodsDAOImpl().getGoodsById(goodsId));
            return catalogItem;
        } catch (SQLException | AppException e) {
            e.printStackTrace();
            return null;
        }
    }
}
