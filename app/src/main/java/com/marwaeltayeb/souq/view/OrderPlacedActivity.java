package com.marwaeltayeb.souq.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.marwaeltayeb.souq.R;


public class OrderPlacedActivity extends AppCompatActivity {


    TextView orderidview;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        initialize();
    }

    private void initialize() {
        // nhận maDonHang từ CheckoutActivity
        Intent intent = getIntent();
        String maDonHang = intent.getStringExtra("maDonHang");
        //orderid = getIntent().getStringExtra("orderid");
        orderidview = findViewById(R.id.orderid);
        orderidview.setText(maDonHang);
    }

    public void finishActivity(View view) {
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderPlacedActivity.this, ProductActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }
}