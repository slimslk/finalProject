package main.web.command;

import main.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout
 */

public class CommandLogout implements Command {
    private static final Logger log=Logger.getLogger(CommandLogout.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession=request.getSession(false);
        if(httpSession!=null){
            httpSession.invalidate();
        }
        log.debug("Session invalidated");
        return Path.STARTPAGE;
    }
}
