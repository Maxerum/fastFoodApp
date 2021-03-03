package com.example.fastfoodapp.Dim4es.CreditCard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fastfoodapp.Dim4es.PayPage.PayMainActivity;
import com.example.fastfoodapp.R;

public class AddFormatCreditCard extends AppCompatActivity {
    ImageView arrowBackCreditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_format_credit_card);


        //define arrowBackCreditCard
        arrowBackCreditCard = findViewById(R.id.arrowBackCreditCard);


        //arrowBackCreditCard click listener
        arrowBackCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //back to previous page(pay page)
                Intent intent = new Intent(AddFormatCreditCard.this, PayMainActivity.class);
                startActivity(intent);
            }
        });

    }
}