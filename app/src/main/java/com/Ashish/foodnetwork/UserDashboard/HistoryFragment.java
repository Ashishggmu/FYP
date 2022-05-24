package com.Ashish.foodnetwork.UserDashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.adapter.OrderHistoryAdapter;
import com.Ashish.foodnetwork.utils.response.OrderHistoryData;
import com.Ashish.foodnetwork.utils.response.OrderHistoryDetailsData;
import com.Ashish.foodnetwork.utils.response.OrderHistoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment implements OrderHistoryAdapter.EachHistory {

    RecyclerView history;
    List<OrderHistoryData> historyList;
    OrderHistoryAdapter orderHistoryAdapter;
    TextView emptyMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_history, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        history=view.findViewById(R.id.history);
        emptyMsg=view.findViewById(R.id.emptyHistoryMsg);




        historyDataCall();
    }

    private void historyDataCall() {
        Call<OrderHistoryResponse> orderHistoryResponseCall= ApiClient.getApiServices().view_order_history(SharePrefrenceUtils.getStringPreference(getContext(), Constants.API_KEY));

        orderHistoryResponseCall.enqueue(new Callback<OrderHistoryResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if (response.body().getData() != null){
                            setHistoryRv(response.body().getData());
                        }
                        else{
                            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "cant call server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {

            }
        });
    }

    private void setHistoryRv(List<OrderHistoryData> data){
        historyList=data;
        history.setHasFixedSize(true);
        history.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        orderHistoryAdapter=new OrderHistoryAdapter(historyList,getContext());
        history.setAdapter(orderHistoryAdapter);
        orderHistoryAdapter.setEachHistory(this);
    }

    @Override
    public void onhistoryClick(int position) {
        OrderHistoryData historyDetailsData=historyList.get(position);

        Intent intent= new Intent(getActivity(),OrderHistoryDetailsActivity.class);
        intent.putExtra(OrderHistoryDetailsActivity.ORDER_DETAILS_KEY,historyDetailsData);
        startActivity(intent);

    }
}