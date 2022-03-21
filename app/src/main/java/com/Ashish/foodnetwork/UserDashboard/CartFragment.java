package com.Ashish.foodnetwork.UserDashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.adapter.CartAdapter;
import com.Ashish.foodnetwork.utils.response.CartResponse;
import com.Ashish.foodnetwork.utils.response.CartResponseData;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment implements CartAdapter.EachCartClick{

    RecyclerView cart;
    List<CartResponseData> cartList;
    CartAdapter cartAdapter;
    SwipeRefreshLayout refreshHome;
    Button checkout;
    TextView emptyMsg;
    public static String FOOD_NAMES="food";

    static public boolean IS_CART_CHANGED= false;

    int total=0;
    int grandTotal=0;
    int quantity=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        cart=view.findViewById(R.id.cart);
        refreshHome=view.findViewById(R.id.refreshHome1);
        checkout=view.findViewById(R.id.checkout);
        emptyMsg=view.findViewById(R.id.emptyCartMsg);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),CheckOutActivity.class);
                intent.putExtra(CheckOutActivity.TOTAL_PRICE_KEY,String.valueOf(grandTotal));
                String products="";
                for(int i=0; i<cartList.size();i++){
                    products=products+" "+cartList.get(i).getFoodName();
                }
                intent.putExtra(FOOD_NAMES,products);
                startActivity(intent);
            }
        });


        refreshHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cartDataCall();
            }
        });
        cartDataCall();

    }

    public void refreshCart(){
        IS_CART_CHANGED=false;
        cartDataCall();
    }

    private void cartDataCall() {
        Call<CartResponse> cartResponseCall= ApiClient.getApiServices().view_cart(SharePrefrenceUtils.getStringPreference(getContext(), Constants.API_KEY));

        cartResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if(response.isSuccessful()){
                    if (!response.body().getError()){
                        if (response.body().getProducts()!= null){
                            setCartRv(response.body().getProducts());
                            emptyMsg.setVisibility(View.GONE);
                            cart.setVisibility(View.VISIBLE);
                            checkout.setVisibility(View.VISIBLE);
                        }
                        else{
                            emptyMsg.setVisibility(View.VISIBLE);
                            cart.setVisibility(View.GONE);
                            checkout.setVisibility(View.GONE);
                        }
                    }
                }else {
                    Toast.makeText(getContext(),"CAN'T CALL SERVER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }


    private void setCartRv(List<CartResponseData> data) {
        cartList=data;
        cart.setHasFixedSize(true);
        cart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cartAdapter = new CartAdapter(cartList,getContext());
        cart.setAdapter(cartAdapter);
        refreshHome.setRefreshing(false);
        cartAdapter.setEachCartClick(this);
        calculatePrice(cartList);
    }

    private void calculatePrice(List<CartResponseData> data){
        List<CartResponseData> newlist= data;
        total=0;
        grandTotal=0;

        for(int i=0; i<=newlist.size()-1; i++){
            CartResponseData foodResponseData=newlist.get(i);
            int price=foodResponseData.getFoodPrice();
            int quantity= foodResponseData.getFoodQuanity();
            total=price*quantity;
            grandTotal=grandTotal+total;
        }
        checkout.setText("Checkout ( Rs. "+grandTotal+" )");
    }


    @Override
    public void onPlusClick(int position, TextView q) {
        CartResponseData data=cartList.get(position);
        quantity=Integer.parseInt(String.valueOf(q.getText()));
        quantity++;

        Call<CartResponse> call = ApiClient.getApiServices()
                .update_to_quantity(SharePrefrenceUtils.getStringPreference(getContext(),Constants.API_KEY)
                        ,data.getFoodId(),
                        SharePrefrenceUtils.getStringPreference(getContext(), Constants.USER_ID)
                        , quantity* data.getFoodPrice()
                        ,quantity);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if(response.isSuccessful()){
                    if (!response.body().getError()){
                        q.setText(quantity+"");
                        cartDataCall();
                    }else{
                        Snackbar.make(getView(),"There is no more quantity available!",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMinusClick(int position, TextView q) {
        CartResponseData data=cartList.get(position);
        quantity=Integer.parseInt(String.valueOf(q.getText()));
        if(quantity>1){
            quantity--;
            Call<CartResponse> call = ApiClient.getApiServices()
                    .update_to_quantity(SharePrefrenceUtils.getStringPreference(getContext(),Constants.API_KEY)
                            ,data.getFoodId(),
                            SharePrefrenceUtils.getStringPreference(getContext(), Constants.USER_ID)
                            , quantity* data.getFoodPrice()
                            ,quantity);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if(response.isSuccessful()){
                        if (!response.body().getError()) {
                            q.setText(quantity + "");
                           cartDataCall();

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {

                }
            });
        }else{
            Snackbar.make(getView(),"Quantity cannot be less than 1!",Snackbar.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onDeleteClick(int position) {

        CartResponseData data = cartList.get(position);
        Call<CartResponse> call = ApiClient.getApiServices().delete_cart(SharePrefrenceUtils.getStringPreference(getContext(),Constants.API_KEY),
                data.getFoodId(), SharePrefrenceUtils.getStringPreference(getContext(), Constants.USER_ID));

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        cartDataCall();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });


    }
}