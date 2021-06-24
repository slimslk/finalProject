package main.database;

import main.entity.*;
import main.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListDAO {
    private static final Logger log = LogManager.getLogger(OrderListDAO.class);
    private static final String GET_ORDERS_BY_USER_ID = "SELECT orderNumber,username, name,img,ageName,categoryName,genderName, sizeName,styleName, price, goodsQuantity, orderDate, orderStatus\n" +
            "FROM ordersList\n" +
            "         INNER JOIN orders o on ordersList.orderId = o.orderNumber\n" +
            "         INNER JOIN users us ON us.id = o.userId\n" +
            "         INNER JOIN itemsCatalog iC on goodsId = iC.goodsParamId\n" +
            "         INNER JOIN goodsParam gP on iC.goodsParamId = gP.id\n" +
            "         INNER JOIN age a on gP.ageId = a.id\n" +
            "         INNER JOIN category c on gP.categoryId = c.id\n" +
            "         INNER JOIN gender g2 on gP.genderId = g2.id\n" +
            "         INNER JOIN size s on gP.sizeId = s.id\n" +
            "         INNER JOIN style s2 on gP.styleId = s2.id\n" +
            "         INNER JOIN goods g ON g.id = gP.goodsId\n" +
            "WHERE userId = ?\n" +
            "ORDER BY o.orderNumber DESC;";
    private static final String GET_ORDERS = "SELECT orderNumber,username, name,img,ageName,categoryName,genderName, sizeName,styleName, price, goodsQuantity, orderDate, orderStatus\n" +
            "FROM ordersList\n" +
            "         INNER JOIN orders o on ordersList.orderId = o.orderNumber\n" +
            "         INNER JOIN users us ON us.id = o.userId\n" +
            "         INNER JOIN itemsCatalog iC on goodsId = iC.goodsParamId\n" +
            "         INNER JOIN goodsParam gP on iC.goodsParamId = gP.id\n" +
            "         INNER JOIN age a on gP.ageId = a.id\n" +
            "         INNER JOIN category c on gP.categoryId = c.id\n" +
            "         INNER JOIN gender g2 on gP.genderId = g2.id\n" +
            "         INNER JOIN size s on gP.sizeId = s.id\n" +
            "         INNER JOIN style s2 on gP.styleId = s2.id\n" +
            "         INNER JOIN goods g ON g.id = gP.goodsId\n" +
            "ORDER BY orderDate DESC;";
    private final String INSERT_ORDER_IN_LIST = "INSERT INTO ordersList(orderId, goodsId, goodsQuantity) VALUES (?,?,?)";
    private final String CHANGE_ORDER_STATUS = "UPDATE orders SET orderStatus=? WHERE orderNumber=?";

    public void changeOrderStatus(long orderNumber, int orderStatus) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(CHANGE_ORDER_STATUS);
            log.error("Order status is:" + orderStatus);
            log.error("Order status is:" + orderNumber);
            pstm.setLong(1, orderStatus);
            pstm.setLong(2, orderNumber);
            pstm.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant change Order status, try later", e);
        } finally {
            dbManager.close(pstm);
            dbManager.close(con);
        }
    }

    public void insertOrderInList(long orderId, Map<Long, Integer> goodsIdSet) throws AppException {
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = dbManager.getConnection();
            pstm = con.prepareStatement(INSERT_ORDER_IN_LIST);
            log.error("Order id is:" + orderId);
            pstm.setLong(1, orderId);
            for (Map.Entry<Long, Integer> entry : goodsIdSet.entrySet()) {
                pstm.setLong(2, entry.getKey());
                pstm.setLong(3, entry.getValue());
                pstm.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant inser order to Database, try later", e);
        } finally {
            dbManager.close(pstm);
            dbManager.close(con);
        }
    }

    public UserOrders getOrderListByUserId(long userId) throws AppException {
        log.error("in getOrderListByUserId method");
        UserOrderMapper userOrderMapper = new UserOrderMapper();
        UserOrders userOrders;
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = dbManager.getConnection();
            if (userId > 0) {
                pstm = con.prepareStatement(GET_ORDERS_BY_USER_ID);
                pstm.setLong(1, userId);
            } else {
                pstm = con.prepareStatement(GET_ORDERS);
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                userOrderMapper.addUserOrderToList(rs);
            }
            log.error("Usermapper: "+userOrderMapper.getUserOrders());
            userOrders = userOrderMapper.getUserOrders();
            log.error("User orders is: " + userOrders);
            con.commit();
        } catch (SQLException e) {
            dbManager.rollback(con);
            throw new AppException("Cant get order list, try later", e);
        } finally {
            dbManager.close(rs);
            dbManager.close(pstm);
            dbManager.close(con);
        }
        return userOrders;
    }

    private static class UserOrderMapper {
        UserOrders userOrders = null;
        long orderNumber;
        List<UserOrder> listOfUserOrder;
        Map<Long, List<UserOrder>> orderMap;

        private void addUserOrderToList(ResultSet rs) {
            log.error("In method");
            try {
                if (userOrders == null) {
                    orderMap = new HashMap<>();
                    userOrders = new UserOrders();
                }
                orderNumber = rs.getLong(Fields.ORDER_NUMBER);
                if (!orderMap.containsKey(orderNumber)) {
                    listOfUserOrder = new ArrayList<>();
                    orderMap.put(orderNumber, listOfUserOrder);
                }
                listOfUserOrder = orderMap.get(orderNumber);
                UserOrder userOrder = new UserOrder();
                Goods goods = new Goods();
                GoodsParam goodsParam = new GoodsParam();
                goods.setName(rs.getString(Fields.NAME));
                goods.setImg(rs.getString(Fields.IMG));
                goodsParam.setAgeName(rs.getString(Fields.AGE_NAME));
                goodsParam.setCategoryName(rs.getString(Fields.CATEGORY_NAME));
                goodsParam.setGenderName(rs.getString(Fields.GENDER_NAME));
                goodsParam.setSizeName(rs.getString(Fields.SIZE_NAME));
                goodsParam.setStyleName(rs.getString(Fields.STYLE_NAME));
                userOrder.setUsername(rs.getString(Fields.USERNAME));
                userOrder.setGoods(goods);
                userOrder.setGoodsParam(goodsParam);
                userOrder.setOrderStatus(new OrderStatusDAO().getStatusNameById(rs.getInt(Fields.ORDER_STATUS)));
                userOrder.setPrice(rs.getDouble(Fields.PRICE));
                userOrder.setQuantity(rs.getInt(Fields.GOODS_QUANTITY));
                userOrder.setOrderDate(rs.getTimestamp(Fields.ORDER_DATE));
                log.error("user order added to list is: " + userOrder);
                listOfUserOrder.add(userOrder);
                orderMap.put(orderNumber, listOfUserOrder);
            } catch (SQLException | AppException e) {
                e.printStackTrace();
            }
        }

        public UserOrders getUserOrders() {
            if(orderMap==null){
                return null;
            }
            userOrders.setUserOrders(orderMap);
            return userOrders;
        }
    }
}
