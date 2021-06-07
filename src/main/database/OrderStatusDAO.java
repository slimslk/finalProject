package main.database;

import main.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusDAO {
    private final String GET_STATUS_ID = "SELECT id from orderStatus where statusName=?";
    private final String GET_STATUS_NAME = "SELECT statusName from orderStatus where id=?";

    public int getStatusByName(String statusName) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        int statusId = 0;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            try (PreparedStatement pstm = con.prepareStatement(GET_STATUS_ID)) {
                pstm.setString(1, statusName);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    statusId = rs.getInt(Fields.ENTITY_ID);
                }
                rs.close();
            }
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Cant get order status from Database, try later", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return statusId;
    }

    public String getStatusNameById(int id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        String statusName = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            try (PreparedStatement pstm = con.prepareStatement(GET_STATUS_NAME)) {
                pstm.setLong(1, id);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    statusName = rs.getString(Fields.STATUS_NAME);
                }
                rs.close();
            }
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Cant get status name from Database, try later", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return statusName;
    }
}
