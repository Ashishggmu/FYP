package com.Ashish.foodnetwork.admin.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.FoodResponse;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductsActivity extends AppCompatActivity {
    RecyclerView allProductRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        allProductRV = findViewById(R.id.allProductRV);

        serverCall();
    }

    private void serverCall() {
        Call<FoodResponse> foodResponseCall= ApiClient.getApiServices().getFood();
        foodResponseCall.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                setProductRecyclerView(response.body().getDatas());
            }

            @Override
            public void onFailure(Call<FoodResponse> call, Throwable t) {
                Toast.makeText(ListProductsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setProductRecyclerView(List<FoodResponseData> products) {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        allProductRV.setLayoutManager(layoutManager);
        ShopAdapterAdmin shopAdapter=new ShopAdapterAdmin(products,this);
        allProductRV.setAdapter(shopAdapter);

    }
}