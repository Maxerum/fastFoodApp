package com.example.fastfoodapp.eugene.cardsdialog;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;


public class PaymentMethodViewModel {

    public static final String TAG = "PaymentMethodViewModel";

    public final ObservableBoolean checked = new ObservableBoolean(false);

    public final ObservableField<String> paymentMethodName = new ObservableField<>();

    private UncheckPaymentMethod uncheckPaymentMethod;

    public PaymentMethodViewModel() {
    }

    public void setPaymentMethodName(String name) {
        paymentMethodName.set(name);
    }

    public void setUncheckPaymentMethod(UncheckPaymentMethod uncheckPaymentMethod) {
        this.uncheckPaymentMethod = uncheckPaymentMethod;
    }

    public void setChecked() {
        if (!checked.get()) {
            if (uncheckPaymentMethod != null) {
                uncheckPaymentMethod.uncheckPaymentMethod();
            }
            checked.set(true);
        }
    }
}
