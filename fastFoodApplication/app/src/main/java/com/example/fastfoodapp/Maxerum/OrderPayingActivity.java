package com.example.fastfoodapp.Maxerum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

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
        exampleList.add(new ExampleItem(R.drawable.first_burger, "FUCKING BIRGER","KEKC"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "FUCKING BIRGER","KEKC"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "FUCKING BIRGER","KEKC"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "FUCKING BIRGER","KEKC"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "FUCKING BIRGER","KEKC"));




        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}