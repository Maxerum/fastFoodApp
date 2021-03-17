package com.example.fastfoodapp.eugene.data;

import com.example.fastfoodapp.eugene.data.category.MenuCategory;
import com.example.fastfoodapp.eugene.data.item.MenuItem;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;

import java.util.ArrayList;
import java.util.List;

public interface MenuItemsDataSource {

    interface LoadMenuItemsMainInfoCallback {

        void onMenuItemsLoaded(List<MenuItemMainInfo> items);

        void onDataNotAvailable();
    }

    interface LoadMenuCategoriesCallback {

        void onMenuCategoriesLoaded(List<MenuCategory> categories);

        void onDataNotAvailable();
    }

    interface GetMenuItemCallback {

        void onMenuItemLoaded(MenuItem item);

        void onDataNotAvailable();
    }

    void addNewMenuItem(MenuItem menuItem);

    void getMenuItemsMainInfo(String category, LoadMenuItemsMainInfoCallback callback);

    void getMenuItemByTitle(String title, GetMenuItemCallback callback);

    void deleteAllMenuItems();

    void addMenuCategories(ArrayList<MenuCategory> categories);

    void getMenuCategories(LoadMenuCategoriesCallback callback);

    void deleteAllCategories();
}
