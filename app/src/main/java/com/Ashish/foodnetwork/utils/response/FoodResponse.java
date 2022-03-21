package com.Ashish.foodnetwork.utils.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodResponse {
    @SerializedName("datas")
    @Expose
    private List<FoodResponseData> datas = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FoodResponseData> getDatas() {
        return datas;
    }

    public void setDatas(List<FoodResponseData> datas) {
        this.datas = datas;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
