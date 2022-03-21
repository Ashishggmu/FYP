package com.Ashish.foodnetwork.Api;

import com.Ashish.foodnetwork.utils.response.CartResponse;
import com.Ashish.foodnetwork.utils.response.CategoryResponse;
import com.Ashish.foodnetwork.utils.response.FoodResponse;
import com.Ashish.foodnetwork.utils.response.LoginResponse;
import com.Ashish.foodnetwork.utils.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServices {
    @FormUrlEncoded
    @POST("/myApi/v1/signUpUser")
    Call<RegisterResponse> signUpUser(@Field("full_name") String name,
                                      @Field("email") String email,
                                      @Field("phone_number") String Phone,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("/myApi/v1/loginUser")
    Call<LoginResponse> loginUser(@Field("email") String email,
                                  @Field("password") String password);


    @GET("/myApi/v1/get-all-category")
    Call<CategoryResponse> getCategory();

    @GET("/myApi/v1/get-all-foods")
    Call<FoodResponse> getFood();


    @FormUrlEncoded
    @POST("/myApi/v1/add_cart")
    Call<CartResponse> add_cart(@Header ("api_key") String apikey,
                                @Field("food_id") Integer food_id,
                                @Field("Quantity") Integer quantity,
                                @Field("Price") Integer price);


    @GET("/myApi/v1/view_cart")
    Call<CartResponse> view_cart(@Header ("api_key") String apikey);

    @FormUrlEncoded
    @POST("/myApi/v1/delete_cart")
    Call<CartResponse> delete_cart(@Header ("api_key") String apikey,
                                   @Field("food_id") Integer food_id,
                                   @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("/myApi/v1/update_to_quantity")
    Call<CartResponse> update_to_quantity(@Header ("api_key") String apikey,
                                   @Field("food_id") Integer food_id,
                                   @Field("user_id") String user_id,
                                   @Field("Price") Integer price,
                                   @Field("Quantity")Integer quantity);

    @FormUrlEncoded
    @POST("/myApi/v1/orders")
    Call<FoodResponse> make_order(@Header ("api_key") String apikey,
                                   @Field("Payment_Method") String Payment_Method,
                                   @Field("Payment_Status")  int Payment_Status);
}
