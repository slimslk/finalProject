package main.database;

import main.entity.Goods;
import main.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO {
    private final DBManager dbManager = DBManager.getInstance();
    private final String GET_ITEM_BY_ID = "SELECT * FROM goods WHERE id=?";

    public Goods getGoodsById(long id) throws AppException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = null;
        Goods goods = new Goods();
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ITEM_BY_ID);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                goods = mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            e.printStackTrace();
            throw new AppException("Database not response, can't get goods by ID", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return goods;
    }

    private Goods mapRow(ResultSet rs) {
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
