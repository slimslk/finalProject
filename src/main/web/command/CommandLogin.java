package main.web.command;

import main.Path;
import main.database.UserDAO;
import main.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandLogin implements Command {
    private static final Logger log = Logger.getLogger(CommandLogin.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        //Obtain username and password from request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String forward = Path.LOGINPAGE;
        String errorMsg;
        System.out.println("Before all");
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            errorMsg = "Username or password can't be empty";
            return setErrorMessage(errorMsg, request);
        }
        System.out.println("username: "+username);
        System.out.println("password: "+password);
        User user = new UserDAO().getUserByUsername(username);
        System.out.println(user);
        if (user==null||!user.getPassword().equals(password)){
            System.out.println("user: "+user);
            errorMsg = "Incorrect username or password";
            return setErrorMessage(errorMsg,request);
        }

//        session.setAttribute("user", user);
            return forward;
    }

    private String setErrorMessage(String errorMsg, HttpServletRequest request) {
        String forward = Path.ERRORPAGE;
        request.setAttribute("errorMessage", errorMsg);
        log.error("Error: " + errorMsg);
        return forward;
    }
}
