package com.example.fastfoodapp.Dim4es.CreditCard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.example.fastfoodapp.Dim4es.PayPage.PayMainActivity;
import com.example.fastfoodapp.Dim4es.Watchers.CreditCardWatcher;
import com.example.fastfoodapp.R;

public class AddFormatCreditCard extends AppCompatActivity {
    ImageView arrowBackOnCreditCard, calendarIcon;
    Dialog validCreditCardDialog;
    String month, year;
    EditText cardValidDate, cardNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dim4es_activity_add_format_credit_card);


        //define arrowBackCreditCard
        arrowBackOnCreditCard = findViewById(R.id.arrowBackOnCreditCardPage);
        //arrowBackCreditCard click listener
        arrowBackOnCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to previous page(pay page)
                Intent intent = new Intent(AddFormatCreditCard.this, PayMainActivity.class);
                startActivity(intent);
            }
        });


        //define calendarIcon
        calendarIcon = findViewById(R.id.chooseDateImageView);
        //calendar click listener
        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showValidCreditCardDialog();
            }
        });


        //define edit text to show Choose date dialog
        cardValidDate = findViewById(R.id.cardValidDate);
        cardValidDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showValidCreditCardDialog();
            }
        });




        //define cardNumberEditText
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        //create CreditCardWatcher
        CreditCardWatcher creditCardWatcher = new CreditCardWatcher(cardNumberEditText);
        cardNumberEditText.addTextChangedListener(creditCardWatcher);
    }


    private void showValidCreditCardDialog() {
        validCreditCardDialog = new Dialog(this); // create Dialog obj
        //set our dialog layout
        validCreditCardDialog.setContentView(R.layout.dim4es_dialog_to_choose_date);
        validCreditCardDialog.setCancelable(false);

        ////////////
        //define NumberPickers for month and year
        NumberPicker monthPicker, yearPicker;
        ///////////
        //month
        monthPicker = validCreditCardDialog.findViewById(R.id.monthPicker);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(5);
        ///////////
        //year
        yearPicker = validCreditCardDialog.findViewById(R.id.yearPicker);
        yearPicker.setMinValue(21);
        yearPicker.setMaxValue(77);
        yearPicker.setValue(22);

        ///////////////////////////////
        //Define done and cancel textViews
        TextView doneBtn, cancelBtn;
        doneBtn = validCreditCardDialog.findViewById(R.id.textDone);
        cancelBtn = validCreditCardDialog.findViewById(R.id.textCancel);
        ///////////////////////////////
        //add textView listeners
        //done text
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month = "" + monthPicker.getValue();
                year = "" + yearPicker.getValue();
                //edit date in main page
                cardValidDate = findViewById(R.id.cardValidDate);
                cardValidDate.setText(month + "/" + year);
                validCreditCardDialog.dismiss();
            }
        });
        //cancel text
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validCreditCardDialog.dismiss();
            }
        });

        //show dialog
        validCreditCardDialog.show();
    }
}