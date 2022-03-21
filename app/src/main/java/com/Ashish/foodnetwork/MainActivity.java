package com.Ashish.foodnetwork;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Ashish.foodnetwork.UserDashboard.HomeActivity;
import com.Ashish.foodnetwork.userAccount.UserAccountActivity;
import com.Ashish.foodnetwork.utils.Constants;
import com.Ashish.foodnetwork.utils.SharePrefrenceUtils;

public class MainActivity extends AppCompatActivity {

    TextView toSignUp;
    Button loginBtn;
    TextView password;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(this, UserAccountActivity.class);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean is_logged=
                        SharePrefrenceUtils.getBooleanPreference(MainActivity.this, Constants.IS_LOGIN_KEY,false);

                if(is_logged){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }else {
                    startActivity(intent);
                }
                finish();
            }
        },1000);
    }
}