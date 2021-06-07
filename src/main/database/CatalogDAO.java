package main.database;

import main.entity.CatalogItem;
import main.exception.DBException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogDAO {
    private static final Logger log = LogManager.getLogger(CatalogDAO.class);

    private final int COUNT_OF_ROWS = 9999;
    private final DBManager dbManager = DBManager.getInstance();
    private final String GET_ITEM_BY_GOODS_ID = "SELECT * FROM itemsCatalog WHERE goodsParamId=?";
    private final String UPDATE_ITEM_BY_GOODS_ID = "UPDATE itemsCatalog SET price=?, quantity=?, addDate=? WHERE goodsParamId=?;";

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
            "       goodsParamId\n" +
            "FROM goodsParam\n" +
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

    public List<CatalogItem> getListOfSortedItems(String[] param, String sort, String dir, int start) throws DBException {
        StringBuilder expression = new StringBuilder();
        Map<Integer, Object> map = new HashMap<>();
        int mapCounter = 1;
        expression.append(GET_LIST_OF_GOODS_1);
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
                                return null;
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
            expression.append(" ORDER BY ").append(Fields.SORT_MAP.get(sort))
                    .append(" ").append(Fields.SORT_MAP.get(dir));
        }
        expression.append(" LIMIT ?,?;");
        map.put(0, expression.toString());
        map.put(mapCounter++, start);
        map.put(mapCounter, COUNT_OF_ROWS);
        return getItemsFromDB(map);
    }

    public List<CatalogItem> getItemsFromDB(Map<Integer, Object> stringMap) throws DBException {
        List<CatalogItem> catalogItemList = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            CatalogMapper cMap = new CatalogMapper();
            con = dbManager.getConnection();
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
                catalogItemList.add(cMap.mapRow(rs));
            }
            rs.close();
            pstm.close();
        } catch (SQLException ex) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Can't get items from Database", ex);
        } finally {
            dbManager.commitAndClose(con);
        }
//        log.error("==> List of goods: " + catalogItemList);
        return catalogItemList;
    }

    public CatalogItem getItemByGoodsId(long id) throws DBException {
        CatalogItem cItem = new CatalogItem();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            CatalogMapper cMap = new CatalogMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ITEM_BY_GOODS_ID);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                cItem = cMap.mapRow(rs);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Can't get items from Database", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return cItem;
    }

    public List<CatalogItem> getItemsByGoodsId(List<Long> list) throws DBException {
        CatalogItem cItem;
        List<CatalogItem> catalogList = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ITEM_BY_GOODS_ID);
            CatalogMapper cMap = new CatalogMapper();
            for (long id : list) {
                pstm.setLong(1, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    cItem = cMap.mapRow(rs);
                    catalogList.add(cItem);
                }
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Can't get items from Database", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return catalogList;
    }

    public void updateCatalogItem(CatalogItem catalogItem) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        int i = 0;
        log.error("Catalog item is: " + catalogItem);
        try {
            connection = dbManager.getConnection();
            try (PreparedStatement pstm = connection.prepareStatement(UPDATE_ITEM_BY_GOODS_ID)) {
                pstm.setDouble(++i, catalogItem.getPrice());
                pstm.setInt(++i, catalogItem.getQuantity());
                pstm.setDate(++i, (Date) catalogItem.getAddDate());
                pstm.setLong(++i, catalogItem.getGoodsParamId());
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
            throw new DBException("Can't update catalog items in Database", e);
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
    }

    private static class CatalogMapper implements EntityMapper<CatalogItem> {
        @Override
        public CatalogItem mapRow(ResultSet rs) {
            try {
                CatalogItem catalogItem = new CatalogItem();
                catalogItem.setId(rs.getLong("id"));
                long gpId = rs.getLong("goodsParamId");
                catalogItem.setGoodsParamId(gpId);
                catalogItem.setPrice(rs.getDouble("price"));
                catalogItem.setQuantity(rs.getInt("quantity"));
                catalogItem.setAddDate(rs.getDate("addDate"));
                catalogItem.setGoodsParam(new GoodsParamDAO().getGoodsParamByID(gpId));
                long goodsId = catalogItem.getGoodsParam().getGoodsId();
                catalogItem.setGoods(new GoodsDAO().getGoodsById(goodsId));
                return catalogItem;
            } catch (SQLException | DBException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
