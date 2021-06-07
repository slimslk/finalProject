package main.web.command;

import main.database.CatalogDAO;
import main.entity.CatalogItem;
import main.entity.UserCart;
import main.exception.DBException;
import main.web.Path;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandCart implements Command {
    private static final Logger log = LogManager.getLogger(CommandCart.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        HttpSession session = req.getSession();
        UserCart userCart = (UserCart) session.getAttribute("userCart");
        log.error("user cart is in session: "+userCart);
        Map<Long, Integer> map = userCart.getGoodsId();
        if (map.size() > 0) {
            List<Long> listId = new ArrayList<>();
            for (Map.Entry<Long,Integer> entry: map.entrySet() ) {
                log.error("k is:" +entry.getKey());
                listId.add(entry.getKey());
                log.error("user map: "+userCart.getGoodsId());
                log.error("list Id is: " + listId);
            }
            List<CatalogItem> catalogItemList = new CatalogDAO().getItemsByGoodsId(listId);
            log.error("Items in cart: " + catalogItemList);
            log.error("In the cart!");
            session.setAttribute("cartGoods",catalogItemList);
        } else {
            log.error("No items on the cart");
        }
        return Path.CART;
    }
}
