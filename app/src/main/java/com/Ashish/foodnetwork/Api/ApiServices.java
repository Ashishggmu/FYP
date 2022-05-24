package com.Ashish.foodnetwork.Api;

import com.Ashish.foodnetwork.utils.response.AddressResponse;
import com.Ashish.foodnetwork.utils.response.CartResponse;
import com.Ashish.foodnetwork.utils.response.CategoryResponse;
import com.Ashish.foodnetwork.utils.response.DashResponse;
import com.Ashish.foodnetwork.utils.response.FoodResponse;
import com.Ashish.foodnetwork.utils.response.LoginResponse;
import com.Ashish.foodnetwork.utils.response.OrderHistoryDetailsResponse;
import com.Ashish.foodnetwork.utils.response.OrderHistoryResponse;
import com.Ashish.foodnetwork.utils.response.RegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
                                   @Field("Payment_Status")  int Payment_Status,
                                  @Field("address_id") int address_id);

    @GET("/myApi/v1/address")
    Call<AddressResponse> getMyAddresses(@Header("api_key") String apikey);

    @FormUrlEncoded
    @POST("/myApi/v1/address")
    Call<AddressResponse> addAddress(
            @Header("api_key") String apikey,
            @Field("city") String city,
            @Field("street") String street,
            @Field("province") String province,
            @Field("description") String description);

    @Multipart
    @POST("/myApi/v1/upload_category")
    Call<CategoryResponse> uploadCategory(
            @Header("api_key") String apikey,
            @Part MultipartBody.Part file,
            @Part("Cat_name") RequestBody name,
            @Part("Cat_desc") RequestBody description

    );
    @Multipart
    @POST("/myApi/v1/upload_food")
    Call<RegisterResponse> uploadProduct(
            @Header("api_key") String apikey,
            @Part MultipartBody.Part[] files,
            RequestBody food_name, RequestBody rPrice,
            @Part("food_name") RequestBody name,
            @Part("food_price") RequestBody price,
            @Part("food_quantity") RequestBody quantity,
            @Part("categories") RequestBody categories
    );

    @POST("/myApi/v1/view_order_history")
    Call<OrderHistoryResponse> view_order_history(@Header("api_key") String apikey);


    @FormUrlEncoded
    @POST("/myApi/v1/view_each_order")
    Call<OrderHistoryDetailsResponse> view_each_order(@Header("api_key") String apikey,
                                                      @Field("order_id") int order_id);

    @FormUrlEncoded
    @POST("/myApi/v1/updateProfile")
    Call<RegisterResponse> updateProfile(@Header("api_key") String apikey,
                                         @Field("full_name") String fullname,
                                         @Field("phone_number") String phone_number);

    @GET("/myApi/v1/dash")
    Call<DashResponse> getDash(@Header("api_key") String apikey);

    @FormUrlEncoded
    @POST("/myApi/v1/delete_category")
    Call<CategoryResponse> delete_category(@Header ("api_key") String apikey,
                                   @Field("Cat_id") Integer Cat_id);


}
