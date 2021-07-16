package com.epam.finalProject.entity;

import java.util.HashMap;
import java.util.Map;

public class GoodsParameters {
    private final Map<String, Map<Integer, String>> goodsParametersMap;
    private final Map<Integer, String> ageParameter;
    private final Map<Integer, String> categoryParameter;
    private final Map<Integer, String> genderParameter;
    private final Map<Integer, String> sizeParameter;
    private final Map<Integer, String> styleParameter;
    {
        goodsParametersMap = new HashMap<>();
        ageParameter = new HashMap<>();
        categoryParameter = new HashMap<>();
        genderParameter = new HashMap<>();
        sizeParameter = new HashMap<>();
        styleParameter = new HashMap<>();

        ageParameter.put(1, "Baby");
        ageParameter.put(2, "Toddler");
        ageParameter.put(3, "Kid");

        categoryParameter.put(1,"Bottoms");
        categoryParameter.put(2,"Tops");
        categoryParameter.put(3,"Pajamas");
        categoryParameter.put(4,"Sets");
        categoryParameter.put(5,"Shoes");
        categoryParameter.put(6,"Dresses");
        categoryParameter.put(7,"Rompers");

        genderParameter.put(1,"Girl");
        genderParameter.put(2,"Boy");

        sizeParameter.put(1,"3M");
        sizeParameter.put(2,"6M");
        sizeParameter.put(3,"9M");
        sizeParameter.put(4,"12M");
        sizeParameter.put(5,"18M");
        sizeParameter.put(6,"24M");

        styleParameter.put(1,"Graphics");
        styleParameter.put(2,"Casual");
        styleParameter.put(3,"Dresses");
        styleParameter.put(4,"Shorts");
        styleParameter.put(5,"Top and tess");
        styleParameter.put(6,"Sleep and play");
        styleParameter.put(7,"Romper");

        goodsParametersMap.put("age", ageParameter);
        goodsParametersMap.put("category", categoryParameter);
        goodsParametersMap.put("gender", genderParameter);
        goodsParametersMap.put("size", sizeParameter);
        goodsParametersMap.put("style", styleParameter);
    }

    public Map<String, Map<Integer, String>> getGoodsParametersMap() {
        return goodsParametersMap;
    }
}
