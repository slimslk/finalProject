package main.web.command;

import main.exception.DBException;
import main.web.Path;
import main.database.CatalogDAO;
import main.entity.CatalogItem;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandCatalog implements Command {
    private static final Logger log = LogManager.getLogger(CommandCatalog.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws DBException {
        return Path.CATALOG;
    }
}
