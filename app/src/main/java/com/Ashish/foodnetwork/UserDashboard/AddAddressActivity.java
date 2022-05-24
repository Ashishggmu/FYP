package com.Ashish.foodnetwork.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.response.AddressResponse;
import com.Ashish.foodnetwork.utils.response.CartResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
ImageView backIVA;
EditText City,Street, desc, province;
Button addbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        City=findViewById(R.id.City);
        Street=findViewById(R.id.Street);
        desc=findViewById(R.id.desc);
        province=findViewById(R.id.province);
        addbtn=findViewById(R.id.addbtn);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callResponse(City.getText().toString(),Street.getText().toString(),desc.getText().toString(),province.getText().toString());
                finish();
            }
        });




        backIVA=findViewById(R.id.backIVA);

        backIVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void callResponse(String city,String street, String desc, String province){
        Call<AddressResponse> addressResponseCall= ApiClient.getApiServices().addAddress(SharePrefrenceUtils.getStringPreference(this, Constants.API_KEY),
                city,street,desc,province);
        addressResponseCall.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if(response.isSuccessful()){
                    AddressResponse addressResponse= response.body();
                    if (!addressResponse.getError()){
                        Toast.makeText(getApplicationContext(),addressResponse.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });
    }
}