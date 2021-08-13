package com.example.fastfoodapp.orderdetails;

import com.example.fastfoodapp.data.order.PaymentMethod;

public class PaymentMethodViewModel {

    private ItemNavigator mNavigator;

    public void setNavigator(ItemNavigator navigator) {
        mNavigator = navigator;
    }

    public void selectWithCash() {
        if (mNavigator != null) {
            mNavigator.paymentMethodSelected(PaymentMethod.CASH);
        }
    }

    public void selectWithCard() {
        if (mNavigator != null) {
            mNavigator.paymentMethodSelected(PaymentMethod.CARD);
        }
    }
}
