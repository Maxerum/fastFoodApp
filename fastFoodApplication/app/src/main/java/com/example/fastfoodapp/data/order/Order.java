package com.example.fastfoodapp.data.order;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.Map;

public class Order {

    private final Timestamp timestamp;
    private final Map<String, Integer> orderItems;
    private final int status;
    private final String restaurant;
    private final String orderTotal;
    private final int paymentMethod;
    private final int paymentStatus;

    public Order(Map<String, Integer> orderItems, String restaurant,
                 String orderTotal, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.timestamp = new Timestamp(new Date());
        this.orderItems = orderItems;
        status = OrderStatus.Cooking.getValue();
        this.restaurant = restaurant;
        this.orderTotal = orderTotal;
        this.paymentMethod = paymentMethod.getValue();
        this.paymentStatus = paymentStatus.getValue();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Map<String, Integer> getOrderItems() {
        return orderItems;
    }

    public int getStatus() {
        return status;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getOrderTotal() { return orderTotal; }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }
}
