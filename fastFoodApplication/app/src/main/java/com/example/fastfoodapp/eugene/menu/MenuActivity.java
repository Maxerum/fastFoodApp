package com.example.fastfoodapp.eugene.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.Maxerum.OrderPayingActivity;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.userprofile.UserProfileActivity;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.eugene.util.ActivityUtils;

import java.util.HashMap;


public class MenuActivity extends AppCompatActivity implements ShoppingCartNavigator,
        UserProfileNavigator{

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
            container.dataSource.prepopulateDatabaseIfNeeded();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), MenuViewModel.TAG);

            return viewModel;
        }
    }

    @Override
    public void openOrderPayingActivity(HashMap<MenuItemMainInfo, Integer> selectedItems) {
        Intent intent = new Intent(this, OrderPayingActivity.class);

        Bundle args = new Bundle();
        args.putSerializable("selected items", selectedItems);
        intent.putExtras(args);

        startActivity(intent);
    }

    @Override
    public void openUserProfileActivity() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
