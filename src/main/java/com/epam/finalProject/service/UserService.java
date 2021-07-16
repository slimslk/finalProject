package com.epam.finalProject.service;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;

import java.util.List;

public interface UserService {
    boolean changeUserStatus(String username, int status) throws AppException;
    boolean setUserLocale(String username, String locale) throws AppException;
    List<User> getListOfUsers() throws AppException;

}
