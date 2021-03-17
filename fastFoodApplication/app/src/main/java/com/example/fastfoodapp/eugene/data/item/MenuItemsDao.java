package com.example.fastfoodapp.eugene.data.item;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MenuItemsDao {

    @Insert
    void insertAll(MenuItem... menuItems);

    @Query("DELETE FROM menu_items")
    void deleteAll();

    @Query("SELECT title, price, image_uri FROM menu_items WHERE menu_category = :category")
    List<MenuItemMainInfo> getMenuItemsMainInfo(String category);

    @Query("SELECT * FROM menu_items WHERE title = :title")
    MenuItem getMenuItemByTitle(String title);
}
