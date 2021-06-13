package main.web.command;

import main.database.UserDAO;
import main.database.UserInfoDAO;
import main.entity.User;
import main.entity.UserCart;
import main.entity.UserInfo;
import main.exception.AppException;
import main.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandSingUp implements Command {

    private static final Logger log = LogManager.getLogger(CommandSingUp.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws AppException {
        String lang = null;
        log.debug("Start writing user to DB");
        UserDAO userDAO = new UserDAO();
        UserInfo userInfo = new UserInfo();
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        log.error("HERE");
        if(req.getParameter("lang")!=null){
            lang = req.getParameter("lang").toLowerCase();
        }
        //set username and password to a User object
        log.error("Username: " + username);
        if (userDAO.getUserByUsername(username)!= null) {
            log.trace(username+" was already exist");
            session.setAttribute("errorMessage", username+"was already exist");
            return Path.ERRORPAGE;
        }
        User user = new User();
        log.error("Didnt find user in DB.");
        user.setUsername(username);
        if(lang==null){
            user.setLang("ru");
        }else {
            user.setLang(lang);
        }
        log.debug("set username to the user: "+user);
        user.setPassword(req.getParameter("password"));
        log.trace("New user:" +user);
        //insert the User object to DB
        log.error("User: "+user);
        userDAO.insertUser(user);
        log.debug("User added to DB");
        //set name and surname to a UserInfo object
        userInfo.setName(req.getParameter("name"));
        userInfo.setSurname(req.getParameter("surname"));
        log.debug("User info added to DB: "+userInfo);
        //take from DB user id
        user=userDAO.getUserByUsername(user.getUsername());
        long userId = user.getId();
        log.trace("take user id from DB: "+userId);
        userInfo.setUserId(userId);
        //insert the UserInfo object to DB
        log.trace("new user info: "+userInfo);
        new UserInfoDAO().insertUserInfo(userInfo);
        user.setUserInfo(userInfo);
        session.setAttribute("user", user);
        UserCart userCart=(UserCart)session.getAttribute("userCart");
        userCart.setUserId(user.getId());
        log.debug("Adding user to session: "+user);
        return "controller?command=catalog&sort=addDate&start=0&end=5";
    }
}
