package main.database;

import main.entity.User;

import java.sql.*;

public class UserDAO {

    private final String FIND_USER_BY_ID = "select * from users where id=?";
    private final String FIND_USER_BY_USERNAME = "select * from users where username=?";
    private final String INSERT_USER = "insert into users (id, userName, password, role) values (?,?,?,?)";

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
        insertUser(user, "user");
    }

    public void insertUser(User user, String roleName) {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        int roleId = new RoleDAO().getRoleByName(roleName);
        int i = 0;
        try {
            connection = dbManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(INSERT_USER);
            pstm.setString(++i, user.getUsername());
            pstm.setString(++i, user.getPassword());
            pstm.setInt(++i, roleId);
            pstm.close();
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
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
