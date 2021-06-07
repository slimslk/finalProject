package main.database;

import main.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    private final String GET_ROLE_NAME = "select roleId from roles where roleName=?";

    public int getRoleByName(String roleName) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        int roleId = 0;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            try (PreparedStatement pstm = con.prepareStatement(GET_ROLE_NAME)) {
                pstm.setString(1, roleName);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    roleId = rs.getInt(Fields.ROLE_ID);
                }
                rs.close();
            }
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Cant get role name from Database, try later", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return roleId;
    }
}
