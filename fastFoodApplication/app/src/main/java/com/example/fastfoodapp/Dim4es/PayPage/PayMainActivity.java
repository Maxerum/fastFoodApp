package com.example.fastfoodapp.Dim4es.PayPage;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfoodapp.R;
public class PayMainActivity extends AppCompatActivity {
    ImageView arrowBack;
    TextView textViewToChooseSpinner;
    Spinner spinnerRestaurant;

    //some names to fill the void
    String[] restaurants = {"Ресторан0", "Ресторан1", "Ресторан2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_main);

        arrowBack = findViewById(R.id.arrowBack);

        //INfo about "Choose restaurant"
        textViewToChooseSpinner = findViewById(R.id.textViewToChooseSpinner);

        //
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
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Arrow back to return to previous page
                //log info
                Toast toast = Toast.makeText(PayMainActivity.this, "Back arrow clicked", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.TOP, 20, 30);
                toast.show();
            }
        });
    }

    private String onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id){
        Adapter adapter = adapterView.getAdapter();
        String choose = adapter.getItem(position).toString();

        //some log info
        Toast toast = Toast.makeText(getApplicationContext(), "Selected rest." + choose, Toast.LENGTH_SHORT);
        toast.show();
        return choose;
    }

}