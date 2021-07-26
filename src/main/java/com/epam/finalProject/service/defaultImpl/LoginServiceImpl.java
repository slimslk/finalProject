package com.epam.finalProject.service.defaultImpl;

import com.epam.finalProject.database.UserDAO;
import com.epam.finalProject.database.impl.UserDAOImpl;
import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.LoginService;

public class LoginServiceImpl implements LoginService {
    private UserDAO userDAOI =new UserDAOImpl();

    @Override
    public User getUserByUsername(String username, String password) throws AppException {
        User user= userDAOI.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }
}
