package com.example.fastfoodapp.Dim4es.PayPage;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfoodapp.Dim4es.CreditCard.AddFormatCreditCard;
import com.example.fastfoodapp.Dim4es.SuccessPay.SuccessPayPage;
import com.example.fastfoodapp.R;
public class PayMainActivity extends AppCompatActivity {
    ImageView arrowBackPayPage;
    TextView textViewToChooseSpinner;
    Spinner spinnerRestaurant;

    //Buttons
    Button userCreditCard, addUserCard, finalSendButton;

    //some names to fill the void
    String[] restaurants = {"Ресторан0", "Ресторан1", "Ресторан2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_main);

        //define all buttons with id in xml
        userCreditCard = findViewById(R.id.userCreditCard);
        addUserCard = findViewById(R.id.addUserCard);
        finalSendButton = findViewById(R.id.finalSendButton);


        //define arrow in toolbar
        arrowBackPayPage = findViewById(R.id.arrowBackPayPage);

        //INfo about "Choose restaurant"
        textViewToChooseSpinner = findViewById(R.id.textViewToChooseSpinner);

        //define spinner
        spinnerRestaurant = findViewById(R.id.restaurantSpinner);
        //spinner contains TextView (to choose)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, restaurants);
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRestaurant.setAdapter(adapter);



        //select item on spinner
        spinnerRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewToChooseSpinner.setText(onItemSelectedHandler(parent, view, position, id) + "\n<some info + picture(mb?)>");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //arrow back on toolbar event listener
        arrowBackPayPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Arrow back to return to previous page
                //log info
                Toast toast = Toast.makeText(PayMainActivity.this, "Back arrow clicked", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.TOP, 20, 30);
                toast.show();
            }
        });



        //user credit card (which already exist), **mb show?**
        userCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(PayMainActivity.this, "User credit card button clicked", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.TOP, 20, 30);
                toast.show();
            }
        });


        //add user credit card (? new page with formatted input?)
        addUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast toast = Toast.makeText(PayMainActivity.this, "Add user credit Card button clicked", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.TOP, 20, 30);
                toast.show();*/
                Intent intent = new Intent(PayMainActivity.this, AddFormatCreditCard.class);
                startActivity(intent);
            }
        });


        // success or not, this order(new page mb?)
        finalSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast toast = Toast.makeText(PayMainActivity.this, "Отправить в печь - button is clicked", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.TOP, 20, 30);
                toast.show();*/
                Intent intent = new Intent(PayMainActivity.this, SuccessPayPage.class);
                startActivity(intent);
            }
        });



    }

    private String onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id){
        Adapter adapter = adapterView.getAdapter();
        String choose = adapter.getItem(position).toString();

        //some log info
        //Toast toast = Toast.makeText(getApplicationContext(), "Selected rest." + choose, Toast.LENGTH_SHORT);
        //toast.show();
        return choose;
    }

}