package com.Ashish.foodnetwork.utils.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.OrderHistoryData;
import com.Ashish.foodnetwork.utils.response.OrderHistoryDetailsData;

import java.io.Serializable;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> implements Serializable {

    LayoutInflater layoutInflater;
    List<OrderHistoryData> data;
    Context context;
    EachHistory eachHistory;

    public OrderHistoryAdapter(List<OrderHistoryData> data,Context context){
        this.data=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.order_history,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderHistoryData orderHistoryData=data.get(position);
        holder.id.setText("#"+orderHistoryData.getOrderId()+"");

        if(orderHistoryData.getOrderStatus()==0){

            holder.status.setText("Pending");
        }
        else{
            holder.status.setText("Delivered");
        }
        holder.date.setText(orderHistoryData.getOrderDate());
        holder.address.setText(orderHistoryData.getProvince()+","+orderHistoryData.getCity()+"");
        if(orderHistoryData.getPaymentStatus()==0){
            holder.paymentstatus.setText("Not Paid");
        }else{
            holder.paymentstatus.setText("Paid");
        }
        holder.paymentMethod.setText(orderHistoryData.getPaymentMethod());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView  date, status, paymentstatus, paymentMethod,address, id;
        LinearLayout whole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.orderstatus);
            whole=itemView.findViewById(R.id.whole);
            address=itemView.findViewById(R.id.address);
            paymentMethod=itemView.findViewById(R.id.paymentMethod);
            paymentstatus=itemView.findViewById(R.id.paymentstatus);

            whole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachHistory.onhistoryClick(getAdapterPosition());
                }
            });
        }
    }
    public interface EachHistory{
        void onhistoryClick(int position);
    }
    public void setEachHistory(EachHistory eachHistory){
        this.eachHistory=eachHistory;
    }
}
