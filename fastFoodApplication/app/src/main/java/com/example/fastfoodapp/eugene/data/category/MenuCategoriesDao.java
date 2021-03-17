package com.example.fastfoodapp.eugene.data.category;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MenuCategoriesDao {

    @Insert
    void insertAll(MenuCategory... menuCategories);

    @Query("DELETE FROM menu_categories")
    void deleteAll();

    @Query("SELECT * FROM menu_categories")
    List<MenuCategory> getAll();
}
