package com.epam.finalProject.service.defaultImpl;

import com.epam.finalProject.database.UserDAO;
import com.epam.finalProject.database.UserInfoDAO;
import com.epam.finalProject.database.impl.UserDAOImpl;
import com.epam.finalProject.database.impl.UserInfoDAOImpl;
import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserInfo;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.SingUpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingUpServiceImpl implements SingUpService {
    private static final Logger log = LogManager.getLogger(SingUpServiceImpl.class);
    private UserDAO userDAO =new UserDAOImpl();
    private UserInfoDAO userInfoDAO =new UserInfoDAOImpl();

    @Override
    public User insertUserToDB(String username,String password,String name,String surname, String lang) throws AppException {
        UserInfo userInfo=new UserInfo();
        User userFromDB = userDAO.getUserByUsername(username);
        if (userFromDB != null && userFromDB.getUsername().equals(username)) {
            return null;
        }
        //set username and password to a User object
        User user = new User();
        user.setUsername(username);
        if (lang == null) {
            user.setLang("ru");
        } else {
            user.setLang(lang);
        }
        user.setPassword(password);
        //insert the User object to DB
        log.error("User: " + user);
        userDAO.insertUser(user);
        log.debug("User added to DB");
        //set name and surname to a UserInfo object
        userInfo.setName(name);
        userInfo.setSurname(surname);
        log.debug("User info added to DB: " + userInfo);
        //take from DB user id
        user = userDAO.getUserByUsername(user.getUsername());
        long userId = user.getId();
        log.trace("take user id from DB: " + userId);
        userInfo.setUserId(userId);
        //insert the UserInfo object to DB
        log.trace("new user info: " + userInfo);
        userInfoDAO.insertUserInfo(userInfo);
        user.setUserInfo(userInfo);
        return user;
    }
}
