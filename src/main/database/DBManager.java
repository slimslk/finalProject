package main.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Kuzavkov
 */

public class DBManager {

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    /**
     * Return a DB connection from the pool connections.
     */

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/finalProject");
            connection = ds.getConnection();
        } catch (NamingException ex) {
            System.out.println(ex.getCause());
        }
        return connection;
    }

    /**
     * Commit and close connection
     * @param con
     * Connection to be committed and closed.
     */

    public void commitAndClose(Connection con){
        try {
            con.commit();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback and close connection
     * @param con
     * Connection to be rollback and closed.
     *
     */

    public void rollbackAndClose(Connection con){
        try {
            con.rollback();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
