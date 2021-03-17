package com.example.fastfoodapp.eugene.data.item;

import androidx.room.ColumnInfo;

public class MenuItemMainInfo {
    // TODO: access fields through getters and setters
    public String title;
    public String price;
    @ColumnInfo(name = "image_uri")
    public String imageUri;
}
