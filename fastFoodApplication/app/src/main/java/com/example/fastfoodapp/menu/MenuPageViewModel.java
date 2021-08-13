package com.example.fastfoodapp.menu;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.data.MenuItemsDataSource;
import com.example.fastfoodapp.data.MenuItemsLocalDataSource;
import com.example.fastfoodapp.data.item.MenuItemMainInfo;

import java.util.ArrayList;
import java.util.List;

public class MenuPageViewModel {
    public static final String TAG = "MenuPageViewModel";

    private String menuCategory;

    private final MenuItemsLocalDataSource mDataSource;

    public final ObservableArrayList<MenuItemMainInfo> mItems = new ObservableArrayList<>();

    private ArrayList<MenuItemViewModel> mMenuItemViewModels;

    public MenuPageViewModel(MenuItemsLocalDataSource dataSource) {
        mDataSource = dataSource;
    }

    public void start() {
        loadData();
    }

    public void setMenuCategory(String category) {
        menuCategory = category;
    }

    public void loadData() {
        mDataSource.getMenuItemsMainInfo(menuCategory, new MenuItemsDataSource.LoadMenuItemsMainInfoCallback() {
            @Override
            public void onMenuItemsLoaded(List<MenuItemMainInfo> items) {
                mItems.clear();
                mItems.addAll(items);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "Data not available");
            }
        });
    }

    public void setViewModels(ArrayList<MenuItemViewModel> viewModels) {
        mMenuItemViewModels = viewModels;
    }

    public ArrayList<MenuItemViewModel> getViewModels() {
        return mMenuItemViewModels;
    }

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, ArrayList<MenuItemMainInfo> items) {
        MenuPageFragment.MenuItemsAdapter adapter = (MenuPageFragment.MenuItemsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items, null);
        }
    }
}
