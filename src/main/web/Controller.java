package main.web;

import main.web.command.Command;
import main.web.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/controller")
public class Controller extends HttpServlet {
    private static final Logger log = Logger.getLogger(Controller.class);
    private static final long serialVersionUID = -2865377066620126318L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        process(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        process(request, response);
    }

    /**
     * Main method of this controller
     */

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("Controller starts");
        System.out.println("Controller starts");
        String commandName = request.getParameter("command");
        log.trace("Get command from request: " + commandName);
        System.out.println("Get command from request: " + commandName);
        Command command = CommandContainer.getCommand(commandName);
        log.trace("Obtain command");
        String forward = command.execute(request, response);
        log.trace("Execute command and forward to address: " + forward);
        System.out.println("Execute command and forward to address: " + forward);
        log.debug("Controller finished");
        if (forward != null) {
            if (forward.equals(Path.ERRORPAGE)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
            }else {
                response.sendRedirect(forward);
            }
        }
    }
}
