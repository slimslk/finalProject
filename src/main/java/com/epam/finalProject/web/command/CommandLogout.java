package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout
 */

public class CommandLogout implements Command {
    private static final Logger log=LogManager.getLogger(CommandLogout.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession httpSession=request.getSession(false);
        if(httpSession!=null){
            httpSession.invalidate();
        }
        UserCart userCart=new UserCart();
        httpSession=request.getSession();
        httpSession.setAttribute("userCart", userCart);
        log.debug("Session invalidated");
        return Path.CATALOG;
    }
}
