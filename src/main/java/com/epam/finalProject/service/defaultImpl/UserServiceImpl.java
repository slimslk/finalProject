package com.epam.finalProject.service.defaultImpl;

import com.epam.finalProject.database.impl.UserDAOImpl;
import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAOImpl userDAOImpl =new UserDAOImpl();

    @Override
    public boolean setUserLocale(String username, String locale) throws AppException {
        userDAOImpl.updateUserLocale(username, locale);
        return true;
    }

    @Override
    public List<User> getListOfUsers() throws AppException {
        return userDAOImpl.getUsers();
    }

    @Override
    public boolean changeUserStatus(String username, int status) throws AppException {
        userDAOImpl.updateUserStatus(username, status);
        return true;
    }
}
