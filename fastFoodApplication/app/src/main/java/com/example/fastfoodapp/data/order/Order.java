package com.example.fastfoodapp.data.order;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.Map;

public class Order {

    private Timestamp timestamp;
    private Map<String, Integer> orderItems;
    private int status;
    private String restaurant;
    private String orderTotal;
    private int paymentMethod;
    private int paymentStatus;

    public Order() {}

    public Order(Map<String, Integer> orderItems, String restaurant,
                 String orderTotal, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.timestamp = new Timestamp(new Date());
        this.orderItems = orderItems;
        status = OrderStatus.COOKING.getValue();
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
