package main.entity;

public enum Role {
    SUPERADMIN, ADMIN, USER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }
    public String getName() {
        return name().toLowerCase();
    }

    public String getUserRole(){
//        getRole(user);
//        return user.getPassword();
        return "BAraBAsh";
    }
}
