package main.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main interface for the Command pattern implementation.
 */

public interface Command {
    /**
     *Executing method for command
     * @return Address to go.
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
