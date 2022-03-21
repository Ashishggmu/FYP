package com.Ashish.foodnetwork.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Serializable, Filterable {
    LayoutInflater layoutInflater;
    List<FoodResponseData> data;
    List<FoodResponseData> searchData;
    Context context;

    EachItemClick eachItemClick;

    public ItemAdapter(List<FoodResponseData> data, Context context){
        this.data= data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        searchData=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.food_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        FoodResponseData foodResponseData = data.get(position);
        holder.foodname.setText(foodResponseData.getFoodName());
        Picasso.get().load(foodResponseData.getFoodImg()).into(holder.foodimage);
        holder.quantity.setText(foodResponseData.getFoodQuantity()+"");
        holder.rate.setText(foodResponseData.getFoodPrice()+"");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FoodResponseData> foodResponseDataList =new ArrayList<>();
            if (constraint==null|| constraint.length()==0){
                foodResponseDataList.addAll(searchData);
            }else{
                String filterPattern= constraint.toString().toLowerCase().trim();
                for (FoodResponseData foodResponseData:searchData){
                    if (foodResponseData.getFoodName().toLowerCase().contains(filterPattern)){
                        foodResponseDataList.add(foodResponseData);
                    }
                    else if (foodResponseData.getCatId().toString().toLowerCase().contains(filterPattern)){
                        foodResponseDataList.add(foodResponseData);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=foodResponseDataList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodimage;
        TextView foodname, quantity, rate;
        Button tocart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodimage=itemView.findViewById(R.id.foodimage);
            foodname=itemView.findViewById(R.id.foodname);
            quantity=itemView.findViewById(R.id.quantity);
            rate=itemView.findViewById(R.id.rate);
            tocart=itemView.findViewById(R.id.tocart);

            tocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachItemClick.onCartClick(getAdapterPosition());
                }
            });

            foodimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachItemClick.onItemClick(getAdapterPosition());

                }
            });
        }
    }

    public interface EachItemClick{
        void onItemClick(int position);
        void onCartClick(int position);
    }

    public void setEachItemClickListener(EachItemClick eachItemClick){
        this.eachItemClick=eachItemClick;
    }
}
