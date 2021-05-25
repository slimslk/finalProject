package main.database;

import main.entity.GoodsParam;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsParamDAO {
    private static final Logger log = Logger.getLogger(GoodsParamDAO.class);
    private final DBManager dbManager = DBManager.getInstance();
    private final String GET_PARAMS_BY_ID = "SELECT * FROM goodsParam WHERE id=?";

    public GoodsParam getGoodsParamByID(long id) {
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
            e.printStackTrace();
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
            try {
                GoodsParam goodsParam = new GoodsParam();
                //Get names from DB associated with it's ID
                String gender = new ParamDAO().getParamName(Fields.GENDER, rs.getLong("genderId"));
                String age = new ParamDAO().getParamName(Fields.AGE, rs.getLong("ageId"));
                String size = new ParamDAO().getParamName(Fields.SIZE, rs.getLong("sizeID"));
                String category = new ParamDAO().getParamName(Fields.CATEGORY, rs.getLong("categoryId"));
                String style = new ParamDAO().getParamName(Fields.STYLE, rs.getLong("styleId"));
                //Set to the GoodsParam object
                goodsParam.setId(rs.getLong("id"));
                goodsParam.setGoodsId(rs.getLong("goodsId"));
                goodsParam.setGenderName(gender);
                goodsParam.setAgeName(age);
                goodsParam.setSizeName(size);
                goodsParam.setCategoryName(category);
                goodsParam.setStyleName(style);
                return goodsParam;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
