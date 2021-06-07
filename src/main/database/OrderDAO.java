package main.database;

import main.entity.Order;
import main.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class OrderDAO {
    private static final Logger log = LogManager.getLogger(OrderDAO.class);
    private final String GET_ORDER_BY_ORDER_NUMBER = "SELECT * FROM orders WHERE orderNumber=?";
    private final String SET_ORDER = "INSERT INTO orders (userId, orderNumber, orderStatus, orderDate) VALUES (?,?,?,?)";
    private final String GET_MAX_ID = "SELECT MAX(orderNumber) FROM orders";

    public Order getOrderByNumber(int orderNumber) {
        DBManager dbManager = DBManager.getInstance();
        Order order = new Order();
        Connection con = null;
        PreparedStatement pstm;
        ResultSet rs;
        try {
            OrderMapper orderMapper = new OrderMapper();
            con = dbManager.getConnection();
            pstm = con.prepareStatement(GET_ORDER_BY_ORDER_NUMBER);
            pstm.setInt(1, orderNumber);
            rs = pstm.executeQuery();
            while (rs.next()) {
                order = orderMapper.mapRow(rs);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollbackAndClose(con);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return order;
    }

    public long getMaxId() {
        log.error("Get max ID");
        long maxId = 0;
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        Statement st;
        ResultSet rs = null;
        try {
            con=dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(GET_MAX_ID);
            while (rs.next()) {
                log.error("has a rs");
                maxId = rs.getLong(1);
                log.error("max id"+maxId);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            dbManager.rollbackAndClose(con);
            return 0;
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return maxId;
    }

    public long insertOrder(Order order, String status) throws DBException {
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
            }
        } catch (SQLException e) {
            dbManager.rollbackAndClose(connection);
            throw new DBException("Cant create order. Database not response, try later.", e);
        } finally {
            if (connection != null) {
                dbManager.commitAndClose(connection);
            }
        }
        log.error("Order number is:" + order.getOrderNumber());
        return order.getOrderNumber();
    }

    private static class OrderMapper implements EntityMapper<Order> {
        Order order;

        @Override
        public Order mapRow(ResultSet rs) {
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
}
