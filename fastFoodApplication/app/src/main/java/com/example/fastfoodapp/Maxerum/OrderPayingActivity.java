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

    private ArrayList<ExampleItem> exampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Trying outside");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_paying);
        System.out.println("ЗДАРОВА");
        createExampleList();
        System.out.println("ЗДАРОВА");
        buildRecyclerView();
        setButtons();
        // Maxim, что за button12 ???    щ(ಠ益ಠщ)
        // чтобы исправил!!
        //НЕ ТВОЕ ДЕЛО ЧОРТ

    }

    public void removeItem(int position) {
        System.out.println("REMOVER item");
        exampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void setButtons() {
        Button payButton = findViewById(R.id.button12);
        Button cancelButton = findViewById(R.id.cancel_button);
        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PayMainActivity.class);
            startActivity(intent);
        });
        cancelButton.setOnClickListener(v->{
            finish();
        });
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public void createExampleList(){
        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
    }

}