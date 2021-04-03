package com.example.fastfoodapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.fastfoodapp.eugene.data.MenuItemsDatabase;
import com.example.fastfoodapp.eugene.data.MenuItemsLocalDataSource;
import com.example.fastfoodapp.eugene.data.UsersRemoteDataSource;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppContainer {

    private final MenuItemsDatabase database;

    private final FirebaseFirestore firestoreDb;

    public AppContainer(Context context) {
        database = MenuItemsDatabase.newInstance(context);
        firestoreDb = FirebaseFirestore.getInstance();

        dataSource = MenuItemsLocalDataSource.newInstance(executorService, database.menuItemsDao(),
                database.menuCategoryDao());
        usersDataSource = new UsersRemoteDataSource(firestoreDb);
    }

    public final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public final MenuItemsLocalDataSource dataSource;

    public final UsersRemoteDataSource usersDataSource;
}
