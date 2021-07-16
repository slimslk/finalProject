package com.epam.finalProject.service.defaultImpl;

import com.epam.finalProject.database.OrderDAO;
import com.epam.finalProject.database.OrderListDAO;
import com.epam.finalProject.entity.Order;
import com.epam.finalProject.entity.UserOrders;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.OrderService;
import com.epam.finalProject.web.Path;
import com.epam.finalProject.web.command.CommandAddOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private static final Logger log = LogManager.getLogger(OrderServiceImpl.class);
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    public UserOrders getUserOrdersList(long userId) throws AppException {
        return orderListDAO.getOrderListByUserId(userId);
    }

    @Override
    public boolean changeOrder(long orderNumber, int orderStatusId) throws AppException{
        orderListDAO.changeOrderStatus(orderNumber, orderStatusId);
        return true;
    }

    private OrderListDAO orderListDAO = new OrderListDAO();

    @Override
    public boolean addOrder(String[] goodsIdArray, String[] goodsQuantityArray, long userId) throws AppException {
        Map<Long, Integer> goodsMap = new HashMap<>();
        try {
            for (int i = 0; i < goodsIdArray.length; i++) {
                long goodsId = Long.parseLong(goodsIdArray[i]);
                int goodsQuantity = Integer.parseInt(goodsQuantityArray[i]);
                goodsMap.put(goodsId, goodsQuantity);
            }
        } catch (NumberFormatException e) {
            log.error("Parameter not a number");
            e.printStackTrace();
            return false;
        }
        log.error("Goods map: " + goodsMap);
        Order order = new Order();
        long orderNumber = orderDAO.getMaxId() + 1;
        log.error("Order number is: " + orderNumber);
        order.setOrderNumber(orderNumber);
        order.setUserId(userId);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        log.error("Order: " + order);
        orderListDAO.insertOrderInList(orderDAO.insertOrder(order, "registered"), goodsMap);
        log.error("Order is: " + order);
        return true;
    }
}
