package com.Ashish.foodnetwork.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.Address;

import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    List<Address> addressList;
    Context context;
    LayoutInflater inflater;
    OnAddressItemClickListener onAddressItemClickListener;


    public AddressAdapter(List<Address> addressList, Context context){
        this.addressList=addressList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressViewHolder(inflater.inflate(R.layout.item_address,parent,false));
    }

    public void setOnAddressItemClickListener(OnAddressItemClickListener onAddressItemClickListener){
        this.onAddressItemClickListener = onAddressItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.cityStreet.setText(address.getCity()+" "+ address.getStreet());
        holder.province.setText(address.getProvince());
        holder.dec.setText(address.getDescription());
        holder.addressL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddressItemClickListener.onAddressClick(holder.getAdapterPosition(),addressList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView cityStreet, province, dec;
        LinearLayout addressL;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            cityStreet = itemView.findViewById(R.id.cityStreetTV);
            province = itemView.findViewById(R.id.provinceTV);
            dec = itemView.findViewById(R.id.decTV);
            addressL = itemView.findViewById(R.id.addressLL);
        }
    }

    public interface OnAddressItemClickListener{
        public void onAddressClick(int position, Address address);
    }
}
