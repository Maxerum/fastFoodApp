package com.example.fastfoodapp.ordersummary;

import com.stripe.android.paymentsheet.PaymentSheetResult;

public interface PaymentSheetController {

    interface PaymentSheetCallback {

        void onPaymentSheetResult(PaymentSheetResult result);
    }

    void setPaymentSheetCallback(PaymentSheetCallback callback);
    void presentPaymentSheet(String paymentIntentSecret, String ephemeralKey, String customerId);
}
