package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.UserOrders;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.OrderService;
import com.epam.finalProject.service.defaultImpl.OrderServiceImpl;
import com.epam.finalProject.web.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandAdminUserOrders implements Command {
    private OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        String path = Path.ADMIN_USER_ORDER;
        HttpSession session = request.getSession();
        long userId = 0;
        int statusID = 0;
        String status = request.getParameter("status");
        String sDate = request.getParameter("sDate");
        String eDate = request.getParameter("eDate");
        System.err.println("sDate: " + status + "eDate:" + eDate);
        String pattern = "^[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(sDate);
        Matcher mm=p.matcher(eDate);
        if(!m.find()||!mm.find()){
            session.setAttribute("errorMessage", "Wrong Date format");
            return Path.ERRORPAGE;
        }
        if (status.equals("registred")) {
            statusID = 1;
        }
        if (status.equals("paid")) {
            statusID = 2;
        }
        if (status.equals("canceled")) {
            statusID = 3;
        }
        System.err.println("Status is: " + status);
        try {
            userId = Long.parseLong(request.getParameter("userid"));
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Not a number");
            return Path.ERRORPAGE;
        }
        if (userId == 0) {
            return Path.ERRORPAGE;
        }
        System.err.println("statusID: " + statusID);
        UserOrders userOrders = orderService.getUserOrdersFilter(userId, statusID, sDate, eDate);
        System.out.println(userOrders);
        session.setAttribute("userOrdersF", userOrders);
        session.setAttribute("userid", userId);
        System.err.println("USERORDERS");
        return path;
    }
}
