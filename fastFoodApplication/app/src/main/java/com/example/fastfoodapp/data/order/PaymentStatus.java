package com.example.fastfoodapp.data.order;

public enum PaymentStatus {

    PAID(0),
    NOT_PAID(1);

    private final int value;

    PaymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
