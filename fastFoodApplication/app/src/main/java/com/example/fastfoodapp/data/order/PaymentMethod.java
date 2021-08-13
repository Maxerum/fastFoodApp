package com.example.fastfoodapp.data.order;

public enum PaymentMethod {

    CASH(0),
    CARD(1);

    private final int value;

    PaymentMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
