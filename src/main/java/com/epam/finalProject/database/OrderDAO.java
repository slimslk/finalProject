package com.epam.finalProject.database;

import com.epam.finalProject.entity.Order;
import com.epam.finalProject.exception.AppException;

public interface OrderDAO {
    long getMaxId() throws AppException;

    long insertOrder(Order order, String status) throws AppException;
}
