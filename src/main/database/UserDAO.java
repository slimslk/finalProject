package main.database;

import main.entity.User;
import main.exception.DBException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final Logger log = LogManager.getLogger(UserDAO.class);

    private final String GET_USERS = "SELECT * FROM users INNER JOIN usersInfo uI ON users.id = uI.userId ORDER BY roleId DESC";
    private final String FIND_USER_BY_ID = "select * from users where id=?";
    private final String FIND_USER_BY_USERNAME = "select * from users where username=?";
    private final String INSERT_USER = "insert into users (userName, password, roleId,status) values (?,?,?,?)";
    private final String UPDATE_USER_STATUS = "UPDATE users SET status=? WHERE username=?";
    private final String UPDATE_USER_LOCALE = "UPDATE users SET locale=? WHERE username=?";

    public void updateUserStatus(String username, int status) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        PreparedStatement pstm;
        log.error("Username: "+username+" statusId: "+status);
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(UPDATE_USER_STATUS);
            pstm.setInt(1, status);
            pstm.setString(2, username);
            pstm.executeUpdate();
            pstm.close();
            log.log(Level.DEBUG, "Update done");
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Cant update user status, try later", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
    }

    public void updateUserLocale(String username, String locale) {
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        PreparedStatement pstm;
        log.error("Username: "+username+" statusId: "+locale);
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(UPDATE_USER_LOCALE);
            pstm.setString(1, locale);
            pstm.setString(2, username);
            pstm.executeUpdate();
            pstm.close();
            log.log(Level.DEBUG, "Update done");
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
    }

    public List<User> getUsers() throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<User> userList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm;
        ResultSet rs;
        User user;
        try {
            UserMapper userMapper = new UserMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_USERS);
            rs = pstm.executeQuery();
            while (rs.next()) {
                user = userMapper.mapRow(rs);
                if (user != null) {
                    userList.add(user);
                }
            }
            rs.close();
            pstm.close();
            log.log(Level.DEBUG, "User list is: " + userList);
        } catch (SQLException e) {
            dbManager.rollbackAndClose(con);
            throw new DBException("Cant get user from Database, try later", e);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return userList;
    }

    public User getUserById(long id) {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        PreparedStatement pstm;
        ResultSet rs;
        User user = null;
        try {
            UserMapper userMapper = new UserMapper();
            connection = dbManager.getConnection();
            pstm = connection.prepareStatement(FIND_USER_BY_ID);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                user = userMapper.mapRow(rs);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
        return user;
    }

    public User getUserByUsername(String username) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        PreparedStatement pstm;
        ResultSet rs;
        User user = null;
        try {
            UserMapper userMapper = new UserMapper();
            connection = dbManager.getConnection();
            pstm = connection.prepareStatement(FIND_USER_BY_USERNAME);
            pstm.setString(1, username);
            rs = pstm.executeQuery();
            while (rs.next()) {
                user = userMapper.mapRow(rs);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
            throw new DBException("Cant get user from Database, try later", e);
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
        return user;
    }

    public void insertUser(User user) throws DBException {
        insertUser(user, "user", "active");
    }

    //Roles id: 1- superadmin, 2 - admin, 3 - user
    //User statuses: 1 - active, 2 - disabled

    public void insertUser(User user, String roleName, String statusName) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        int roleId = new RoleDAO().getRoleByName(roleName);
        int statusId = new UserStatusDAO().getStatusByName(statusName);
        int i = 0;
        try {
            connection = dbManager.getConnection();
            try (PreparedStatement pstm = connection.prepareStatement(INSERT_USER)) {
                pstm.setString(++i, user.getUsername());
                pstm.setString(++i, user.getPassword());
                pstm.setInt(++i, roleId);
                pstm.setInt(++i, statusId);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
            throw new DBException("Cant insert user to Database, try later", e);
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
    }

    private static class UserMapper implements EntityMapper<User> {
        @Override
        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setId(rs.getLong(Fields.ENTITY_ID));
                user.setUsername(rs.getString(Fields.USERNAME));
                user.setPassword(rs.getString(Fields.PASSWORD));
                user.setRoleId(rs.getInt(Fields.ROLE_ID));
                user.setStatusId(rs.getInt(Fields.USER_STATUS_ID));
                user.setUserInfo(new UserInfoDAO().getUserInfoById(user.getId()));
                return user;
            } catch (SQLException | DBException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
