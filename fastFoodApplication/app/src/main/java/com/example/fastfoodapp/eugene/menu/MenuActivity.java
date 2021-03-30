package com.example.fastfoodapp.eugene.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.Maxerum.OrderPayingActivity;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.data.MenuItemsDatabase;
import com.example.fastfoodapp.eugene.data.MenuItemsLocalDataSource;
import com.example.fastfoodapp.eugene.data.category.MenuCategories;
import com.example.fastfoodapp.eugene.data.category.MenuCategory;
import com.example.fastfoodapp.eugene.data.item.MenuItem;
import com.example.fastfoodapp.eugene.util.ActivityUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuActivity extends AppCompatActivity implements ShoppingCartNavigator {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        MenuFragment fragment = findOrCreateFragment();

        MenuViewModel viewModel = findOrCreateViewModel();
        viewModel.setShoppingCartNavigator(this);
        fragment.setViewModel(viewModel);
    }

    private MenuFragment findOrCreateFragment() {
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (menuFragment == null) {

            menuFragment = MenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), menuFragment,
                    R.id.fragment_container);
        }
        return menuFragment;
    }

    private MenuViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<MenuViewModel> holder = (ViewModelHolder<MenuViewModel>)
                getSupportFragmentManager().findFragmentByTag(MenuViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {

            AppContainer container = ((FastFoodApp) getApplication()).appContainer;
            MenuViewModel viewModel = new MenuViewModel(container.dataSource);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), MenuViewModel.TAG);

            return viewModel;
        }
    }

    @Override
    public void openOrderPayingActivity() {
        Intent intent = new Intent(this, OrderPayingActivity.class);
        startActivity(intent);
    }
}
