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
    UserDAO userDAO=new UserDAO();
    UserInfoDAO userInfoDAO=new UserInfoDAO();

    public CommandSingUp(UserDAO userDAO, UserInfoDAO userInfoDAO) {
        this.userDAO = userDAO;
        this.userInfoDAO = userInfoDAO;
    }

    public CommandSingUp() {
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws AppException {
        String lang = null;
        log.debug("Start writing user to DB");
        UserInfo userInfo = new UserInfo();
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            log.error("Username or password are empty");
            session.setAttribute("incorrect", "login_jsp.alert.incorrect.empty.username");
            return Path.SINGUP;
        }
        log.error("Username: " + username);
        User userFromDB = userDAO.getUserByUsername(username);
        if (userFromDB != null && userFromDB.getUsername().equals(username)) {
            log.trace(username + " was already exist");
            session.setAttribute("incorrect", "sing-up.jsp.alert.incorrect.username.taken");
            return Path.SINGUP;
        }
        if (name == null || surname == null || name.isEmpty() || surname.isEmpty()) {
            log.error("Name or surname are empty");
            session.setAttribute("incorrect", "sing-up.jsp.alert.incorrect.empty.name");
            return Path.SINGUP;
        }
        if (req.getParameter("lang") != null) {
            lang = req.getParameter("lang").toLowerCase();
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
        session.setAttribute("user", user);
        UserCart userCart = (UserCart) session.getAttribute("userCart");
        userCart.setUserId(user.getId());
        log.debug("Adding user to session: " + user);
        return "controller?command=catalog";
    }
}
