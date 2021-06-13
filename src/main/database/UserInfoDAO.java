package main.database;

import main.entity.UserInfo;
import main.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDAO {
    private static final Logger log = LogManager.getLogger(UserInfoDAO.class);
    private final String GET_USER_BY_USERNAME = "SELECT usersInfo.id, name, surname, userId FROM usersInfo INNER JOIN users u on usersInfo.userId = u.id WHERE username=?";
    private final String GET_USER_BY_ID = "SELECT * FROM usersInfo  WHERE id=?";
    private final String INSERT_USER_INFO = "INSERT INTO usersInfo(userId,name,surname) VALUES (?,?,?)";

    public UserInfo getUserInfoById(long id) throws AppException, SQLException {
        UserInfo userInfo = new UserInfo();
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_USER_BY_ID);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                userInfo = mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant get user info from Database, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return userInfo;
    }

    public void insertUserInfo(UserInfo userInfo) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        int i = 0;
        try {
            connection = dbManager.getConnection();
            try (PreparedStatement pstm = connection.prepareStatement(INSERT_USER_INFO)) {
                pstm.setLong(++i, userInfo.getUserId());
                pstm.setString(++i, userInfo.getName());
                pstm.setString(++i, userInfo.getSurname());
                pstm.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            dbManager.rollback(connection);
            throw new AppException("Cant insert user info to Database, try later", e);
        } finally {
            dbManager.close(connection);
        }
    }

    private UserInfo mapRow(ResultSet rs) {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(rs.getLong(Fields.ENTITY_ID));
            userInfo.setName(rs.getString(Fields.NAME));
            userInfo.setSurname(rs.getString(Fields.SURNAME));
            userInfo.setUserId(rs.getLong(Fields.USER_ID));
            return userInfo;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
