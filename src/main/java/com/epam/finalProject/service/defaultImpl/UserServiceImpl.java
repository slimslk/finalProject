package com.epam.finalProject.service.defaultImpl;

import com.epam.finalProject.database.UserDAO;
import com.epam.finalProject.database.impl.UserDAOImpl;
import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO =new UserDAOImpl();

    @Override
    public boolean setUserLocale(String username, String locale) throws AppException {
        userDAO.updateUserLocale(username, locale);
        return true;
    }

    @Override
    public List<User> getListOfUsers() throws AppException {
        return userDAO.getUsers();
    }

    @Override
    public boolean changeUserStatus(String username, int status) throws AppException {
        userDAO.updateUserStatus(username, status);
        return true;
    }
}
