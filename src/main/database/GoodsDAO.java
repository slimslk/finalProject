package main.database;

import main.entity.Goods;
import main.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO {
    private final DBManager dbManager = DBManager.getInstance();
    private final String GET_ALL_ITEMS = "SELECT * FROM goods";
    private final String GET_ITEM_BY_ID="SELECT * FROM goods WHERE id=?";

    public List<Goods> getAllItems() throws DBException {
        List<Goods> allGoods=new ArrayList<>();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        Goods goods = new Goods();
        try {
            GoodsMapper gMap = new GoodsMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ALL_ITEMS);
            rs = pstm.executeQuery();
            while (rs.next()) {
                goods = gMap.mapRow(rs);
                allGoods.add(goods);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Can't get all goods", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return allGoods;
    }

    public Goods getGoodsById(long id) throws DBException {
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        Goods goods = new Goods();
        try {
            GoodsMapper gMap = new GoodsMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ITEM_BY_ID);
            pstm.setLong(1,id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                goods = gMap.mapRow(rs);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Database not response, can't get goods by ID", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return goods;
    }

    private static class GoodsMapper implements EntityMapper<Goods> {
        @Override
        public Goods mapRow(ResultSet rs) {
            try {
                Goods goods = new Goods();
                goods.setId(rs.getLong(Fields.ENTITY_ID));
                goods.setName(rs.getString(Fields.NAME));
                goods.setImg(rs.getString(Fields.IMG));
                return goods;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
