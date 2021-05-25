package main.web.command;

import main.database.CatalogDAO;
import main.entity.CatalogItem;
import main.entity.UserCart;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class CommandCart implements Command {
    private static final Logger log = Logger.getLogger(CommandCart.class);

    private String addToCart(HttpSession session, long id) {
        log.debug("Goods ID parameter is: " + id);
        CatalogItem cItem = new CatalogDAO().getItemsByGoodsId(id);
        int count = cItem.getQuantity();
        log.debug("Quantity of goods in the stock: " + count);
        if (count < 1) {
            log.debug("No goods in the stock, return 0");
            return "0";
        }

        UserCart userCart = (UserCart) session.getAttribute("userCart");
        userCart.addToCart(id);
        log.debug("Goods added to cart: " + userCart.getGoodsId());
        return "1";
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String parameter = req.getParameter("cart");
        log.debug("Value of the the parameter cart is: " + parameter);
        log.debug("Value of the parameter Goods ID is: " + req.getParameter("goodsId"));
        if (parameter.equals("add")) {
            long id = Long.parseLong(req.getParameter("goodsId"));
            log.debug("Goods ID parameter is: " + id);
            CatalogItem cItem = new CatalogDAO().getItemsByGoodsId(id);
            int count = cItem.getQuantity();
            log.debug("Quantity of goods in the stock: " + count);
            res.setContentType("text/plain");
            PrintWriter out = res.getWriter();
            out.print(addToCart(session, id));
            out.flush();
            out.close();
        }
        return null;
    }
}
