package main.web.command;

import main.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for the Command pattern implementation.
 */

public interface Command {
    /**
     *Executing method for command
     * @return Address to go.
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws AppException;
}
