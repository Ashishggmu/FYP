package com.Ashish.foodnetwork.userAccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.MainActivity;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.UserDashboard.HomeActivity;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;
import com.Ashish.foodnetwork.utils.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    TextView signUpLetter;
    Button loginBtn;
    EditText password;
    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpLetter=view.findViewById(R.id.singupUnLetter);

        loginBtn=view.findViewById(R.id.loginbtn);
        password=view.findViewById(R.id.password);
        email=view.findViewById(R.id.email);

        onClickMethod();


    }

    private void onClickMethod() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if( validate()){
                   callResponse(email.getText().toString(),password.getText().toString());
               }
            }


        });

        signUpLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userLogin,new SignUpFragment()).commit();
            }
        });
    }

    private Boolean validate() {
        if (password.getText().toString().isEmpty()&&email.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Empty Field not allowed!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(getActivity(), "Loading!!!",Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public void callResponse(String email, String password){
        Call<LoginResponse> call = ApiClient.getApiServices().loginUser(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){

                    LoginResponse loginResponse= response.body();
                    if(!loginResponse.getError()){
                        Intent intent = new Intent(getActivity(),HomeActivity.class);
                        SharePrefrenceUtils.setStringPreference(getContext(), Constants.API_KEY,loginResponse.getApiKey());
                        SharePrefrenceUtils.setStringPreference(getContext(), Constants.USER_ID,loginResponse.getUserId()+"");
                        Toast.makeText(getActivity(),"You have logged in succesfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        getActivity().finish();
                        SharePrefrenceUtils.setBooleanPreference(getContext(), Constants.IS_LOGIN_KEY,true);

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}