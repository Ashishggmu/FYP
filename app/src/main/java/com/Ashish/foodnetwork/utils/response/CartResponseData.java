package com.Ashish.foodnetwork.utils.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartResponseData implements Serializable {
    @SerializedName("food_id")
    @Expose
    private Integer foodId;
    @SerializedName("food_name")
    @Expose
    private String foodName;
    @SerializedName("food_price")
    @Expose
    private Integer foodPrice;
    @SerializedName("food_img")
    @Expose
    private String foodImg;
    @SerializedName("food_quanity")
    @Expose
    private Integer foodQuanity;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Integer foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public Integer getFoodQuanity() {
        return foodQuanity;
    }

    public void setFoodQuanity(Integer foodQuanity) {
        this.foodQuanity = foodQuanity;
    }

}
