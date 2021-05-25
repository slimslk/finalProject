package main.database;

public final class Fields {

    //Row names
    public static final String ENTITY_ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ROLE_ID = "roleId";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String USER_ID = "userId";
    public static final String USER_STATUS_ID = "status";
    public static final String IMG = "img";

    //SQL query expression
    public static final String GENDER="SELECT genderName FROM gender WHERE id=?";
    public static final String AGE="SELECT ageName FROM age WHERE id=?";
    public static final String SIZE="SELECT sizeName FROM size WHERE id=?";
    public static final String CATEGORY="SELECT categoryName FROM category WHERE id=?";
    public static final String STYLE="SELECT styleName FROM style WHERE id=?";
}
