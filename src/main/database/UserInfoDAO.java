package main.database;

import main.entity.UserInfo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDAO {
    private static final Logger log = Logger.getLogger(UserInfoDAO.class);
    private final String GET_USER_BY_USERNAME = "SELECT usersInfo.id, name, surname, userId FROM usersInfo INNER JOIN users u on usersInfo.userId = u.id WHERE username=?";

    public UserInfo getUserInfoByUsername(String username) {
        UserInfo userInfo = new UserInfo();
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        PreparedStatement pstm;
        try {
            UserInfoMapper uim = new UserInfoMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_USER_BY_USERNAME);
            pstm.setString(1, username);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                userInfo=uim.mapRow(rs);
            }
            userInfo = uim.mapRow(rs);
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            e.printStackTrace();
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return userInfo;
    }

    private static class UserInfoMapper implements EntityMapper<UserInfo> {
        @Override
        public UserInfo mapRow(ResultSet rs) {
            try {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(rs.getLong("id"));
                userInfo.setName(rs.getString("name"));
                userInfo.setSurname(rs.getString("surname"));
                userInfo.setUserId(rs.getLong("userId"));
                return userInfo;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
