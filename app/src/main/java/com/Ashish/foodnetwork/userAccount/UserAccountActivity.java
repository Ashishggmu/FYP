package com.Ashish.foodnetwork.userAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.Ashish.foodnetwork.R;

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        getSupportFragmentManager().beginTransaction().add(R.id.userLogin,new LoginFragment()).commit();
    }
}