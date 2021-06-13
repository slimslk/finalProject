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
    UserDAO userDAO=new UserDAO();

    public CommandLogin(UserDAO userDAO){
        this.userDAO=userDAO;
    }

    public CommandLogin(){
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();

        //Obtain username and password from request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String forward = Path.CATALOG;
        String errorMsg;
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            errorMsg = "Username or password can't be empty";
            return setErrorMessage(errorMsg, request);
        }
        User user = userDAO.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            errorMsg = "Incorrect username or password";
            return setErrorMessage(errorMsg, request);
        }
        String lang=user.getLang();
//        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", lang);
//        session.setAttribute("currentLocale", lang);
        session.setAttribute("user", user);
        UserCart userCart=(UserCart)session.getAttribute("userCart");
        userCart.setUserId(user.getId());
        return forward;
    }

    private String setErrorMessage(String errorMsg, HttpServletRequest request) {
        String forward = Path.ERRORPAGE;
        request.getSession().setAttribute("errorMessage", errorMsg);
        log.error("Error: " + errorMsg);
        return forward;
    }
}
