package main.web.command;

import main.database.UserDAO;
import main.database.UserInfoDAO;
import main.entity.User;
import main.entity.UserInfo;
import main.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandSingUp implements Command {

    private static final Logger log = Logger.getLogger(CommandSingUp.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        log.debug("Start writing user to DB");
        UserDAO userDAO = new UserDAO();
        UserInfo userInfo = new UserInfo();
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        //set username and password to a User object
        log.trace("Username: " + username);
        if (userDAO.getUserByUsername(username)!= null) {
            log.trace(username+" was already exist");
            req.setAttribute("errorMessage", "User already exist");
            return Path.ERRORPAGE;
        }
        User user = new User();
        log.debug("Didnt find user in DB.");
        user.setUsername(username);
        log.debug("set username to the user: "+user);
        user.setPassword(req.getParameter("password"));
        log.trace("New user:" +user);
        //insert the User object to DB
        userDAO.insertUser(user);
        log.debug("User added to DB");
        //set name and surname to a UserInfo object
        userInfo.setName(req.getParameter("name"));
        userInfo.setSurname(req.getParameter("surname"));
        log.debug("User info added to DB: "+userInfo);
        //take from DB user id
        user=userDAO.getUserByUsername(user.getUsername());
        log.trace("Get user from DB by username: "+user);
        long userId = user.getId();
        log.trace("take user id from DB: "+userId);
        userInfo.setUserId(userId);
        //insert the UserInfo object to DB
        log.trace("new user info: "+userInfo);
        new UserInfoDAO().insertUserInfo(userInfo);
        user.setUserInfo(userInfo);
        session.setAttribute("user", user);
        log.debug("Adding user to session: "+user);
        return "controller?command=catalog&sort=addDate&start=0&end=5";
    }
}
