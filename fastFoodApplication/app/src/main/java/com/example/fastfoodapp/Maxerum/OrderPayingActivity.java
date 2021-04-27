package com.example.fastfoodapp.Maxerum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fastfoodapp.Dim4es.PayPage.PayMainActivity;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderPayingActivity extends AppCompatActivity {

    private ArrayList<ExampleItem> exampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView textView3;

    private HashMap<MenuItemMainInfo, Integer> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Trying outside");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_paying);
        System.out.println("ЗДАРОВА");
//        Bundle args = getIntent().getExtras();
//        selectedItems = (HashMap<MenuItemMainInfo, Integer>)
//                args.getSerializable("selected items");

        selectedItems = ((FastFoodApp) getApplication()).appContainer.selectedItems;

        System.out.println("ЗДАРОВА");
        createExampleList(selectedItems);
        System.out.println("ЗДАРОВА");
        buildRecyclerView();
        setButtons();
        // Maxim, что за button12 ???    щ(ಠ益ಠщ)
        // чтобы исправил!!
        //НЕ ТВОЕ ДЕЛО ЧОРТ


        // Это я добавил (Женя)

    }

    public void removeItem(int position) {
        System.out.println("REMOVER item");

        int i = 0;
        MenuItemMainInfo bufferedItem = null;
        for (MenuItemMainInfo menuItem : selectedItems.keySet()) {
            if (position == i) {
                bufferedItem = menuItem;
                break;
            }
            i++;
        }
        selectedItems.remove(bufferedItem);
        exampleList.remove(position);

        setPriceText(recalculateTotalPrice());
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

    public void createExampleList(HashMap<MenuItemMainInfo, Integer> selectedItems){
        exampleList = new ArrayList<>();
        textView3 = findViewById(R.id.textView3);
        float bigSum = 0;
        String bigSumString = " ", bigSumString2 = " ";
        int amount;
        for(MenuItemMainInfo info: selectedItems.keySet()){

//            System.out.println(info.imageUri);
            String imh = info.imageUri.substring(43,53);
//            System.out.println(imh);
            exampleList.add(new ExampleItem(Integer.parseInt(imh), info.title, info.price + " " + "\n" + "x" + selectedItems.get(info)));
            System.out.println(selectedItems.get(info));

            amount = selectedItems.get(info);
            String mul = info.price.substring(0, info.price.length() - 2);
            bigSum += amount * Float.parseFloat(mul);
            bigSumString = String.format("%.2f", bigSum);
            bigSumString2 = "TOTAL: " + bigSumString + "$";
//            bigSum = String.format("%.2f",value);
//            System.out.println(bigSum);
        }
        textView3.setText(bigSumString2);
//        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
//        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
//        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
//        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
//        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
//        exampleList.add(new ExampleItem(R.drawable.first_burger, "BURGER","5$"));
    }

    private float recalculateTotalPrice() {
        float totalPrice = 0;
        for (MenuItemMainInfo menuItem : selectedItems.keySet()) {
            String number = menuItem.price.substring(0, menuItem.price.length() - 2);
            float itemPrice = Float.parseFloat(number) * selectedItems.get(menuItem);
            totalPrice += itemPrice;
        }
        return totalPrice;
    }

    private void setPriceText(float price) {
        @SuppressLint("DefaultLocale") String priceStr = String.format("%.2f", price);
        String totalPrice = "TOTAL: " + priceStr + "$";
        textView3.setText(totalPrice);
    }

    private void printSelectedItems() {
        for (MenuItemMainInfo menuItem : selectedItems.keySet()) {
            Log.d("TAG", menuItem.title);
        }
    }

}