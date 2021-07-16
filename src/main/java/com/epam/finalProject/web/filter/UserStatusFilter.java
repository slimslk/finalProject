package com.epam.finalProject.web.filter;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UserStatusFilter implements Filter {
    private final static Logger log= LogManager.getLogger(UserStatusFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getStatusId() == 2) {
            log.error("Account was blocked");
            session.setAttribute("errorMessage", "Your account was blocked!");
            RequestDispatcher dispatcher = req.getRequestDispatcher(Path.ERRORPAGE);
            dispatcher.forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
