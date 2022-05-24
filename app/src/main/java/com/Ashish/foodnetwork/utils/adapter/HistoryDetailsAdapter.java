package com.Ashish.foodnetwork.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.OrderHistoryDetailsData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class HistoryDetailsAdapter extends RecyclerView.Adapter<HistoryDetailsAdapter.ViewHolder> implements Serializable {

    LayoutInflater layoutInflater;
    List<OrderHistoryDetailsData> data;
    Context context;


    public HistoryDetailsAdapter(List<OrderHistoryDetailsData> data, Context context){

        this.data=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.history_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryDetailsAdapter.ViewHolder holder, int position) {
        OrderHistoryDetailsData historyDetailsData=data.get(position);
        holder.name.setText(historyDetailsData.getFoodName());
        holder.Quantity.setText(historyDetailsData.getQuantity()+"");
        holder.price.setText(historyDetailsData.getPrice()+"");
        Picasso.get().load(historyDetailsData.getFoodImg()).into(holder.picture);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name,Quantity,price;
            ImageView picture;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture=itemView.findViewById(R.id.Picture);
            name=itemView.findViewById(R.id.name);
            Quantity=itemView.findViewById(R.id.Quantity);
            price=itemView.findViewById(R.id.price);
        }
    }
}
