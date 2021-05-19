package main.database;

import main.entity.Catalog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDAO {
    private final String GET_ALL_ITEMS = "SELECT * FROM catalog";

    public List<Catalog> getAllItems() {
        DBManager dbManager = new DBManager();
        List<Catalog> catalogList = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            CatalogMapper cMap=new CatalogMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ALL_ITEMS);
            rs=pstm.executeQuery();
            while (rs.next()){
                catalogList.add(cMap.mapRow(rs));
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
        return catalogList;
    }

    private static class CatalogMapper implements EntityMapper<Catalog>{
        @Override
        public Catalog mapRow(ResultSet rs) {
            try {
                Catalog catalog=new Catalog();
                catalog.setId(rs.getLong("id"));
                catalog.setGoodsId(rs.getLong("goodsId"));
                catalog.setPrice(rs.getDouble("price"));
                catalog.setQuantity(rs.getInt("quantity"));
                catalog.setAddDate(rs.getDate("addDate"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
