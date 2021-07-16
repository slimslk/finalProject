package com.epam.finalProject.database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Fields {
    private Fields() {
    }

    //Parameter map
    public static final Map<String, String> PARAM_MAP = new HashMap<>();
    public static final Map<String, String> SORT_MAP = new HashMap<>();

    //Parameter values set
    public static final Set<String> VALUES_SET = new HashSet();


    //Row names
    public static final String ENTITY_ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ROLE_ID = "roleId";
    public static final String LOCALE = "locale";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String USER_ID = "userId";
    public static final String USER_STATUS_ID = "status";
    public static final String IMG = "img";
    public static final String ORDER_STATUS="orderStatus";
    public static final String ORDER_DATE="orderDate";
    public static final String GOODS_ID="goodsId";
    public static final String ORDER_ID="orderId";
    public static final String GOODS_QUANTITY="goodsQuantity";
    public static final String ORDER_NUMBER="orderNumber";
    public static final String STATUS_NAME="statusName";
    public static final String PRICE="price";
    public static final String AGE_NAME="ageName";
    public static final String CATEGORY_NAME="categoryName";
    public static final String GENDER_NAME="genderName";
    public static final String SIZE_NAME="sizeName";
    public static final String STYLE_NAME="styleName";


    //SQL query expression
    public static final String GENDER = "SELECT genderName FROM gender WHERE id=?";
    public static final String AGE = "SELECT ageName FROM age WHERE id=?";
    public static final String SIZE = "SELECT sizeName FROM size WHERE id=?";
    public static final String CATEGORY = "SELECT categoryName FROM category WHERE id=?";
    public static final String STYLE = "SELECT styleName FROM style WHERE id=?";

    static {
        PARAM_MAP.put("gender", "genderName IN (");
        PARAM_MAP.put("age", "ageName IN (");
        PARAM_MAP.put("size", "sizeName IN (");
        PARAM_MAP.put("category", "categoryName IN (");
        PARAM_MAP.put("style", "styleName IN (");
        PARAM_MAP.put("price", "price BETWEEN ? AND ? ");

        SORT_MAP.put("name", "name");
        SORT_MAP.put("adddate", "addDate");
        SORT_MAP.put("price", "price");
        SORT_MAP.put("desc", "DESC");
        SORT_MAP.put("asc", "ASC");

        VALUES_SET.add("baby");
        VALUES_SET.add("toddler");
        VALUES_SET.add("kid");
        VALUES_SET.add("bottoms");
        VALUES_SET.add("tops");
        VALUES_SET.add("pajamas");
        VALUES_SET.add("sets");
        VALUES_SET.add("shoes");
        VALUES_SET.add("dresses");
        VALUES_SET.add("rompers");
        VALUES_SET.add("romper");
        VALUES_SET.add("girl");
        VALUES_SET.add("boy");
        VALUES_SET.add("3m");
        VALUES_SET.add("6m");
        VALUES_SET.add("9m");
        VALUES_SET.add("12m");
        VALUES_SET.add("18m");
        VALUES_SET.add("24m");
        VALUES_SET.add("graphics");
        VALUES_SET.add("casual");
        VALUES_SET.add("shorts");
        VALUES_SET.add("top_tees");
        VALUES_SET.add("sleep_play");
    }
}
