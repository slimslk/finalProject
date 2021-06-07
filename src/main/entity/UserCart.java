package main.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserCart extends Entity{
    private static final Logger log= LogManager.getLogger(UserCart.class);

    private long userId;
    //Map key - "goods id" and value - "quantity"
    private Map<Long,Integer> goodsId;

    public UserCart(){
        goodsId=new HashMap<>();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Map<Long, Integer> getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Map<Long,Integer> goodsId) {
        this.goodsId = goodsId;
    }

    public void addToCart(long id){
        if(!goodsId.containsKey(id)){
            goodsId.put(id,1);
        }else {
            int quantity = goodsId.get(id);
            goodsId.put(id,++quantity);
        }
        log.debug("Quantity of the goods in the cart is: "+goodsId.get(id));
    }

    public void removeFromCart(long id, int q){
        if(!goodsId.containsKey(id)){
            log.error("There are no goods in the cart");
            return;
        }
        if(goodsId.get(id)>0){
            int quantity=goodsId.get(id);
            quantity-=q;
            goodsId.put(id,quantity);
            if(quantity==0){
                goodsId.remove(id);
            }
        }
        log.debug("item removed from cart");
        log.debug("Quantity of the goods in the cart is: "+goodsId.get(id));
    }

    public int getSize(){
        return goodsId.size();
    }

    public int getQuantity(long id){
        return goodsId.get(id);
    }

    @Override
    public String toString() {
        return "userCart{" +
                " userId=" + userId +
                ", goodsId=" + goodsId +
                '}';
    }
}
