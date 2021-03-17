package com.example.fastfoodapp.Dim4es.SuccessPay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fastfoodapp.Dim4es.PayPage.PayMainActivity;
import com.example.fastfoodapp.R;

public class SuccessPayPage extends AppCompatActivity {
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dim4es_activity_success_pay_page);

        //define el-s
        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessPayPage.this, PayMainActivity.class);
                startActivity(intent);
            }
        });
    }
}