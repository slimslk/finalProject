package com.epam.finalProject.entity;

import java.util.Date;

public class CatalogItem extends Entity {
    private long goodsParamId;
    private double price;
    private int quantity;
    private Date addDate;
    private GoodsParam goodsParam;
    private Goods goods;


    public long getGoodsParamId() {
        return goodsParamId;
    }

    public void setGoodsParamId(long goodsParamId) {
        this.goodsParamId = goodsParamId;
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

    public GoodsParam getGoodsParam() {
        return goodsParam;
    }

    public void setGoodsParam(GoodsParam goodsParam) {
        this.goodsParam = goodsParam;
    }

    @Override
    public String toString() {
        return "CatalogItem{" +
                "goodsParamId=" + goodsParamId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", addDate=" + addDate +
                ", goodsParam=" + goodsParam +
                ", goods=" + goods +
                '}';
    }
}
