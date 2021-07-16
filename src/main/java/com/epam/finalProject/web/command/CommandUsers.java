package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.UserServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandUsers implements Command {
    private static final String ERROR = "errorMessage";
    private static final Logger log = LogManager.getLogger(CommandUsers.class);
    private static final Map<Integer, String> userStatusesMap;
    private UserServiceImpl userService = new UserServiceImpl();

    //To enable displaying user role in admin-user.jsp uncomment userRole map
//    private static final Map<Integer,String> userRoles;

    static {
        userStatusesMap = new HashMap<>();
//        userRoles=new HashMap<>();
        userStatusesMap.put(1, "Active");
        userStatusesMap.put(2, "Disabled");
//        userRoles.put(1,"Superadmin");
//        userRoles.put(2,"Admin");
//        userRoles.put(3,"User");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        String path = Path.ERRORPAGE;
        request.getSession().setAttribute("errorMessage", "Invalid parameter action");
        String s = request.getParameter("action");
        log.error("Action: " + s);
        if (s.equals("users")) {
            return redirectToUsers(request);
        }
        if (s.equals("update")) {
            return changeUserStatus(request);
        }
        return path;
    }

    private String redirectToUsers(HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getRoleId() > 2) {
            request.setAttribute(ERROR, "Access denied");
            return Path.ERRORPAGE;
        }
        List<User> userList = userService.getListOfUsers();
        session.setAttribute("userList", userList);
        session.setAttribute("userStatuses", userStatusesMap);
//        session.setAttribute("userRoles", userRoles);
        return Path.ADMIN_USERS;
    }

    private String changeUserStatus(HttpServletRequest request) throws AppException {
        String usernameToChange = request.getParameter("param");
        int status;
        try {
            status = Integer.parseInt(request.getParameter("status"));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Something wrong with parameters!");
            e.printStackTrace();
            log.log(Level.ERROR, e.getMessage());
            return Path.ERRORPAGE;
        }
        userService.changeUserStatus(usernameToChange, status);
        log.log(Level.ERROR, "Status changed");
        return redirectToUsers(request);
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }
}
