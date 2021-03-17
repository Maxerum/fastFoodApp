package com.example.fastfoodapp.Maxerum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fastfoodapp.Dim4es.PayPage.PayMainActivity;
import com.example.fastfoodapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderPayingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Trying outside");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_paying);

        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.first_burger, "Burger #1","Meat"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "Burger #2","Meat"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "Burger #3","Meat"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "Burger #4","Meat"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "Burger #5","Meat"));




        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Maxim, что за button12 ???    щ(ಠ益ಠщ)
        // чтобы исправил!!
        Button payButton = findViewById(R.id.button12);
        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PayMainActivity.class);
            startActivity(intent);
        });
    }

}