package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.SingUpService;
import com.epam.finalProject.service.defaultImpl.SingUpServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandSingUp implements Command {
    private static final Logger log = LogManager.getLogger(CommandSingUp.class);
    private SingUpService singUpService = new SingUpServiceImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws AppException {
        log.debug("Start writing user to DB");
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String lang = null;

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            log.error("Username or password are empty");
            session.setAttribute("incorrect", "login_jsp.alert.incorrect.empty.username");
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
        User user = singUpService.insertUserToDB(username, password, name, surname, lang);
        if (user == null) {
            log.error(username + " was already exist");
            session.setAttribute("incorrect", "sing-up.jsp.alert.incorrect.username.taken");
            return Path.SINGUP;
        }
        session.setAttribute("user", user);
        UserCart userCart = (UserCart) session.getAttribute("userCart");
        userCart.setUserId(user.getId());
        log.debug("Adding user to session: " + user);
        return "controller?command=catalog";
    }

    public void setSingUpService(SingUpService singUpService) {
        this.singUpService = singUpService;
    }
}
