package main.database;

import main.entity.GoodsParam;
import main.exception.DBException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsParamDAO {
    private static final Logger log = LogManager.getLogger(GoodsParamDAO.class);
    private final DBManager dbManager = DBManager.getInstance();
    private final String GET_PARAMS_BY_ID = "SELECT * FROM goodsParam WHERE id=?";

    public GoodsParam getGoodsParamByID(long id) throws DBException {
        GoodsParam goodsParam = new GoodsParam();
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            GoodsParamMapper gpMap = new GoodsParamMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_PARAMS_BY_ID);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                goodsParam = gpMap.mapRow(rs);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Cant get Goods parameter from Database, try later.", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return goodsParam;
    }


    private static class GoodsParamMapper implements EntityMapper<GoodsParam> {
        @Override
        public GoodsParam mapRow(ResultSet rs) {
            ParamDAO paramDAO=new ParamDAO();
            try {
                GoodsParam goodsParam = new GoodsParam();
                //Get names from DB associated with it's ID
                String gender = paramDAO.getParamName(Fields.GENDER, rs.getLong("genderId"));
                String age = paramDAO.getParamName(Fields.AGE, rs.getLong("ageId"));
                String size = paramDAO.getParamName(Fields.SIZE, rs.getLong("sizeID"));
                String category = paramDAO.getParamName(Fields.CATEGORY, rs.getLong("categoryId"));
                String style = paramDAO.getParamName(Fields.STYLE, rs.getLong("styleId"));
                //Set to the GoodsParam object
                goodsParam.setId(rs.getLong("id"));
                goodsParam.setGoodsId(rs.getLong("goodsId"));
                goodsParam.setGenderName(gender);
                goodsParam.setAgeName(age);
                goodsParam.setSizeName(size);
                goodsParam.setCategoryName(category);
                goodsParam.setStyleName(style);
                return goodsParam;
            } catch (SQLException | DBException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}