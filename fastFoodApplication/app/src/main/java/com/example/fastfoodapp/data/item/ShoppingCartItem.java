package com.example.fastfoodapp.data.item;

public class ShoppingCartItem {

    private final String itemName;

    private final String priceForOne;

    private final String imageUri;

    private final int quantity;

    public ShoppingCartItem(String itemName, String priceForOne, String imageUri, int quantity) {
        this.itemName = itemName;
        this.priceForOne = priceForOne;
        this.imageUri = imageUri;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPriceForOne() {
        return priceForOne;
    }

    public String getImageUri() {
        return imageUri;
    }

    public int getQuantity() {
        return quantity;
    }
}
