package main.web.command;

import main.Path;
import main.database.CatalogDAO;
import main.entity.CatalogItem;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandCatalog implements Command {
    private static final Logger log = Logger.getLogger(CommandCatalog.class);
    private List<CatalogItem> catalog = new ArrayList<>();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String sort = req.getParameter("sort");
        int start = Integer.parseInt(req.getParameter("start"));
        int end = Integer.parseInt(req.getParameter("end"));
        log.debug("Sort parameter: " + sort + " Start index: " + start + " End index: " + end);
        catalog = new CatalogDAO().getPartItemsAndSort(sort, start, end);
        req.getSession().setAttribute("catalog",catalog);
        System.out.println(catalog);
        return Path.CATALOG;
    }
}
