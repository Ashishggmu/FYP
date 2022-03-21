package com.Ashish.foodnetwork.utils.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryResponseData implements Serializable {
    @SerializedName("Cat_id")
    @Expose
    private Integer catId;
    @SerializedName("Cat_name")
    @Expose
    private String catName;
    @SerializedName("Cat_desc")
    @Expose
    private String catDesc;
    @SerializedName("Cat_img")
    @Expose
    private String catImg;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getCatImg() {
        return catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }
}
