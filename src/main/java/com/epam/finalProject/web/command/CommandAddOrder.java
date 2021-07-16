package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.OrderServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandAddOrder implements Command {
    private static final Logger log = LogManager.getLogger(CommandAddOrder.class);
    private OrderServiceImpl orderService = new OrderServiceImpl();

    public CommandAddOrder() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String[] goodsIdAsStringArray;
        String[] goodsQuantityAsStringArray;
        long userId;
        goodsIdAsStringArray = request.getParameterValues("goodsId");
        goodsQuantityAsStringArray = request.getParameterValues("quantity");
        log.error("GID: " + goodsIdAsStringArray + " GQ: " + goodsQuantityAsStringArray);
        if (goodsIdAsStringArray.length != goodsQuantityAsStringArray.length) {
            session.setAttribute("errorMessage", "Something wrong with creating order");
            return Path.ERRORPAGE;
        }
        userId = user.getId();
        if (!orderService.addOrder(goodsIdAsStringArray, goodsQuantityAsStringArray, userId)) {
            session.setAttribute("errorMessage", "Something wrong with parameters when we trying to add your order to the cart");
            return Path.ERRORPAGE;
        }
        UserCart userCart = new UserCart();
        userCart.setUserId(userId);
        session.setAttribute("userCart", userCart);
        session.setAttribute("inCartCount", 0);
        return Path.REDIRECT_TO_USER_ORDERS;
    }

    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
}
