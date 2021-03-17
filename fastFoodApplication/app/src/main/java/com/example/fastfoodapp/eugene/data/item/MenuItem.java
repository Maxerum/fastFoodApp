package com.example.fastfoodapp.eugene.data.item;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "menu_items")
public class MenuItem {

    @NonNull
    @PrimaryKey
    private String title;

    private String price;

    @ColumnInfo(name = "image_uri")
    private String imageUri;

    private String calories;

    @ColumnInfo(name = "total_fat")
    private String totalFat;

    @ColumnInfo(name = "total_carbs")
    private String totalCarbs;

    private String protein;

    @ColumnInfo(name = "menu_category")
    private String menuCategory;

    public MenuItem(String title, String price, String imageUri, String calories,
                    String totalFat, String totalCarbs, String protein, String menuCategory) {
        this.title = title;
        this.price = price;
        this.imageUri = imageUri;
        this.calories = calories;
        this.totalFat = totalFat;
        this.totalCarbs = totalCarbs;
        this.protein = protein;
        this.menuCategory = menuCategory;
    }

    public String getTitle() {
        return title;
    }

    public String getCalories() {
        return calories;
    }

    public String getTotalFat() {
        return totalFat;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getTotalCarbs() {
        return totalCarbs;
    }

    public String getProtein() {
        return protein;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setTotalFat(String totalFat) {
        this.totalFat = totalFat;
    }

    public void setTotalCarbs(String totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }
}
