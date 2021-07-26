package com.epam.finalProject.database.impl;

import com.epam.finalProject.database.DBManager;
import com.epam.finalProject.database.Fields;
import com.epam.finalProject.database.UserStatusDAO;
import com.epam.finalProject.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStatusDAOImpl implements UserStatusDAO {
    private static final String GET_STATUS_BY_NAME = "select id from userStatuses where statusName=?";

    public int getStatusByName(String statusName) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        int id = 0;
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = dbManager.getConnection();
            try (PreparedStatement pstm = connection.prepareStatement(GET_STATUS_BY_NAME)) {
                pstm.setString(1, statusName);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt(Fields.ENTITY_ID);
                }
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollback(connection);
            throw new AppException("Cant get user status from Database, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(connection);
        }
        return id;
    }
}
