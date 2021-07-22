package com.epam.finalProject.database;

import com.epam.finalProject.exception.AppException;

public interface RoleDAO {
    int getRoleByName(String roleName) throws AppException;
}
