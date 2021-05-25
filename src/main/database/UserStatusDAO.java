package main.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStatusDAO {
    private final String GET_STATUS_BY_NAME = "select id from userStatuses where statusName=?";

    public int getStatusByName(String statusName) {
        DBManager dbManager = DBManager.getInstance();
        int id = 0;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            try (PreparedStatement pstm = con.prepareStatement(GET_STATUS_BY_NAME)) {
                pstm.setString(1, statusName);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt(Fields.ENTITY_ID);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollbackAndClose(con);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return id;
    }
}
