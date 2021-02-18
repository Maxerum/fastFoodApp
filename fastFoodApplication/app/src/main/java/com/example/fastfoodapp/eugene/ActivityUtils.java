package com.example.fastfoodapp.eugene.menu;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment, int containerId) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }

    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commit();
    }
}
