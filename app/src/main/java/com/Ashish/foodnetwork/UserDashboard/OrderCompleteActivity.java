package com.Ashish.foodnetwork.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Ashish.foodnetwork.R;

public class OrderCompleteActivity extends AppCompatActivity {

TextView shopMoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        shopMoreTV=findViewById(R.id.shopMoreTv);
        shopMoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OrderCompleteActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}