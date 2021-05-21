package main.web.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandCart implements Command {
    private static final Logger log = Logger.getLogger(CommandCart.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String id=req.getParameter("goodsId");
        log.debug("Come in to add to cart command and parameter is: "+id);
        return null;
    }
}
