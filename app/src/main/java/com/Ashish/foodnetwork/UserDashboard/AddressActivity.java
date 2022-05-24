package com.Ashish.foodnetwork.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.adapter.AddressAdapter;
import com.Ashish.foodnetwork.utils.response.AddressResponse;
import com.Ashish.foodnetwork.utils.response.Address;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    RecyclerView rvAddress;
    TextView addAdd;
    SwipeRefreshLayout swipeRefreshLayout;
    public static String ADDRESS_SELECTED_KEY="DFa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        rvAddress=findViewById(R.id.addressRV);
        addAdd=findViewById(R.id.addAdd);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getAddressOnline();
            }
        });

        addAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),AddAddressActivity.class);
                startActivity(intent);
            }
        });
        listAddress(new ArrayList<>());
        getAddressOnline();
    }

    private void getAddressOnline() {
        Call<AddressResponse> confirmAddressCall= ApiClient.getApiServices()
                .getMyAddresses(SharePrefrenceUtils.getStringPreference(getApplicationContext(), Constants.API_KEY));

        confirmAddressCall.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                       listAddress(response.body().getAddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });
    }

    private void listAddress(List<Address> addressResponseData){
        rvAddress.setHasFixedSize(true);
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        AddressAdapter addressAdapter= new AddressAdapter(addressResponseData,this);
        addressAdapter.setOnAddressItemClickListener(new AddressAdapter.OnAddressItemClickListener() {
            @Override
            public void onAddressClick(int position, Address address) {
                Intent intent= new Intent();
                intent.putExtra(ADDRESS_SELECTED_KEY, address);
                setResult(Activity.RESULT_OK, intent);
                finish();;
            }
        });
        rvAddress.setAdapter(addressAdapter);
    }

}