package com.example.fastfoodapp.data.category;

import androidx.annotation.NonNull;

public enum MenuCategories {
    BURGERS,
    SNACKS_AND_SIDES,
    BEVERAGES,
    DESSERTS_AND_SHAKES,
    BREAKFAST;

    @NonNull
    @Override
    public String toString() {
        String name = name();
        String result = null;
        if (name.equals("BURGERS")) {
            result = "Burgers";
        } else if (name.equals("SNACKS_AND_SIDES")) {
            result = "Snacks & Sides";
        } else if (name.equals("BEVERAGES")) {
            result = "Beverages";
        } else if (name.equals("DESSERTS_AND_SHAKES")) {
            result = "Desserts & Shakes";
        } else {
            result = "Breakfast";
        }
        return result;
    }
}
