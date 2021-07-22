package com.epam.finalProject.database;

import com.epam.finalProject.exception.AppException;

public interface OrderStatusDAO {
    int getStatusByName(String statusName) throws AppException;
    String getStatusNameById(int id) throws AppException;
}
