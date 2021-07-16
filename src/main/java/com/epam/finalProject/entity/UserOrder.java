package com.epam.finalProject.entity;

import java.sql.Timestamp;

public class UserOrder {
    private Goods goods;
    private GoodsParam goodsParam;
    private String username;
    private int quantity;
    private double price;
    private String orderStatus;
    private Timestamp orderDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsParam getGoodsParam() {
        return goodsParam;
    }

    public void setGoodsParam(GoodsParam goodsParam) {
        this.goodsParam = goodsParam;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "goods=" + goods +
                ", goodsParam=" + goodsParam +
                ", username='" + username + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
