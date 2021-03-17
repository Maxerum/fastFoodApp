package com.example.fastfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fastfoodapp.Maxerum.OrderPayingActivity;
import com.example.fastfoodapp.eugene.account.AccountActivity;
import com.example.fastfoodapp.eugene.authorization.AuthorizationActivity;
import com.example.fastfoodapp.eugene.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}