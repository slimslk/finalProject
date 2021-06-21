package main.web.filter;

import main.entity.User;
import main.web.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UserStatusFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getStatusId() == 2) {
            session.setAttribute("errorMessage", "Your account was blocked!");
            RequestDispatcher dispatcher = req.getRequestDispatcher(Path.ERRORPAGE);
            dispatcher.forward(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
