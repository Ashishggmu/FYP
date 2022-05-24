package com.Ashish.foodnetwork.admin.products;

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

import java.util.List;

public class ShopAdapterAdmin extends RecyclerView.Adapter<ShopAdapterAdmin.ShopViewHolder> {

    List<FoodResponseData> prDataList;
    LayoutInflater layoutInflater;
    Context context;

    public ShopAdapterAdmin(List<FoodResponseData> productDataList, ListProductsActivity context) {
        this.prDataList = productDataList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ShopAdapterAdmin.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopViewHolder(layoutInflater.inflate(R.layout.item_product_admin, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapterAdmin.ShopViewHolder holder, int position) {
        holder.Price.setText(prDataList.get(position).getFoodPrice()+"");
        holder.quantityTV.setText(prDataList.get(position).getFoodQuantity()+"");
        holder.nameTV.setText(prDataList.get(position).getFoodName());
        Picasso.get().load(prDataList.get(position).getFoodImg()).into(holder.productIV);
        holder.mainLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public int getItemCount() {
        return prDataList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV;
        LinearLayout mainLL;
        TextView nameTV,  Price,  quantityTV;
        public ShopViewHolder(@NonNull View itemView) {

            super(itemView);
            productIV = itemView.findViewById(R.id.productIV);
            nameTV = itemView.findViewById(R.id.productNameTV);
            mainLL = itemView.findViewById(R.id.mainLL);
            Price = itemView.findViewById(R.id.PriceTV);
            quantityTV = itemView.findViewById(R.id.quantityTV);
        }
    }
}
