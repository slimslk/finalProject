package main.database;

import main.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStatusDAO {
    private final String GET_STATUS_BY_NAME = "select id from userStatuses where statusName=?";

    public int getStatusByName(String statusName) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        int id = 0;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            try (PreparedStatement pstm = con.prepareStatement(GET_STATUS_BY_NAME)) {
                pstm.setString(1, statusName);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt(Fields.ENTITY_ID);
                }
                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollback(con);
            throw new AppException("Cant get user status from Database, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(con);
        }
        return id;
    }
}
