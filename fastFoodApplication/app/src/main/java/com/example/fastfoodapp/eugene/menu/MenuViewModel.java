package com.example.fastfoodapp.eugene.menu;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fastfoodapp.eugene.data.MenuItemsDataSource;
import com.example.fastfoodapp.eugene.data.MenuItemsLocalDataSource;
import com.example.fastfoodapp.eugene.data.category.MenuCategory;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuViewModel {
    public static final String TAG = "MenuViewModel";

    public static ObservableList<MenuCategory> menuCategories = new ObservableArrayList<>();

    public ObservableList<MenuPageFragment> menuPageFragments = new ObservableArrayList<>();

    private final MenuItemsLocalDataSource mDataSource;

    private ShoppingCartNavigator mNavigator;

    public MenuViewModel(MenuItemsLocalDataSource dataSource) {
        mDataSource = dataSource;
    }

    private void loadMenuPages(List<MenuCategory> categories) {
        ArrayList<MenuPageFragment> fragments = new ArrayList<>();

        for (MenuCategory category : categories) {
            MenuPageFragment fragment = new MenuPageFragment();
            MenuPageViewModel viewModel = new MenuPageViewModel(mDataSource);
            viewModel.setMenuCategory(category.getCategory());
            fragment.setViewModel(viewModel);

            fragments.add(fragment);
        }
        menuPageFragments.clear();
        menuPageFragments.addAll(fragments);
    }

    public void start() {
        mDataSource.getMenuCategories(new MenuItemsDataSource.LoadMenuCategoriesCallback() {
            @Override
            public void onMenuCategoriesLoaded(List<MenuCategory> categories) {
                menuCategories.clear();
                menuCategories.addAll(categories);

                loadMenuPages(categories);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "Data not available");
            }
        });
    }

    @BindingAdapter("bind:pages")
    public static void setAdapter(ViewPager2 viewPager, ObservableList<MenuPageFragment> fragments) {
        MenuFragment.ViewPagerAdapter pagerAdapter = (MenuFragment.ViewPagerAdapter) viewPager.getAdapter();
        if (pagerAdapter != null) {
            if (fragments != null) {
                pagerAdapter.replaceData(new ArrayList<>(fragments));
            }
        }
    }

    @BindingAdapter("bind:pager")
    public static void setupTabs(TabLayout tabLayout, ViewPager2 viewPager) {
        if (viewPager != null && viewPager.getAdapter() != null) {
            TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager,
                    ((tab, position) -> tab.setText(menuCategories.get(position).getCategory())));
            mediator.attach();
        }
    }

    public void setShoppingCartNavigator(ShoppingCartNavigator navigator) {
        mNavigator = navigator;
    }

    public void openOrderPayingActivity() {
        if (mNavigator != null) {
            // TODO: need to find a better way of passing data between activities
            mNavigator.openOrderSummaryActivity(formOrder());
        }
    }

    private HashMap<MenuItemMainInfo, Integer> formOrder() {
        HashMap<MenuItemMainInfo, Integer> selectedItems = new HashMap<>();

        for (MenuPageFragment fragment : menuPageFragments) {
            MenuPageViewModel pageViewModel = fragment.getViewModel();

            if (pageViewModel.getViewModels() != null) {
                for (MenuItemViewModel item : pageViewModel.getViewModels()) {
                    if (item.mQuantity.get() > 0) {
                        selectedItems.put(item.mMenuItem.get(), item.mQuantity.get());
                    }
                }
            }
        }
        return  selectedItems;
    }
}
