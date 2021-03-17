package com.example.fastfoodapp.eugene.data;

import android.util.Log;

import com.example.fastfoodapp.eugene.data.category.MenuCategoriesDao;
import com.example.fastfoodapp.eugene.data.category.MenuCategory;
import com.example.fastfoodapp.eugene.data.item.MenuItem;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.eugene.data.item.MenuItemsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MenuItemsLocalDataSource implements MenuItemsDataSource {

    private final Executor executor;
    private final MenuItemsDao menuItemsDao;
    private final MenuCategoriesDao menuCategoriesDao;

    private MenuItemsLocalDataSource(Executor executor, MenuItemsDao menuItemsDao,
                                     MenuCategoriesDao menuCategoriesDao) {
        this.executor = executor;
        this.menuItemsDao = menuItemsDao;
        this.menuCategoriesDao = menuCategoriesDao;
    }

    public static MenuItemsLocalDataSource newInstance(Executor executor, MenuItemsDao menuItemsDao,
                                                       MenuCategoriesDao menuCategoriesDao) {
        return new MenuItemsLocalDataSource(executor, menuItemsDao, menuCategoriesDao);
    }

    @Override
    public void addNewMenuItem(MenuItem menuItem) {
        executor.execute(() -> menuItemsDao.insertAll(menuItem));
    }

    @Override
    public void getMenuItemsMainInfo(String category, LoadMenuItemsMainInfoCallback callback) {
        executor.execute(() -> {
            final List<MenuItemMainInfo> items = menuItemsDao.getMenuItemsMainInfo(category);
            if (!items.isEmpty()) {
                callback.onMenuItemsLoaded(items);
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getMenuItemByTitle(String title, GetMenuItemCallback callback) {
        executor.execute(() -> {
            final MenuItem item = menuItemsDao.getMenuItemByTitle(title);
            if (item != null) {
                callback.onMenuItemLoaded(item);
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllMenuItems() {
        executor.execute(() -> {
            menuItemsDao.deleteAll();
        });
    }

    @Override
    public void addMenuCategories(ArrayList<MenuCategory> categories) {
        MenuCategory[] categoriesArray = new MenuCategory[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoriesArray[i] = categories.get(i);
        }
        executor.execute(() -> { menuCategoriesDao.insertAll(categoriesArray); });
    }

    @Override
    public void getMenuCategories(LoadMenuCategoriesCallback callback) {
        executor.execute(() -> {
            final List<MenuCategory> categories = menuCategoriesDao.getAll();
            if (!categories.isEmpty()) {
                callback.onMenuCategoriesLoaded(categories);
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllCategories() {
        executor.execute(() -> {
            menuCategoriesDao.deleteAll();
        });
    }
}
