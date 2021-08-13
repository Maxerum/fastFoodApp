package com.example.fastfoodapp.orderdetails;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StripePaymentSheetConfigProvider {

    private final FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();

    public Task<String> fetchInitData(int amount, String currency, String apiVersion) {

        Map<String, Object> data = new HashMap<>();
        data.put("amount", amount);
        data.put("currency", currency);
        data.put("api_version", apiVersion);

        return mFunctions.getHttpsCallable("createStripePayment")
                .call(data)
                .continueWith(task ->
                        Objects.requireNonNull(task.getResult().getData()).toString());
    }
}
