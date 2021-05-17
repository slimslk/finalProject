package main.database;

import main.entity.User;

public class test {
    public static void main(String[] args) {
        UserDAO userDAO=new UserDAO();
        User user=userDAO.getUserByUsername("slim.slk@gmail.com");
        System.out.println(user);
    }
}
