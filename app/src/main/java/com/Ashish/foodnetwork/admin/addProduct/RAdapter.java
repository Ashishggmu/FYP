package com.Ashish.foodnetwork.admin.addProduct;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.CategoryResponseData;

import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {
    LayoutInflater inflater;
    boolean isImage = false;
    List<Uri> uris;
    List<CategoryResponseData> cats;
    OnItemClick onItemCLick;


    public RAdapter(boolean isImage, List<Uri> uris, List<CategoryResponseData> cats, Context context, OnItemClick onItemCLick){
        this.isImage = isImage;
        this.uris = uris;
        inflater = LayoutInflater.from(context);
        this.cats = cats;
        this.onItemCLick = onItemCLick;
    }
    @NonNull
    @Override
    public RAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isImage)
            return new ViewHolder(inflater.inflate(R.layout.item_image, parent, false), isImage);
        else
            return new ViewHolder(inflater.inflate(R.layout.item_add_category, parent, false), isImage);
    }

    @Override
    public void onBindViewHolder(@NonNull RAdapter.ViewHolder holder, int position) {
        if (isImage){
            holder.imageView.setImageURI(uris.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemCLick.onClick(holder.getAdapterPosition());
                }
            });
        }else{
            holder.textView.setText(cats.get(position).getCatName());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemCLick.onClick(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isImage)
            return uris.size();
        else
        return cats.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        boolean isC;

        public ViewHolder(@NonNull View itemView, boolean isC) {
            super(itemView);
            if (isC)
                imageView = itemView.findViewById(R.id.cImg);
            else
                textView = itemView.findViewById(R.id.catNameTV);
        }
    }
    interface OnItemClick{
        void onClick(int position);
    }
}
