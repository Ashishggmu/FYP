package com.Ashish.foodnetwork.UserDashboard;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.adapter.CartAdapter;
import com.Ashish.foodnetwork.utils.adapter.DetailsAdater;
import com.Ashish.foodnetwork.utils.adapter.ItemAdapter;
import com.Ashish.foodnetwork.utils.response.CartResponse;
import com.Ashish.foodnetwork.utils.response.CartResponseData;
import com.Ashish.foodnetwork.utils.response.FoodResponse;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailsActivity extends AppCompatActivity implements DetailsAdater.ItemClick {
        ImageView food_img, back;
        TextView food_name, food_desc, food_price;
        LinearLayout order;
        List<FoodResponseData> itemList;
        List<CartResponseData> cartList;
        DetailsAdater detailsAdater;
        Button toCart;
        RecyclerView jpt;

    public static String FOOD_NAME="food";

    FoodResponseData foodResponseData;



    public  static String FoodData ="Data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        food_img= findViewById(R.id.food_img);
        food_name= findViewById(R.id.food_name);
        food_desc= findViewById(R.id.food_desc);
        food_price=findViewById(R.id.food_price);
        order=findViewById(R.id.order);
        toCart=findViewById(R.id.tocart);
        back=findViewById(R.id.back);
        jpt=findViewById(R.id.jpt);



        foodResponseData = (FoodResponseData) getIntent().getSerializableExtra(FoodData);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<CartResponse> cartResponseCall= ApiClient.getApiServices()
                        .add_cart(SharePrefrenceUtils.getStringPreference(getApplicationContext(), Constants.API_KEY),
                                foodResponseData.getFoodId(),1, foodResponseData.getFoodPrice());
                cartResponseCall.enqueue(new Callback<CartResponse>() {
                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                        if(response.isSuccessful()){
                            if(!response.body().getError()){
                                CartFragment.IS_CART_CHANGED=true;
                                //Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }else {
                                //Toast.makeText(getApplicationContext(),"Food already Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {

                    }
                });

                Intent intent= new Intent(FoodDetailsActivity.this,CheckOutActivity.class);
                intent.putExtra(CheckOutActivity.TOTAL_PRICE_KEY,String.valueOf(foodResponseData.getFoodPrice()));

                intent.putExtra(FOOD_NAME,foodResponseData.getFoodName());

                startActivity(intent);

            }
        });

        itemDataCall();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),HomeActivity.class);

                startActivity(intent);
            }
        });

        food_name.setText(foodResponseData.getFoodName());
        food_price.setText("Price  Rs."+ foodResponseData.getFoodPrice());
        Picasso.get().load(foodResponseData.getFoodImg()).into(food_img);

        //button
        toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<CartResponse> cartResponseCall= ApiClient.getApiServices()
                        .add_cart(SharePrefrenceUtils.getStringPreference(getApplicationContext(), Constants.API_KEY),
                        foodResponseData.getFoodId(),1, foodResponseData.getFoodPrice());
                cartResponseCall.enqueue(new Callback<CartResponse>() {
                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                        if(response.isSuccessful()){
                            if(!response.body().getError()){
                                CartFragment.IS_CART_CHANGED=true;
                                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"Food already Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {

                    }
                });
            }
        });



    }


    private void itemDataCall() {
        Call<FoodResponse> foodResponseCall=ApiClient.getApiServices().getFood();


        foodResponseCall.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if (response.body().getDatas() != null){
                            setItemsRv(response.body().getDatas());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "cant call server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodResponse> call, Throwable t) {

            }
        });
    }
    private void setItemsRv(List<FoodResponseData> data){
        itemList=data;
        jpt.setHasFixedSize(true);
        jpt.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        detailsAdater= new DetailsAdater(itemList,getApplicationContext());
        jpt.setAdapter(detailsAdater);
        detailsAdater.setItemClickListener(this);
    }

    @Override
    public void onClickItem(int position) {
        FoodResponseData responseData=itemList.get(position);

        Intent intent= new Intent(getApplicationContext(),FoodDetailsActivity.class);
        intent.putExtra(FoodDetailsActivity.FoodData,responseData);
        startActivity(intent);
    }




}