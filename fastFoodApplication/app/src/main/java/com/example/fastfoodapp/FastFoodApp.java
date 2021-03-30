package com.example.fastfoodapp;

import android.app.Application;

public class FastFoodApp extends Application {

    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();

        appContainer = new AppContainer(getApplicationContext());
    }
}
