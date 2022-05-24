package com.Ashish.foodnetwork.utils.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderHistoryDetailsData implements Serializable {

    @SerializedName("Order_DetId")
    @Expose
    private Integer orderDetId;
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("food_img")
    @Expose
    private String foodImg;
    @SerializedName("food_name")
    @Expose
    private String foodName;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Price")
    @Expose
    private Integer price;

    public Integer getOrderDetId() {
        return orderDetId;
    }

    public void setOrderDetId(Integer orderDetId) {
        this.orderDetId = orderDetId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
