package com.example.fastfoodapp.eugene.menu;

import android.content.Context;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MenuViewModel {
    public static final String TAG = "MenuViewModel";

    private final Context mContext;

    private ArrayList<String> menuCategories;

    private ShoppingCartNavigator mNavigator;

    public MenuViewModel(Context context) {
        mContext = context;

        menuCategories = new ArrayList<>();
    }

    private static ArrayList<String> getMenuCategoriesList() {
        ArrayList<String> categoriesList = new ArrayList<>();
        categoriesList.add("Burgers");
        categoriesList.add("Snacks & Sides");
        categoriesList.add("Beverages");
        categoriesList.add("Desserts & Shakes");
        categoriesList.add("Breakfast");
        return categoriesList;
    }

    public ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MenuPageFragment fragment = new MenuPageFragment();
            MenuPageViewModel viewModel = new MenuPageViewModel(mContext);
            fragment.setViewModel(viewModel);

            fragments.add(fragment);
        }
        return fragments;
    }

    @BindingAdapter("bind:handler")
    public static void setAdapter(ViewPager2 viewPager, MenuViewModel viewModel) {
        ((MenuFragment.ViewPagerAdapter) viewPager.getAdapter()).replaceData(viewModel.getFragmentList());
    }

    @BindingAdapter("bind:pager")
    public static void setupTabs(TabLayout tabLayout, ViewPager2 viewPager) {
        if (viewPager != null && viewPager.getAdapter() != null) {
            ArrayList<String> categories = MenuViewModel.getMenuCategoriesList();
            TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager,
                    ((tab, position) -> tab.setText(categories.get(position))));
            mediator.attach();
        }
    }

    public void setShoppingCartNavigator(ShoppingCartNavigator navigator) {
        mNavigator = navigator;
    }

    public void openOrderPayingActivity() {
        if (mNavigator != null) {
            mNavigator.openOrderPayingActivity();
        }
    }
}
