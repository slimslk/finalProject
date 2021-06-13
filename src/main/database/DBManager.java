package main.database;

import main.exception.AppException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger log = LogManager.getLogger(DBManager.class);

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    /**
     * Return a DB connection from the pool connections.
     */

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/finalProject");
            connection = ds.getConnection();
        } catch (NamingException ex) {
            log.error("NamingException: " + ex.getExplanation());
        }
        return connection;
    }

    /**
     * Rollback and close connection
     *
     * @param con Connection to be rollback and closed.
     */

    public void rollback(Connection con) throws AppException {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            throw new AppException("", e);
        }
    }

    public void close(AutoCloseable autoCloseable) throws AppException {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException("", e);
            }
        }
    }
}
