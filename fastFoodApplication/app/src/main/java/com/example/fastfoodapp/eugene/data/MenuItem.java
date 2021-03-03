package com.example.fastfoodapp.eugene.data;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MenuItem {

    private final String mTitle;

    private final int mCalories;

    private final int mTotalFat;

    private final String mPrice;

    private final Uri mImageUri;

    @Nullable
    private final ArrayList<Ingredient> mIngredients;

    public MenuItem(String title, int calories, int totalFat, String price,
                    Uri imageUri, ArrayList<Ingredient> ingredients) {
        mTitle = title;
        mCalories = calories;
        mTotalFat = totalFat;
        mPrice = price;
        mImageUri = imageUri;
        mIngredients = ingredients;
    }

    public MenuItem(String title, int calories, int totalFat, String price, Uri imageUri) {
        this(title, calories, totalFat, price, imageUri, null);
    }

    public String getTitle() {
        return mTitle;
    }

    public int getCalories() {
        return mCalories;
    }

    public int getTotalFat() {
        return mTotalFat;
    }

    public String getPrice() {
        return mPrice;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    @Nullable
    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }
}
