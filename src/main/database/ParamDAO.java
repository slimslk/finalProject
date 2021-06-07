package main.database;

import main.exception.DBException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParamDAO {
    private final DBManager dbManager = DBManager.getInstance();
    private static final Logger log = LogManager.getLogger(ParamDAO.class);
//    private final String GET_GENDER_NAME_BY_ID = "SELECT genderName FROM gender WHERE id=?";
//    private final String GET_AGE_NAME_BY_ID = "SELECT ageName FROM age WHERE id=?";
//    private final String GET_SIZE_NAME_BY_ID = "SELECT sizeName FROM size WHERE id=?";
//    private final String GET_CATEGORY_NAME_BY_ID = "SELECT categoryName FROM category WHERE id=?";
//    private final String GET_STYLE_NAME_BY_ID = "SELECT styleName FROM style WHERE id=?";

    public String getParamName(String sqlExpresion, long id) throws DBException {
        String param = null;
        PreparedStatement pstm;
        ResultSet rs;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(sqlExpresion);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                param = rs.getString(1);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Cant get parameter name from Database, try later", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return param;
    }
}
