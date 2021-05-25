package main.database;

import main.entity.CatalogItem;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDAO {
    private static final Logger log = Logger.getLogger(CatalogDAO.class);
    private final DBManager dbManager = DBManager.getInstance();
    private final String GET_ALL_ITEMS = "SELECT * FROM itemsCatalog";
    private final String GET_ITEM_BY_GOODS_ID = "SELECT * FROM itemsCatalog WHERE goodsParamId=?";
    private final String GET_PART_AND_SORT = "SELECT * FROM itemsCatalog ORDER BY ? ? LIMIT ?,?";

    private final String GET_LIST_OF_GOODS ="SELECT name,\n" +
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
            "         INNER JOIN itemsCatalog iC on goodsParam.id = iC.goodsParamId\n" +
            "WHERE ? ORDER BY genderName DESC LIMIT 0,9";


    public List<CatalogItem> getAllItems() {
        List<CatalogItem> catalogItemList = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            CatalogMapper cMap = new CatalogMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ALL_ITEMS);
            rs = pstm.executeQuery();
            while (rs.next()) {
                catalogItemList.add(cMap.mapRow(rs));
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            e.printStackTrace();
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return catalogItemList;
    }

    public CatalogItem getItemsByGoodsId(long id) {
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
            e.printStackTrace();
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return cItem;
    }

    public List<CatalogItem> getPartItemsAndSort(String param, String sort, String dir, int start, int end) {
        List<CatalogItem> catalogItemList = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            CatalogMapper cMap = new CatalogMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_LIST_OF_GOODS);
            pstm.setString(1, param);
//            pstm.setString(2, sort);
//            pstm.setString(3, dir);
//            pstm.setInt(4, start);
//            pstm.setInt(5, end);
            rs = pstm.executeQuery();
            while (rs.next()) {
                catalogItemList.add(cMap.mapRow(rs));
            }
            rs.close();
            pstm.close();
        } catch (SQLException ex) {
            dbManager.rollbackAndClose(con);
        } finally {
            dbManager.commitAndClose(con);
        }
        return catalogItemList;
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
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
