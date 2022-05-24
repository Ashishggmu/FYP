package com.Ashish.foodnetwork.UserDashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.userAccount.UserAccountActivity;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    Button logout,update;
    LinearLayout aboutus;
    TextView full_name, username, email, fName, phn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout= view.findViewById(R.id.logout);
        full_name=view.findViewById(R.id.full_name);
        username=view.findViewById(R.id.username);
        email=view.findViewById(R.id.email);
        fName=view.findViewById(R.id.fName);
        phn=view.findViewById(R.id.phN);
        update=view.findViewById(R.id.update);
        aboutus=view.findViewById(R.id.aboutus);


        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),AboutUsActivity.class);
                startActivity(intent);
            }
        });

        full_name.setText(SharePrefrenceUtils.getStringPreference(getContext(),Constants.Full_name));
        fName.setText(SharePrefrenceUtils.getStringPreference(getContext(),Constants.Full_name));
        username.setText(SharePrefrenceUtils.getStringPreference(getContext(),Constants.Email_ID));
        email.setText(SharePrefrenceUtils.getStringPreference(getContext(),Constants.Email_ID));
        phn.setText(SharePrefrenceUtils.getStringPreference(getContext(),Constants.Phone));

        update=view.findViewById(R.id.update);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    callResponse(fName.getText().toString(),phn.getText().toString());

                    fName.setText("");
                    phn.setText("");
                }
            }
        });




        onClickmethod();
    }

    private Boolean validate() {

        if(phn.getText().toString().length()<10){
            Toast.makeText(getActivity(), "Less than 10 letters or numbers are not allowed!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(getActivity(), "You have successfully updated the data!!!",Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void callResponse(String full_name, String phone_number) {
        Call<RegisterResponse> registerResponseCall= ApiClient.getApiServices().updateProfile(SharePrefrenceUtils.getStringPreference(getContext(), Constants.API_KEY),full_name,phone_number);

        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    if (!response.body().getError()){


                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    private void onClickmethod() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePrefrenceUtils.setBooleanPreference(getContext(), Constants.IS_LOGIN_KEY,false);
                SharePrefrenceUtils.setStringPreference(getContext(),Constants.API_KEY,"");
                Intent intent= new Intent(getActivity(), UserAccountActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}