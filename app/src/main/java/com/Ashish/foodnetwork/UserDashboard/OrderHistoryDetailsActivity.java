package com.Ashish.foodnetwork.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.adapter.HistoryDetailsAdapter;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;
import com.Ashish.foodnetwork.utils.response.OrderHistoryData;
import com.Ashish.foodnetwork.utils.response.OrderHistoryDetailsData;
import com.Ashish.foodnetwork.utils.response.OrderHistoryDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryDetailsActivity extends AppCompatActivity {
public static String TOTAL_PRICE_KEY_D="total";

    RecyclerView orderrv;
    ImageView back;
    List<OrderHistoryDetailsData> historylist;
    HistoryDetailsAdapter historyDetailsAdapter;
    int totalAmount=0;
    long totalAmountInPaisa=0;

    public static String ORDER_DETAILS_KEY="odk";

    OrderHistoryData orderHistoryData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);
        orderrv=findViewById(R.id.orderRv);
        back=findViewById(R.id.backIvS);

        orderHistoryData = (OrderHistoryData) getIntent().getSerializableExtra(ORDER_DETAILS_KEY);

        getBIllData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getBIllData(){
        Call<OrderHistoryDetailsResponse> orderHistoryDetailsResponseCall= ApiClient.getApiServices()
                .view_each_order(SharePrefrenceUtils.getStringPreference(getApplicationContext(), Constants.API_KEY),orderHistoryData.getOrderId());
        orderHistoryDetailsResponseCall.enqueue(new Callback<OrderHistoryDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryDetailsResponse> call, Response<OrderHistoryDetailsResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()) {
                        if (response.body().getData() != null) {
                            setOrderrv(response.body().getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryDetailsResponse> call, Throwable t) {

            }
        });
    }

    private void setOrderrv(List<OrderHistoryDetailsData> dataList){
        historylist= dataList;
        orderrv.setHasFixedSize(true);
        historyDetailsAdapter=new HistoryDetailsAdapter(historylist,this);
        orderrv.setLayoutManager(new LinearLayoutManager(this));
        orderrv.setAdapter(historyDetailsAdapter);
    }


}