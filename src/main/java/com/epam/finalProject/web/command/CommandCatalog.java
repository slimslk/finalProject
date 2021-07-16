package com.epam.finalProject.web.command;

import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.defaultImpl.CatalogServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandCatalog implements Command {
    private static final Logger log = LogManager.getLogger(CommandCatalog.class);
    private CatalogServiceImpl catalogService = new CatalogServiceImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws AppException {
        log.error("In get Catalog");
        HttpSession session = req.getSession();
        int count = catalogService.getCountOfGoods();
        session.setAttribute("itemsCount", count);
        return Path.CATALOG;
    }

    public void setCatalogService(CatalogServiceImpl catalogService) {
        this.catalogService = catalogService;
    }
}
