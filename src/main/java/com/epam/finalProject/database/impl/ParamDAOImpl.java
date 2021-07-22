package com.epam.finalProject.database.impl;

import com.epam.finalProject.database.DBManager;
import com.epam.finalProject.database.ParamDAO;
import com.epam.finalProject.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParamDAOImpl implements ParamDAO {
    private final DBManager dbManager = DBManager.getInstance();
    private static final Logger log = LogManager.getLogger(ParamDAOImpl.class);

    public String getParamName(String sqlExpresion, long id) throws AppException {
        String param = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(sqlExpresion);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                param = rs.getString(1);
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant get parameter name from Database, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return param;
    }
}
