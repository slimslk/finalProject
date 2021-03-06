package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.UserService;
import com.epam.finalProject.service.defaultImpl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

public class CommandLocale implements Command {
    private static final Logger log = LogManager.getLogger(CommandLocale.class);
    private UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String lang = request.getParameter("lang").toLowerCase();
        if (lang != null && !lang.isEmpty()) {
            if (user != null) {
                userService.setUserLocale(user.getUsername(), lang);
            }
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", lang);
            session.setAttribute("currentLocale", lang);
        }
        log.error("new locale is: " + lang);
        return null;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
