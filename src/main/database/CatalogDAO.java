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
    private final String GET_ALL_ITEMS = "SELECT * FROM catalog";
    private final String GET_PART_AND_SORT = "SELECT * FROM catalog INNER JOIN goods g ON catalog.goodsId = g.id ORDER BY ? LIMIT ?,?";


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

    public List<CatalogItem> getPartItemsAndSort(String sort, int start, int end) {
        List<CatalogItem> catalogItemList = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            CatalogMapper cMap = new CatalogMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_PART_AND_SORT);
            pstm.setString(1, sort);
            pstm.setInt(2, start);
            pstm.setInt(3, end);
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
                catalogItem.setGoodsId(rs.getLong("goodsId"));
                catalogItem.setPrice(rs.getDouble("price"));
                catalogItem.setQuantity(rs.getInt("quantity"));
                catalogItem.setAddDate(rs.getDate("addDate"));
                catalogItem.setGoods(new GoodsDAO().getGoodsById(catalogItem.getId()));
                return catalogItem;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}
