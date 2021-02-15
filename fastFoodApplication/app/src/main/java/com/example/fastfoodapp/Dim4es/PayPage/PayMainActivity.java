package com.example.fastfoodapp.Dim4es.PayPage;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fastfoodapp.R;
public class PayMainActivity extends AppCompatActivity {
    ImageView arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_main);

        arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Arrow back to return to previous page
                Toast toast = Toast.makeText(PayMainActivity.this, "Back arrow clicked", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.TOP, 20, 30);
                toast.show();
            }
        });
    }
}