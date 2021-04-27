package com.example.fastfoodapp.eugene.data.order;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;

public class Order {

    private String uid;

    private Timestamp timestamp;

    private String paymentMethodName;

    private HashMap<String, Integer> orderItems;

    private String status;

    private String restaurant;

    public Order() {}

    public Order(String uid, String paymentMethodName,
                 HashMap<String, Integer> orderItems, String restaurant) {
        this.uid = uid;
        this.paymentMethodName = paymentMethodName;
        this.timestamp = new Timestamp(new Date());
        this.orderItems = orderItems;
        status = OrderStatus.Cooking.toString();
        this.restaurant = restaurant;
    }

    public String getUid() {
        return uid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public HashMap<String, Integer> getOrderItems() {
        return orderItems;
    }

    public String getStatus() {
        return status;
    }

    public String getRestaurant() {
        return restaurant;
    }
}
