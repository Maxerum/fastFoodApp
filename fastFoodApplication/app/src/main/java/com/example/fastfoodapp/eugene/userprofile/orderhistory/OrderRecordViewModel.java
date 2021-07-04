package com.example.fastfoodapp.eugene.userprofile.orderhistory;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.example.fastfoodapp.eugene.data.order.Order;

import java.util.HashMap;

public class OrderRecordViewModel {

    public final ObservableField<String> mOrderTimestamp = new ObservableField<>();

    public final ObservableField<String> mOrderPaymentMethodName = new ObservableField<>();

    public final ObservableField<String> mOrderTotal = new ObservableField<>();

    public final ObservableField<String> mOrderItems = new ObservableField<>();

    public final ObservableField<String> mOrderStatus = new ObservableField<>();

    public final ObservableField<Order> mOrder = new ObservableField<>();

    public OrderRecordViewModel() {

        mOrder.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mOrderTimestamp.set(mOrder.get().getTimestamp().toDate().toString());
                mOrderTotal.set(mOrder.get().getOrderTotal());
                mOrderPaymentMethodName.set(mOrder.get().getPaymentMethodName());
                mOrderItems.set(convertOrderItemsToString(mOrder.get().getOrderItems()));
                mOrderStatus.set(mOrder.get().getStatus());
            }
        });
    }

    // TODO: probably need to change the implementation later
    private String convertOrderItemsToString(HashMap<String, Integer> orderItems) {
        return orderItems.toString();
    }
}
