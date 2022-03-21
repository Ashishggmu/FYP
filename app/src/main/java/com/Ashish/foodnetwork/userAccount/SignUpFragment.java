package com.Ashish.foodnetwork.userAccount;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Ashish.foodnetwork.Api.ApiClient;
import com.Ashish.foodnetwork.R;
import com.Ashish.foodnetwork.utils.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment {

    TextView loginUnLetter;
    Button singupbtn;
    TextView password;
    TextView email;
    TextView phone;
    TextView fullName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginUnLetter=view.findViewById(R.id.loginUnLetter);
        singupbtn=view.findViewById(R.id.singupbtn);
        password=view.findViewById(R.id.passwordET);
        email=view.findViewById(R.id.email);
        phone=view.findViewById(R.id.phone);
        fullName=view.findViewById(R.id.fullName);
        loginUnLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userLogin,new LoginFragment()).commit();
            }
        });
        singupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                   callResponse(fullName.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString());
                }
            }
        });

    }
    private Boolean validate() {
        if (password.getText().toString().isEmpty()&&email.getText().toString().isEmpty()&&phone.getText().toString().isEmpty()&&fullName.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Empty Field not allowed!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(phone.getText().toString().length()<10||phone.getText().toString().length()>10||password.getText().toString().length()<10){
            Toast.makeText(getActivity(), "Less than 10 letters or numbers are not allowed!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(getActivity(), "Loading!!!",Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public void callResponse(String name, String email,String phone, String password){
        Call<RegisterResponse> registerResponseCall= ApiClient.getApiServices()
                .signUpUser(name, email, phone, password);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    RegisterResponse registerResponse= response.body();
                    if (!registerResponse.getError()){
                        Toast.makeText(getActivity(),registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getActivity(), UserAccountActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }
}