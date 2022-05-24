package com.Ashish.foodnetwork.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class DetailsAdater extends RecyclerView.Adapter<DetailsAdater.ViewHolder> implements Serializable {

    LayoutInflater layoutInflater;
    List<FoodResponseData> data;
    Context context;
    ItemClick eachitemClick;


    public DetailsAdater(List<FoodResponseData> data,Context context){
        this.data=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public DetailsAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.items,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdater.ViewHolder holder, int position) {

        FoodResponseData foodResponseData= data.get(position);
        Picasso.get().load(foodResponseData.getFoodImg()).into(holder.categoryimage);
        holder.categoryname.setText(foodResponseData.getFoodName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout categoryClick;
        ImageView categoryimage;
        TextView categoryname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryimage=itemView.findViewById(R.id.categoryimage);
            categoryname=itemView.findViewById(R.id.categoryname);
            categoryClick=itemView.findViewById(R.id.categoryClick);

            categoryClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachitemClick.onClickItem(getAdapterPosition());
                }
            });
        }

    }


    public interface ItemClick{
        void onClickItem(int position);
    }
    public void setItemClickListener(ItemClick itemClick){
        this.eachitemClick=itemClick;
    }
}
