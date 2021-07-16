package com.epam.finalProject.entity;

import java.util.HashMap;
import java.util.Map;

public class OrderList extends Entity {
    //key - orderId , value - map of key - goodsParamId, value - quantity
    private Map<Long, Map<Long,Integer>> orderMap;

    public OrderList() {
        orderMap = new HashMap<>();
    }

    public Map<Long, Map<Long,Integer>> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<Long,  Map<Long,Integer>> orderMap) {
        this.orderMap = orderMap;
    }

    public void addToOrderList(long orderId,  Map<Long,Integer> goodsList) {
        orderMap.put(orderId, goodsList);
    }

}
