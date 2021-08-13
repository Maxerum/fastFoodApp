package com.example.fastfoodapp;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;

public class FastFoodApp extends Application {

    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();

        appContainer = new AppContainer(getApplicationContext());
        PaymentConfiguration.init(getApplicationContext(),
                "pk_test_51JHnfiI0dsbE5ibsJuMEOrpOplPeM0FKQUm5JLe9nISHO23fgFbBaJROZkgGwPPYTPV3qcsrDYbcKVHP1SSJJa8100dedMRozO");
    }
}
