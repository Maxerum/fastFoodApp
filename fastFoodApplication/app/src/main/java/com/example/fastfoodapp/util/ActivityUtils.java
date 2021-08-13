package com.example.fastfoodapp.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fastfoodapp.R;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment, int containerId,
                                             boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.fade_out)
                .replace(containerId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public static void addFragmentToActivity(FragmentManager manager, Fragment fragment, String tag,
                                             boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment, tag)
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.fade_out);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
