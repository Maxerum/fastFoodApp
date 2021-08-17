package com.example.fastfoodapp.userprofile.orderhistory;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.example.fastfoodapp.data.order.Order;
import com.example.fastfoodapp.data.order.OrderStatus;
import com.example.fastfoodapp.util.ConvertUtils;

import java.util.HashMap;
import java.util.Objects;

public class OrderRecordViewModel {

    public final ObservableField<String> mOrderTimestamp = new ObservableField<>();

    public final ObservableField<String> mOrderTotal = new ObservableField<>();

    public final ObservableField<String> mOrderItems = new ObservableField<>();

    public final ObservableField<String> mOrderStatus = new ObservableField<>();

    public final ObservableField<String> mOrderRestaurant = new ObservableField<>();

    private final ObservableField<Order> mOrder = new ObservableField<>();

    public OrderRecordViewModel(Order order) {
        mOrder.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mOrderTimestamp.set(Objects.requireNonNull(mOrder.get()).getTimestamp()
                        .toDate().toString());
                mOrderTotal.set(Objects.requireNonNull(mOrder.get()).getOrderTotal());
                mOrderStatus.set(OrderStatus.createEnum(Objects.requireNonNull(mOrder
                        .get()).getStatus()).toString());
                mOrderRestaurant.set(Objects.requireNonNull(mOrder.get()).getRestaurant());

                String itemsConverted = ConvertUtils.convertMapToString(
                        Objects.requireNonNull(mOrder.get()).getOrderItems());
                mOrderItems.set(itemsConverted);
            }
        });

        mOrder.set(order);
    }
}
