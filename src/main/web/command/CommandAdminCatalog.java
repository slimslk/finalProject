package main.web.command;

import main.database.ChangeGoodsDAO;
import main.entity.*;
import main.exception.DBException;
import main.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

public class CommandAdminCatalog implements Command {
    private static final Logger log = LogManager.getLogger(CommandAdminCatalog.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String path = Path.ERRORPAGE;
        if (user.getRoleId() > 2) {
            request.setAttribute("errorMessage", "You don't have permission for that");
            return Path.ERRORPAGE;
        }

        String action = request.getParameter("action");
        System.out.println("action: " + action);
        if (action == null) {
            return Path.ADMIN_CATALOG;
        }
        if (action.equals("confirm") || action.equals("create")) {
            path = changeItem(request, action);
        }
        if (action.equals("remove")) {
            path = removeItem(request);
        }
        System.out.println("Path is:" + path);
        return path;
    }

    public String removeItem(HttpServletRequest request) {
        try {
            long goodsParamId = Long.parseLong(request.getParameter("goodsParamId"));
            new ChangeGoodsDAO().removeGoods(goodsParamId);
        } catch (NumberFormatException | DBException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Wrong number parameters");
            return Path.ERRORPAGE;
        }
        return Path.ADMIN_CATALOG;
    }

    /**
     * Method implements creating new or updating exist goods item
     *
     * @param action parameter set what kind of the action need to do: create new or update exist goods item
     * @return path to the admin catalog
     */
    public String changeItem(HttpServletRequest request, String action) {
        try {
            CatalogItem catalogItem = new CatalogItem();
            GoodsParam goodsParam = new GoodsParam();
            Goods goods = new Goods();
            String name = request.getParameter("name");
            String age = request.getParameter("age");
            String category = request.getParameter("category");
            String gender = request.getParameter("gender");
            String size = request.getParameter("size");
            String style = request.getParameter("style");
            String img = request.getParameter("img");
            long itemCatalogId=0;
            long goodsParamId=0;
            long goodsId=0;
            if (action.equals("confirm")) {
                itemCatalogId = Long.parseLong(request.getParameter("itemCatalogId"));
                goodsParamId = Long.parseLong(request.getParameter("goodsParamId"));
                goodsId = Long.parseLong(request.getParameter("goodsId"));
            }
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            goods.setId(goodsId);
            goods.setName(name);
            goods.setImg(img);
            goodsParam.setGoodsId(goodsId);
            goodsParam.setId(goodsParamId);
            goodsParam.setAgeName(age);
            goodsParam.setCategoryName(category);
            goodsParam.setGenderName(gender);
            goodsParam.setSizeName(size);
            goodsParam.setStyleName(style);
            catalogItem.setGoods(goods);
            catalogItem.setGoodsParam(goodsParam);
            catalogItem.setId(itemCatalogId);
            catalogItem.setGoodsParamId(goodsParamId);
            catalogItem.setPrice(price);
            catalogItem.setQuantity(quantity);
            catalogItem.setAddDate(java.sql.Date.valueOf(LocalDate.now()));
            if (action.equals("create")) {
                new ChangeGoodsDAO().createGoods(catalogItem);
                return Path.ADMIN_CATALOG;
            }
            log.error("Catalog item is: " + catalogItem);
            new ChangeGoodsDAO().updateGoods(catalogItem);
            return Path.ADMIN_CATALOG;
        } catch (NumberFormatException | DBException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Wrong number parameters");
            return Path.ERRORPAGE;
        }
    }
}
