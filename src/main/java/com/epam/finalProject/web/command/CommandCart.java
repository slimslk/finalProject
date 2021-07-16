package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.CatalogServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandCart implements Command {
    private static final Logger log = LogManager.getLogger(CommandCart.class);
    private CatalogServiceImpl catalogService = new CatalogServiceImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws AppException {
        HttpSession session = req.getSession();
        UserCart userCart = (UserCart) session.getAttribute("userCart");
        log.error("user cart is in session: " + userCart);
        Map<Long, Integer> map = userCart.getGoodsId();
        if (map.size() > 0) {
            List<Long> listId = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : map.entrySet()) {
                log.error("k is:" + entry.getKey());
                listId.add(entry.getKey());
                log.error("user map: " + userCart.getGoodsId());
                log.error("list Id is: " + listId);
            }
            List<CatalogItem> catalogItemList = catalogService.getListOfItems(listId);
            log.error("Items in cart: " + catalogItemList);
            session.setAttribute("cartGoods", catalogItemList);
        } else {
            log.error("No items on the cart");
        }
        return Path.CART;
    }

    public void setCatalogService(CatalogServiceImpl catalogService) {
        this.catalogService = catalogService;
    }
}
