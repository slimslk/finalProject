package main.entity;

import java.util.Date;

public class CatalogItem extends Entity {
    private long goodsId;
    private double price;
    private int quantity;
    private Date addDate;
    private Goods goods;

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

    public Goods getGoods(){ return goods;}

    public void setGoods(Goods goods){this.goods=goods;}

    @Override
    public String toString() {
        return "CatalogItem{" +
                "goodsId=" + goodsId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", addDate=" + addDate +
                ", goods=" + goods +
                '}';
    }
}
