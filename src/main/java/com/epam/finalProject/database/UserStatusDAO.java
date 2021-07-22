package com.epam.finalProject.database;

import com.epam.finalProject.exception.AppException;

public interface UserStatusDAO {
    int getStatusByName(String statusName) throws AppException;
}
