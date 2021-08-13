package com.example.fastfoodapp.data.category;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menu_categories")
public class MenuCategory {

    @NonNull
    @PrimaryKey
    private final String category;

    public MenuCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
