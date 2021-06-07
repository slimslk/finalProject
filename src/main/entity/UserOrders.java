package main.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserOrders implements Serializable {
    private static final long serialVersionUID = 6907660117882643598L;
    //key - orderNumber, value - list of User order
    private Map<Long, List<UserOrder>> userOrders;

    public Map<Long, List<UserOrder>> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(Map<Long, List<UserOrder>> userOrders) {
        this.userOrders = userOrders;
    }

    public List<UserOrder> getOrderList(long key) {
        if (userOrders.containsKey(key)) {
            return userOrders.get(key);
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "UserOrders{" +
                "userOrders=" + userOrders +
                '}';
    }
}
