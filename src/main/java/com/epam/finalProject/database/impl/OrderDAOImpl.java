package com.epam.finalProject.database.impl;

import com.epam.finalProject.database.DBManager;
import com.epam.finalProject.database.OrderDAO;
import com.epam.finalProject.entity.Order;
import com.epam.finalProject.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger log = LogManager.getLogger(OrderDAOImpl.class);
    private static final String GET_ORDER_BY_ORDER_NUMBER = "SELECT * FROM orders WHERE orderNumber=?";
    private static final String SET_ORDER = "INSERT INTO orders (userId, orderNumber, orderStatus, orderDate) VALUES (?,?,?,?)";
    private static final String GET_MAX_ID = "SELECT MAX(orderNumber) FROM orders";

    public long getMaxId() throws AppException {
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
                maxId = rs.getLong(1);
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
        int statusId = new OrderStatusDAOImpl().getStatusByName(status);
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
}
