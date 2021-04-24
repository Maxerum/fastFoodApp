package com.example.fastfoodapp.eugene.data;


import java.util.HashMap;

public class CardInfo {

    private String number;

    private HashMap<String, Integer> expirationDate;

    public CardInfo() {}

    public CardInfo(String number, HashMap<String, Integer> expDate) {
        this.number = number;
        expirationDate = expDate;
    }

    public String getNumber() {
        return number;
    }

    public HashMap<String, Integer> getExpirationDate() {
        return expirationDate;
    }
}
