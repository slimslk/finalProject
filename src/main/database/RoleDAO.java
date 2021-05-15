package main.database;

import main.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    private final String GET_ROLE_NAME = "select roleId from roles where roleName=?";

    public int getRoleByName(String roleName) {
        int roleId = 0;
        try {
            Connection con = DBManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(GET_ROLE_NAME);
            pstm.setString(1, roleName);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                roleId = rs.getInt(Fields.ROLE_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleId;
    }
}
