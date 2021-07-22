package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserOrders;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.OrderService;
import com.epam.finalProject.service.defaultImpl.OrderServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandOrders implements Command {
    private static final Logger log = LogManager.getLogger(CommandOrders.class);
    private OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        String path = Path.ERRORPAGE;
        String s = request.getParameter("action");
        if (s.equals("orders")) {
            return redirectToOrders(session);
        }
        if (s.equals("update")) {
            return changeOrderStatus(request);
        }
        return path;
    }

    private String changeOrderStatus(HttpServletRequest request) {
        try {
            long orderNumber = Long.parseLong(request.getParameter("param"));
            int orderStatusId = Integer.parseInt(request.getParameter("status"));
            orderService.changeOrder(orderNumber, orderStatusId);
            return redirectToOrders(request.getSession());
        } catch (NumberFormatException | AppException e) {
            request.getSession().setAttribute("errorMessage", "Something wrong with parameter");
            e.printStackTrace();
            return Path.ERRORPAGE;
        }
    }

    private String redirectToOrders(HttpSession session) throws AppException {
        long userId = 0;
        String path = Path.ADMIN_ORDERS;
        User user = (User) session.getAttribute("user");
        if (user.getRoleId() == 3) {
            userId = user.getId();
            path = Path.USER_ORDERS;
        }
        UserOrders userOrders = orderService.getUserOrdersList(userId);
        session.setAttribute("userOrders", userOrders);
        return path;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
