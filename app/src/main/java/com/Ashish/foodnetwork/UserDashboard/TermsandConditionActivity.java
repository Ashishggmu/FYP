package com.Ashish.foodnetwork.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.Ashish.foodnetwork.R;

public class TermsandConditionActivity extends AppCompatActivity {
    ImageView termsAndConditionbackIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_condition);
        termsAndConditionbackIV=findViewById(R.id.termsAndConditionbackIV);

        termsAndConditionbackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}