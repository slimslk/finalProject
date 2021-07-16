package com.epam.finalProject.web.command;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.entity.Goods;
import com.epam.finalProject.entity.GoodsParam;
import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.AdminServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class CommandAdminCatalog implements Command {
    private static final Logger log = LogManager.getLogger(CommandAdminCatalog.class);
    private AdminServiceImpl adminService = new AdminServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String path = Path.ERRORPAGE;
        if (user.getRoleId() > 2) {
            session.setAttribute("errorMessage", "You don't have permission for that");
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

    private String removeItem(HttpServletRequest request) {
        try {
            long goodsParamId = Long.parseLong(request.getParameter("goodsParamId"));
            adminService.removeItem(goodsParamId);
        } catch (NumberFormatException | AppException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Wrong number parameters");
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
    private String changeItem(HttpServletRequest request, String action) throws AppException {
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
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Part file = request.getPart("img");
            String javaPath = "/Users/dimm/MyFiles/JavaLearn/EPAM/finalProject/src/main/webapp/img/";
            String fileName;
            if (file.getSize() > 0) {
                System.out.println("File is: " + file);
                fileName = file.getSubmittedFileName();
                System.err.println("SIZE OF FILE: " + file.getSize());
                ServletContext servletContext = request.getServletContext();
                String path = servletContext.getRealPath("/") + "img/";
                file.write(path + fileName);
                if (new File(javaPath).exists()) {
                    file.write(javaPath + fileName);
                }
            } else {
                fileName = request.getParameter("filename");
            }
            long itemCatalogId = 0;
            long goodsParamId = 0;
            long goodsId = 0;
            if (action.equals("confirm")) {
                itemCatalogId = Long.parseLong(request.getParameter("itemCatalogId"));
                goodsParamId = Long.parseLong(request.getParameter("goodsParamId"));
                goodsId = Long.parseLong(request.getParameter("goodsId"));
            }
            goods.setId(goodsId);
            goods.setName(name);
            goods.setImg(fileName);
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
                adminService.createItem(catalogItem);
                return Path.ADMIN_CATALOG;
            }
            log.error("Catalog item is: " + catalogItem);
            adminService.updateItem(catalogItem);
            return Path.ADMIN_CATALOG;
        } catch (NumberFormatException | AppException | IOException | ServletException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Wrong number parameters");
            throw new AppException("Wrong parameters", e);
        }
    }

    public void setAdminService(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

}
