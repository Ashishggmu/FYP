package com.Ashish.foodnetwork.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ashish.foodnetwork.R;

public class AboutUsActivity extends AppCompatActivity {
    ImageView backIV;
    TextView termsandcondtion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        backIV= findViewById(R.id.contactUsback);
        termsandcondtion=findViewById(R.id.termsandcoditions);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        termsandcondtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),TermsandConditionActivity.class);
                startActivity(intent);
            }
        });
    }
}