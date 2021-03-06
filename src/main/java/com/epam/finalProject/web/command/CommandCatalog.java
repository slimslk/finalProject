package com.epam.finalProject.web.command;

import com.epam.finalProject.database.impl.CatalogDAOImpl;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.CatalogService;
import com.epam.finalProject.service.defaultImpl.CatalogServiceImpl;
import com.epam.finalProject.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandCatalog implements Command {
    private static final Logger log = LogManager.getLogger(CommandCatalog.class);
    private CatalogService catalogService = new CatalogServiceImpl(new CatalogDAOImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws AppException {
        log.error("In get Catalog");
        HttpSession session = req.getSession();
        int count = catalogService.getCountOfGoods();
        session.setAttribute("itemsCount", count);
        return Path.CATALOG;
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }
}
