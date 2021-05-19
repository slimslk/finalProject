package main.database;

import org.apache.log4j.Logger;

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
    private static final Logger log=Logger.getLogger(DBManager.class);

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager(){
    }

    /**
     * Return a DB connection from the pool connections.
     */

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/finalProject");
            try {
                connection = ds.getConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (NamingException ex) {
            System.out.println("NamingException: " + ex.getExplanation());
        }
        return connection;
    }

    /**
     * Commit and close connection
     *
     * @param con Connection to be committed and closed.
     */

    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback and close connection
     *
     * @param con Connection to be rollback and closed.
     */

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
