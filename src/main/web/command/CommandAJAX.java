package main.web.command;

import main.database.CatalogDAO;
import main.entity.CatalogItem;
import main.entity.User;
import main.entity.UserCart;
import main.exception.DBException;
import main.jackson.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CommandAJAX implements Command {
    private static final Logger log = LogManager.getLogger(CommandAJAX.class);

    private String filterGoods(HttpServletRequest req) throws DBException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        log.error("In filter now!");
        List<CatalogItem> catalog;
        String[] params;
        String s = "";
        //Getting sorting parameters from request
        params = req.getParameterValues("param");
        String sort = req.getParameter("sort").toLowerCase();
        String dir = req.getParameter("direction").toLowerCase();
        int start = Integer.parseInt(req.getParameter("start"));
        if (params != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < params.length; i++) {
                params[i] = params[i].toLowerCase();
                sb.append(params[i]);
            }
            s = sb.toString().toLowerCase();
        }
        log.error(s + " || " + "Sort parameter: " + sort + " || Direction: " + dir + " || Start index: " + start + " || Count of columns: ");
        catalog = new CatalogDAO().getListOfSortedItems(params, sort, dir, start);
        if (user != null && user.getRoleId() < 3) {
            session.setAttribute("itemCatalogList", catalog);
        }
        if (catalog == null) {
            req.setAttribute("errorMessage", "Something wrong whit query to DB, try again in few a minutes");
            return "errorMessage";
        }
        JSONParser jsonParser = new JSONParser();
        String jsonString = jsonParser.generateJSONFromList(catalog);
        return jsonString;
    }

    private String addToCart(HttpServletRequest request, boolean isAdd) throws DBException {
        CatalogDAO catalogDAO = new CatalogDAO();
        HttpSession session = request.getSession();
        int inCartCount=0;
        if(session.getAttribute("inCartCount")!=null){
            inCartCount = (int) session.getAttribute("inCartCount");
        }
        long id;
        int q;
        try {
            id = Long.parseLong(request.getParameter("goodsId"));
            q = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
            log.error("Not a number in parameter", e);
            return "0";
        }
        log.debug("Goods ID parameter is: " + id);
        log.debug("Quantity parameter is: " + q);
        CatalogItem cItem = catalogDAO.getItemByGoodsId(id);
        int quantity = cItem.getQuantity();
        log.error("Quantity");
        UserCart userCart = (UserCart) session.getAttribute("userCart");
        log.error("Quantity of goods in the stock: " + quantity);
        log.error("Catalog item before update if: " + cItem);
        if (isAdd) {
            if (quantity < 1) {
                log.debug("No goods in the stock, return 0");
                return "0";
            }
            userCart.addToCart(id);
            inCartCount++;
            log.error("Goods added to cart: " + userCart.getGoodsId());
            cItem.setQuantity(quantity - 1);
        } else {
            log.error("We are in remove and quantity is:" + q);
            userCart.removeFromCart(id, q);
            inCartCount-=q;
            log.error("Goods removed from cart: " + userCart.getGoodsId());
            cItem.setQuantity(quantity + q);
        }
        session.setAttribute("inCartCount", inCartCount);
        catalogDAO.updateCatalogItem(cItem);
        return "1";
    }


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        String responseString = "";
        String parameter = req.getParameter("do");

        log.error("Value of the the parameter ajax is: " + parameter);
        switch (parameter) {
            case "add":
                responseString = addToCart(req, true);
                break;
            case "remove":
                responseString = addToCart(req, false);
                break;
            case "filter":
                responseString = filterGoods(req);
                break;
            default:
                responseString = "Invalid parameter";
        }

        res.setContentType("text/plain");
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            log.error("Cant write to response");
            throw new DBException("Server not response, try later", e);
        }
        out.print(responseString);
        out.flush();
        out.close();
        return null;
    }
}
