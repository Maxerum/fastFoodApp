package com.example.fastfoodapp.data.order;

public enum OrderStatus {

    COOKING(0),
    READY(1),
    CLOSED(2);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatus createEnum(int value) {
        OrderStatus status = null;
        switch (value) {
            case 0:
                status = COOKING;
                break;
            case 1:
                status = READY;
                break;
            case 2:
                status = CLOSED;
                break;
        }
        return status;
    }
}
