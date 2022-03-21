package com.Ashish.foodnetwork.UserDashboard;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

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
import com.Ashish.foodnetwork.utils.response.CartResponse;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailsActivity extends AppCompatActivity {
        ImageView food_img, back;
        TextView food_name, food_desc, food_price;
        LinearLayout order;
        Button toCart;


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


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        foodResponseData = (FoodResponseData) getIntent().getSerializableExtra(FoodData);

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
}