package main.entity;

import java.util.ArrayList;
import java.util.List;

public class UserCart extends Entity{
    private long userId;
    private List<Long> goodsId;

    public UserCart(){
        goodsId=new ArrayList<>();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(List<Long> goodsId) {
        this.goodsId = goodsId;
    }

    public void addToCart(long id){
        System.out.println("id is: "+id);
        goodsId.add(id);
        System.out.println("item added to cart");
    }

    @Override
    public String toString() {
        return "userCrate{" +
                " userId=" + userId +
                ", goodsId=" + goodsId +
                '}';
    }
}
