package main.web.command;

import main.exception.AppException;
import main.web.Path;
import main.database.UserDAO;
import main.entity.User;
import main.entity.UserCart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class CommandLogin implements Command {
    private static final Logger log = LogManager.getLogger(CommandLogin.class);
    UserDAO userDAO = new UserDAO();

    public CommandLogin(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public CommandLogin() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();

        //Obtain username and password from request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String forward = Path.STARTPAGE;
        String errorMsg;
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            session.setAttribute("incorrect", "login_jsp.alert.incorrect.empty.username");
            return Path.LOGINPAGE;
        }
        User user = userDAO.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            session.setAttribute("incorrect", "login_jsp.alert.incorrect.username");
            return Path.LOGINPAGE;
        }
        log.error("User is: " + user);
        log.error("User lang is: " + user.getLang());
        String lang = user.getLang();
        log.error("User locale: ", user.getLang());
        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", lang);
        session.setAttribute("currentLocale", lang);
        session.setAttribute("user", user);
        UserCart userCart = (UserCart) session.getAttribute("userCart");
        userCart.setUserId(user.getId());
        if(user.getRoleId()>2){
            return "controller?command=catalog";
        }
        return Path.REDIRECT_TO_USER_ORDERS;
    }
}
