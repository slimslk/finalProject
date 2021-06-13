package main.database;

import main.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusDAO {
    private final String GET_STATUS_ID = "SELECT id from orderStatus where statusName=?";
    private final String GET_STATUS_NAME = "SELECT statusName from orderStatus where id=?";

    public int getStatusByName(String statusName) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        int statusId = 0;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            try (PreparedStatement pstm = con.prepareStatement(GET_STATUS_ID)) {
                pstm.setString(1, statusName);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    statusId = rs.getInt(Fields.ENTITY_ID);
                }
                con.commit();
            }
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant get order status from Database, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(con);
        }
        return statusId;
    }

    public String getStatusNameById(int id) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        String statusName = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            try (PreparedStatement pstm = con.prepareStatement(GET_STATUS_NAME)) {
                pstm.setLong(1, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    statusName = rs.getString(Fields.STATUS_NAME);
                }
                con.commit();
            }
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant get status name from Database, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(con);
        }
        return statusName;
    }
}
