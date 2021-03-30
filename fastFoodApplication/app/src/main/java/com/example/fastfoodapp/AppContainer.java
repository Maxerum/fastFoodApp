package com.example.fastfoodapp;

import android.content.Context;

import com.example.fastfoodapp.eugene.data.MenuItemsDatabase;
import com.example.fastfoodapp.eugene.data.MenuItemsLocalDataSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppContainer {

    private final MenuItemsDatabase database;

    public AppContainer(Context context) {
        database = MenuItemsDatabase.newInstance(context);
        dataSource = MenuItemsLocalDataSource.newInstance(executorService, database.menuItemsDao(),
                database.menuCategoryDao());
    }

    public final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public final MenuItemsLocalDataSource dataSource;
}
