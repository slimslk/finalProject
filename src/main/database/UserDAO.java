package main.database;

import main.entity.User;

import java.sql.*;

public class UserDAO {

    private final String FIND_USER_BY_ID = "select * from users where id=?";
    private final String FIND_USER_BY_USERNAME = "select * from users where username=?";
    private final String INSERT_USER = "insert into users (userName, password, roleId,status) values (?,?,?,?)";

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

    public User getUserByUsername(String username) {
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
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
        return user;
    }

    public void insertUser(User user) {
        insertUser(user, "user", "active");
    }

    //Roles id: 1- superadmin, 2 - admin, 3 - user
    //User statuses: 1 - active, 2 - disabled

    public void insertUser(User user, String roleName, String statusName) {
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
                pstm.setInt(++i,statusId);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
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
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
