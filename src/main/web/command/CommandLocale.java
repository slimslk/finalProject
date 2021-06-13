package main.web.command;

import main.database.UserDAO;
import main.entity.User;
import main.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

public class CommandLocale implements Command {
    private static final Logger log = LogManager.getLogger(CommandContainer.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String lang = request.getParameter("lang").toLowerCase();
        if (lang != null && !lang.isEmpty()) {
            if(user!=null){
                new UserDAO().updateUserLocale(user.getUsername(),lang);
            }
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", lang);
            session.setAttribute("currentLocale", lang);
        }
        log.error("new loacal is: " + lang);
        return null;
    }
}
