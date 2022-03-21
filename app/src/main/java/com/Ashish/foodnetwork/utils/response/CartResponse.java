package com.Ashish.foodnetwork.utils.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("products")
    @Expose
    private List<CartResponseData> products = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<CartResponseData> getProducts() {
        return products;
    }

    public void setProducts(List<CartResponseData> products) {
        this.products = products;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
