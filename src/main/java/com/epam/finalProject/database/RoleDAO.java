package com.epam.finalProject.database;

import com.epam.finalProject.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    private final String GET_ROLE_NAME = "select roleId from roles where roleName=?";

    private DBManager dbManager;

    public int getRoleByName(String roleName) throws AppException {
        dbManager = DBManager.getInstance();
        int roleId = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ROLE_NAME);
            pstm.setString(1, roleName);
            rs = pstm.executeQuery();
            while (rs.next()) {
                roleId = rs.getInt(Fields.ROLE_ID);
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant get role name from Database, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return roleId;
    }
}
