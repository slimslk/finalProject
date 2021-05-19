package main.entity;

public class UserInfo extends Entity{
    private String name;
    private String surname;
    private long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
