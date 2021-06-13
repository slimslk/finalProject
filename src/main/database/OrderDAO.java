package main.database;

import main.entity.Order;
import main.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class OrderDAO {
    private static final Logger log = LogManager.getLogger(OrderDAO.class);
    private final String GET_ORDER_BY_ORDER_NUMBER = "SELECT * FROM orders WHERE orderNumber=?";
    private final String SET_ORDER = "INSERT INTO orders (userId, orderNumber, orderStatus, orderDate) VALUES (?,?,?,?)";
    private final String GET_MAX_ID = "SELECT MAX(orderNumber) FROM orders";

    public Order getOrderByNumber(int orderNumber) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        Order order = new Order();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ORDER_BY_ORDER_NUMBER);
            pstm.setInt(1, orderNumber);
            rs = pstm.executeQuery();
            while (rs.next()) {
                order = mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollback(con);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return order;
    }

    public long getMaxId() throws AppException {
        log.error("Get max ID");
        long maxId = 0;
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(GET_MAX_ID);
            while (rs.next()) {
                log.error("has a rs");
                maxId = rs.getLong(1);
                log.error("max id" + maxId);
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollback(con);
            return 0;
        } finally {
            dbManager.close(rs);
            dbManager.close(st);
            dbManager.close(con);
        }
        return maxId;
    }

    public long insertOrder(Order order, String status) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        Connection connection = null;
        int statusId = new OrderStatusDAO().getStatusByName(status);
        int i = 0;
        try {
            connection = dbManager.getConnection();
            try (PreparedStatement pstm = connection.prepareStatement(SET_ORDER)) {
                pstm.setLong(++i, order.getUserId());
                pstm.setLong(++i, order.getOrderNumber());
                pstm.setInt(++i, statusId);
                pstm.setTimestamp(++i, (Timestamp) order.getOrderDate());
                pstm.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            dbManager.rollback(connection);
            throw new AppException("Cant create order. Database not response, try later.", e);
        } finally {
            dbManager.close(connection);
        }
        log.error("Order number is:" + order.getOrderNumber());
        return order.getOrderNumber();
    }

    private Order mapRow(ResultSet rs) {
        Order order;
        try {
            order = new Order();
            order.setOrderNumber(rs.getLong(Fields.ORDER_NUMBER));
            order.setUserId(rs.getLong(Fields.USER_ID));
            order.setOrderStatus(rs.getInt(Fields.ORDER_STATUS));
            order.setOrderDate(rs.getTimestamp(Fields.ORDER_DATE));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return order;
    }
}
