package com.Ashish.foodnetwork.UserDashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.adapter.CategoryAdapter;
import com.Ashish.foodnetwork.utils.adapter.ItemAdapter;
import com.Ashish.foodnetwork.utils.adapter.SliderAdapter;
import com.Ashish.foodnetwork.utils.response.CartResponse;
import com.Ashish.foodnetwork.utils.response.CategoryResponse;
import com.Ashish.foodnetwork.utils.response.CategoryResponseData;
import com.Ashish.foodnetwork.utils.response.FoodResponse;
import com.Ashish.foodnetwork.utils.response.FoodResponseData;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ItemAdapter.EachItemClick, CategoryAdapter.EachCategoryClick {
 RecyclerView category;
 RecyclerView subcategory;
 List<CategoryResponseData> categoryList;
 List<FoodResponseData> subCategoryList;
 CategoryAdapter categoryAdapter;
 ItemAdapter itemAdapter;
 SearchView searchView;
 SliderView sliderView;
 int[] images={R.drawable.slider1,
         R.drawable.slider2,
         R.drawable.slider3,
         R.drawable.slider4,
         R.drawable.slider5,
         R.drawable.slider6,
         R.drawable.slider7,
 };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        category=view.findViewById(R.id.category);
        subcategory=view.findViewById(R.id.subcategory);
        searchView=view.findViewById(R.id.searchView);
        sliderView=view.findViewById(R.id.imageSlider);



        SliderAdapter sliderAdapter= new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();



        subcategoryDataCall();
        categoryDataCall();
        searchListener();
    }
    ///search

    private void searchListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private  void fitlerByCategory(String selectedCat){
        itemAdapter.getFilter().filter(selectedCat);
    }

    private void subcategoryDataCall() {
        Call<FoodResponse> foodResponseCall = ApiClient.getApiServices().getFood();

        foodResponseCall.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if (response.body().getDatas() != null){
                            setItemsRv(response.body().getDatas());
                        }
                        else{
                            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "cant call server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodResponse> call, Throwable t) {

            }
        });
    }
    //search


    private void categoryDataCall() {
        Call<CategoryResponse> categoryResponseCall= ApiClient.getApiServices().getCategory();

        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    if (!response.body().getError()){
                        if (response.body().getDatas() != null){
                            setCategoryRv(response.body().getDatas());
                        }
                        else{
                            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();;
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "Can't call server", Toast.LENGTH_SHORT).show();;
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
    }

    private void setItemsRv(List<FoodResponseData> data) {
        subCategoryList=data;
        subcategory.setHasFixedSize(true);
        subcategory.setLayoutManager( new GridLayoutManager(getContext(),2));
        itemAdapter =new ItemAdapter(subCategoryList,getContext());
        subcategory.setAdapter(itemAdapter);
        itemAdapter.setEachItemClickListener(this);

    }

    private void setCategoryRv(List<CategoryResponseData> data) {
        categoryList=data;
        category.setHasFixedSize(true);
        category.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        categoryAdapter = new CategoryAdapter(categoryList,getContext());
        category.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryClick(this::onClick);

    }

    @Override
    public void onItemClick(int position) {
        FoodResponseData responseData= subCategoryList.get(position);

        Intent intent= new Intent(getActivity(),FoodDetailsActivity.class);
        intent.putExtra(FoodDetailsActivity.FoodData, responseData);
        startActivity(intent);
    }

    @Override
    public void onCartClick(int position) {

        FoodResponseData responseData= subCategoryList.get(position);
//here
        Call<CartResponse> cartResponseCall= ApiClient.getApiServices()
                .add_cart(SharePrefrenceUtils.getStringPreference(getContext(), Constants.API_KEY),
                        responseData.getFoodId(),1,responseData.getFoodPrice());

        cartResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(int position) {
        CategoryResponseData categoryResponseData= categoryList.get(position);
        fitlerByCategory(categoryResponseData.getCatName());
    }
}