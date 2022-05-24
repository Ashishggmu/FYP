package com.Ashish.foodnetwork.UserDashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.adapter.CheckoutAdapter;
import com.Ashish.foodnetwork.utils.response.Address;
import com.Ashish.foodnetwork.utils.response.CartResponse;
import com.Ashish.foodnetwork.utils.response.CartResponseData;
import com.Ashish.foodnetwork.utils.response.FoodResponse;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.widget.KhaltiButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity  {
    public  static String TOTAL_PRICE_KEY="total";
    RecyclerView allProductsRV;
    CheckoutAdapter checkoutAdapter;
    List<CartResponseData> cartList;
    TextView totalTV, emptyAddress, cityStreet, province;
    RadioButton cash, khalti;
    LinearLayout confirmOrder,addressLL;
    KhaltiButton khaltiButton;
    Address addressData;
    ImageView back;
    int totalAmount=0;
    long totalAmountInPaisa=0;
    String FoodName;
    String FoodId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        allProductsRV= findViewById(R.id.allProductsRV);
        totalTV= findViewById(R.id.totalTV);
        cash=  findViewById(R.id.cash);
        khalti=  findViewById(R.id.khalti);
        confirmOrder=findViewById(R.id.confirmLL);
        emptyAddress=findViewById(R.id.emptyAddressTv);
        addressLL=findViewById(R.id.addressLL);
        cityStreet=findViewById(R.id.cityStreetTV);
        province=findViewById(R.id.provinceTV);

        back=findViewById(R.id.backIv);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddressActivity.class);
                startActivityForResult(intent,1);
            }
        });
        emptyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddressActivity.class);
                startActivityForResult(intent,1);
            }
        });

        khaltiButton= (KhaltiButton) findViewById(R.id.khaltiConfirmButton);

        getBillData();

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cash.isChecked()&&addressData!=null){
                    confirmOrder("Cash", 0,addressData.getId());

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please select address !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        totalAmount=Integer.valueOf(getIntent().getExtras().getString(TOTAL_PRICE_KEY));
        totalAmountInPaisa=(long) (totalAmount*100);
        FoodName=getIntent().getStringExtra(CartFragment.FOOD_NAMES);
        FoodId=FoodName.substring(1,2);
        khaltiButton.setCheckOutConfig(getBoulderConfig());


    }




    private void getBillData(){
        Call<CartResponse> cartResponseCall = ApiClient.getApiServices().view_cart(SharePrefrenceUtils.getStringPreference(this, Constants.API_KEY));
        cartResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        if (response.body().getProducts() != null){
                            setRV(response.body().getProducts());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }

    private void setRV(List<CartResponseData> dataList){
        cartList= dataList;
        allProductsRV.setHasFixedSize(true);
        checkoutAdapter= new CheckoutAdapter(cartList, this);
        allProductsRV.setLayoutManager(new LinearLayoutManager(this));
        allProductsRV.setAdapter(checkoutAdapter);
        totalTV.setText("Rs. "+getIntent().getExtras().getString(TOTAL_PRICE_KEY));

    }

    private void confirmOrder(String pay_method, int pay_status,int address_id){
        Call<FoodResponse> confirmOrderCall= ApiClient.getApiServices()
                .make_order(SharePrefrenceUtils.getStringPreference(getApplicationContext(),Constants.API_KEY),pay_method,pay_status,address_id);

        confirmOrderCall.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        CartFragment.IS_CART_CHANGED=true;
                        Toast.makeText(getApplicationContext(), "Order Success", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(CheckOutActivity.this,OrderCompleteActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodResponse> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            assert data !=null;
            if (data.getSerializableExtra(AddressActivity.ADDRESS_SELECTED_KEY)!= null){
                showSelectedAddress((Address) data.getSerializableExtra(AddressActivity.ADDRESS_SELECTED_KEY));
            }
        }
    }




    private void showSelectedAddress(Address address){
        addressData= address;
        emptyAddress.setVisibility(View.GONE);
        cityStreet.setText(addressData.getCity()+" "+ addressData.getStreet());
        province.setText(addressData.getProvince());
        addressLL.setVisibility(View.VISIBLE);
    }

    public void onRadioButtonClicked(View view) {

        switch (view.getId()){
            case R.id.cash:
                confirmOrder.setVisibility(View.VISIBLE);
               khaltiButton.setVisibility(View.GONE);
                break;
            case R.id.khalti:
                khaltiButton.setVisibility(View.VISIBLE);
                confirmOrder.setVisibility(View.GONE);
                break;
        }
    }

    private Config getBoulderConfig(){
        Map<String, Object> map = new HashMap<>();
        map.put("merchant_extra", "This is extra data");

        Config.Builder builder = new Config.Builder(Constants.KHALTI_TEST_PUB_KEY, FoodId, FoodName, totalAmountInPaisa, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                confirmOrder("Khalti", 1,addressData.getId());
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.KHALTI);

                }})
                .additionalData(map)
                .productUrl("http://example.com/product")
                .mobile("9800000000");

        return builder.build();
    }
}