package main.database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
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
//Connection pool
                Connection connection = null;
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/finalProject");
            System.out.println("Check2");
            System.out.println("Datasource: "+ds);
            try{
                connection = ds.getConnection();
            }catch (SQLException ex){
                System.out.println(ex.getErrorCode());
            }
            System.out.println(connection+" connection");
        } catch (NamingException ex) {
            System.out.println("NamingException: "+ex.getExplanation());
        }

//        Connection connection= null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshop","root","rootroot");
//        } catch (SQLException e) {
//            System.out.println("Connection error code:"+ e.getErrorCode());
//
//        }
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
