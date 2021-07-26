package com.epam.finalProject.database;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;

import java.util.List;

public interface UserDAO {
    void updateUserStatus(String username, int status) throws AppException;

    void updateUserLocale(String username, String locale) throws AppException;

    List<User> getUsers() throws AppException;

    User getUserByUsername(String username) throws AppException;

    void insertUser(User user, String roleName, String statusName) throws AppException;

    void insertUser(User user) throws AppException;
}
