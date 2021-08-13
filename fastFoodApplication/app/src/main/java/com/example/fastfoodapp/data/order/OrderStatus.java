package com.example.fastfoodapp.data.order;

public enum OrderStatus {

    Cooking(0),
    Ready(1),
    Closed(2);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
