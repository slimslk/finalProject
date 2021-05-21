package main.entity;

import java.util.List;

public class UserCart extends Entity{
    private long userId;
    private List<Long> goodsId;

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

    @Override
    public String toString() {
        return "userCrate{" +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                '}';
    }
}
