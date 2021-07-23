package com.epam.finalProject.web;

import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.web.command.Command;
import com.epam.finalProject.web.command.CommandContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100)
public class Controller extends HttpServlet {
    private static final Logger log = LogManager.getLogger(Controller.class);
    private static final long serialVersionUID = -2865377066620126318L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        process(req,resp);
    }

    /**
     * Main method of this controller
     */

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = Path.ERRORPAGE;
        log.debug("Controller starts");
        System.out.println("Controller");
        String commandName = request.getParameter("command");
        log.error("Get command from request: " + commandName);
        Command command = CommandContainer.getCommand(commandName);
        log.error("Obtain command"+command);
        try {
            forward = command.execute(request, response);
        } catch (AppException ex) {
            log.error("Catch exception " + ex);
            request.getSession().setAttribute("errorMessage", ex);
        }
        log.error("Execute command and forward to address: " + forward);
        System.out.println("Execute command and forward to address: " + forward);
        log.debug("Controller finished");
        if (forward != null) {
            if (forward.equals(Path.ERRORPAGE)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(forward);
            }
        }
    }
}
