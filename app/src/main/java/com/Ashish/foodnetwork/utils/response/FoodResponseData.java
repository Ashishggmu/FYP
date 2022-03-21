package com.Ashish.foodnetwork.utils.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodResponseData implements Serializable {
    @SerializedName("food_id")
    @Expose
    private Integer foodId;
    @SerializedName("food_name")
    @Expose
    private String foodName;
    @SerializedName("food_price")
    @Expose
    private Integer foodPrice;
    @SerializedName("food_quantity")
    @Expose
    private Integer foodQuantity;
    @SerializedName("food_img")
    @Expose
    private String foodImg;
    @SerializedName("cat_id")
    @Expose
    private List<String> catId = null;

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

    public Integer getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(Integer foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public List<String> getCatId() {
        return catId;
    }

    public void setCatId(List<String> catId) {
        this.catId = catId;
    }
}
