package main.entity;

import java.util.Date;

public class Catalog extends Entity {
    private long goodsId;
    private double price;
    private int quantity;
    private Date addDate;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "goodsId=" + goodsId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", addDate=" + addDate +
                '}';
    }
}
