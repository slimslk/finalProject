package com.epam.finalProject.service;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserOrders;
import com.epam.finalProject.exception.AppException;

public interface OrderService {

    boolean addOrder(String[] goodsIdArray, String[] goodsQuantityArray, long userId) throws AppException;
    boolean changeOrder(long orderNumber,int orderStatusId) throws AppException;
    UserOrders getUserOrdersList(long userId) throws AppException;
    UserOrders getUserOrdersFilter(long userId, int status,String sDate,String eDate) throws AppException;
}
