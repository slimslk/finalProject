package main.database;

import main.entity.GoodsParam;
import main.exception.AppException;
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

    public GoodsParam getGoodsParamByID(long id) throws AppException {
        GoodsParam goodsParam = new GoodsParam();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_PARAMS_BY_ID);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                goodsParam = mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            e.printStackTrace();
            throw new AppException("Cant get Goods parameter from Database, try later.", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return goodsParam;
    }


    private GoodsParam mapRow(ResultSet rs) {
        ParamDAO paramDAO = new ParamDAO();
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
        } catch (SQLException | AppException e) {
            e.printStackTrace();
            return null;
        }
    }
}
