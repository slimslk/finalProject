package main.web.listener;

import main.database.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ContextListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(ContextListener.class);
    private static String contextPath;
    private static Map<Integer, String> orderStatuses;

    static {
        orderStatuses = new HashMap<>();
        orderStatuses.put(1, "Registered");
        orderStatuses.put(2, "Paid");
        orderStatuses.put(3, "Canceled");
    }

    public void setContextPath(String contextPath){
        this.contextPath=contextPath;
    }

    public static String getContextPath(){
        return contextPath;
    }

    public void contextInitialized(ServletContextEvent event) {
        //creating instance of DBManager
        DBManager dbManager = DBManager.getInstance();

        // obtain file name with locales descriptions
        ServletContext context = event.getServletContext();
        String localesFileName = context.getInitParameter("locales");
        setContextPath(context.getContextPath());
        String localesFileRealPath = context.getRealPath(localesFileName);

        // load descriptions
        Properties locales = new Properties();
        try (FileInputStream fs = new FileInputStream(localesFileRealPath)) {
            locales.load(fs);
        } catch (IOException e) {
            log.error("Can't load file");
            e.printStackTrace();
        }
        // save descriptions to servlet context
        context.setAttribute("locales", locales);
        context.setAttribute("orderStatuses", orderStatuses);
    }
}
