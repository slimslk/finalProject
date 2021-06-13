package main.web.command;

import main.database.CatalogDAO;
import main.exception.AppException;
import main.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandCatalog implements Command {
    private static final Logger log = LogManager.getLogger(CommandCatalog.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws AppException {
        log.error("In get Catalog");
        HttpSession session=req.getSession();
        int count=new CatalogDAO().getCountOfGoods();
        session.setAttribute("itemsCount",count);
        return Path.CATALOG;
    }
}
