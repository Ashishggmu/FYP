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
import com.Ashish.foodnetwork.utils.response.CartResponseData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements Serializable {

    LayoutInflater layoutInflater;
    List<CartResponseData> data;
    Context context;

    EachCartClick eachCartClick;
    public CartAdapter(List<CartResponseData> data,Context context){

        this.data= data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.add_tocart,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CartResponseData cartResponseData = data.get(position);
        holder.food.setText(cartResponseData.getFoodName());
        holder.counter.setText(cartResponseData.getFoodQuanity()+"");
        holder.price.setText("Rs. "+cartResponseData.getFoodPrice());
        Picasso.get().load(cartResponseData.getFoodImg()).into(holder.imagefood);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagefood, plus, minus, close;
        TextView food, counter, price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagefood=itemView.findViewById(R.id.imagefood);
            food=itemView.findViewById(R.id.food);
            plus=itemView.findViewById(R.id.plus);
            counter=itemView.findViewById(R.id.counter);
            minus=itemView.findViewById(R.id.minus);
            close=itemView.findViewById(R.id.close);
            price=itemView.findViewById(R.id.price);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachCartClick.onPlusClick(getAdapterPosition(),counter);
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachCartClick.onMinusClick(getAdapterPosition(),counter);
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachCartClick.onDeleteClick(getAdapterPosition());
                }
            });
        }
    }

    public interface EachCartClick{
        void onPlusClick(int position, TextView q);
        void onMinusClick(int position, TextView q);
        void onDeleteClick(int position);
    }
    public void setEachCartClick(EachCartClick eachCartClick){
        this.eachCartClick=eachCartClick;
    }
}
