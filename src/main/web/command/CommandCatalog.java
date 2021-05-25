package main.web.command;

import main.web.Path;
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
        String param = req.getParameter("param");
        String sort = req.getParameter("sort");
        String dir = req.getParameter("direction");
        int start = Integer.parseInt(req.getParameter("start"));
        int end = Integer.parseInt(req.getParameter("end"));
        log.debug("Parametrs:" +param+" Sort parameter: " + sort + " Start index: " + start + " End index: " + end);
        catalog = new CatalogDAO().getPartItemsAndSort(param, sort, dir, start, end);
        req.getSession().setAttribute("catalog", catalog);
        System.out.println(catalog);
        return Path.CATALOG;
    }
}
