package com.epam.finalProject.web;

import static com.epam.finalProject.web.listener.ContextListener.getContextPath;

/**
 * Path class for holding addresses
 */
public final class Path {
    public static final String STARTPAGE = getContextPath() + "/index.jsp";
    public static final String LOGINPAGE = getContextPath() + "/login.jsp";
    public static final String SINGUP = getContextPath() + "/sign-up.jsp";
    public static final String ERRORPAGE = "/error-page.jsp";
    public static final String CATALOG = getContextPath() + "/user/catalog.jsp";
    public static final String ADMIN_CATALOG = getContextPath() + "/admin/admin-catalog.jsp";
    public static final String CART = getContextPath() + "/user/cart.jsp";
    public static final String REDIRECT_TO_USER_ORDERS = getContextPath() + "/controller?command=orders&action=orders";
    public static final String USER_ORDERS = getContextPath() + "/user/user-orders.jsp";
    public static final String ADMIN_ORDERS = getContextPath() + "/admin/admin-orders.jsp";
    public static final String ADMIN_USERS = getContextPath() + "/admin/admin-users.jsp";
}
