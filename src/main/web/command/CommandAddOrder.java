package main.web.command;

import main.database.OrderDAO;
import main.database.OrderListDAO;
import main.entity.Order;
import main.entity.User;
import main.entity.UserCart;
import main.exception.AppException;
import main.web.Path;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class CommandAddOrder implements Command {
    private static final Logger log = LogManager.getLogger(CommandAddOrder.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<Long, Integer> goodsMap = new HashMap<>();
        String[] goodsIdAsStringArray;
        String[] goodsQuantityAsStringArray;
        long userId;
        goodsIdAsStringArray = request.getParameterValues("goodsId");
        goodsQuantityAsStringArray = request.getParameterValues("quantity");
        if (goodsIdAsStringArray.length != goodsQuantityAsStringArray.length) {
            session.setAttribute("errorMessage", "Something wrong with creating order");
            return Path.ERRORPAGE;
        }
        try {
            userId = user.getId();
            for (int i = 0; i < goodsIdAsStringArray.length; i++) {
                long goodsId = Long.parseLong(goodsIdAsStringArray[i]);
                int goodsQuantity = Integer.parseInt(goodsQuantityAsStringArray[i]);
                goodsMap.put(goodsId, goodsQuantity);
            }
        } catch (NumberFormatException e) {
            log.error("Parameter not a number");
            e.printStackTrace();
            session.setAttribute("errorMessage", "Something wrong with parameters when we trying to add your order to the cart");
            return Path.ERRORPAGE;
        }
        log.error("Goods map: " + goodsMap);
        Order order = new Order();
        OrderDAO orderDAO = new OrderDAO();
        long orderNumber = orderDAO.getMaxId() + 1;
        log.error("Order number is: " + orderNumber);
        order.setOrderNumber(orderNumber);
        order.setUserId(userId);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        log.error("Order: " + order);
        new OrderListDAO().insertOrderInList(orderDAO.insertOrder(order, "registered"), goodsMap);
        log.error("Order is: " + order);
        UserCart userCart = new UserCart();
        userCart.setUserId(userId);
        session.setAttribute("userCart", userCart);
        session.setAttribute("inCartCount", 0);
        return Path.REDIRECT_TO_USER_ORDERS;
    }
}
