package main.entity;

public class GoodsParam extends Entity{
    private long goodsId;
    private String genderName;
    private String ageName;
    private String sizeName;
    private String categoryName;
    private String styleName;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getAgeName() {
        return ageName;
    }

    public void setAgeName(String ageName) {
        this.ageName = ageName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    @Override
    public String toString() {
        return "GoodsParam{" +
                "goodsId=" + goodsId +
                ", genderName='" + genderName + '\'' +
                ", ageName='" + ageName + '\'' +
                ", sizeName='" + sizeName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", styleName='" + styleName + '\'' +
                '}';
    }
}
