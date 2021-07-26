package com.epam.finalProject.database;

import com.epam.finalProject.entity.UserOrders;
import com.epam.finalProject.exception.AppException;

import java.util.Map;

public interface OrderListDAO {
    void changeOrderStatus(long orderNumber, int orderStatus) throws AppException;

    void insertOrderInList(long orderId, Map<Long, Integer> goodsIdSet) throws AppException;

    UserOrders getOrderListByUserId(long userId) throws AppException;

    UserOrders getOrderListByUserIdFilter(long userId, int status, String sDate, String eDate) throws AppException;
}
