package com.Ashish.foodnetwork.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.CartResponseData;

import java.io.Serializable;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> implements Serializable {

    List<CartResponseData> cartData;
    Context context;
    LayoutInflater layoutInflater;

    public CheckoutAdapter(List<CartResponseData> cartData, Context context){
        this.cartData= cartData;
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.layout_bill,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.ViewHolder holder, int position) {
        CartResponseData holderList = cartData.get(position);
        holder.name.setText(holderList.getFoodName());
        holder.Quantity.setText(""+holderList.getFoodQuanity());
        holder.price.setText(""+holderList.getFoodPrice());

    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, Quantity, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.name);
            Quantity=itemView.findViewById(R.id.Quantity);
            price=itemView.findViewById(R.id.price);
        }
    }
}
