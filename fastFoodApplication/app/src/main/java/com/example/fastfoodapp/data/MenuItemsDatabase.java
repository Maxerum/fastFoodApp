package com.example.fastfoodapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fastfoodapp.data.category.MenuCategory;
import com.example.fastfoodapp.data.category.MenuCategoriesDao;
import com.example.fastfoodapp.data.item.MenuItem;
import com.example.fastfoodapp.data.item.MenuItemsDao;

@Database(entities = {MenuItem.class, MenuCategory.class}, version = 4)
public abstract class MenuItemsDatabase extends RoomDatabase {

    public static final String DB_NAME = "MenuItemsDatabase";

    public abstract MenuItemsDao menuItemsDao();

    public abstract MenuCategoriesDao menuCategoryDao();

    public static MenuItemsDatabase newInstance(Context context) {
        return Room.databaseBuilder(context, MenuItemsDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration().build();
    }
}
