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
import com.Ashish.foodnetwork.utils.response.CategoryResponseData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements Serializable {
    LayoutInflater layoutInflater;
    List<CategoryResponseData> data;
    Context context;
    EachCategoryClick eachCategoryClick;

    public CategoryAdapter(List<CategoryResponseData> data, Context context){
        this.data= data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= layoutInflater.inflate(R.layout.category_items,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryResponseData categoryResponseData= data.get(position);
        holder.categoryname.setText(categoryResponseData.getCatName());
        Picasso.get().load(categoryResponseData.getCatImg()).into(holder.categoryimage);
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
                    eachCategoryClick.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface EachCategoryClick{
        void onClick(int position);
    }

    public  void setCategoryClick(EachCategoryClick eachCategoryClick){
       this.eachCategoryClick=eachCategoryClick;
    }
}
