package com.epam.finalProject.service;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;

public interface LoginService {
    User getUserByUsername(String username, String password) throws AppException;
}
